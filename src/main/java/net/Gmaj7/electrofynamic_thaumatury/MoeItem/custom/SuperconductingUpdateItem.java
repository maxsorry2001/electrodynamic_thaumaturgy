package net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeItem.MoeItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SuperconductingUpdateItem extends Item {
    public SuperconductingUpdateItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(usedHand == InteractionHand.MAIN_HAND && player.getOffhandItem().getItem() instanceof ElectromagneticTierItem item){
            if(item instanceof LcOscillatorModuleItem) player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(MoeItems.SUPERCONDUCTING_LC.get()));
            else if(item instanceof PowerAmplifierItem) player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(MoeItems.SUPERCONDUCTING_POWER.get()));
            player.swing(usedHand);
            return InteractionResultHolder.success(player.getMainHandItem());
        }
        else return InteractionResultHolder.fail(player.getItemInHand(usedHand));
    }
}
