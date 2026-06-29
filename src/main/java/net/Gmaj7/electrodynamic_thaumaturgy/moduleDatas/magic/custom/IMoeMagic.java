package net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.MagicDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;

public interface IMoeMagic {
    void playerCast(Player caster, ItemStack stack, MagicDefinition def);

    void mobCast(LivingEntity source, LivingEntity target, ItemStack stack, MagicDefinition def);

    void blockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition def);

    default boolean canBlockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition def){
        if(electromagneticDriverBE.getEnergy().getAmountAsInt() < def.baseEnergyCost() * 128) return false;
        List<LivingEntity> list = electromagneticDriverBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(electromagneticDriverBE.getBlockPos()).inflate(7));
        list.remove(electromagneticDriverBE.getOwner());
        if(list.isEmpty()) return false;
        else return true;
    };

    boolean success(LivingEntity livingEntity, ItemStack itemStack);

    String getTranslate();
}
