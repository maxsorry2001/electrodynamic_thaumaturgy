package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IMoeMagic {
    MoeMagicType getType();

    void cast(Player player, ItemStack itemStack);

    int getBaseEnergyCost();

    int getBaseCooldown();
}
