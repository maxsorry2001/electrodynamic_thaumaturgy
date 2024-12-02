package net.Gmaj7.magic_of_electromagnetic.EventDispose;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.model.PulsedPlasmaEntityModel;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.render.MoeRayEntityRender;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.MoeMenuType;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.hud.MoeShowMagicHud;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.screen.MoeAssemblyTableScreen;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeKeyMapping;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;


public class ClientEventHandler {
    @EventBusSubscriber(modid = MagicOfElectromagnetic.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class busEvent{
        @SubscribeEvent
        public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(MoeRayEntityRender.MODEL_LAYER_LOCATION, MoeRayEntityRender::createBodyLayer);
            event.registerLayerDefinition(PulsedPlasmaEntityModel.LAYER_LOCATION, PulsedPlasmaEntityModel::createBodyLayer);
        }
        @SubscribeEvent
        public static void registerScreen(RegisterMenuScreensEvent event){
            event.register(MoeMenuType.ASSEMBLY_TABLE_MENU.get(), MoeAssemblyTableScreen::new);
        }

        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event){
            event.registerItem(Capabilities.EnergyStorage.ITEM, ((itemStack, unused) -> new ComponentEnergyStorage(itemStack, MoeDataComponentTypes.MOE_ENERGY.get(), 65536)),
                    MoeItems.ELECTROMAGNETIC_ROD.get(),
                    MoeItems.ELECTROMAGNETIC_BOOK.get());
        }

        @SubscribeEvent
        public static void registerKey(RegisterKeyMappingsEvent event){
            event.register(MoeKeyMapping.SWITCH_MAGIC);
        }

        @SubscribeEvent
        public static void registerHud(RegisterGuiLayersEvent event){
            event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "type_show"), new MoeShowMagicHud());
        }
    }

    @EventBusSubscriber(modid = MagicOfElectromagnetic.MODID, value = Dist.CLIENT)
    public static class notBusEvent{
        @SubscribeEvent
        public static void keyInput(InputEvent.Key event){
        }
    }
}
