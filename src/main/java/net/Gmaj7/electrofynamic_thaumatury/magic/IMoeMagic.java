package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastBlockBE;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;

public interface IMoeMagic {

    void cast(LivingEntity livingEntity, ItemStack itemStack);

    int getBaseEnergyCost();

    int getBaseCooldown();

    boolean success(LivingEntity livingEntity, ItemStack itemStack);

    String getTranslate();

    void blockCast(MagicCastBlockBE magicCastBlockBE);

    default boolean canBlockCast(MagicCastBlockBE magicCastBlockBE){
        if(magicCastBlockBE.getEnergy().getEnergyStored() < getBaseEnergyCost() * 128) return false;
        List<LivingEntity> list = magicCastBlockBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(magicCastBlockBE.getBlockPos()).inflate(7));
        list.remove(magicCastBlockBE.getOwner());
        if(list.isEmpty()) return false;
        else return true;
    };
}
