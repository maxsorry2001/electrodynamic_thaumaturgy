package net.Gmaj7.magic_of_electromagnetic.EventDispose;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeData.MoeDataGet;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoePacket;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = MagicOfElectromagnetic.MODID)
public class DamageEvent {

    @SubscribeEvent
    public static void damageDeal(LivingDamageEvent.Pre event){
        LivingEntity livingEntity = event.getEntity();
        float protecting = ((MoeDataGet)livingEntity).getProtective().getProtecting();
        if (protecting > 0){
            float damage = event.getNewDamage();
            if(protecting > damage){
                float newProtecting = protecting - damage;
                ((MoeDataGet)livingEntity).getProtective().setProtecting(newProtecting);
                PacketDistributor.sendToAllPlayers(new MoePacket.ProtectingPacket(newProtecting));
                event.setNewDamage(0);
            }
            else {
                ((MoeDataGet)livingEntity).getProtective().setProtecting(0);
                event.setNewDamage(damage - protecting);
            }
        }
    }
}
