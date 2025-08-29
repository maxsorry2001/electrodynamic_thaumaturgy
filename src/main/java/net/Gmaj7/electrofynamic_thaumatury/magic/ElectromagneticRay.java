package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastMachineBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.MoeRayEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ElectromagneticRay extends AbstractWideMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        Level level = livingEntity.level();
        MoeFunction.RayHitResult hitResult = MoeFunction.getLineHitResult(level, livingEntity, start, end, true, 0.5F);
        MoeRayEntity moeRayEntity = new MoeRayEntity(level, start, hitResult.getEnd(), livingEntity, true);
        level.addFreshEntity(moeRayEntity);
        for (HitResult result : hitResult.getTargets()) {
            if (result instanceof EntityHitResult) {
                Entity target = ((EntityHitResult) result).getEntity();
                if (target instanceof LivingEntity) {
                    target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), livingEntity), MoeFunction.getMagicAmount(itemStack));
                    MoeFunction.checkTargetEnhancement(itemStack, (LivingEntity) target);
                }
            }
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        Vec3 start = new Vec3(source.getX(), source.getY() + 1, source.getZ());
        Vec3 end = new Vec3(target.getX(), (target.getY() + target.getEyeY()) / 2, target.getZ());
        Level level = source.level();
        MoeFunction.RayHitResult hitResult = MoeFunction.getLineHitResult(level, source, start, end, true, 0.5F);
        MoeRayEntity moeRayEntity = new MoeRayEntity(level, start, end, source, false);
        level.addFreshEntity(moeRayEntity);
        for (HitResult result : hitResult.getTargets()) {
            if (result instanceof EntityHitResult) {
                Entity entity = ((EntityHitResult) result).getEntity();
                if (entity instanceof LivingEntity) {
                    entity.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), source), MoeFunction.getMagicAmount(MagicCastMachineBE.magicItem));
                    MoeFunction.checkTargetEnhancement(MagicCastMachineBE.magicItem, (LivingEntity) target);
                }
            }
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 128;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }

    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.ray_module";
    }

    @Override
    public void blockCast(MagicCastMachineBE magicCastMachineBE) {
        List<LivingEntity> list = magicCastMachineBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(magicCastMachineBE.getBlockPos()).inflate(7));
        list.remove(magicCastMachineBE.getOwner());
        if(list.isEmpty()) return;
        LivingEntity lineTarget = list.get(RandomSource.create().nextInt(list.size()));
        Vec3 start = new Vec3(magicCastMachineBE.getBlockPos().getX(), magicCastMachineBE.getBlockPos().getY() + 1, magicCastMachineBE.getBlockPos().getZ());
        Vec3 end = new Vec3(lineTarget.getX(), (lineTarget.getY() + lineTarget.getEyeY()) / 2, lineTarget.getZ());
        Level level = magicCastMachineBE.getLevel();
        MoeFunction.RayHitResult hitResult = MoeFunction.getBlockLineHitResult(level, magicCastMachineBE, start, end, true, 0.5F);
        MoeRayEntity moeRayEntity = new MoeRayEntity(level, start, end, (LivingEntity) magicCastMachineBE.getOwner(), false);
        level.addFreshEntity(moeRayEntity);
        for (HitResult result : hitResult.getTargets()) {
            if (result instanceof EntityHitResult) {
                Entity target = ((EntityHitResult) result).getEntity();
                if (target instanceof LivingEntity) {
                    target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), magicCastMachineBE.getOwner()), MoeFunction.getMagicAmount(MagicCastMachineBE.magicItem));
                    MoeFunction.checkTargetEnhancement(MagicCastMachineBE.magicItem, (LivingEntity) target);
                }
            }
        }
        magicCastMachineBE.setCooldown(getBaseCooldown());
        magicCastMachineBE.extractEnergy(getBaseEnergyCost());
    }
}
