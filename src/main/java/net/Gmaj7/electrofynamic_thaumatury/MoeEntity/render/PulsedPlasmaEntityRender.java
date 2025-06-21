package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.PulsedPlasmaEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.model.PulsedPlasmaEntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PulsedPlasmaEntityRender extends EntityRenderer<PulsedPlasmaEntity> {
    private final PulsedPlasmaEntityModel pulsedPlasmaEntityModel;
    public PulsedPlasmaEntityRender(EntityRendererProvider.Context context) {
        super(context);
        this.pulsedPlasmaEntityModel = new PulsedPlasmaEntityModel(context.bakeLayer(PulsedPlasmaEntityModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(PulsedPlasmaEntity pulsedPlasmaEntity) {
        return ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/entity/pulsed_plasma_entity.png");
    }

    @Override
    public void render(PulsedPlasmaEntity p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, p_entity.yRotO, p_entity.getYRot())));
        poseStack.mulPose(Axis.XN.rotationDegrees(Mth.lerp(partialTick, p_entity.xRotO, p_entity.getXRot())));
        poseStack.scale(2.5F, 2.5F, 2.5F);
        VertexConsumer buffer = bufferSource.getBuffer(this.pulsedPlasmaEntityModel.renderType(this.getTextureLocation(p_entity)));
        this.pulsedPlasmaEntityModel.renderToBuffer(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
