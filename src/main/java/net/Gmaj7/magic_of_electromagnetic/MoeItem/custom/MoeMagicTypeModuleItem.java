package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.item.Item;

public class MoeMagicTypeModuleItem extends Item {
    private final MoeMagicType magicType;
    public MoeMagicTypeModuleItem(MoeMagicType magicType, Properties properties) {
        super(properties);
        this.magicType = magicType;
    }

    public MoeMagicType getMagicType() {
        return magicType;
    }
}
