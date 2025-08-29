package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastMachineBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.MagmaLightingBeaconEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.MoeItems;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.Gmaj7.electrofynamic_thaumatury.MoeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class MagmaLighting extends AbstractBlockBeaconMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        Direction direction = blockHitResult.getDirection();
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = blockPos.getCenter();
        MagmaLightingBeaconEntity magmaLightingBeaconEntity = new MagmaLightingBeaconEntity(livingEntity.level(), livingEntity, itemStack);
        magmaLightingBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        magmaLightingBeaconEntity.setDirection(direction);
        livingEntity.level().addFreshEntity(magmaLightingBeaconEntity);
        if(livingEntity.level() instanceof ServerLevel) {
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_SMALL.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY(), magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_MIDDLE.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY() + 5, magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_LARGE.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY() + 10, magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_SMALL_IN.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY(), magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_MIDDLE_IN.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY() + 5, magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_LARGE_IN.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY() + 10, magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        MagmaLightingBeaconEntity magmaLightingBeaconEntity = new MagmaLightingBeaconEntity(source.level(), (LivingEntity) source, MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get()));
        magmaLightingBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        magmaLightingBeaconEntity.setDirection(Direction.UP);
        source.level().addFreshEntity(magmaLightingBeaconEntity);
        if(source.level() instanceof ServerLevel) {
            ((ServerLevel) source.level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_SMALL.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY(), magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) source.level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_MIDDLE.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY() + 5, magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) source.level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_LARGE.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY() + 10, magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) source.level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_SMALL_IN.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY(), magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) source.level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_MIDDLE_IN.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY() + 5, magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) source.level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_LARGE_IN.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY() + 10, magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 50;
    }

    @Override
    protected BlockHitResult getBlock(LivingEntity livingEntity){
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.3, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.magma_lighting_module";
    }

    @Override
    public void blockCast(MagicCastMachineBE magicCastMachineBE) {
        LivingEntity target = getBlockTarget(magicCastMachineBE);
        if(target == null) return;
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        MagmaLightingBeaconEntity magmaLightingBeaconEntity = new MagmaLightingBeaconEntity(magicCastMachineBE.getLevel(), (LivingEntity) magicCastMachineBE.getOwner(), MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get()));
        magmaLightingBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        magmaLightingBeaconEntity.setDirection(Direction.UP);
        magicCastMachineBE.getLevel().addFreshEntity(magmaLightingBeaconEntity);
        if(magicCastMachineBE.getLevel() instanceof ServerLevel) {
            ((ServerLevel) magicCastMachineBE.getLevel()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_SMALL.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY(), magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) magicCastMachineBE.getLevel()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_MIDDLE.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY() + 5, magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) magicCastMachineBE.getLevel()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_LARGE.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY() + 10, magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) magicCastMachineBE.getLevel()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_SMALL_IN.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY(), magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) magicCastMachineBE.getLevel()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_MIDDLE_IN.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY() + 5, magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) magicCastMachineBE.getLevel()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_LARGE_IN.get(), magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY() + 10, magmaLightingBeaconEntity.getZ(), 1, 0, 0, 0, 0);
        }
        magicCastMachineBE.setCooldown(getBaseCooldown());
        magicCastMachineBE.extractEnergy(getBaseEnergyCost());
    }
}
