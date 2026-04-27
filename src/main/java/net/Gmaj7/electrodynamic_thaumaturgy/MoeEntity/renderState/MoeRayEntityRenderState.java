package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.renderState;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.MoeRayEntity;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class MoeRayEntityRenderState extends EntityRenderState {
    public int lifeTime = MoeRayEntity.time;
    public float distance;
    public float xRot;
    public float yRot;
    public double boxYHalf;
}
