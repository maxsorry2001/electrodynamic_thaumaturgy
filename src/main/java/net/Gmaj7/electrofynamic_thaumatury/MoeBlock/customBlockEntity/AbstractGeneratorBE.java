package net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractGeneratorBE extends BlockEntity implements IMoeEnergyBlockEntity{
    public AbstractGeneratorBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AbstractGeneratorBE abstractGeneratorBE){
        if(abstractGeneratorBE.canEnergyMake()) {
            abstractGeneratorBE.energyMake(abstractGeneratorBE);
        }
    }

    protected abstract void energyMake(AbstractGeneratorBE blockEntity);

    protected abstract boolean canEnergyMake();
}
