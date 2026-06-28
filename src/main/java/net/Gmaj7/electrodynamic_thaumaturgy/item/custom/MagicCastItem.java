package net.Gmaj7.electrodynamic_thaumaturgy.item.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.EnhancementData;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.ItemContainerData;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinitionLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.function.Consumer;

public class MagicCastItem extends Item {
    private static final int maxMagicSlots = 10;
    private static final int magicBaseSlots = 2;
    private static final int powerNum = 0;
    private static final int lcNum = 1;
    public MagicCastItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        ItemStack typeStack = getMagic(itemStack);
        EnergyHandler energyHandler = itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack));
        MagicDefinition magicDefinition = MagicDefinitionLoader.get(typeStack.get(EtDataComponentTypes.MAGIC_DEF_LOCATION));
        if(typeStack.getItem() instanceof EtMagicTypeModuleItem item && !item.isEmpty()
                && energyHandler.getAmountAsInt() >= magicDefinition.baseEnergyCost() && !player.getCooldowns().isOnCooldown(typeStack)
                && !(usedHand == InteractionHand.OFF_HAND && player.getMainHandItem().getItem() instanceof BatteryItem)
                && item.success(player, typeStack)) {
            item.cast(player, itemStack, typeStack);
            try (Transaction transaction = Transaction.openRoot()){
                int cost = (int) (magicDefinition.baseEnergyCost() * Function.getEfficiency(itemStack));
                int i = energyHandler.extract(cost, transaction);
                if(i == cost){
                    transaction.commit();
                    player.getCooldowns().addCooldown(typeStack, (int) (magicDefinition.baseCooldown() / Function.getCoolDownRate(itemStack)));
                    player.swing(usedHand);
                }
            }
            return InteractionResult.CONSUME;
        }
        else return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, display, builder, tooltipFlag);
        builder.accept(getTranslate(stack));
        EnergyHandler energyHandler = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack));
        int i = energyHandler.getAmountAsInt(),j = energyHandler.getCapacityAsInt();
        builder.accept(Component.translatable("moe_show_energy").append(i + " FE / " + j + " FE"));
        EnhancementData enhancementData = stack.get(EtDataComponentTypes.ENHANCEMENT_DATA);
        builder.accept(Component.translatable("enhance_chip.electrodynamic_thaumaturgy.cooldown_enhance").append(":" + enhancementData.coolDown()));
        builder.accept(Component.translatable("enhance_chip.electrodynamic_thaumaturgy.strength_enhance").append(":" + enhancementData.strength()));
        builder.accept(Component.translatable("enhance_chip.electrodynamic_thaumaturgy.efficiency_enhance").append(":" + enhancementData.efficiency()));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getAmountAsInt() < stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getCapacityAsInt();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int i = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getAmountAsInt();
        int stackMaxEnergy = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getCapacityAsInt();
        return Math.round(13.0F - (stackMaxEnergy - i) * 13.0F / stackMaxEnergy);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int i = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getAmountAsInt();
        int stackMaxEnergy = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getCapacityAsInt();
        float f = Math.max(0.0F, (float) i / stackMaxEnergy);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    private Component getTranslate(ItemStack itemStack){
        ItemStack typeStack = getMagic(itemStack);
        Item item = typeStack.getItem();
        if(item instanceof EtMagicTypeModuleItem && !((EtMagicTypeModuleItem) item).isEmpty()) return ((EtMagicTypeModuleItem) item).getTranslate(MagicDefinitionLoader.get(typeStack.get(EtDataComponentTypes.MAGIC_DEF_LOCATION)));
        return Component.translatable("moe_no_magic");
    }

    private ItemStack getMagic(ItemStack itemStack){
        if(itemStack.has(EtDataComponentTypes.ET_CONTAINER.get()) && itemStack.has(EtDataComponentTypes.MAGIC_SELECT)) {
            ItemContainerData contents = itemStack.getOrDefault(EtDataComponentTypes.ET_CONTAINER.get(), ItemContainerData.EMPTY);
            if(contents.isEmpty()) return ItemStack.EMPTY;
            ItemStack typeStack = contents.getStackInSlot(itemStack.get(EtDataComponentTypes.MAGIC_SELECT));
            return typeStack;
        }
        else return new ItemStack(EtItems.PRIMARY_CODE_MODULE.get());
    }

    public static int getMaxMagicSlots() {
        return maxMagicSlots;
    }

    public static int getPowerNum() {
        return powerNum;
    }

    public static int getLcNum() {
        return lcNum;
    }
}
