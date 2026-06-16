package net.Gmaj7.electrodynamic_thaumaturgy.EventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.EtEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.custom.MagnetoEntropyWitchEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.custom.MagnetoOrderSageEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.model.MagnetoEntityModel;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.model.PhotoCorrosiveNovaEntityModel;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.model.PulsedPlasmaEntityModel;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.render.EtRayEntityRender;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.EtMenuTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.hud.MagicWheelHud;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.hud.ProtectHud;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.hud.ShowMagicHud;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.screen.*;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.KeyMapping;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.KeyState;
import net.Gmaj7.electrodynamic_thaumaturgy.Item.custom.MagicCastItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;


public class ClientEventHandler {
    @EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID, value = Dist.CLIENT)
    public static class busEvent{
        @SubscribeEvent
        public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(EtRayEntityRender.MODEL_LAYER_LOCATION, EtRayEntityRender::createBodyLayer);
            event.registerLayerDefinition(PulsedPlasmaEntityModel.LAYER_LOCATION, PulsedPlasmaEntityModel::createBodyLayer);
            event.registerLayerDefinition(MagnetoEntityModel.LAYER_LOCATION, MagnetoEntityModel::createBodyLayer);
            event.registerLayerDefinition(PhotoCorrosiveNovaEntityModel.LAYER_LOCATION, PhotoCorrosiveNovaEntityModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event){
            event.put(EtEntities.MAGNETO_ENTROPY_WITCH_ENTITY.get(), MagnetoEntropyWitchEntity.createAttributes().build());
            event.put(EtEntities.MAGNETO_ORDER_SAGE_ENTITY.get(), MagnetoOrderSageEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void registerScreen(RegisterMenuScreensEvent event){
            event.register(EtMenuTypes.ASSEMBLY_TABLE_MENU.get(), AssemblyTableScreen::new);
            event.register(EtMenuTypes.MODEM_TABLE_MENU.get(), ModemTableScreen::new);
            event.register(EtMenuTypes.ENERGY_BLOCK_MENU.get(), EnergyBlockScreen::new);
            event.register(EtMenuTypes.MAGIC_ENCODE_TABLE_MENU.get(), MagicEncodeTableScreen::new);
            event.register(EtMenuTypes.THERMAL_GENERATOR_MENU.get(), ThermalGeneratorScreen::new);
            event.register(EtMenuTypes.BIOMASS_GENERATOR_MENU.get(), BiomassGeneratorScreen::new);
            event.register(EtMenuTypes.ELECTROMAGNETIC_DRIVER_MACHINE_MENU.get(), ElectromagneticDriverBlockScreen::new);
            event.register(EtMenuTypes.BIO_REPLICATION_VAT_MACHINE_MENU.get(), EntityCloneBlockScreen::new);
            event.register(EtMenuTypes.GEOLOGICAL_METAL_EXCAVATOR_MENU.get(), ElectromagneticExtractorBlockScreen::new);
            event.register(EtMenuTypes.ATOMIC_RECONSTRUCTION_BLOCK_MENU.get(), AtomicReconstructionBlockScreen::new);
            event.register(EtMenuTypes.MAGNETO_FUSION_BLOCK_MENU.get(), MagnetoFusionBlockScreen::new);
            event.register(EtMenuTypes.ELECTROMAGNETIC_DISSOCIATION_BLOCK_MENU.get(), ElectromagneticDissociationBlockScreen::new);
            event.register(EtMenuTypes.ELECTROMAGNETIC_INFUSER_BLOCK_MENU.get(), ElectromagneticInfuserBlockScreen::new);
            event.register(EtMenuTypes.EDDY_CURRENT_REMELTER_BLOCK_MENU.get(), EddyCurrentRemelterBlockScreen::new);
            event.register(EtMenuTypes.FLUID_PIPE_NET_MENU.get(), FluidPipeNetScreen::new);
            event.register(EtMenuTypes.ITEM_PIPE_NET_MENU.get(), ItemPipeNetScreen::new);
            event.register(EtMenuTypes.ENERGY_PIPE_NET_MENU.get(), EnergyPipeNetScreen::new);
            event.register(EtMenuTypes.FILTER_SETTING_MENU.get(), FilterSettingScreen::new);
        }


        @SubscribeEvent
        public static void registerKey(RegisterKeyMappingsEvent event){
            event.register(KeyMapping.SELECT_MAGIC);
        }

        @SubscribeEvent
        public static void registerHud(RegisterGuiLayersEvent event){
            event.registerAboveAll(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "type_show"), new ShowMagicHud());
            event.registerAboveAll(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magic_select"), MagicWheelHud.instance);
            event.registerAboveAll(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "protect_show"), new ProtectHud());
        }
    }

    @EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID, value = Dist.CLIENT)
    public static class notBusEvent{
        private static final KeyState SELECT_MAGIC = new KeyState(KeyMapping.SELECT_MAGIC);
        @SubscribeEvent
        public static void keyInput(InputEvent.Key event){
            if(SELECT_MAGIC.wasPressed()){
                if(Minecraft.getInstance().screen == null) {
                    for (InteractionHand hand : InteractionHand.values()){
                        if(Minecraft.getInstance().player.getItemInHand(hand).getItem() instanceof MagicCastItem) {
                            MagicWheelHud.instance.open(hand);
                            break;
                        }
                    }
                }
            }
            if(SELECT_MAGIC.wasReleased()){
                if(Minecraft.getInstance().screen == null && MagicWheelHud.instance.active) MagicWheelHud.instance.close();
            }
            SELECT_MAGIC.update();
        }
    }
}
