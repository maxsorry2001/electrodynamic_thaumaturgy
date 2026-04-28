package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.HarmonicSovereignSummonEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.renderState.HarmonicSovereignSummonRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class HarmonicSovereignSummonRender extends ArrowRenderer<HarmonicSovereignSummonEntity, HarmonicSovereignSummonRenderState> {
    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "moe_ray_entity_model"), "main");
    private static final Identifier LIGHT = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/harmonic_sovereign_summon_entity.png");
    private final ModelPart body;
    public HarmonicSovereignSummonRender(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelPart = context.bakeLayer(MODEL_LAYER_LOCATION);
        this.body = modelPart.getChild("body");
    }

    @Override
    public HarmonicSovereignSummonRenderState createRenderState() {
        return new HarmonicSovereignSummonRenderState();
    }

    @Override
    protected Identifier getTextureLocation(HarmonicSovereignSummonRenderState state) {
        return LIGHT;
    }

    @Override
    public boolean shouldRender(HarmonicSovereignSummonEntity entity, Frustum culler, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void extractRenderState(HarmonicSovereignSummonEntity entity, HarmonicSovereignSummonRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.boxYHalf =  entity.getBoundingBox().getYsize() * .5f;
        state.tickCount = entity.tickCount;
    }

    @Override
    public void submit(HarmonicSovereignSummonRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        float scalar = .25f;
        float length = 32 * scalar * scalar;
        float f = state.ageInTicks;
        poseStack.translate(0, state.boxYHalf, 0);
        poseStack.scale(scalar, scalar, scalar);


        for (float i = 0; i < Math.min(state.tickCount * 4, 400 - state.tickCount * 4); i += length) {
            poseStack.translate(0, length, 0);
            {
                poseStack.pushPose();
                float expansion = 1;
                poseStack.scale(expansion, 1, expansion);
                poseStack.mulPose(Axis.YP.rotationDegrees(f * - 2));
                submitNodeCollector.submitModelPart(this.body, poseStack, RenderTypes.entityCutout(getTextureLocation(state)), 15728880, OverlayTexture.NO_OVERLAY, null);
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }
}
