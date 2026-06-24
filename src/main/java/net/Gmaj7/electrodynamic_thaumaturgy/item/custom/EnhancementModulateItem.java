package net.Gmaj7.electrodynamic_thaumaturgy.item.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.EnhancementData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.List;
import java.util.function.Consumer;

public class EnhancementModulateItem extends Item{
    public EnhancementModulateItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        EnhancementData enhancementData = itemStack.get(EtDataComponentTypes.ENHANCEMENT_DATA);
        builder.accept(Component.translatable("item.electrodynamic_thaumaturgy.cooldown_enhance").append(":" + enhancementData.coolDown()));
        builder.accept(Component.translatable("item.electrodynamic_thaumaturgy.strength_enhance").append(":" + enhancementData.strength()));
        builder.accept(Component.translatable("item.electrodynamic_thaumaturgy.efficiency_enhance").append(":" + enhancementData.efficiency()));
    }
}
