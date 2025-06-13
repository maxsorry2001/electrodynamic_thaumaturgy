package net.Gmaj7.magic_of_electromagnetic.MoeEntity.render;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.CoulombDomainBeaconEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CoulombDomainRender extends ArrowRenderer<CoulombDomainBeaconEntity> {
    public CoulombDomainRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CoulombDomainBeaconEntity coulombDomainBeaconEntity) {
        return ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/entity/plasma_torch_beacon_entity.png");
    }
}
