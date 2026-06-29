package net.Gmaj7.electrodynamic_thaumaturgy;

import com.mojang.logging.LogUtils;
import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.effect.EtEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.EtEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.fluid.EtFluidTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.fluid.EtFluids;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.EtMenuTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.AttachmentType;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Attributes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.mixinData.DataGet;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.ProtectingPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.Magics;
import net.Gmaj7.electrodynamic_thaumaturgy.particle.EtParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.EtRecipes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
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
        EtItems.ITEM.register(modEventBus);
        EtBlocks.BLOCKS.register(modEventBus);
        EtTabs.MOE_CREATIVE_TABS.register(modEventBus);
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
        EtFluidTypes.register(modEventBus);
        EtFluids.register(modEventBus);

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
}
