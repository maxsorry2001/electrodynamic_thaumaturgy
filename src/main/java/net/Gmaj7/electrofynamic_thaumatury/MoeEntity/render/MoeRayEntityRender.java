package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.MoeRayEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MoeRayEntityRender extends EntityRenderer<MoeRayEntity> {
    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "moe_ray_entity_model"), "main");
    private static final ResourceLocation CORE = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/entity/moe_ray_entity.png");

    private final ModelPart body;
    public MoeRayEntityRender(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelPart = context.bakeLayer(MODEL_LAYER_LOCATION);
        this.body = modelPart.getChild("body");
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
    public void render(MoeRayEntity p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        float lifetime = MoeRayEntity.time;
        float scalar = .25f;
        float length = 32 * scalar * scalar;
        float f = p_entity.tickCount + partialTick;
        poseStack.translate(0, p_entity.getBoundingBox().getYsize() * .5f, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(-p_entity.getYRot() - 180.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(-p_entity.getXRot() - 90));
        poseStack.scale(scalar, scalar, scalar);


        for (float i = 0; i < p_entity.distance * 4; i += length) {
            poseStack.translate(0, length, 0);
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.energySwirl(CORE, 0, 0));
            {
                poseStack.pushPose();
                float expansion = Mth.clampedLerp(1, 0, f / (lifetime - 8));
                poseStack.scale(expansion, 1, expansion);
                poseStack.mulPose(Axis.YP.rotationDegrees(f * -10));
                this.body.render(poseStack, consumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, -1);
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(MoeRayEntity moeRayEntity) {
        return CORE;
    }
}
