package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MoeRayEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.PlasmaEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class MagicUseItem extends Item {
    public MagicUseItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);
        if(livingEntity instanceof Player player){
            player.getCooldowns().addCooldown(stack.getItem(), 10);
        }

    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        int energy = stack.get(MoeDataComponentTypes.MOE_ENERGY.get());
        if(energy < 100)
            livingEntity.stopUsingItem();
        else if(remainingUseDuration % 5 == 0){
            Vec3 start = livingEntity.getEyePosition().subtract(0,0.25, 0);
            Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
            MoeFunction.RayHitResult hitResult = MoeFunction.getRayHitResult(level, livingEntity, start, end, true, 0.15F);
            MoeRayEntity moeRayEntity = new MoeRayEntity(level, start, hitResult.getEnd(), livingEntity);
            level.addFreshEntity(moeRayEntity);
            stack.set(MoeDataComponentTypes.MOE_ENERGY.get(), energy - 100);
            for (Entity target : hitResult.getTargets()){
                if(target instanceof LivingEntity){
                    target.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC), livingEntity), 2);
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        int Type = itemStack.get(MoeDataComponentTypes.ELECTROMAGNETIC_MAGIC_TYPE.get());
        int energy = itemStack.get(MoeDataComponentTypes.MOE_ENERGY.get());
        if (Type == 1 && energy > 5){
            player.startUsingItem(usedHand);
        }
        else if (Type == 2 && energy > 600){
            PlasmaEntity plasmaEntity = new PlasmaEntity(player, level);
            itemStack.set(MoeDataComponentTypes.MOE_ENERGY.get(), energy - 600);
            plasmaEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 5, 0);
            level.addFreshEntity(plasmaEntity);
            player.getCooldowns().addCooldown(itemStack.getItem(), 10);
        }
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        int type = stack.get(MoeDataComponentTypes.ELECTROMAGNETIC_MAGIC_TYPE.get());
        Component typeLiteral = null;
        switch (type){
            case 0 -> typeLiteral = Component.translatable("moe_no_magic");
            case 1 -> typeLiteral = Component.translatable("moe_ray");
            case 2 -> typeLiteral = Component.translatable("moe_plasma");
        }
        tooltipComponents.add(typeLiteral);
        IEnergyStorage energyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        int i = energyStorage.getEnergyStored(),j = energyStorage.getMaxEnergyStored();
        if(i < j){
            tooltipComponents.add(Component.translatable("moe_show_energy").append(i + " FE / " + j + " FE"));
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored() < stack.getCapability(Capabilities.EnergyStorage.ITEM).getMaxEnergyStored();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int i = stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored();
        int stackMaxEnergy = stack.getCapability(Capabilities.EnergyStorage.ITEM).getMaxEnergyStored();
        return Math.round(13.0F - (stackMaxEnergy - i) * 13.0F / stackMaxEnergy);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int i = stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored();
        int stackMaxEnergy = stack.getCapability(Capabilities.EnergyStorage.ITEM).getMaxEnergyStored();
        float f = Math.max(0.0F, (float) i / stackMaxEnergy);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }
}
