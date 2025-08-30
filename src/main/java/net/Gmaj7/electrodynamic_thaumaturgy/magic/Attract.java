package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.MagicCastMachineBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.AttractBeaconEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class Attract extends AbstractBlockBeaconMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = blockPos.getCenter();
        AttractBeaconEntity attractBeaconEntity = new AttractBeaconEntity(livingEntity.level(), livingEntity);
        attractBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        attractBeaconEntity.setLiveTime((int) MoeFunction.getMagicAmount(itemStack) * 10);
        livingEntity.level().addFreshEntity(attractBeaconEntity);
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        AttractBeaconEntity attractBeaconEntity = new AttractBeaconEntity(target.level(), source);
        attractBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        attractBeaconEntity.setLiveTime((int) MoeFunction.getMagicAmount(itemStack) * 10);
        target.level().addFreshEntity(attractBeaconEntity);
    }

    @Override
    public int getBaseEnergyCost() {
        return 256;
    }

    @Override
    public int getBaseCooldown() {
        return 300;
    }

    protected BlockHitResult getBlock(LivingEntity livingEntity){
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.attract_module";
    }

    @Override
    public void blockCast(MagicCastMachineBE magicCastMachineBE) {
        LivingEntity target = getBlockTarget(magicCastMachineBE);
        if(target == null) return;
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        AttractBeaconEntity attractBeaconEntity = new AttractBeaconEntity(target.level(), (LivingEntity) magicCastMachineBE.getOwner());
        attractBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        attractBeaconEntity.setLiveTime((int) MoeFunction.getMagicAmount(MagicCastMachineBE.magicItem) * 10);
        target.level().addFreshEntity(attractBeaconEntity);
        magicCastMachineBE.setCooldown(getBaseCooldown());
        magicCastMachineBE.extractEnergy(getBaseEnergyCost());
    }
}
