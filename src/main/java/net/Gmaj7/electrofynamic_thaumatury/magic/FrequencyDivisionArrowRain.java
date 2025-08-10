package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.FrequencyDivisionBeaconEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class FrequencyDivisionArrowRain extends AbstractBlockBeaconMagic {
    @Override
    protected BlockHitResult getBlock(LivingEntity livingEntity) {
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.3, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        Vec3 vec3 = blockHitResult.getBlockPos().getCenter();
        FrequencyDivisionBeaconEntity frequencyDivisionArrowEntity = new FrequencyDivisionBeaconEntity(livingEntity.level(), vec3.x(), vec3.y(), vec3.z(), itemStack, livingEntity);
        livingEntity.level().addFreshEntity(frequencyDivisionArrowEntity);
        if(livingEntity.level() instanceof ServerLevel)
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.FREQUENCY_DIVISION_ARROW_RAIN_PARTICLE.get(), vec3.x(),  vec3.y() + 7, vec3.z(), 1, 0, 0, 0, 0);
    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 100;
    }

    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.frequency_division_arrow_rain_module";
    }
}
