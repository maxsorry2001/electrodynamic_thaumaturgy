package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.MoeRayEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.renderState.MoeRayEntityRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class MoeRayEntityRender extends EntityRenderer<MoeRayEntity, MoeRayEntityRenderState> {
    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "moe_ray_entity_model"), "main");
    private static final Identifier CORE = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/moe_ray_entity.png");

    private final ModelPart body;
    private final SpriteGetter sprites;
    public MoeRayEntityRender(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelPart = context.bakeLayer(MODEL_LAYER_LOCATION);
        this.body = modelPart.getChild("body");
        this.sprites = context.getSprites();
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8, -16, -8, 16, 32, 16), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public boolean shouldRender(MoeRayEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void extractRenderState(MoeRayEntity entity, MoeRayEntityRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.distance = entity.distance;
        state.xRot = - entity.getXRot() - 180.0F;
        state.yRot = - entity.getXRot() - 90F;
        state.boxYHalf =  entity.getBoundingBox().getYsize() * .5f;
    }

    @Override
    public MoeRayEntityRenderState createRenderState() {
        return new MoeRayEntityRenderState();
    }

    @Override
    public void submit(MoeRayEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        float lifetime = MoeRayEntity.time;
        float scalar = .25f;
        float length = 32 * scalar * scalar;
        float f = state.ageInTicks;
        poseStack.translate(0, state.boxYHalf, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(state.xRot));
        poseStack.scale(scalar, scalar, scalar);

        for (float i = 0; i < state.distance * 4; i += length) {
            poseStack.translate(0, length, 0);
            {
                poseStack.pushPose();
                float expansion = Mth.clampedLerp(1, 0, f / (lifetime - 8));
                poseStack.scale(expansion, 1, expansion);
                poseStack.mulPose(Axis.YP.rotationDegrees(f * -10));
                submitNodeCollector.submitModelPart(this.body, poseStack, RenderTypes.entityCutout(CORE), 15728880, OverlayTexture.NO_OVERLAY, null);
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }
}
