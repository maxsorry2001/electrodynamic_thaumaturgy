package net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.MagneticRecombinationCannonBeaconEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.MagicDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class MagneticRecombinationCannon extends AbstractBlockBeaconMagic {

    @Override
    public void playerCast(Player livingEntity, ItemStack itemStack, MagicDefinition magicDefinition) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = blockPos.getCenter();
        MagneticRecombinationCannonBeaconEntity magneticRecombinationCannonBeaconEntity = new MagneticRecombinationCannonBeaconEntity(livingEntity.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), itemStack, livingEntity);
        livingEntity.level().addFreshEntity(magneticRecombinationCannonBeaconEntity);
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack, MagicDefinition magicDefinition) {
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        MagneticRecombinationCannonBeaconEntity magneticRecombinationCannonBeaconEntity = new MagneticRecombinationCannonBeaconEntity(source.level(),vec3.x(), blockPos.getY() + 1, vec3.z(), ElectromagneticDriverBE.magicItem, source);
        source.level().addFreshEntity(magneticRecombinationCannonBeaconEntity);
    }

    protected BlockHitResult getBlock(LivingEntity livingEntity){
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(8).add(start);
        return Function.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.magnetic_recombination_cannon_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition magicDefinition) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        if(!electromagneticDriverBE.extract(magicDefinition.baseEnergyCost())) return;
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        MagneticRecombinationCannonBeaconEntity magneticRecombinationCannonBeaconEntity = new MagneticRecombinationCannonBeaconEntity(electromagneticDriverBE.getLevel(), vec3.x(), blockPos.getY() + 1, vec3.z(), ElectromagneticDriverBE.magicItem, (LivingEntity) electromagneticDriverBE.getOwner());
        electromagneticDriverBE.getLevel().addFreshEntity(magneticRecombinationCannonBeaconEntity);
        electromagneticDriverBE.setCooldown(magicDefinition.baseCooldown());
    }
}
