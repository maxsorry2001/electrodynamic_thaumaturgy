package net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrofynamic_thaumatury.magic.IMoeMagic;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nullable;
import java.util.List;

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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
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
