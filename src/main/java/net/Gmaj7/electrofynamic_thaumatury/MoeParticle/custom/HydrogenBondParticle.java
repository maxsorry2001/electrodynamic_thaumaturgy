package net.Gmaj7.electrofynamic_thaumatury.MoeParticle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BubbleParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class HydrogenBondParticle extends BubbleParticle {
    protected HydrogenBondParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        } else {
            this.yd += 0.002;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.8500000238418579;
            this.yd *= 0.8500000238418579;
            this.zd *= 0.8500000238418579;
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprites) {
            this.sprite = sprites;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            HydrogenBondParticle hydrogenBondParticle = new HydrogenBondParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            hydrogenBondParticle.pickSprite(this.sprite);
            return hydrogenBondParticle;
        }
    }
}
