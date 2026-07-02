package net.Gmaj7.electrodynamic_thaumaturgy.entity.render;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.FocusBulletEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class FocusBulletRender extends ArrowRenderer<FocusBulletEntity, ArrowRenderState> {
    public FocusBulletRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    protected Identifier getTextureLocation(ArrowRenderState arrowRenderState) {
        return Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/entity/focus_bullet_entity.png");
    }

    @Override
    public boolean shouldRender(FocusBulletEntity entity, Frustum culler, double camX, double camY, double camZ) {
        return true;
    }
}
