package net.Gmaj7.electrofynamic_thaumatury.EventDispose;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.model.PulsedPlasmaEntityModel;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.render.MoeRayEntityRender;
import net.Gmaj7.electrofynamic_thaumatury.MoeGui.MoeMenuType;
import net.Gmaj7.electrofynamic_thaumatury.MoeGui.hud.MoeMagicWheelHud;
import net.Gmaj7.electrofynamic_thaumatury.MoeGui.hud.MoeShowMagicHud;
import net.Gmaj7.electrofynamic_thaumatury.MoeGui.screen.*;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeKeyMapping;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeKeyState;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom.MagicCastItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;


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
            event.register(MoeMenuType.MAGIC_LITHOGRAPHY_TABLE_MENU.get(), MoeMagicLithographyTableScreen::new);
            event.register(MoeMenuType.THERMAL_ENERGY_MAKER_MENU.get(), MoeThermalEnergyMakerScreen::new);
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
