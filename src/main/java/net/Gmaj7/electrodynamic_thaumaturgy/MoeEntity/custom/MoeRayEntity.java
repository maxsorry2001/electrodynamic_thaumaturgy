package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;

public class MoeRayEntity extends Entity implements IEntityWithComplexSpawn {
    public static final int time = 20;

    public float distance;


    public MoeRayEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public MoeRayEntity(Level level, Vec3 start, Vec3 end, LivingEntity owner, boolean fromEntity) {
        super(MoeEntities.MOE_RAY_ENTITY.get(), level);
        this.setPos(start.subtract(0, .75f, 0));
        this.distance = (float) start.distanceTo(end);
        if (fromEntity)
            this.setRot(owner.getYRot(), owner.getXRot());
        else {
            Vec3 vec3 = end.subtract(start);
            this.setYRot(- (float)(Mth.atan2(vec3.x, vec3.z) * 180.0 / 3.1415927410125732));
            this.setXRot(- (float)(Mth.atan2(vec3.y, vec3.horizontalDistance()) * 180.0 / 3.1415927410125732));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
    }

    @Override
    public void tick() {
        if(++tickCount > time){
            this.discard();
        }
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float v) {
        return false;
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.putFloat("moe_ray_time", this.distance);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        this.distance = valueInput.getFloatOr("moe_ray_time", 0);
    }

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        registryFriendlyByteBuf.writeInt((int) (distance * 10));
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        this.distance = registryFriendlyByteBuf.readInt() / 10f;
    }
}
