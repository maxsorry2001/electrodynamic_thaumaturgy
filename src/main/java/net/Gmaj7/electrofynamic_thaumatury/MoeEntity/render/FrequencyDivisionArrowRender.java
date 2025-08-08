package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.render;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.FrequencyDivisionArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FrequencyDivisionArrowRender extends ArrowRenderer<FrequencyDivisionArrowEntity> {
    public FrequencyDivisionArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FrequencyDivisionArrowEntity frequencyDivisionArrowEntity) {
        return ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/entity/plasma_torch_beacon_entity.png");
    }
}
