package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastBlockBE;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public abstract class AbstractBlockBeaconMagic implements IMoeMagic{

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getBlock(livingEntity).getType() != HitResult.Type.MISS;
    }

    protected abstract BlockHitResult getBlock(LivingEntity livingEntity);

    protected LivingEntity getBlockTarget(MagicCastBlockBE magicCastBlockBE){
        List<LivingEntity> list = magicCastBlockBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(magicCastBlockBE.getBlockPos()).inflate(7));
        list.remove(magicCastBlockBE.getOwner());
        if(list.isEmpty()) return null;
        else return list.get(RandomSource.create().nextInt(list.size()));
    }
}
