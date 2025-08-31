package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.FrequencyDivisionArrowEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FrequencyDivisionArrowRender extends ArrowRenderer<FrequencyDivisionArrowEntity> {
    public FrequencyDivisionArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(FrequencyDivisionArrowEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(FrequencyDivisionArrowEntity frequencyDivisionArrowEntity) {
        return ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "textures/entity/frequency_division_arrow_entity.png");
    }
}
