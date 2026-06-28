package net.Gmaj7.electrodynamic_thaumaturgy.item.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.EnhancementData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class EnhancementChipItem extends Item{
    public EnhancementChipItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        EnhancementData enhancementData = itemStack.get(EtDataComponentTypes.ENHANCEMENT_DATA);
        builder.accept(Component.translatable("enhance_chip.electrodynamic_thaumaturgy." + enhancementData.name()));
        if(enhancementData.coolDown() != 0) builder.accept(Component.translatable("enhance_chip.electrodynamic_thaumaturgy.cooldown_enhance").append(":" + enhancementData.coolDown()));
        if(enhancementData.strength() != 0) builder.accept(Component.translatable("enhance_chip.electrodynamic_thaumaturgy.strength_enhance").append(":" + enhancementData.strength()));
        if(enhancementData.efficiency() != 0) builder.accept(Component.translatable("enhance_chip.electrodynamic_thaumaturgy.efficiency_enhance").append(":" + enhancementData.efficiency()));
        if(enhancementData.criticalRate() != 0) builder.accept(Component.translatable("enhance_chip.electrodynamic_thaumaturgy.critical_rate_enhance").append(":" + enhancementData.efficiency()));
        if(enhancementData.criticalDamage() != 0) builder.accept(Component.translatable("enhance_chip.electrodynamic_thaumaturgy.critical_damage_enhance").append(":" + enhancementData.efficiency()));
    }
}
