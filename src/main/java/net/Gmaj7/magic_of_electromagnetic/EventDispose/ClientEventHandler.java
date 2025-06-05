package net.Gmaj7.magic_of_electromagnetic.EventDispose;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.IMoeEnergyBlockEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.IMoeItemBlockEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.model.PulsedPlasmaEntityModel;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.render.MoeRayEntityRender;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.MoeMenuType;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.hud.MoeMagicWheelHud;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.hud.MoeShowMagicHud;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.screen.MagicLithographyTableScreen;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.screen.MoeAssemblyTableScreen;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.screen.MoeEnergyBlockScreen;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.screen.MoeModemTableScreen;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeKeyMapping;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeKeyState;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MagicCastItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
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
            event.register(MoeMenuType.MODEM_TABLE_MENU.get(), MoeModemTableScreen::new);
            event.register(MoeMenuType.ENERGY_BLOCK_MENU.get(), MoeEnergyBlockScreen::new);
            event.register(MoeMenuType.MAGIC_LITHOGRAPHY_TABLE_MENU.get(), MagicLithographyTableScreen::new);
        }

        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event){
            event.registerItem(Capabilities.EnergyStorage.ITEM, ((itemStack, unused) -> new ComponentEnergyStorage(itemStack, MoeDataComponentTypes.MOE_ENERGY.get(), 65536)),
                    MoeItems.ELECTROMAGNETIC_ROD.get(),
                    MoeItems.ELECTROMAGNETIC_BOOK.get());
            event.registerItem(Capabilities.EnergyStorage.ITEM, ((itemStack, unused) -> new ComponentEnergyStorage(itemStack, MoeDataComponentTypes.MOE_ENERGY.get(), 16384, 0, 16384)),
                    MoeItems.FE_CU_POTATO_BATTERY.get(),
                    MoeItems.FE_CU_CARROT_BATTERY.get(),
                    MoeItems.FE_CU_SOLUTION_BATTERY.get());
            event.registerItem(Capabilities.EnergyStorage.ITEM, ((itemStack, unused) -> new ComponentEnergyStorage(itemStack, MoeDataComponentTypes.MOE_ENERGY.get(), 65535)),
                    MoeItems.ENERGY_BLOCK.get());
            event.registerBlock(Capabilities.EnergyStorage.BLOCK, ((level, blockPos, blockState, blockEntity, direction) ->
                    blockEntity instanceof IMoeEnergyBlockEntity ? ((IMoeEnergyBlockEntity) blockEntity).getEnergy() : null),
                    MoeBlocks.ENERGY_BLOCK.get(),
                    MoeBlocks.TEMPERATURE_ENERGY_MAKER_BLOCK.get(),
                    MoeBlocks.PHOTOVOLTAIC_ENERGY_MAKER_BLOCK.get());
            event.registerBlock(Capabilities.ItemHandler.BLOCK, ((level, blockPos, blockState, blockEntity, direction) ->
                    blockEntity instanceof IMoeItemBlockEntity ? ((IMoeItemBlockEntity) blockEntity).getItemHandler() : null),
                    MoeBlocks.ENERGY_BLOCK.get());
        }

        @SubscribeEvent
        public static void registerKey(RegisterKeyMappingsEvent event){
            event.register(MoeKeyMapping.SELECT_MAGIC);
        }

        @SubscribeEvent
        public static void registerHud(RegisterGuiLayersEvent event){
            event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "type_show"), new MoeShowMagicHud());
            event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "magic_select"), MoeMagicWheelHud.instance);
        }
    }

    @EventBusSubscriber(modid = MagicOfElectromagnetic.MODID, value = Dist.CLIENT)
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
