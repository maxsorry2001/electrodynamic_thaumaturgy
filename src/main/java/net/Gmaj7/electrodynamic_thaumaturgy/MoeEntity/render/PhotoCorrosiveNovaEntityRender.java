package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.PhotoCorrosiveNovaEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.model.PhotoCorrosiveNovaEntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class PhotoCorrosiveNovaEntityRender extends EntityRenderer<PhotoCorrosiveNovaEntity, EntityRenderState> {
    private final PhotoCorrosiveNovaEntityModel photoCorrosiveNovaEntityModel;
    public PhotoCorrosiveNovaEntityRender(EntityRendererProvider.Context context) {
        super(context);
        this.photoCorrosiveNovaEntityModel = new PhotoCorrosiveNovaEntityModel(context.bakeLayer(PhotoCorrosiveNovaEntityModel.LAYER_LOCATION));
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    public static Identifier getTextureLocation() {
        return Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/photo_corrosive_nova_entity.png");
    }

    @Override
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);poseStack.pushPose();
        this.photoCorrosiveNovaEntityModel.setupAnim(state);
        submitNodeCollector.submitModel(this.photoCorrosiveNovaEntityModel, state, poseStack, RenderTypes.entityCutout(getTextureLocation()), 15728880, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        poseStack.popPose();
    }
}
