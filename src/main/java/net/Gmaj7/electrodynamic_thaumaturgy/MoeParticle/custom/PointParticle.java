package net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class PointParticle extends TextureSheetParticle {
    public final Vec3 center;
    public final double radius;
    public final float xRot;
    public final float yRot;
    protected PointParticle(ClientLevel level, double x, double y, double z, PointParticleOption pointParticleOption) {
        super(level, x, y, z);
        this.rCol = pointParticleOption.color.x() / 255;
        this.gCol = pointParticleOption.color.y() / 255;
        this.bCol = pointParticleOption.color.z() / 255;
        this.center = new Vec3(pointParticleOption.center);
        this.radius = center.subtract(x, y, z).length();
        this.xRot = pointParticleOption.xRot;
        this.yRot = pointParticleOption.yRot;
        this.lifetime = 40;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public void tick() {
        super.tick();// 计算半径向量
        Vec3 radiusVector = new Vec3(this.x, this.y, this.z).subtract(center);

        // 获取旋转后平面的法向量
        //
        // 原始z轴单位向量
        Vec3 originalZ = new Vec3(0, 0, 1);
        //使用相同的旋转将z轴旋转到新方向
        MoeFunction.Quaternion rotation = MoeFunction.Quaternion.fromEulerYX(yRot, xRot).normalize();
        //
        Vec3 planeNormal = rotation.rotateVector(originalZ);

        // 创建绕法向量旋转的四元数
        MoeFunction.Quaternion circularRotation = MoeFunction.Quaternion.fromAxisAngle(planeNormal, Mth.PI / 8).normalize();

        // 旋转半径向量
        Vec3 newRadius = circularRotation.rotateVector(radiusVector);

        // 返回新位置
        Vec3 newPos = center.add(newRadius);
        this.setPos(newPos.x(), newPos.y(), newPos.z());
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
