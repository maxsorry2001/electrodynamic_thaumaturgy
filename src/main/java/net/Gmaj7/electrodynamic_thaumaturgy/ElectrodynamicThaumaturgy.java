package net.Gmaj7.electrodynamic_thaumaturgy;

import com.mojang.logging.LogUtils;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.Effect.EtEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.EtEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.render.*;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.EtMenuTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.AttachmentType;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Attributes;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.MixinData.DataGet;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.ProtectingPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.Item.EtItems;
import net.Gmaj7.electrodynamic_thaumaturgy.Particle.EtParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.Particle.custom.HydrogenBondParticle;
import net.Gmaj7.electrodynamic_thaumaturgy.Particle.custom.PointLineParticle;
import net.Gmaj7.electrodynamic_thaumaturgy.Particle.custom.PointRotateParticle;
import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.EtRecipes;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.Magics;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ElectrodynamicThaumaturgy.MODID)
public class ElectrodynamicThaumaturgy
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "electrodynamic_thaumaturgy";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ElectrodynamicThaumaturgy(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        EtTabs.MOE_CREATIVE_TABS.register(modEventBus);
        EtBlocks.BLOCKS.register(modEventBus);
        EtItems.ITEM.register(modEventBus);
        AttachmentType.register(modEventBus);
        EtEffects.register(modEventBus);
        EtEntities.register(modEventBus);
        EtDataComponentTypes.register(modEventBus);
        EtMenuTypes.register(modEventBus);
        EtParticles.register(modEventBus);
        EtBlockEntities.register(modEventBus);
        EtRecipes.register(modEventBus);
        Attributes.register(modEventBus);
        Magics.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    @SubscribeEvent
    public void entityJoin(EntityJoinLevelEvent event){
        if(!event.getLevel().isClientSide() && event.getEntity() instanceof Player player){
            PacketDistributor.sendToAllPlayers(new ProtectingPacket(((DataGet)player).getProtective().getProtecting()));
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents
    {
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
    }
}
