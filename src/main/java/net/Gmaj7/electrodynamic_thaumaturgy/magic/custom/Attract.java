package net.Gmaj7.electrodynamic_thaumaturgy.magic.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.AttractBeaconEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class Attract extends AbstractBlockBeaconMagic {

    @Override
    public void playerCast(Player player, ItemStack itemStack, MagicDefinition magicDefinition) {
        BlockHitResult blockHitResult = getBlock(player);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = blockPos.getCenter();
        AttractBeaconEntity attractBeaconEntity = new AttractBeaconEntity(player.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), player);
        attractBeaconEntity.setLiveTime((int) MoeFunction.getMagicAmount(itemStack) * 10);
        player.level().addFreshEntity(attractBeaconEntity);
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack, MagicDefinition magicDefinition) {
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        AttractBeaconEntity attractBeaconEntity = new AttractBeaconEntity(target.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), source);
        attractBeaconEntity.setLiveTime((int) MoeFunction.getMagicAmount(itemStack) * 10);
        target.level().addFreshEntity(attractBeaconEntity);
    }

    protected BlockHitResult getBlock(LivingEntity livingEntity){
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(8).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.attract_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition magicDefinition) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        BlockPos blockPos = target.getOnPos();
        if(!electromagneticDriverBE.extract(magicDefinition.baseEnergyCost())) return;
        Vec3 vec3 = blockPos.getCenter();
        AttractBeaconEntity attractBeaconEntity = new AttractBeaconEntity(target.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), (LivingEntity) electromagneticDriverBE.getOwner());
        attractBeaconEntity.setLiveTime((int) MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem) * 10);
        target.level().addFreshEntity(attractBeaconEntity);
        electromagneticDriverBE.setCooldown(magicDefinition.baseCooldown());
    }
}
