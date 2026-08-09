package net.Gmaj7.electrodynamic_thaumaturgy.item.custom.weapon;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.FocusBulletEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.MagnoAnnihilationCannonBullet;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.ElectromagneticTierItem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class MagnoAnnihilationCannon extends AbstractEnergyGun{
    private static int shootUse = 65536;
    public MagnoAnnihilationCannon(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        if(usedHand == InteractionHand.OFF_HAND && player.getMainHandItem().getItem() instanceof ElectromagneticTierItem item) {
            changeModule(level, player, item.getChangeSlot());
            return InteractionResult.CONSUME;
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void shoot(Level level, Player player, ItemStack itemStack){
        EnergyHandler energyHandler = itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack));
        int pattern = itemStack.getOrDefault(EtDataComponentTypes.WEAPON_WORK_PATTERN, 0),
                energyUse = (int) (shootUse / Function.getEfficiency(itemStack));
        if(energyHandler.getAmountAsInt() < energyUse) return;
        float damage = Function.getResultAmount(itemStack);
        MagnoAnnihilationCannonBullet bullet = new MagnoAnnihilationCannonBullet(level, player, damage, pattern);
        bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, pattern == 2 ? 0.5F : 5.0F, 0.0F);
        level.addFreshEntity(bullet);
        player.getCooldowns().addCooldown(itemStack, (int) (60 / Function.getCoolDownRate(itemStack)));
        try (Transaction transaction = Transaction.openRoot()){
            energyHandler.extract(energyUse, transaction);
            transaction.commit();
        }
    }

    @Override
    public Identifier getIcon(int pattern) {
        return switch (pattern){
            case 1 -> Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "container/weapon/focus_gun_semi");
            case 2 -> Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "container/weapon/focus_gun_chain");
            default -> Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "container/weapon/focus_gun_fast");
        };
    }
}
