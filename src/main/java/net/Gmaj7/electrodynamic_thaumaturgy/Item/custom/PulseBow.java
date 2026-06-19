package net.Gmaj7.electrodynamic_thaumaturgy.Item.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.Entity.custom.PulseArrowEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.Item.EtItems;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PulseBow extends Item {
    private static int shootUse = 1024;
    public PulseBow(Properties properties) {
        super(properties);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return 72000;
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int remainingTime) {
        if (entity instanceof Player player) {
            float damage = Function.getDamageAmount(itemStack);
            PulseArrowEntity arrow = new PulseArrowEntity(level, entity, damage, itemStack.getOrDefault(EtDataComponentTypes.BOW_WORK_PATTERN, 0));
            arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 5 * getPowerForTime(this.getUseDuration(itemStack, entity) - remainingTime), 0.0F);
            level.addFreshEntity(arrow);
            try (Transaction transaction = Transaction.openRoot()){
                itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack)).extract(shootUse, transaction);
                transaction.commit();
            }
            return true;
        }
        else {
            return false;
        }
    }

    public static float getPowerForTime(int timeHeld) {
        float pow = (float)timeHeld / 20.0F;
        pow = (pow * pow + pow * 2.0F) / 3.0F;
        if (pow > 1.0F) {
            pow = 1.0F;
        }

        return pow;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(itemStack.getItem() instanceof PulseBow && itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack)).getCapacityAsInt() > shootUse)
            player.startUsingItem(hand);
        return InteractionResult.CONSUME;
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

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        EnergyHandler energyHandler = itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack));
        int i = energyHandler.getAmountAsInt(),j = energyHandler.getCapacityAsInt();
        builder.accept(Component.translatable("moe_show_energy").append(i + " FE / " + j + " FE"));
    }

    private static void setEmptyContainer(ItemStack itemStack) {
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(EtItems.EMPTY_POWER.get()));
        list.add(new ItemStack(EtItems.EMPTY_LC.get()));
        itemStack.set(EtDataComponentTypes.ET_CONTAINER.get(), ItemContainerContents.fromItems(list));
    }
}
