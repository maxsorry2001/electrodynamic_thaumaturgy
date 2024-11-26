package net.Gmaj7.magic_of_electromagnetic.EventDispose;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.model.PlasmaEntityModel;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.render.MoeRayEntityRender;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.MoeAssemblyTableScreen;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.MoeMenuType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;

@EventBusSubscriber(modid = MagicOfElectromagnetic.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(MoeRayEntityRender.MODEL_LAYER_LOCATION, MoeRayEntityRender::createBodyLayer);
        event.registerLayerDefinition(PlasmaEntityModel.LAYER_LOCATION, PlasmaEntityModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerScreen(RegisterMenuScreensEvent event){
        event.register(MoeMenuType.ASSEMBLY_TABLE_MENU.get(), MoeAssemblyTableScreen::new);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerItem(Capabilities.EnergyStorage.ITEM, ((itemStack, unused) -> new ComponentEnergyStorage(itemStack, MoeDataComponentTypes.MOE_ENERGY.get(), 65536, 65536, 65536)),
                MoeItems.ELECTROMAGNETIC_ROD.get(),
                MoeItems.ELECTROMAGNETIC_BOOK.get());
    }
}
