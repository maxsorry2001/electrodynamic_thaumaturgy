package net.Gmaj7.electrodynamic_thaumaturgy;

import com.mojang.logging.LogUtils;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.MoeEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render.*;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.MoeMenuType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeAttachmentType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeData.MoeDataGet;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.*;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MoeRecipes;
import net.minecraft.client.renderer.entity.EntityRenderers;
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
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(EelectrodynamicThaumaturgy.MODID)
public class EelectrodynamicThaumaturgy
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "electrodynamic_thaumaturgy";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public EelectrodynamicThaumaturgy(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);



        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        MoeTabs.MOE_CREATIVE_TABS.register(modEventBus);
        MoeBlocks.MOE_BLOCKS.register(modEventBus);
        MoeItems.MOE_ITEM.register(modEventBus);
        MoeAttachmentType.register(modEventBus);
        MoeEffects.register(modEventBus);
        MoeEntities.register(modEventBus);
        MoeDataComponentTypes.register(modEventBus);
        MoeMenuType.register(modEventBus);
        MoeParticles.register(modEventBus);
        MoeBlockEntities.register(modEventBus);
        MoeRecipes.register(modEventBus);

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
            PacketDistributor.sendToAllPlayers(new MoePacket.ProtectingPacket(((MoeDataGet)player).getProtective().getProtecting()));
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(MoeEntities.MOE_RAY_ENTITY.get(), MoeRayEntityRender::new);
            EntityRenderers.register(MoeEntities.PULSED_PLASMA_ENTITY.get(), PulsedPlasmaEntityRender::new);
            EntityRenderers.register(MoeEntities.ATTRACT_BEACON_ENTITY.get(), MagnetArrowRender::new);
            EntityRenderers.register(MoeEntities.MAGNETIC_RECOMBINATION_CANNON_BEACON_ENTITY.get(), MagneticRecombinationCannonBeaconRender::new);
            EntityRenderers.register(MoeEntities.MAGMA_LIGHTING_BEACON_ENTITY.get(), MagmaLightingBeaconRender::new);
            EntityRenderers.register(MoeEntities.COULOMB_DOMAIN_BEACON_ENTITY.get(), CoulombDomainRender::new);
            EntityRenderers.register(MoeEntities.MIRAGE_ENTITY.get(), MirageEntityRender::new);
            EntityRenderers.register(MoeEntities.MAGNETIC_FLUX_CASCADE_ENTITY.get(), MagneticFluxCascadeRender::new);
            EntityRenderers.register(MoeEntities.FREQUENCY_DIVISION_ARROW_ENTITY.get(), FrequencyDivisionArrowRender::new);
            EntityRenderers.register(MoeEntities.FREQUENCY_DIVISION_BEACON_ENTITY.get(), FrequencyDivisionBeaconRender::new);
            EntityRenderers.register(MoeEntities.HARMONIC_SOVEREIGN_ENTITY.get(), HarmonicSovereignEntityRender::new);
            EntityRenderers.register(MoeEntities.HARMONIC_SOVEREIGN_SUMMON_ENTITY.get(), HarmonicSovereignSummonRender::new);
            EntityRenderers.register(MoeEntities.HARMONIC_SAINT_ENTITY.get(), HarmonicSaintEntityRender::new);
            EntityRenderers.register(MoeEntities.PHOTOACOUSTIC_PULSE_BEACON_ENTITY.get(), PhotoacousticPulseBeaconEntityRender::new);
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event){
            event.registerSpriteSet(MoeParticles.MAGMA_LIGHTING_PARTICLE_MIDDLE.get(), MagmaLightingParticleMiddle.Provider::new);
            event.registerSpriteSet(MoeParticles.MAGMA_LIGHTING_PARTICLE_SMALL.get(), MagmaLightingParticleSmall.Provider::new);
            event.registerSpriteSet(MoeParticles.MAGMA_LIGHTING_PARTICLE_LARGE.get(), MagmaLightingParticleLarge.Provider::new);
            event.registerSpriteSet(MoeParticles.MAGMA_LIGHTING_PARTICLE_MIDDLE_IN.get(), MagmaLightingParticleMiddleIn.Provider::new);
            event.registerSpriteSet(MoeParticles.MAGMA_LIGHTING_PARTICLE_SMALL_IN.get(), MagmaLightingParticleSmallIn.Provider::new);
            event.registerSpriteSet(MoeParticles.MAGMA_LIGHTING_PARTICLE_LARGE_IN.get(), MagmaLightingParticleLargeIn.Provider::new);
            event.registerSpriteSet(MoeParticles.TORCH_PARTICLE.get(), TorchParticle.Provider::new);
            event.registerSpriteSet(MoeParticles.TORCH_PARTICLE_IN.get(), TorchParticleIn.Provider::new);
            event.registerSpriteSet(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), SelfMagicCircleParticle.Provider::new);
            event.registerSpriteSet(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), SelfMagicCircleParticleIn.Provider::new);
            event.registerSpriteSet(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE.get(), WildMagicCircleParticle.Provider::new);
            event.registerSpriteSet(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE_IN.get(), WildMagicCircleParticleIn.Provider::new);
            event.registerSpriteSet(MoeParticles.HIGH_INTENSITY_MAGNETIC_PARTICLE_IN.get(), DisturbingByHighIntensityMagneticParticleIn.Provider::new);
            event.registerSpriteSet(MoeParticles.FRONT_MAGIC_CIRCLE_PARTICLE.get(), FrontMagicCircleParticle.Provider::new);
            event.registerSpriteSet(MoeParticles.FRONT_MAGIC_CIRCLE_PARTICLE_IN.get(), FrontMagicCircleParticleIn.Provider::new);
            event.registerSpriteSet(MoeParticles.NORMAL_CIRCLE_PARTICLE.get(), NormalCircleParticle.Provider::new);
            event.registerSpriteSet(MoeParticles.NORMAL_SHRINK_CIRCLE_PARTICLE.get(), NormalShrinkCircleParticle.Provider::new);
            event.registerSpriteSet(MoeParticles.HYDROGEN_BOND_PARTICLE.get(), HydrogenBondParticle.Provider::new);
            event.registerSpriteSet(MoeParticles.MAGNETIC_FLUX_CASCADE_PARTICLE.get(), MagneticFluxCascadeParticle.Provider::new);
            event.registerSpriteSet(MoeParticles.FREQUENCY_DIVISION_ARROW_RAIN_PARTICLE.get(), FrequencyDivisionArrowRainParticle.Provider::new);
        }
    }
}
