package net.Gmaj7.electrodynamic_thaumaturgy.item.custom.weapon;

import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.ItemContainerData;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.ElectromagneticTierItem;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;

import java.util.function.Consumer;

public abstract class ElectromagneticWeaponItem extends Item {
    protected static final int powerNum = 0;
    protected static final int lcNum = 1;
    public ElectromagneticWeaponItem(Properties properties) {
        super(properties);
    }

    protected void changeModule(Level level, Player player, int changeSlot){
        ItemContainerData oldData = player.getOffhandItem().get(EtDataComponentTypes.ET_CONTAINER), data = oldData.getNewWithSlot(changeSlot, ItemStackTemplate.fromNonEmptyStack(player.getMainHandItem()));
        ItemStack changedStack = oldData.getStackInSlot(changeSlot);
        player.getOffhandItem().set(EtDataComponentTypes.ET_CONTAINER, data);
        player.getMainHandItem().shrink(1);
        if(changedStack.getItem() instanceof  ElectromagneticTierItem item && !item.isEmpty())
            player.addItem(changedStack);
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


    public static int getPowerNum() {
        return powerNum;
    }

    public static int getLcNum() {
        return lcNum;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, display, builder, tooltipFlag);
        EnergyHandler energyHandler = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack));
        int i = energyHandler.getAmountAsInt(),j = energyHandler.getCapacityAsInt();
        builder.accept(Component.translatable("moe_show_energy").append(i + " FE / " + j + " FE"));
    }
}
