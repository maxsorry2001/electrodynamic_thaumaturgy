package net.Gmaj7.magic_of_electromagnetic.EventDispose;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MirageEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDamageType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeData.MoeDataGet;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoePacket;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

@EventBusSubscriber(modid = MagicOfElectromagnetic.MODID)
public class DamageEvent {

    @SubscribeEvent
    public static void damageDeal(LivingDamageEvent.Pre event){
        LivingEntity eventTarget = event.getEntity();
        DamageSource source = event.getSource();
        Entity sourceEntity = source.getEntity();
        float protecting = ((MoeDataGet)eventTarget).getProtective().getProtecting();
        if (protecting > 0){
            float damage = event.getNewDamage();
            if(protecting > damage){
                float newProtecting = protecting - damage;
                ((MoeDataGet)eventTarget).getProtective().setProtecting(newProtecting);
                PacketDistributor.sendToAllPlayers(new MoePacket.ProtectingPacket(newProtecting));
                event.setNewDamage(0);
            }
            else {
                ((MoeDataGet)eventTarget).getProtective().setProtecting(0);
                event.setNewDamage(damage - protecting);
            }
        }
        if(eventTarget.hasEffect(MoeEffects.MAGNET_RESONANCE) && sourceEntity instanceof Player && !source.is(MoeDamageType.magnet_resonance)){
            int l = eventTarget.getEffect(MoeEffects.MAGNET_RESONANCE).getAmplifier();
            List<LivingEntity> list = eventTarget.level().getEntitiesOfClass(LivingEntity.class, eventTarget.getBoundingBox().inflate(5, 2, 5));
            list.remove(eventTarget);
            list.remove(sourceEntity);
            for(LivingEntity target : list){
                if((target instanceof Mob && ((Mob) target).getTarget() == eventTarget) || target instanceof Enemy)
                    target.hurt(new DamageSource(MoeFunction.getHolder(eventTarget.level(), Registries.DAMAGE_TYPE, MoeDamageType.magnet_resonance), sourceEntity), event.getNewDamage() * l / (l + 5));
            }
        }
        if(sourceEntity != null && !source.is(MoeDamageType.mirage)){
            List<MirageEntity> list = eventTarget.level().getEntitiesOfClass(MirageEntity.class, sourceEntity.getBoundingBox().inflate(0, 3, 0));
            if (!list.isEmpty()) {
                for (MirageEntity mirage : list) {
                    if (mirage.getOwner() == sourceEntity) {
                        mirage.addTarget(new MirageEntity.CastTarget(event.getNewDamage(), eventTarget));
                    }
                }
            }
        }
    }
}
