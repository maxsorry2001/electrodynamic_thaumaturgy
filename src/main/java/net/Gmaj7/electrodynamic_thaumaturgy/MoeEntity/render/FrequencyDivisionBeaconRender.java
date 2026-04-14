package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.FrequencyDivisionBeaconEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class FrequencyDivisionBeaconRender extends ArrowRenderer<FrequencyDivisionBeaconEntity> {
    public FrequencyDivisionBeaconRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(FrequencyDivisionBeaconEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

    }

    @Override
    public Identifier getTextureLocation(FrequencyDivisionBeaconEntity frequencyDivisionBeaconEntity) {
        return Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/magnet_arrow_entity.png");
    }
}
