package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeRegistries;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinitionLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class MoeMagicTypeModuleItem extends Item implements IMoeModuleItem{
    private final boolean isEmpty;

    public MoeMagicTypeModuleItem(Properties properties, boolean isEmpty){
        super(properties);
        this.isEmpty = isEmpty;
    }

    public MoeMagicTypeModuleItem(Properties properties) {
        this(properties, false);
    }

    public void cast(Player player, ItemStack itemStack){
        Identifier key = itemStack.get(MoeDataComponentTypes.MAGIC_DEF_LOCATION);
        if(key == null) return;
        MagicDefinition definition = MagicDefinitionLoader.get(key);
        if(!player.level().isClientSide())
            player.level().registryAccess().lookupOrThrow(MoeRegistries.MAGIC_KEY).getOrThrow(definition.behaviorKey()).value().playerCast(player, itemStack, definition);
    }


    public boolean success(LivingEntity livingEntity, ItemStack itemStack){
        Identifier key = itemStack.get(MoeDataComponentTypes.MAGIC_DEF_LOCATION);
        if(key == null) return false;
        MagicDefinition definition = MagicDefinitionLoader.get(key);
        return livingEntity.level().registryAccess().lookupOrThrow(MoeRegistries.MAGIC_KEY).getOrThrow(definition.behaviorKey()).value().success(livingEntity, itemStack);
    }

        @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }

    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition magicDefinition){
        electromagneticDriverBE.getLevel().registryAccess().lookupOrThrow(MoeRegistries.MAGIC_KEY).getOrThrow(magicDefinition.behaviorKey()).value().blockCast(electromagneticDriverBE, magicDefinition);
    }

    public boolean canBlockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition magicDefinition){
        return  electromagneticDriverBE.getLevel().registryAccess().lookupOrThrow(MoeRegistries.MAGIC_KEY).getOrThrow(magicDefinition.behaviorKey()).value().canBlockCast(electromagneticDriverBE, magicDefinition);
    }

    public Component getTranslate(MagicDefinition magicDefinition){
        return Component.translatable(magicDefinition.translationKey());
    }

    @Override
    public boolean isEmpty() {
        return isEmpty;
    }
}
