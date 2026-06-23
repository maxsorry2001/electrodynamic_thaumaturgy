package net.Gmaj7.electrodynamic_thaumaturgy.eventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.block.blockEntityRender.FluidBlockRender;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.EtEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.MagnetoEntropyWitchEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.MagnetoOrderSageEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.model.MagnetoEntityModel;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.model.PhotoCorrosiveNovaEntityModel;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.model.PulsedPlasmaEntityModel;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.render.*;
import net.Gmaj7.electrodynamic_thaumaturgy.fluid.EtFluidTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.fluid.EtFluids;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.EtMenuTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.hud.BowWheelHud;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.hud.MagicWheelHud;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.hud.ProtectHud;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.hud.ShowMagicHud;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.screen.*;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Attributes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.KeyMapping;
import net.Gmaj7.electrodynamic_thaumaturgy.init.KeyState;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.MagicCastItem;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.PulseBow;
import net.Gmaj7.electrodynamic_thaumaturgy.particle.EtParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.particle.custom.HydrogenBondParticle;
import net.Gmaj7.electrodynamic_thaumaturgy.particle.custom.PointLineParticle;
import net.Gmaj7.electrodynamic_thaumaturgy.particle.custom.PointRotateParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;


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
            event.register(EtMenuTypes.FLUID_BLOCK_MENU.get(), FluidBlockScreen::new);
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
        public static void registerRenders(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(EtBlockEntities.FLUID_BLOCK_BE.get(), FluidBlockRender::new);
        }

        @SubscribeEvent
        public static void registerKey(RegisterKeyMappingsEvent event){
            event.register(KeyMapping.TOOL_SWITCH);
        }

        @SubscribeEvent
        public static void registerHud(RegisterGuiLayersEvent event){
            event.registerAboveAll(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "type_show"), new ShowMagicHud());
            event.registerAboveAll(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magic_select"), MagicWheelHud.instance);
            event.registerAboveAll(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "bow_select"), BowWheelHud.instance);
            event.registerAboveAll(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "protect_show"), new ProtectHud());
        }

        @SubscribeEvent
        public static void onComputeFovModifierEvent(ComputeFovModifierEvent event) {
            if (event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() == EtItems.PULSE_BOW.get()) {
                float fovModifier = 1f;
                int ticksUsingItem = event.getPlayer().getTicksUsingItem();
                float scale = Math.min(ticksUsingItem / 20.0F, 1.0F);
                fovModifier *= 1.0F - Mth.square(scale) * 0.15F;
                event.setNewFovModifier(Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get().floatValue(), 1.0F, fovModifier));
            }
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(EtEntities.ET_RAY_ENTITY.get(), EtRayEntityRender::new);
            EntityRenderers.register(EtEntities.PULSED_PLASMA_ENTITY.get(), PulsedPlasmaEntityRender::new);
            EntityRenderers.register(EtEntities.ATTRACT_BEACON_ENTITY.get(), MagnetArrowRender::new);
            EntityRenderers.register(EtEntities.MAGNETIC_RECOMBINATION_CANNON_BEACON_ENTITY.get(), MagneticRecombinationCannonBeaconRender::new);
            EntityRenderers.register(EtEntities.MAGMA_LIGHTING_BEACON_ENTITY.get(), MagmaLightingBeaconRender::new);
            EntityRenderers.register(EtEntities.COULOMB_DOMAIN_BEACON_ENTITY.get(), CoulombDomainRender::new);
            EntityRenderers.register(EtEntities.MIRAGE_ENTITY.get(), MirageEntityRender::new);
            EntityRenderers.register(EtEntities.MAGNETIC_FLUX_CASCADE_ENTITY.get(), MagneticFluxCascadeRender::new);
            EntityRenderers.register(EtEntities.FREQUENCY_DIVISION_ARROW_ENTITY.get(), FrequencyDivisionArrowRender::new);
            EntityRenderers.register(EtEntities.FREQUENCY_DIVISION_BEACON_ENTITY.get(), FrequencyDivisionBeaconRender::new);
            EntityRenderers.register(EtEntities.MAGNETO_ENTROPY_WITCH_ENTITY.get(), MagnetoEntropyWitchEntityRender::new);
            EntityRenderers.register(EtEntities.MAGNETO_ENTROPY_WITCH_SUMMON_ENTITY.get(), MagnetoEntropyWitchSummonRender::new);
            EntityRenderers.register(EtEntities.MAGNETO_ORDER_SAGE_ENTITY.get(), MagnetoOrderSageEntityRender::new);
            EntityRenderers.register(EtEntities.PHOTOACOUSTIC_PULSE_BEACON_ENTITY.get(), PhotoacousticPulseBeaconEntityRender::new);
            EntityRenderers.register(EtEntities.PHOTO_CORROSIVE_NOVA_ENTITY.get(), PhotoCorrosiveNovaEntityRender::new);
            EntityRenderers.register(EtEntities.PULSE_ARROW_ENTITY.get(), PulseArrowRender::new);
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event){
            event.registerSpriteSet(EtParticles.HYDROGEN_BOND_PARTICLE.get(), HydrogenBondParticle.Provider::new);
            event.registerSpriteSet(EtParticles.POINT_ROTATE_PARTICLE.get(), PointRotateParticle.Provider::new);
            event.registerSpriteSet(EtParticles.POINT_LINE_PARTICLE.get(), PointLineParticle.Provider::new);
        }

        @SubscribeEvent
        public static void attributeAdd(EntityAttributeModificationEvent event){
            for (EntityType<? extends LivingEntity> entityType : event.getTypes()){
                event.add(entityType, Attributes.CORROSION);
            }
        }

        @SubscribeEvent
        public static void registerOnClientExtensions(RegisterClientExtensionsEvent event){
            event.registerFluidType(EtFluidTypes.MAGNETIC_FLUX_EXTENSION, EtFluidTypes.MAGNETIC_FLUX_FLUID_TYPE.get());
        }

        @SubscribeEvent
        public static void registerFluidModel(RegisterFluidModelsEvent event){
            FluidModel.Unbaked model = new FluidModel.Unbaked(
                    new Material(Identifier.withDefaultNamespace("block/water_still")),
                    new Material(Identifier.withDefaultNamespace("block/water_flow")),
                    new Material(Identifier.withDefaultNamespace("block/water_overlay")),
                    state -> 0xA1EB1734);
            event.register(model, EtFluids.MAGNETIC_FLUX_SOURCE.get());
            event.register(model, EtFluids.MAGNETIC_FLUX_FLOWING.get());
        }
    }

    @EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID, value = Dist.CLIENT)
    public static class notBusEvent{
        private static final KeyState SELECT_MAGIC = new KeyState(KeyMapping.TOOL_SWITCH);
        @SubscribeEvent
        public static void keyInput(InputEvent.Key event){
            if(SELECT_MAGIC.wasPressed()){
                if(Minecraft.getInstance().screen == null) {
                    for (InteractionHand hand : InteractionHand.values()){
                        if(Minecraft.getInstance().player.getItemInHand(hand).getItem() instanceof MagicCastItem) {
                            MagicWheelHud.instance.open(hand);
                            break;
                        }
                        else if (Minecraft.getInstance().player.getItemInHand(hand).getItem() instanceof PulseBow){
                            BowWheelHud.instance.open(hand);
                            break;
                        }
                    }
                }
            }
            if(SELECT_MAGIC.wasReleased()){
                if(Minecraft.getInstance().screen == null && MagicWheelHud.instance.active) MagicWheelHud.instance.close();
                else if(Minecraft.getInstance().screen == null && BowWheelHud.instance.active) BowWheelHud.instance.close();
            }
            SELECT_MAGIC.update();
        }
    }
}
