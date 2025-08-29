package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.SummonedHarmonicSovereignEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.model.HarmonicSovereignEntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.WitchItemLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Witch;

public class SummonedHarmonicSovereignEntityRender extends MobRenderer<SummonedHarmonicSovereignEntity, HarmonicSovereignEntityModel<SummonedHarmonicSovereignEntity>> {
    private static final ResourceLocation TEX_LOCATION = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/entity/mob/summoned_harmonic_sovereign.png");

    public SummonedHarmonicSovereignEntityRender(EntityRendererProvider.Context p_174443_) {
        super(p_174443_, new HarmonicSovereignEntityModel<>(p_174443_.bakeLayer(HarmonicSovereignEntityModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new WitchItemLayer(this, p_174443_.getItemInHandRenderer()));
    }

    public void render(SummonedHarmonicSovereignEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    public ResourceLocation getTextureLocation(SummonedHarmonicSovereignEntity entity) {
        return TEX_LOCATION;
    }

    protected void scale(Witch livingEntity, PoseStack poseStack, float partialTickTime) {
        float f = 0.9375F;
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
