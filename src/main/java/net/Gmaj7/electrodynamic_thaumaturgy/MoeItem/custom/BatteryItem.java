package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class BatteryItem extends Item {
    public BatteryItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if(remainingUseDuration % 5 == 0) {
            EnergyHandler energyHandler1 = livingEntity.getOffhandItem().getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack));
            EnergyHandler energyHandler2 = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack));
            if (energyHandler2.getEnergyStored() > 0 && energyHandler1.getEnergyStored() < energyHandler1.getMaxEnergyStored()) {
                energyHandler1.receiveEnergy(4096, false);
                energyHandler2.extractEnergy(4096, false);
            } else livingEntity.stopUsingItem();
        }
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        if (usedHand == InteractionHand.MAIN_HAND && player.getOffhandItem().getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)) != null
                && player.getOffhandItem().getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).canReceive()
                && player.getMainHandItem().getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getEnergyStored() > 0){
            player.startUsingItem(usedHand);
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        EnergyHandler energyStorage = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack));
        if(energyStorage.getEnergyStored() <= 0 && !stack.is(MoeItems.POWER_BANK.get())){
            level.addFreshEntity(new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.COPPER_INGOT)));
            if(stack.is(MoeItems.SOLUTION_BATTERY.get()))
                level.addFreshEntity(new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.GLASS_BOTTLE)));
            stack.shrink(1);
        }
        super.inventoryTick(stack, level, entity, slot);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getEnergyStored() < stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getMaxEnergyStored();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int i = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getEnergyStored();
        int stackMaxEnergy = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getMaxEnergyStored();
        return Math.round(13.0F - (stackMaxEnergy - i) * 13.0F / stackMaxEnergy);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int i = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getEnergyStored();
        int stackMaxEnergy = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getMaxEnergyStored();
        float f = Math.max(0.0F, (float) i / stackMaxEnergy);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        EnergyHandler energyStorage = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack));
        int i = energyStorage.getEnergyStored(),j = energyStorage.getMaxEnergyStored();
        tooltipComponents.add(Component.translatable("moe_show_energy").append(i + " FE / " + j + " FE"));
    }
}
