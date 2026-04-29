package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.HarmonicSaintEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.model.HarmonicSovereignEntityModel;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.renderState.SovereignRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.Witch;

public class HarmonicSaintEntityRender extends MobRenderer<HarmonicSaintEntity, SovereignRenderState, HarmonicSovereignEntityModel> {
    private static final Identifier TEX_LOCATION = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/mob/harmonic_saint.png");

    public HarmonicSaintEntityRender(EntityRendererProvider.Context p_174443_) {
        super(p_174443_, new HarmonicSovereignEntityModel(p_174443_.bakeLayer(HarmonicSovereignEntityModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public SovereignRenderState createRenderState() {
        return new SovereignRenderState();
    }

    @Override
    public void submit(SovereignRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public void extractRenderState(HarmonicSaintEntity entity, SovereignRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.castAnimationState.copyFrom(entity.castAnimationState);
    }

    protected void scale(Witch livingEntity, PoseStack poseStack, float partialTickTime) {
        float f = 0.9375F;
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public Identifier getTextureLocation(SovereignRenderState harmonicSaintEntityRenderState) {
        return TEX_LOCATION;
    }
}
