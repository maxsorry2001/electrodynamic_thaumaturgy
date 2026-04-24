package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.IMoeMagic;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class MoeMagicTypeModuleItem extends Item implements IMoeModuleItem{
    private final IMoeMagic magic;
    public MoeMagicTypeModuleItem(@Nullable IMoeMagic magicType, Properties properties) {
        super(properties);
        this.magic = magicType;
    }

    public boolean isEmpty(){
        return this.magic == null;
    }

    public void cast(LivingEntity livingEntity, ItemStack itemStack){
        this.magic.playerCast(livingEntity, itemStack);
    }

    public int getBaseEnergyCost(){
        return this.magic.getBaseEnergyCost();
    }

    public int getBaseCooldown(){
        return this.magic.getBaseCooldown();
    }

    public boolean success(LivingEntity livingEntity, ItemStack itemStack){return magic.success(livingEntity, itemStack);}

        @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }

    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE){
        magic.blockCast(electromagneticDriverBE);
    }

    public boolean canBlockCast(ElectromagneticDriverBE electromagneticDriverBE){
        return magic.canBlockCast(electromagneticDriverBE);
    }

    public Component getTranslate(){
        return Component.translatable(magic.getTranslate());
    }
}
