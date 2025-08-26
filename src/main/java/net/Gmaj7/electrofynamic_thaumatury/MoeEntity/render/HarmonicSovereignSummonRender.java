package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.CoulombDomainBeaconEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.HarmonicSovereignSummonEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class HarmonicSovereignSummonRender extends ArrowRenderer<HarmonicSovereignSummonEntity> {
    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "moe_ray_entity_model"), "main");
    private static final ResourceLocation LIGHT = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/entity/harmonic_sovereign_summon_entity.png");
    private final ModelPart body;
    public HarmonicSovereignSummonRender(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelPart = context.bakeLayer(MODEL_LAYER_LOCATION);
        this.body = modelPart.getChild("body");
    }

    @Override
    public ResourceLocation getTextureLocation(HarmonicSovereignSummonEntity harmonicSovereignSummonEntity) {
        return LIGHT;
    }

    @Override
    public void render(HarmonicSovereignSummonEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        float scalar = .25f;
        float length = 32 * scalar * scalar;
        float f = entity.tickCount + partialTicks;
        poseStack.translate(0, entity.getBoundingBox().getYsize() * .5f, 0);
        poseStack.scale(scalar, scalar, scalar);


        for (float i = 0; i < Math.min(entity.tickCount * 4, 400 - entity.tickCount * 4); i += length) {
            poseStack.translate(0, length, 0);
            VertexConsumer consumer = buffer.getBuffer(RenderType.energySwirl(LIGHT, 0, 0));
            {
                poseStack.pushPose();
                float expansion = 20 - (float) entity.tickCount / 5;
                poseStack.scale(expansion, 1, expansion);
                poseStack.mulPose(Axis.YP.rotationDegrees(f * - 2));
                this.body.render(poseStack, consumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, -1);
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }
}
