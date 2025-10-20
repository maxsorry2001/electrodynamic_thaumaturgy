package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.CoulombDomainBeaconEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CoulombDomainRender extends ArrowRenderer<CoulombDomainBeaconEntity> {
    public CoulombDomainRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CoulombDomainBeaconEntity coulombDomainBeaconEntity) {
        return ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/plasma_torch_beacon_entity.png");
    }

    @Override
    public void render(CoulombDomainBeaconEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

    }
}
