package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.MagicCastMachineBE;
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

    protected LivingEntity getBlockTarget(MagicCastMachineBE magicCastMachineBE){
        List<LivingEntity> list = magicCastMachineBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(magicCastMachineBE.getBlockPos()).inflate(7));
        list.remove(magicCastMachineBE.getOwner());
        if(list.isEmpty()) return null;
        else return list.get(RandomSource.create().nextInt(list.size()));
    }
}
