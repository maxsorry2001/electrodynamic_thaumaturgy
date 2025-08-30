package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;

public interface IMoeMagic {

    void playerCast(LivingEntity livingEntity, ItemStack itemStack);

    void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack);

    int getBaseEnergyCost();

    int getBaseCooldown();

    boolean success(LivingEntity livingEntity, ItemStack itemStack);

    String getTranslate();

    void blockCast(ElectromagneticDriverBE electromagneticDriverBE);

    default boolean canBlockCast(ElectromagneticDriverBE electromagneticDriverBE){
        if(electromagneticDriverBE.getEnergy().getEnergyStored() < getBaseEnergyCost() * 128) return false;
        List<LivingEntity> list = electromagneticDriverBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(electromagneticDriverBE.getBlockPos()).inflate(7));
        list.remove(electromagneticDriverBE.getOwner());
        if(list.isEmpty()) return false;
        else return true;
    }
}
