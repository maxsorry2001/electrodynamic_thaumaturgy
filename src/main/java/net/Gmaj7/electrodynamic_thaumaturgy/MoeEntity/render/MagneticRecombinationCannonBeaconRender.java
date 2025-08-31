package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.MagneticRecombinationCannonBeaconEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MagneticRecombinationCannonBeaconRender extends ArrowRenderer<MagneticRecombinationCannonBeaconEntity> {
    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "moe_ray_entity_model"), "main");
    private static final ResourceLocation LIGHT = ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "textures/entity/magnetic_recombination_cannon_beacon_entity.png");
    private final ModelPart body;
    public MagneticRecombinationCannonBeaconRender(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelPart = context.bakeLayer(MODEL_LAYER_LOCATION);
        this.body = modelPart.getChild("body");
    }

    @Override
    public ResourceLocation getTextureLocation(MagneticRecombinationCannonBeaconEntity magneticRecombinationCannonBeaconEntity) {
        if(magneticRecombinationCannonBeaconEntity.getStartTime() > 100) return LIGHT;
        return ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "textures/entity/plasma_torch_beacon_entity.png");
    }

    @Override
    public void render(MagneticRecombinationCannonBeaconEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.getStartTime() > 100 && entity.getStartTime() <= 120){
            poseStack.pushPose();
            float lifetime = entity.getStartTime() - 100;
            float scalar = .25f;
            float length = 32 * scalar * scalar;
            float f = entity.tickCount + partialTicks;
            poseStack.translate(0, entity.getBoundingBox().getYsize() * .5f, 0);
            poseStack.scale(scalar, scalar, scalar);


            for (float i = 0; i < 20 * 4; i += length) {
                poseStack.translate(0, length, 0);
                VertexConsumer consumer = buffer.getBuffer(RenderType.energySwirl(LIGHT, 0, 0));
                {
                    poseStack.pushPose();
                    float expansion = (float) Math.min(10 * Math.log(lifetime), 30);
                    poseStack.scale(expansion, 1, expansion);
                    poseStack.mulPose(Axis.YP.rotationDegrees(f * - 2));
                    this.body.render(poseStack, consumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, -1);
                    poseStack.popPose();
                }
            }
            poseStack.popPose();
        }
    }
}
