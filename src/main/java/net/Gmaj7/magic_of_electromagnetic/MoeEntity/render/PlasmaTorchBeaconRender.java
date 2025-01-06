package net.Gmaj7.magic_of_electromagnetic.MoeEntity.render;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.PlasmaTorchBeaconEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class PlasmaTorchBeaconRender extends ArrowRenderer<PlasmaTorchBeaconEntity> {
    public PlasmaTorchBeaconRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(PlasmaTorchBeaconEntity plasmaTorchBeaconEntity) {
        return ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/entity/plasma_torch_beacon_entity.png");
    }
}
