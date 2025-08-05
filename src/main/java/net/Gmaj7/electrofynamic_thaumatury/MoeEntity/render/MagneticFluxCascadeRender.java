package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.MagneticFluxCascadeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MagneticFluxCascadeRender extends ArrowRenderer<MagneticFluxCascadeEntity> {
    public MagneticFluxCascadeRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(MagneticFluxCascadeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

    }

    @Override
    public ResourceLocation getTextureLocation(MagneticFluxCascadeEntity magneticFluxCascadeEntity) {
        return ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/entity/magnet_arrow_entity.png");
    }
}
