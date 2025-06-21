package net.Gmaj7.electrofynamic_thaumatury.EventDispose;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.MoeItems;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = MagicOfElectromagnetic.MODID)
public class TickEvent {

    @SubscribeEvent
    public static void toolTip(ItemTooltipEvent event){
        if(event.getItemStack().is(MoeItems.ENERGY_TRANSMISSION_ANTENNA.get())){
            event.getToolTip().add(Component.translatable("advancements.electrofynamic_thaumatury.energy_send.description"));
        }
    }
}
