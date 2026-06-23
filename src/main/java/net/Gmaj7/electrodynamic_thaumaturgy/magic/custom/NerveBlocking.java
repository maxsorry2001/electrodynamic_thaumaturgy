package net.Gmaj7.electrodynamic_thaumaturgy.magic.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.effect.EtEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.MagnetoOrderSageEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.particle.custom.PointLineParticleOption;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class NerveBlocking extends AbstractWideMagic{

    @Override
    public void playerCast(Player livingEntity, ItemStack itemStack, MagicDefinition magicDefinition) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(7));
        for (LivingEntity target : list){
            if(target instanceof Enemy || (target instanceof Mob && ((Mob) target).getTarget() == livingEntity)) {
                target.addEffect(new MobEffectInstance(EtEffects.NERVE_BLOCKING, (int) (200 * Function.getEfficiency(itemStack)), (int) (1 * Function.getStrengthRate(itemStack))));
            }
        }
        if(!livingEntity.level().isClientSide()){
            Thread thread = new Thread(() -> {
                makeParticle((ServerLevel) livingEntity.level(), livingEntity.position().add(0, 0.2, 0), 7);
                makeParticle((ServerLevel) livingEntity.level(), livingEntity.position().add(0, 0.2, 0), 5);
                makeParticle((ServerLevel) livingEntity.level(), livingEntity.position().add(0, 0.2, 0), 3);
            });
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack, MagicDefinition magicDefinition) {
        List<LivingEntity> list = source.level().getEntitiesOfClass(LivingEntity.class, new AABB(source.blockPosition()).inflate(7));
        list.remove(source);
        if(source instanceof MagnetoOrderSageEntity) list.remove(((MagnetoOrderSageEntity) source).getOwner());
        list.add(target);
        for (LivingEntity livingEntity : list){
            target.addEffect(new MobEffectInstance(EtEffects.NERVE_BLOCKING, (int) (200 * Function.getEfficiency(itemStack)), (int) (1 * Function.getStrengthRate(itemStack))));
        }
        if(!source.level().isClientSide()){
            Thread thread = new Thread(() -> {
                makeParticle((ServerLevel) source.level(), source.position().add(0, 0.2, 0), 7);
                makeParticle((ServerLevel) source.level(), source.position().add(0, 0.2, 0), 5);
                makeParticle((ServerLevel) source.level(), source.position().add(0, 0.2, 0), 3);
            });
            thread.start();
        }
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.block_nerve_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition magicDefinition) {
        List<LivingEntity> list = electromagneticDriverBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(electromagneticDriverBE.getBlockPos()).inflate(7));
        list.remove(electromagneticDriverBE.getOwner());
        if(list.isEmpty()) return;
        if(!electromagneticDriverBE.extract(magicDefinition.baseEnergyCost())) return;
        for (LivingEntity target : list){
            if(target instanceof Enemy || (target instanceof Mob && ((Mob) target).getTarget() == electromagneticDriverBE.getOwner())) {
                target.addEffect(new MobEffectInstance(EtEffects.NERVE_BLOCKING, (int) (200 * Function.getEfficiency(ElectromagneticDriverBE.magicItem)), (int) (1 * Function.getStrengthRate(ElectromagneticDriverBE.magicItem))));
            }
        }
        electromagneticDriverBE.setCooldown(magicDefinition.baseCooldown());
        if(!electromagneticDriverBE.getLevel().isClientSide()){
            Thread thread = new Thread(() -> {
                makeParticle((ServerLevel) electromagneticDriverBE.getLevel(), electromagneticDriverBE.getBlockPos().getCenter().add(0, 0.2, 0), 7);
                makeParticle((ServerLevel) electromagneticDriverBE.getLevel(), electromagneticDriverBE.getBlockPos().getCenter().add(0, 0.2, 0), 5);
                makeParticle((ServerLevel) electromagneticDriverBE.getLevel(), electromagneticDriverBE.getBlockPos().getCenter().add(0, 0.7, 0), 3);
            });
            thread.start();
        }
    }

    private void makeParticle(ServerLevel serverLevel, Vec3 center, float radius){
        List<Vec3> partial1 = Function.rotatePointsYX(Function.getCirclePoints((int) (10 * radius), radius), Mth.PI / 2, 0);
        for (int i = 0; i < partial1.size(); i++){
            Vec3 pos = center.add(partial1.get(i));
            serverLevel.sendParticles(new PointLineParticleOption(pos.toVector3f(), new Vector3f(255), new Vector3f(0, -1F, 0), 10), pos.x(), pos.y() + radius, pos.z(), 1, 0, 0 , 0, 0);
        }
    }
}
