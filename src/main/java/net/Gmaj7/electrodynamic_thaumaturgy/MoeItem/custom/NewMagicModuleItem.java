package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeRegistries;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinitionLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NewMagicModuleItem extends Item {

    public NewMagicModuleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide()) return InteractionResult.CONSUME;
        cast((ServerLevel) level, player, player.getItemInHand(hand));
        return InteractionResult.SUCCESS;
    }

    public void cast(ServerLevel serverLevel, Player caster, ItemStack itemStack){
        Identifier key = itemStack.get(MoeDataComponentTypes.MAGIC_DEF_LOCATION);
        if(key == null) return;
        MagicDefinition definition = MagicDefinitionLoader.get(key);
        serverLevel.registryAccess().lookupOrThrow(MoeRegistries.MAGIC_KEY).getOrThrow(definition.behaviorKey()).value().playerCast(caster, itemStack, definition);
    }
}
