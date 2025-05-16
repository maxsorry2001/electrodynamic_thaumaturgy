package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyBlockEntity extends BlockEntity {
    public EnergyBlockEntity(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.ENERGY_BLOCK_BE.get(), pos, blockState);
    }
}
