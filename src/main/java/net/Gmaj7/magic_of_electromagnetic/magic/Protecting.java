package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeData.MoeDataGet;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoePacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

public class Protecting implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.PROTECT;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        if(!livingEntity.level().isClientSide()) {
            float p = MoeFunction.getMagicAmount(itemStack);
            ((MoeDataGet) livingEntity).getProtective().setProtecting(p);
            PacketDistributor.sendToAllPlayers(new MoePacket.ProtectingPacket(p));
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 300;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }
}
