package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MirageEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeData.MoeDataGet;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MiragePursuit implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.MIRAGE_PURSUIT;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        MirageEntity mirageEntity = new MirageEntity(livingEntity.level(), livingEntity);
        ((MoeDataGet)livingEntity).setMirageEntity(mirageEntity);
    }

    @Override
    public int getBaseEnergyCost() {
        return 0;
    }

    @Override
    public int getBaseCooldown() {
        return 0;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }
}
