package net.Gmaj7.electrodynamic_thaumaturgy.entity.renderState;

import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.EtRayEntity;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class EtRayEntityRenderState extends EntityRenderState {
    public int lifeTime = EtRayEntity.time;
    public float distance;
    public float xRot;
    public float yRot;
    public double boxYHalf;
}
