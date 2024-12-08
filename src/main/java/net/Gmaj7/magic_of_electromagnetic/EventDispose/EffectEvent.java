package net.Gmaj7.magic_of_electromagnetic.EventDispose;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeAttachmentType;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = MagicOfElectromagnetic.MODID)
public class EffectEvent {

    @SubscribeEvent
    public static void effectDeal(MobEffectEvent.Remove event){
        Holder<MobEffect> effect = event.getEffect();
        LivingEntity entity = event.getEntity();
        if(effect == MoeEffects.EXCITING && entity.hasData(MoeAttachmentType.EXCITING_DAMAGE))
            entity.removeData(MoeAttachmentType.EXCITING_DAMAGE);
        else if (effect == MoeEffects.PROTECTING && entity.hasData(MoeAttachmentType.ELECTROMAGNETIC_PROTECT))
            entity.removeData(MoeAttachmentType.ELECTROMAGNETIC_PROTECT);
        else if (effect == MoeEffects.ELECTRIC_FIELD_DOMAIN && entity.hasData(MoeAttachmentType.ELECTRIC_FIELD_DOMAIN_DAMAGE))
            entity.removeData(MoeAttachmentType.ELECTRIC_FIELD_DOMAIN_DAMAGE);
    }
}
