package net.Gmaj7.electrodynamic_thaumaturgy.Entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.custom.MagnetoEntropyWitchSummonEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.renderState.MagnetoEntropyWitchSummonRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

public class MagnetoEntropyWitchSummonRender extends ArrowRenderer<MagnetoEntropyWitchSummonEntity, MagnetoEntropyWitchSummonRenderState> {
    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "et_ray_entity_model"), "main");
    private static final Identifier LIGHT = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/magneto_entropy_witch_entity_summon_entity.png");
    private final ModelPart body;
    public MagnetoEntropyWitchSummonRender(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelPart = context.bakeLayer(MODEL_LAYER_LOCATION);
        this.body = modelPart.getChild("body");
    }

    @Override
    public MagnetoEntropyWitchSummonRenderState createRenderState() {
        return new MagnetoEntropyWitchSummonRenderState();
    }

    @Override
    protected Identifier getTextureLocation(MagnetoEntropyWitchSummonRenderState state) {
        return LIGHT;
    }

    @Override
    public boolean shouldRender(MagnetoEntropyWitchSummonEntity entity, Frustum culler, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void extractRenderState(MagnetoEntropyWitchSummonEntity entity, MagnetoEntropyWitchSummonRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.boxYHalf =  entity.getBoundingBox().getYsize() * .5f;
        state.tickCount = entity.tickCount;
    }

    @Override
    protected int getSkyLightLevel(MagnetoEntropyWitchSummonEntity entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    protected int getBlockLightLevel(MagnetoEntropyWitchSummonEntity entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public void submit(MagnetoEntropyWitchSummonRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
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
