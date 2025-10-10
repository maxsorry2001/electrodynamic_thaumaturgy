package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.PhotoacousticPulseBeaconEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class PhotoacousticPulseBeaconEntityRender extends ArrowRenderer<PhotoacousticPulseBeaconEntity> {
    public PhotoacousticPulseBeaconEntityRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(PhotoacousticPulseBeaconEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

    }

    @Override
    public ResourceLocation getTextureLocation(PhotoacousticPulseBeaconEntity photoacousticPulseBeaconEntity) {
        return ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "textures/entity/magnet_arrow_entity.png");
    }
}
