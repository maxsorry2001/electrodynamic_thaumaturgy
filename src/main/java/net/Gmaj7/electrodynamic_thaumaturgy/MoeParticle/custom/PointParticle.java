package net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class PointParticle extends TextureSheetParticle {
    public final Vec3 center;
    protected PointParticle(ClientLevel level, double x, double y, double z, PointParticleOption pointParticleOption) {
        super(level, x, y, z);
        this.rCol = pointParticleOption.color.x() / 255;
        this.gCol = pointParticleOption.color.y() / 255;
        this.bCol = pointParticleOption.color.z() / 255;
        this.center = new Vec3(pointParticleOption.center);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<PointParticleOption> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(PointParticleOption pointParticleOption, ClientLevel clientLevel, double x, double y, double z, double xv, double yv, double zv) {
            PointParticle pointParticle = new PointParticle(clientLevel, x, y, z, pointParticleOption);
            pointParticle.pickSprite(this.spriteSet);
            return pointParticle;
        }
    }
}
