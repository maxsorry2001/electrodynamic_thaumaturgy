package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.FrequencyDivisionBeaconEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class FrequencyDivisionBeaconRender extends ArrowRenderer<FrequencyDivisionBeaconEntity, ArrowRenderState> {
    public FrequencyDivisionBeaconRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    protected Identifier getTextureLocation(ArrowRenderState arrowRenderState) {
        return Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/magnet_arrow_entity.png");
    }

    @Override
    public boolean shouldRender(FrequencyDivisionBeaconEntity entity, Frustum culler, double camX, double camY, double camZ) {
        return false;
    }
}
