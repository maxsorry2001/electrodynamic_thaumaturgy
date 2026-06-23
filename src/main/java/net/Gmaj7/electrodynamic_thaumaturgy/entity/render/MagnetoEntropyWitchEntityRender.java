package net.Gmaj7.electrodynamic_thaumaturgy.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.MagnetoEntropyWitchEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.model.MagnetoEntityModel;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.renderState.MagnetoRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.Witch;

public class MagnetoEntropyWitchEntityRender extends MobRenderer<MagnetoEntropyWitchEntity, MagnetoRenderState, MagnetoEntityModel> {
    private static final Identifier TEX_LOCATION = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/mob/magneto_entropy_witch.png");

    public MagnetoEntropyWitchEntityRender(EntityRendererProvider.Context p_174443_) {
        super(p_174443_, new MagnetoEntityModel(p_174443_.bakeLayer(MagnetoEntityModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public MagnetoRenderState createRenderState() {
        return new MagnetoRenderState();
    }

    @Override
    public void submit(MagnetoRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public void extractRenderState(MagnetoEntropyWitchEntity entity, MagnetoRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.castAnimationState.copyFrom(entity.castAnimationState);
    }

    protected void scale(Witch livingEntity, PoseStack poseStack, float partialTickTime) {
        float f = 0.9375F;
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public Identifier getTextureLocation(MagnetoRenderState harmonicSaintEntityRenderState) {
        return TEX_LOCATION;
    }
}
