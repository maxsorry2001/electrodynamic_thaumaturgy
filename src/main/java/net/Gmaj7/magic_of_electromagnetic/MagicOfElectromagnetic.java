package net.Gmaj7.magic_of_electromagnetic;

import com.mojang.logging.LogUtils;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.MoeEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.render.MoeRayEntityRender;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.render.MagnetArrowRender;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.render.PlasmaTorchBeaconRender;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.render.PulsedPlasmaEntityRender;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.MoeMenuType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeAttachmentType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MagicOfElectromagnetic.MODID)
public class MagicOfElectromagnetic
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "magic_of_electromagnetic";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public MagicOfElectromagnetic(IEventBus modEventBus, ModContainer modContainer)
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

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(MoeEntities.MOE_RAY_ENTITY.get(), MoeRayEntityRender::new);
            EntityRenderers.register(MoeEntities.PULSED_PLASMA_ENTITY.get(), PulsedPlasmaEntityRender::new);
            EntityRenderers.register(MoeEntities.MAGNET_ARROW_ENTITY.get(), MagnetArrowRender::new);
            EntityRenderers.register(MoeEntities.PLASMA_TORCH_BEACON_ENTITY.get(), PlasmaTorchBeaconRender::new);
        }
    }
}
