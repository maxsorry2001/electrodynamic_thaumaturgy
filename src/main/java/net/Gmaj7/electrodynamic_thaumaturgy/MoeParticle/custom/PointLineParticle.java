package net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class PointLineParticle extends TextureSheetParticle {
    public final Vec3 target;
    public final Vec3 speed;
    protected PointLineParticle(ClientLevel level, double x, double y, double z, PointLineParticleOption pointLineParticleOption) {
        super(level, x, y, z);
        this.rCol = pointLineParticleOption.color.x() / 255;
        this.gCol = pointLineParticleOption.color.y() / 255;
        this.bCol = pointLineParticleOption.color.z() / 255;
        this.target = new Vec3(pointLineParticleOption.target);
        this.speed = new Vec3(pointLineParticleOption.speed);
        this.lifetime = pointLineParticleOption.lifeTime;
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        return 0.1F;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 oldPos = this.getPos();
        double speedLength = speed.length(), distance = oldPos.distanceTo(target);
        Vec3 newPos = (oldPos != target && distance > speedLength) ? oldPos.add(speed) : target;
        this.setPos(newPos.x(), newPos.y(), newPos.z());
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<PointLineParticleOption> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(PointLineParticleOption pointLineParticleOption, ClientLevel clientLevel, double x, double y, double z, double xv, double yv, double zv) {
            PointLineParticle pointRotateParticle = new PointLineParticle(clientLevel, x, y, z, pointLineParticleOption);
            pointRotateParticle.pickSprite(this.spriteSet);
            return pointRotateParticle;
        }
    }
}
