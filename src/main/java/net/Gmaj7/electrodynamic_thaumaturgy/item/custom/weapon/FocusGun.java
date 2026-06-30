package net.Gmaj7.electrodynamic_thaumaturgy.item.custom.weapon;

import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.ElectromagneticTierItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FocusGun extends ElectromagneticWeaponItem{
    public FocusGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        if(usedHand == InteractionHand.OFF_HAND && player.getMainHandItem().getItem() instanceof ElectromagneticTierItem item) {
            changeModule(level, player, item.getChangeSlot());
            return InteractionResult.CONSUME;
        }
        return super.use(level, player, usedHand);
    }

    public void shoot(Level level, Player player, ItemStack itemStack){
        Arrow arrow = new Arrow(level, player, new ItemStack(Items.ARROW), null);
        arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.0F, 0.0F);
        level.addFreshEntity(arrow);
    }
}
