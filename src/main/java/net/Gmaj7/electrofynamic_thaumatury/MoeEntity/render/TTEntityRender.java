package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.PulsedPlasmaEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.TTEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.model.TTEntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TTEntityRender extends MobRenderer<TTEntity, TTEntityModel<TTEntity>> {


    public TTEntityRender(EntityRendererProvider.Context context) {
        super(context, new TTEntityModel<>(context.bakeLayer(TTEntityModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    public ResourceLocation getTextureLocation(TTEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/entity/tt_entity.png");
    }

    @Override
    public void render(TTEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
