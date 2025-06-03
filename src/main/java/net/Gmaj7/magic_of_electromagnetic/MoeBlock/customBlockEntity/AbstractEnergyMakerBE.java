package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractEnergyMakerBE extends BlockEntity implements IMoeEnergyBlockEntity{
    public AbstractEnergyMakerBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AbstractEnergyMakerBE abstractEnergyMakerBE){
        if(abstractEnergyMakerBE.canEnergyMake()) {
            abstractEnergyMakerBE.energyMake(abstractEnergyMakerBE);
        }
    }

    protected abstract void energyMake(AbstractEnergyMakerBE blockEntity);

    protected abstract boolean canEnergyMake();
}
