package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastBlockBE;
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
    public void MobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {

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
    public void blockCast(MagicCastBlockBE magicCastBlockBE) {
        List<LivingEntity> list = magicCastBlockBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(magicCastBlockBE.getBlockPos()).inflate(7));
        list.remove(magicCastBlockBE.getOwner());
        if(list.isEmpty()) return;
        LivingEntity target = list.get(RandomSource.create().nextInt(list.size()));
        Vec3 vec3 = new Vec3(target.getX() - magicCastBlockBE.getBlockPos().getX(), target.getY() - magicCastBlockBE.getBlockPos().getY() - 3, target.getZ() - magicCastBlockBE.getBlockPos().getZ()).normalize();
        PulsedPlasmaEntity pulsedPlasmaEntity = new PulsedPlasmaEntity((LivingEntity) magicCastBlockBE.getOwner(), new Vec3(magicCastBlockBE.getBlockPos().getX(), magicCastBlockBE.getBlockPos().getY() + 3, magicCastBlockBE.getBlockPos().getZ()), magicCastBlockBE.getLevel(), MagicCastBlockBE.magicItem);
        pulsedPlasmaEntity.shoot(vec3.x(), vec3.y(), vec3.z(), 2, 5);
        magicCastBlockBE.getLevel().addFreshEntity(pulsedPlasmaEntity);
        magicCastBlockBE.setCooldown(getBaseCooldown());
        magicCastBlockBE.extractEnergy(getBaseEnergyCost());
    }
}
