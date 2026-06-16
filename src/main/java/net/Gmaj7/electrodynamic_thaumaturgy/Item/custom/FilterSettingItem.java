package net.Gmaj7.electrodynamic_thaumaturgy.Item.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.Gui.menu.FilterSettingMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

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
