package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.PhotoCorrosiveNovaEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.model.PhotoCorrosiveNovaEntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PhotoCorrosiveNovaEntityRender extends EntityRenderer<PhotoCorrosiveNovaEntity> {
    private final PhotoCorrosiveNovaEntityModel photoCorrosiveNovaEntityModel;
    public PhotoCorrosiveNovaEntityRender(EntityRendererProvider.Context context) {
        super(context);
        this.photoCorrosiveNovaEntityModel = new PhotoCorrosiveNovaEntityModel(context.bakeLayer(PhotoCorrosiveNovaEntityModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(PhotoCorrosiveNovaEntity photoCorrosiveNovaEntity) {
        return ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/photo_corrosive_nova_entity.png");
    }

    @Override
    public void render(PhotoCorrosiveNovaEntity p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.pushPose();
        this.photoCorrosiveNovaEntityModel.setupAnim(p_entity, 0, 0, p_entity.tickCount, 0, 0);
        VertexConsumer buffer = bufferSource.getBuffer(this.photoCorrosiveNovaEntityModel.renderType(this.getTextureLocation(p_entity)));
        this.photoCorrosiveNovaEntityModel.renderToBuffer(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
