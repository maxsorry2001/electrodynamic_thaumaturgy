package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.PulsedPlasmaEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PulsedPlasma extends AbstractWideMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        PulsedPlasmaEntity pulsedPlasmaEntity = new PulsedPlasmaEntity(livingEntity, livingEntity.level(), itemStack);
        pulsedPlasmaEntity.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0, 5, 1.5F);
        livingEntity.level().addFreshEntity(pulsedPlasmaEntity);
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        Vec3 vec3 = new Vec3(target.getX() - source.getX(), target.getY() - source.getY() - 3, target.getZ() - source.getZ()).normalize();
        PulsedPlasmaEntity pulsedPlasmaEntity = new PulsedPlasmaEntity(source, source.level(), itemStack);
        pulsedPlasmaEntity.shootFromRotation(source, source.getXRot(), source.getYRot(), 0, 5, 1.5F);
        source.level().addFreshEntity(pulsedPlasmaEntity);
    }

    @Override
    public int getBaseEnergyCost() {
        return 128;
    }

    @Override
    public int getBaseCooldown() {
        return 40;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.pulsed_plasma_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        List<LivingEntity> list = electromagneticDriverBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(electromagneticDriverBE.getBlockPos()).inflate(7));
        list.remove(electromagneticDriverBE.getOwner());
        if(list.isEmpty()) return;
        LivingEntity target = list.get(RandomSource.create().nextInt(list.size()));
        Vec3 vec3 = new Vec3(target.getX() - electromagneticDriverBE.getBlockPos().getX(), target.getY() - electromagneticDriverBE.getBlockPos().getY() - 3, target.getZ() - electromagneticDriverBE.getBlockPos().getZ()).normalize();
        PulsedPlasmaEntity pulsedPlasmaEntity = new PulsedPlasmaEntity((LivingEntity) electromagneticDriverBE.getOwner(), new Vec3(electromagneticDriverBE.getBlockPos().getX(), electromagneticDriverBE.getBlockPos().getY() + 3, electromagneticDriverBE.getBlockPos().getZ()), electromagneticDriverBE.getLevel(), ElectromagneticDriverBE.magicItem);
        pulsedPlasmaEntity.shoot(vec3.x(), vec3.y(), vec3.z(), 2, 5);
        electromagneticDriverBE.getLevel().addFreshEntity(pulsedPlasmaEntity);
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
    }
}
