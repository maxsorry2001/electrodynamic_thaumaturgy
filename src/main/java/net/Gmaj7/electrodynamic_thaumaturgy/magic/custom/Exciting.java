package net.Gmaj7.electrodynamic_thaumaturgy.magic.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.Effect.EtEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.Particle.custom.PointRotateParticleOption;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class Exciting extends AbstractWideMagic{
    RandomSource randomSource = RandomSource.create();

    @Override
    public void playerCast(Player livingEntity, ItemStack itemStack, MagicDefinition magicDefinition) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(20));
        for (LivingEntity target : list){
            if(target instanceof Enemy || (target instanceof Mob && ((Mob) target).getTarget() == livingEntity)) {
                target.addEffect(new MobEffectInstance(EtEffects.EXCITING, (int) (200 * Function.getEfficiency(itemStack)), (int) (Function.getDamageAmount(itemStack)) - 7));
                Function.checkTargetEnhancement(itemStack, livingEntity);
                if(livingEntity.level() instanceof ServerLevel){
                    int radius = randomSource.nextInt(2) + 1;
                    float xRot = randomSource.nextFloat() * Mth.PI * 2;
                    float yRot = -randomSource.nextFloat() * Mth.PI * 2;
                    Thread thread = new Thread(() -> makeParticle(target.level(), target, radius, xRot, yRot));
                    thread.start();
                }
            }
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity livingEntity, ItemStack itemStack, MagicDefinition magicDefinition) {
        List<LivingEntity> list = source.level().getEntitiesOfClass(LivingEntity.class, new AABB(source.blockPosition()).inflate(20));
        for (LivingEntity target : list){
            if(target instanceof Enemy || (target instanceof Mob && ((Mob) target).getTarget() == source)) {
                target.addEffect(new MobEffectInstance(EtEffects.EXCITING, (int) (200 * Function.getEfficiency(itemStack)), (int) (Function.getDamageAmount(itemStack)) - 7));
                Function.checkTargetEnhancement(itemStack, source);
                if(source.level() instanceof ServerLevel){
                    int radius = randomSource.nextInt(2) + 1;
                    float xRot = randomSource.nextFloat() * Mth.PI * 2;
                    float yRot = -randomSource.nextFloat() * Mth.PI * 2;
                    Thread thread = new Thread(() -> makeParticle(target.level(), target, radius, xRot, yRot));
                    thread.start();
                }
            }
        }
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.exciting_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition magicDefinition) {
        List<LivingEntity> list = electromagneticDriverBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(electromagneticDriverBE.getBlockPos()).inflate(20));
        list.remove(electromagneticDriverBE.getOwner());
        if(list.isEmpty()) return;
        if(!electromagneticDriverBE.extract(magicDefinition.baseEnergyCost())) return;
        for (LivingEntity target : list){
            if(target instanceof Enemy || (target instanceof Mob && ((Mob) target).getTarget() == electromagneticDriverBE.getOwner())) {
                target.addEffect(new MobEffectInstance(EtEffects.EXCITING, (int) (200 * Function.getEfficiency(ElectromagneticDriverBE.magicItem)), (int) (Function.getDamageAmount(ElectromagneticDriverBE.magicItem)) - 7));
                if(electromagneticDriverBE.getLevel() instanceof ServerLevel){
                    int radius = randomSource.nextInt(2) + 1;
                    float xRot = randomSource.nextFloat() * Mth.PI * 2;
                    float yRot = -randomSource.nextFloat() * Mth.PI * 2;
                    Thread thread = new Thread(() -> makeParticle(target.level(), target, radius, xRot, yRot));
                    thread.start();
                }
            }
        }
        electromagneticDriverBE.setCooldown(magicDefinition.baseCooldown());
    }

    public void makeParticle(Level level, LivingEntity livingEntity, int radius, float xRot, float yRot){
        List<Vec3> list = Function.rotatePointsYX(Function.getCirclePoints(60, radius), xRot, -yRot);
        Vec3 center =  new Vec3(livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ());
        for (int j = 0; j < list.size(); j++) {
            Vec3 pos = center.add(list.get(j));
            ((ServerLevel) level).sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 255, 255)), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
    }
}
