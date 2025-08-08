package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.FrequencyDivisionArrowEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
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
        FrequencyDivisionArrowEntity frequencyDivisionArrowEntity = new FrequencyDivisionArrowEntity(livingEntity.level(), vec3.x(), vec3.y(), vec3.z(), itemStack, livingEntity);
        frequencyDivisionArrowEntity.shoot(0, 1, 0, 2, 0);
        livingEntity.level().addFreshEntity(frequencyDivisionArrowEntity);
    }

    @Override
    public int getBaseEnergyCost() {
        return 0;
    }

    @Override
    public int getBaseCooldown() {
        return 0;
    }

    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.frequency_division_arrow_rain";
    }
}
