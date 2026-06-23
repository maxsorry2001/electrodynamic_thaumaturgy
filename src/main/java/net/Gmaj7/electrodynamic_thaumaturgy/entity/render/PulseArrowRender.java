package net.Gmaj7.electrodynamic_thaumaturgy.entity.render;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.PulseArrowEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class PulseArrowRender extends ArrowRenderer<PulseArrowEntity, ArrowRenderState> {
    public PulseArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    protected Identifier getTextureLocation(ArrowRenderState arrowRenderState) {
        return Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/frequency_division_arrow_entity.png");
    }

    @Override
    public boolean shouldRender(PulseArrowEntity entity, Frustum culler, double camX, double camY, double camZ) {
        return true;
    }
}
