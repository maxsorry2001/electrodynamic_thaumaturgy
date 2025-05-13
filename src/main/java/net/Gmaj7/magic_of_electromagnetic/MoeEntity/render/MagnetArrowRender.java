package net.Gmaj7.magic_of_electromagnetic.MoeEntity.render;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.AttractBeaconEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MagnetArrowRender extends ArrowRenderer<AttractBeaconEntity> {
    public MagnetArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(AttractBeaconEntity attractBeaconEntity) {
        return ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/entity/magnet_arrow_entity.png");
    }
}
