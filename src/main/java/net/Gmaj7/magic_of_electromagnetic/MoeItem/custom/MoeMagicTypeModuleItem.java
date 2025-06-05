package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.Gmaj7.magic_of_electromagnetic.MoeRecipe.MagicLithographyRecipe;
import net.Gmaj7.magic_of_electromagnetic.MoeRecipe.MagicLithographyRecipeInput;
import net.Gmaj7.magic_of_electromagnetic.MoeRecipe.MoeRecipes;
import net.Gmaj7.magic_of_electromagnetic.magic.IMoeMagic;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class MoeMagicTypeModuleItem extends Item implements IMoeModuleItem{
    private final IMoeMagic magic;
    public MoeMagicTypeModuleItem(@Nullable IMoeMagic magicType, Properties properties) {
        super(properties);
        this.magic = magicType;
    }

    public MoeMagicType getMagicType() {
        if(!isEmpty()) return this.magic.getType();
        else return MoeMagicType.EMPTY;
    }

    public boolean isEmpty(){
        return this.magic == null;
    }

    public void cast(LivingEntity livingEntity, ItemStack itemStack){
        this.magic.cast(livingEntity, itemStack);
    }

    public int getBaseEnergyCost(){
        return this.magic.getBaseEnergyCost();
    }

    public int getBaseCooldown(){
        return this.magic.getBaseCooldown();
    }

    public boolean success(LivingEntity livingEntity, ItemStack itemStack){return magic.success(livingEntity, itemStack);}
}
