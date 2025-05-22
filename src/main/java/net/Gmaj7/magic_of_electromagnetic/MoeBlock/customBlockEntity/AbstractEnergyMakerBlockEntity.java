package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractEnergyMakerBlockEntity extends BlockEntity implements IMoeEnergyBlockEntity{
    public AbstractEnergyMakerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AbstractEnergyMakerBlockEntity abstractEnergyMakerBlockEntity){
        if(abstractEnergyMakerBlockEntity.canEnergyMake())
            abstractEnergyMakerBlockEntity.energyMake(abstractEnergyMakerBlockEntity);
    }

    protected abstract void energyMake(AbstractEnergyMakerBlockEntity blockEntity);

    protected abstract boolean canEnergyMake();
}
