package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.render;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.CoulombDomainBeaconEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class CoulombDomainRender extends ArrowRenderer<CoulombDomainBeaconEntity, ArrowRenderState> {
    public CoulombDomainRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    protected Identifier getTextureLocation(ArrowRenderState arrowRenderState) {
        return Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/plasma_torch_beacon_entity.png");
    }

    @Override
    public boolean shouldRender(CoulombDomainBeaconEntity entity, Frustum culler, double camX, double camY, double camZ) {
        return true;
    }
}
