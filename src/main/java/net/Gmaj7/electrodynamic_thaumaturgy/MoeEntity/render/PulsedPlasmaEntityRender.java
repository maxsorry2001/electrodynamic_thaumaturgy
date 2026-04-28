package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.PulsedPlasmaEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.model.PulsedPlasmaEntityModel;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.renderState.PulsedPlasmaEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class PulsedPlasmaEntityRender extends EntityRenderer<PulsedPlasmaEntity, PulsedPlasmaEntityRenderState> {
    private final PulsedPlasmaEntityModel pulsedPlasmaEntityModel;
    public PulsedPlasmaEntityRender(EntityRendererProvider.Context context) {
        super(context);
        this.pulsedPlasmaEntityModel = new PulsedPlasmaEntityModel(context.bakeLayer(PulsedPlasmaEntityModel.LAYER_LOCATION));
    }

    @Override
    public PulsedPlasmaEntityRenderState createRenderState() {
        return new PulsedPlasmaEntityRenderState();
    }

    public static Identifier getTextureLocation() {
        return Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/pulsed_plasma_entity.png");
    }

    @Override
    public void extractRenderState(PulsedPlasmaEntity entity, PulsedPlasmaEntityRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.yRotO = entity.yRotO;
        state.xRotO = entity.xRotO;
        state.yRot = entity.getYRot();
        state.xRot = entity.getXRot();
    }

    @Override
    public void submit(PulsedPlasmaEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(state.partialTick, state.yRotO, state.yRot)));
        poseStack.mulPose(Axis.XN.rotationDegrees(Mth.lerp(state.partialTick, state.xRotO, state.xRot)));
        poseStack.scale(2.5F, 2.5F, 2.5F);
        submitNodeCollector.submitModel(this.pulsedPlasmaEntityModel, state, poseStack, RenderTypes.entityCutout(getTextureLocation()), 15728880, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        poseStack.popPose();
    }
}
