package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

public abstract class MoeBlockEntityItemHandler extends ItemStacksResourceHandler {
    public MoeBlockEntityItemHandler(int size) {
        super(size);
    }

    public ItemStack getStackInSlot(int slot){
        return stacks.get(slot);
    }

    public void setStackInSlot(int slot, ItemStack itemStack){
        stacks.set(slot, itemStack);
    }

    public void serializeWithKey(String key, ValueOutput output) {
        output.store(key, codec, stacks);
    }

    public void deserializeWithKey(String key, ValueInput input) {
        input.read(key, codec).ifPresent(l -> {
            setStacks(l);
        });
    }
}
