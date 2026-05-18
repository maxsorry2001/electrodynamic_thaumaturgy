package net.Gmaj7.electrodynamic_thaumaturgy.NewMagicSystem;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TestBehavior implements INewMagic{
    @Override
    public void playerCast(Player caster, ItemStack stack, MagicDefinition def) {
        caster.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1000, 2));
        caster.sendSystemMessage(Component.literal(String.valueOf(def.baseCooldown())).append(":").append(String.valueOf(def.baseEnergyCost())));
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack stack, MagicDefinition def) {

    }

    @Override
    public void blockCast(ElectromagneticDriverBE be, MagicDefinition def) {

    }

    @Override
    public boolean canBlockCast(ElectromagneticDriverBE be, MagicDefinition def) {
        return false;
    }
}
