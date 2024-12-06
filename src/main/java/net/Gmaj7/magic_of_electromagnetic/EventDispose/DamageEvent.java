package net.Gmaj7.magic_of_electromagnetic.EventDispose;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeAttachmentType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = MagicOfElectromagnetic.MODID)
public class DamageEvent {

    @SubscribeEvent
    public static void damageDeal(LivingDamageEvent.Pre event){
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.hasEffect(MoeEffects.PROTECTING) && livingEntity.hasData(MoeAttachmentType.ELECTROMAGNETIC_PROTECT)){
            float protecting = livingEntity.getData(MoeAttachmentType.ELECTROMAGNETIC_PROTECT), damage = event.getOriginalDamage();
            if(protecting <= damage) livingEntity.removeEffect(MoeEffects.PROTECTING);
            else livingEntity.setData(MoeAttachmentType.ELECTROMAGNETIC_PROTECT, protecting - damage);
            event.setNewDamage(0);
        }
    }
}
