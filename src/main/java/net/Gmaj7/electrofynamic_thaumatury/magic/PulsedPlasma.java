package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastMachineBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.PulsedPlasmaEntity;
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
        return "item.electrofynamic_thaumatury.pulsed_plasma_module";
    }

    @Override
    public void blockCast(MagicCastMachineBE magicCastMachineBE) {
        List<LivingEntity> list = magicCastMachineBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(magicCastMachineBE.getBlockPos()).inflate(7));
        list.remove(magicCastMachineBE.getOwner());
        if(list.isEmpty()) return;
        LivingEntity target = list.get(RandomSource.create().nextInt(list.size()));
        Vec3 vec3 = new Vec3(target.getX() - magicCastMachineBE.getBlockPos().getX(), target.getY() - magicCastMachineBE.getBlockPos().getY() - 3, target.getZ() - magicCastMachineBE.getBlockPos().getZ()).normalize();
        PulsedPlasmaEntity pulsedPlasmaEntity = new PulsedPlasmaEntity((LivingEntity) magicCastMachineBE.getOwner(), new Vec3(magicCastMachineBE.getBlockPos().getX(), magicCastMachineBE.getBlockPos().getY() + 3, magicCastMachineBE.getBlockPos().getZ()), magicCastMachineBE.getLevel(), MagicCastMachineBE.magicItem);
        pulsedPlasmaEntity.shoot(vec3.x(), vec3.y(), vec3.z(), 2, 5);
        magicCastMachineBE.getLevel().addFreshEntity(pulsedPlasmaEntity);
        magicCastMachineBE.setCooldown(getBaseCooldown());
        magicCastMachineBE.extractEnergy(getBaseEnergyCost());
    }
}
