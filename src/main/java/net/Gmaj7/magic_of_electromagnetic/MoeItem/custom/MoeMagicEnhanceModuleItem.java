package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.ElectromagneticTier;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.EnhancementType;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;

public class MoeMagicEnhanceModuleItem extends ElectromagneticTierItem {
    private final EnhancementType type;

    public MoeMagicEnhanceModuleItem(ElectromagneticTier tier, EnhancementType type, Properties properties) {
        super(tier, properties);
        this.type = type;
    }

    public EnhancementType getType() {
        return type;
    }
}
