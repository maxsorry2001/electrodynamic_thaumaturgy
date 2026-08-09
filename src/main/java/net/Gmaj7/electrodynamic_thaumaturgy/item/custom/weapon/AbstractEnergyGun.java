package net.Gmaj7.electrodynamic_thaumaturgy.item.custom.weapon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractEnergyGun extends ElectromagneticWeaponItem {
    public AbstractEnergyGun(Properties properties) {
        super(properties);
    }


    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return 12000;
    }

    public abstract void shoot(Level level, Player player, ItemStack itemStack);
}
