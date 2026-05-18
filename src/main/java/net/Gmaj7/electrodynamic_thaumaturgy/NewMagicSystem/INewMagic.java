package net.Gmaj7.electrodynamic_thaumaturgy.NewMagicSystem;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface INewMagic {
    void playerCast(Player caster, ItemStack stack, MagicDefinition def);
    void mobCast(LivingEntity source, LivingEntity target, ItemStack stack, MagicDefinition def);
    void blockCast(ElectromagneticDriverBE be, MagicDefinition def);
    boolean canBlockCast(ElectromagneticDriverBE be, MagicDefinition def);
}
