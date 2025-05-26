package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;


public class WirelessEnergySendBE extends BlockEntity {
    private final int tickEnergyTranslate = 1024;

    private final ItemStackHandler itemHandler = new ItemStackHandler(2){
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 2;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    public WirelessEnergySendBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.WIRELESS_ENERGY_SEND_BE.get(), pos, blockState);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, WirelessEnergySendBE energyBlockEntity){

    }
}
