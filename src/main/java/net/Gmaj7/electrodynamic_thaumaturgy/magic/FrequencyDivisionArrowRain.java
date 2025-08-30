package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.FrequencyDivisionBeaconEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
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
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        Vec3 vec3 = blockHitResult.getBlockPos().getCenter();
        FrequencyDivisionBeaconEntity frequencyDivisionArrowEntity = new FrequencyDivisionBeaconEntity(livingEntity.level(), vec3.x(), vec3.y(), vec3.z(), itemStack, livingEntity);
        livingEntity.level().addFreshEntity(frequencyDivisionArrowEntity);
        if(livingEntity.level() instanceof ServerLevel)
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.FREQUENCY_DIVISION_ARROW_RAIN_PARTICLE.get(), vec3.x(),  vec3.y() + 11, vec3.z(), 1, 0, 0, 0, 0);
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        Vec3 vec3 = target.getOnPos().getCenter();
        FrequencyDivisionBeaconEntity frequencyDivisionArrowEntity = new FrequencyDivisionBeaconEntity(source.level(), vec3.x(), vec3.y(), vec3.z(), ElectromagneticDriverBE.magicItem, source);
        source.level().addFreshEntity(frequencyDivisionArrowEntity);
        if(source.level() instanceof ServerLevel)
            ((ServerLevel) source.level()).sendParticles(MoeParticles.FREQUENCY_DIVISION_ARROW_RAIN_PARTICLE.get(), vec3.x(),  vec3.y() + 11, vec3.z(), 1, 0, 0, 0, 0);
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
        return "item.electrodynamic_thaumaturgy.frequency_division_arrow_rain_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        Vec3 vec3 = target.getOnPos().getCenter();
        FrequencyDivisionBeaconEntity frequencyDivisionArrowEntity = new FrequencyDivisionBeaconEntity(electromagneticDriverBE.getLevel(), vec3.x(), vec3.y(), vec3.z(), ElectromagneticDriverBE.magicItem, (LivingEntity) electromagneticDriverBE.getOwner());
        electromagneticDriverBE.getLevel().addFreshEntity(frequencyDivisionArrowEntity);
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
        if(electromagneticDriverBE.getLevel() instanceof ServerLevel)
            ((ServerLevel) electromagneticDriverBE.getLevel()).sendParticles(MoeParticles.FREQUENCY_DIVISION_ARROW_RAIN_PARTICLE.get(), vec3.x(),  vec3.y() + 11, vec3.z(), 1, 0, 0, 0, 0);
    }
}
