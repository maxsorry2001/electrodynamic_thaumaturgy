package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastBlockBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.AttractBeaconEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class Attract extends AbstractBlockBeaconMagic {

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = blockPos.getCenter();
        AttractBeaconEntity attractBeaconEntity = new AttractBeaconEntity(livingEntity.level(), livingEntity);
        attractBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        attractBeaconEntity.setLiveTime((int) MoeFunction.getMagicAmount(itemStack) * 10);
        livingEntity.level().addFreshEntity(attractBeaconEntity);
    }

    @Override
    public int getBaseEnergyCost() {
        return 256;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }

    protected BlockHitResult getBlock(LivingEntity livingEntity){
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.attract_module";
    }

    @Override
    public void blockCast(MagicCastBlockBE magicCastBlockBE) {
        LivingEntity target = getBlockTarget(magicCastBlockBE);
        if(target == null) return;
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        AttractBeaconEntity attractBeaconEntity = new AttractBeaconEntity(target.level(), (LivingEntity) magicCastBlockBE.getOwner());
        attractBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        attractBeaconEntity.setLiveTime((int) MoeFunction.getMagicAmount(MagicCastBlockBE.magicItem) * 10);
        target.level().addFreshEntity(attractBeaconEntity);
        magicCastBlockBE.setCooldown(getBaseCooldown());
        magicCastBlockBE.extractEnergy(getBaseEnergyCost());
    }
}
