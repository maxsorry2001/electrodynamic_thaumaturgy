package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;

public abstract class MoeBlockEntityFluidHandler extends FluidStacksResourceHandler {

    public MoeBlockEntityFluidHandler(int size, int capacity) {
        super(size, capacity);
    }

    public FluidStack getStackInSlot(int slot){
        return stacks.get(slot);
    }

    public void setStackInSlot(int slot, FluidStack fluidStack){
        stacks.set(slot, fluidStack);
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
