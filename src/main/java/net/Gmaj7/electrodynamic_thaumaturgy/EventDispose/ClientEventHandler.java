package net.Gmaj7.electrodynamic_thaumaturgy.EventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.HarmonicSaintEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.HarmonicSovereignEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.model.HarmonicSovereignEntityModel;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.model.PhotoCorrosiveNovaEntityModel;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.model.PulsedPlasmaEntityModel;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render.MoeRayEntityRender;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.MoeMenuType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.hud.MoeMagicWheelHud;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.hud.MoeProtectHud;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.hud.MoeShowMagicHud;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen.*;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeKeyMapping;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeKeyState;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom.MagicCastItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;


public class ClientEventHandler {
    @EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class busEvent{
        @SubscribeEvent
        public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(MoeRayEntityRender.MODEL_LAYER_LOCATION, MoeRayEntityRender::createBodyLayer);
            event.registerLayerDefinition(PulsedPlasmaEntityModel.LAYER_LOCATION, PulsedPlasmaEntityModel::createBodyLayer);
            event.registerLayerDefinition(HarmonicSovereignEntityModel.LAYER_LOCATION, HarmonicSovereignEntityModel::createBodyLayer);
            event.registerLayerDefinition(PhotoCorrosiveNovaEntityModel.LAYER_LOCATION, PhotoCorrosiveNovaEntityModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event){
            event.put(MoeEntities.HARMONIC_SOVEREIGN_ENTITY.get(), HarmonicSovereignEntity.createAttributes().build());
            event.put(MoeEntities.HARMONIC_SAINT_ENTITY.get(), HarmonicSaintEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void registerScreen(RegisterMenuScreensEvent event){
            event.register(MoeMenuType.ASSEMBLY_TABLE_MENU.get(), MoeAssemblyTableScreen::new);
            event.register(MoeMenuType.MODEM_TABLE_MENU.get(), MoeModemTableScreen::new);
            event.register(MoeMenuType.ENERGY_BLOCK_MENU.get(), MoeEnergyBlockScreen::new);
            event.register(MoeMenuType.MAGIC_LITHOGRAPHY_TABLE_MENU.get(), MoeMagicLithographyTableScreen::new);
            event.register(MoeMenuType.THERMAL_GENERATOR_MENU.get(), MoeThermalGeneratorScreen::new);
            event.register(MoeMenuType.BIOMASS_GENERATOR_MENU.get(), MoeBiomassGeneratorScreen::new);
            event.register(MoeMenuType.ELECTROMAGNETIC_DRIVER_MACHINE_MENU.get(), ElectromagneticDriverBlockScreen::new);
            event.register(MoeMenuType.BIO_REPLICATION_VAT_MACHINE_MENU.get(), MoeEntityCloneBlockScreen::new);
            event.register(MoeMenuType.GEOLOGICAL_METAL_EXCAVATOR_MENU.get(), MoeElectromagneticExtractorBlockScreen::new);
            event.register(MoeMenuType.NITROGEN_HARVESTER_MENU.get(), MoeNitrogenHarvesterBlockScreen::new);
        }


        @SubscribeEvent
        public static void registerKey(RegisterKeyMappingsEvent event){
            event.register(MoeKeyMapping.SELECT_MAGIC);
        }

        @SubscribeEvent
        public static void registerHud(RegisterGuiLayersEvent event){
            event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "type_show"), new MoeShowMagicHud());
            event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magic_select"), MoeMagicWheelHud.instance);
            event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "protect_show"), new MoeProtectHud());
        }
    }

    @EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID, value = Dist.CLIENT)
    public static class notBusEvent{
        private static final MoeKeyState SELECT_MAGIC = new MoeKeyState(MoeKeyMapping.SELECT_MAGIC);
        @SubscribeEvent
        public static void keyInput(InputEvent.Key event){
            if(SELECT_MAGIC.wasPressed()){
                if(Minecraft.getInstance().screen == null) {
                    for (InteractionHand hand : InteractionHand.values()){
                        if(Minecraft.getInstance().player.getItemInHand(hand).getItem() instanceof MagicCastItem) {
                            MoeMagicWheelHud.instance.open(hand);
                            break;
                        }
                    }
                }
            }
            if(SELECT_MAGIC.wasReleased()){
                if(Minecraft.getInstance().screen == null && MoeMagicWheelHud.instance.active) MoeMagicWheelHud.instance.close();
            }
            SELECT_MAGIC.update();
        }
    }
}
