package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.AttractBeaconEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MagnetArrowRender extends ArrowRenderer<AttractBeaconEntity> {
    public MagnetArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(AttractBeaconEntity attractBeaconEntity) {
        return ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "textures/entity/magnet_arrow_entity.png");
    }

    @Override
    public void render(AttractBeaconEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

    }
}
