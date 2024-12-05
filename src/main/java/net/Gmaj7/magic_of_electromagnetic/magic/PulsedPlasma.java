package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.PulsedPlasmaEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PulsedPlasma implements IMoeMagic{
    public PulsedPlasma(){}
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.PULSED_PLASMA;
    }

    @Override
    public void cast(Player player, ItemStack itemStack) {
        PulsedPlasmaEntity pulsedPlasmaEntity = new PulsedPlasmaEntity(player, player.level());
        pulsedPlasmaEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 5, 1.5F);
        pulsedPlasmaEntity.setPlasmaDamage(MoeFunction.getMagicAmount(itemStack));
        player.level().addFreshEntity(pulsedPlasmaEntity);
    }

    @Override
    public int getBaseEnergyCost() {
        return 600;
    }

    @Override
    public int getBaseCooldown() {
        return 20;
    }
}
