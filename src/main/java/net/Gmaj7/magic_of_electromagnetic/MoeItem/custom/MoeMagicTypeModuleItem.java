package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.magic.IMoeMagic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class MoeMagicTypeModuleItem extends Item implements IMoeModuleItem{
    private final IMoeMagic magic;
    public MoeMagicTypeModuleItem(@Nullable IMoeMagic magicType, Properties properties) {
        super(properties);
        this.magic = magicType;
    }

    public MoeMagicType getMagicType() {
        if(!isEmpty()) return this.magic.getType();
        else return MoeMagicType.EMPTY;
    }

    public boolean isEmpty(){
        return this.magic == null;
    }

    public void cast(LivingEntity livingEntity, ItemStack itemStack){
        this.magic.cast(livingEntity, itemStack);
    }

    public int getBaseEnergyCost(){
        return this.magic.getBaseEnergyCost();
    }

    public int getBaseCooldown(){
        return this.magic.getBaseCooldown();
    }
}
