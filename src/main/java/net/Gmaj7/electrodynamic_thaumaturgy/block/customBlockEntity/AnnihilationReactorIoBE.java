package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AnnihilationReactorIoBE extends BlockEntity {
    protected BlockPos corePos;

    public AnnihilationReactorIoBE(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    public BlockPos getCorePos() {
        return corePos;
    }

    public void checkLink(BlockPos corePos){
        if(this.corePos != null) return;
        this.corePos = corePos;
        for (Direction direction : Direction.values()){
            BlockPos checkPos = getBlockPos().relative(direction);
            if(level.getBlockEntity(checkPos) instanceof AnnihilationReactorIoBE annihilationReactorIoBE)
                annihilationReactorIoBE.checkLink(corePos);
        }
    }
}
