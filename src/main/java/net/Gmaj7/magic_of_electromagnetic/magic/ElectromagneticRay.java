package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MoeRayEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ElectromagneticRay implements IMoeMagic{
    public ElectromagneticRay(){}
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.RAY;
    }

    @Override
    public void cast(Player player, ItemStack itemStack) {
        Vec3 start = player.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = player.getLookAngle().normalize().scale(20).add(start);
        Level level = player.level();
        MoeFunction.RayHitResult hitResult = MoeFunction.getRayHitResult(level, player, start, end, true, 0.15F);
        MoeRayEntity moeRayEntity = new MoeRayEntity(level, start, hitResult.getEnd(), player);
        level.addFreshEntity(moeRayEntity);
        for (HitResult result : hitResult.getTargets()) {
            if (result instanceof EntityHitResult) {
                Entity target = ((EntityHitResult) result).getEntity();
                if (target instanceof LivingEntity)
                    target.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC), player), MoeFunction.getMagicAmount(itemStack) * 0.75F);
            }
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 400;
    }

    @Override
    public int getBaseCooldown() {
        return 20;
    }
}
