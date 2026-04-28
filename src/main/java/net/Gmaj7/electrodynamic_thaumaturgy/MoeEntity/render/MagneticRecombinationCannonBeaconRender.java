package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.MagneticRecombinationCannonBeaconEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.renderState.MagneticRecombinationCannonBeaconRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class MagneticRecombinationCannonBeaconRender extends ArrowRenderer<MagneticRecombinationCannonBeaconEntity, MagneticRecombinationCannonBeaconRenderState> {
    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "moe_ray_entity_model"), "main");
    private static final Identifier LIGHT = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/magnetic_recombination_cannon_beacon_entity.png");
    private final ModelPart body;
    public MagneticRecombinationCannonBeaconRender(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelPart = context.bakeLayer(MODEL_LAYER_LOCATION);
        this.body = modelPart.getChild("body");
    }

    @Override
    public MagneticRecombinationCannonBeaconRenderState createRenderState() {
        return new MagneticRecombinationCannonBeaconRenderState();
    }

    @Override
    protected Identifier getTextureLocation(MagneticRecombinationCannonBeaconRenderState state) {
        return Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/plasma_torch_beacon_entity.png");
    }

    @Override
    public void extractRenderState(MagneticRecombinationCannonBeaconEntity entity, MagneticRecombinationCannonBeaconRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.startTime = entity.getStartTime();
        state.lifeTime = entity.getStartTime() - 100;
        state.boxYHalf = entity.getBoundingBox().getYsize() * .5f;
    }

    @Override
    public void submit(MagneticRecombinationCannonBeaconRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if(state.startTime > 100 && state.startTime <= 120){
            poseStack.pushPose();
            float lifetime = state.lifeTime;
            float scalar = .25f;
            float length = 32 * scalar * scalar;
            float f = state.ageInTicks;//entity.tickCount + partialTicks;
            poseStack.translate(0, state.boxYHalf, 0);
            poseStack.scale(scalar, scalar, scalar);


            for (float i = 0; i < 20 * 4; i += length) {
                poseStack.translate(0, length, 0);
                {
                    poseStack.pushPose();
                    float expansion = (float) Math.min(10 * Math.log(lifetime), 30);
                    poseStack.scale(expansion, 1, expansion);
                    submitNodeCollector.submitModelPart(this.body, poseStack, RenderTypes.entityCutout(LIGHT), 15728880, OverlayTexture.NO_OVERLAY, null);
                    poseStack.mulPose(Axis.YP.rotationDegrees(f * - 2));
                    poseStack.popPose();
                }
            }
            poseStack.popPose();
        }
    }
}
