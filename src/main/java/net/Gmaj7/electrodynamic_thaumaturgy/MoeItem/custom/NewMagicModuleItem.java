package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeRegistries;
import net.Gmaj7.electrodynamic_thaumaturgy.NewMagicSystem.INewMagic;
import net.Gmaj7.electrodynamic_thaumaturgy.NewMagicSystem.MagicDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
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
        cast(player, player.getItemInHand(hand));
        return InteractionResult.SUCCESS;
    }

    public void cast(LivingEntity caster, ItemStack itemStack){
        Identifier identifier = itemStack.get(MoeDataComponentTypes.MAGIC_DEF_LOCATION);
        if(identifier == null) return;
        ResourceKey<MagicDefinition> resourceKey = ResourceKey.create(MoeRegistries.MAGIC_DEFINITION, identifier);
        MagicDefinition def = ;
        var lookup = caster.level().registryAccess().lookup(MoeRegistries.MAGIC_KEY);
        if(lookup.isEmpty()) return;
        var holder = lookup.get().get(def.behaviorKey());
        if(holder.isEmpty()) return;
        INewMagic magic = holder.get().value();
        magic.playerCast(caster, itemStack, def);
    }
}
