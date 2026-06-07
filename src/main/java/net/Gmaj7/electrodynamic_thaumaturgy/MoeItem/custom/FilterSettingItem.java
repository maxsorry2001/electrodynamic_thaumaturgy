package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.FilterSettingMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterSettingItem extends Item {
    public FilterSettingItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND && player.getMainHandItem().getItem() instanceof FilterSettingItem) {
            player.openMenu(new SimpleMenuProvider((id, inv, p) -> new FilterSettingMenu(id, inv, p.getMainHandItem()), Component.empty()));
            return InteractionResult.CONSUME;
        }
        return super.use(level, player, hand);
    }
}
