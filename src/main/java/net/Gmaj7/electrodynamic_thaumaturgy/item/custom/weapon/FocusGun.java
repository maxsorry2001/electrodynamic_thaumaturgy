package net.Gmaj7.electrodynamic_thaumaturgy.item.custom.weapon;

import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.FocusBulletEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.ElectromagneticTierItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class FocusGun extends ElectromagneticWeaponItem{
    private static int shootUse = 1024;
    public FocusGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        if(usedHand == InteractionHand.OFF_HAND && player.getMainHandItem().getItem() instanceof ElectromagneticTierItem item) {
            changeModule(level, player, item.getChangeSlot());
            return InteractionResult.CONSUME;
        }
        ItemStack itemStack = player.getItemInHand(usedHand);
        if(itemStack.getItem() instanceof FocusGun && itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack)).getCapacityAsInt() > shootUse)
            player.startUsingItem(usedHand);
        return InteractionResult.CONSUME;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return 12000;
    }

    public void shoot(Level level, Player player, ItemStack itemStack){
        EnergyHandler energyHandler = itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack));
        int pattern = itemStack.getOrDefault(EtDataComponentTypes.WEAPON_WORK_PATTERN, 0),
            energyUse = (int) ((pattern == 0 ? shootUse / 20 : shootUse) / Function.getEfficiency(itemStack));
        if(energyHandler.getAmountAsInt() < energyUse) return;
        float damage = Function.getResultAmount(itemStack);
        FocusBulletEntity bullet = new FocusBulletEntity(level, player, damage, pattern);
        bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.0F, 0.0F);
        level.addFreshEntity(bullet);
        if(pattern != 0) player.getCooldowns().addCooldown(itemStack, (int) (20 / Function.getCoolDownRate(itemStack)));
        try (Transaction transaction = Transaction.openRoot()){
            energyHandler.extract(energyUse, transaction);
            transaction.commit();
        }
    }
}
