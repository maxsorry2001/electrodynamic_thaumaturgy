package net.Gmaj7.electrodynamic_thaumaturgy.EventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.Effect.EtEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.custom.MagnetoOrderSageEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.custom.MirageEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.*;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.MixinData.DataGet;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.ProtectingPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.componentDatas.EnhancementData;
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

@EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID)
public class DamageEvent {

    @SubscribeEvent
    public static void damageDeal(LivingDamageEvent.Pre event){
        LivingEntity eventTarget = event.getEntity();
        DamageSource source = event.getSource();
        Entity sourceEntity = source.getEntity();
        float protecting = ((DataGet)eventTarget).getProtective().getProtecting();
        if (protecting > 0){
            float damage = event.getNewDamage();
            if(protecting > damage){
                float newProtecting = protecting - damage;
                ((DataGet)eventTarget).getProtective().setProtecting(newProtecting);
                PacketDistributor.sendToAllPlayers(new ProtectingPacket(newProtecting));
                event.setNewDamage(0);
            }
            else {
                ((DataGet)eventTarget).getProtective().setProtecting(0);
                PacketDistributor.sendToAllPlayers(new ProtectingPacket(0));
                event.setNewDamage(damage - protecting);
            }
        }
        if(eventTarget.hasEffect(EtEffects.MAGNET_RESONANCE) && sourceEntity instanceof Player && !source.is(EtDamageType.magnet_resonance)){
            int l = eventTarget.getEffect(EtEffects.MAGNET_RESONANCE).getAmplifier();
            List<LivingEntity> list = eventTarget.level().getEntitiesOfClass(LivingEntity.class, eventTarget.getBoundingBox().inflate(5, 2, 5));
            list.remove(eventTarget);
            list.remove(sourceEntity);
            for(LivingEntity target : list){
                if((target instanceof Mob && ((Mob) target).getTarget() == eventTarget) || target instanceof Enemy)
                    target.hurt(new DamageSource(Function.getHolder(eventTarget.level(), Registries.DAMAGE_TYPE, EtDamageType.magnet_resonance), sourceEntity), event.getNewDamage() * l / (l + 5));
            }
        }
        if(sourceEntity != null && !source.is(EtDamageType.mirage)){
            List<MirageEntity> list = eventTarget.level().getEntitiesOfClass(MirageEntity.class, sourceEntity.getBoundingBox().inflate(0, 3, 0));
            if (!list.isEmpty()) {
                for (MirageEntity mirage : list) {
                    if (mirage.getOwner() == sourceEntity) {
                        mirage.addTarget(new MirageEntity.CastTarget(event.getNewDamage(), eventTarget));
                    }
                }
            }
        }
        if(source.is(EtDamageType.origin_thaumaturgy) && sourceEntity instanceof LivingEntity livingEntity){
            EnhancementData enhancementData = livingEntity.getMainHandItem().get(EtDataComponentTypes.ENHANCEMENT_DATA);
            if(enhancementData == null) enhancementData = livingEntity.getOffhandItem().get(EtDataComponentTypes.ENHANCEMENT_DATA);
            if(enhancementData != null) livingEntity.heal(event.getNewDamage() * 0.2F * enhancementData.lifeExtraction());
        }
        if(sourceEntity instanceof MagnetoOrderSageEntity && ((MagnetoOrderSageEntity) sourceEntity).getOwner() instanceof Player){
            eventTarget.setLastHurtByPlayer((Player) ((MagnetoOrderSageEntity) sourceEntity).getOwner(), 100);
        }
        double corrosion = eventTarget.getAttribute(Attributes.CORROSION).getValue();
        if(corrosion > 1) event.setNewDamage((float) (event.getNewDamage() * corrosion));
    }
}
