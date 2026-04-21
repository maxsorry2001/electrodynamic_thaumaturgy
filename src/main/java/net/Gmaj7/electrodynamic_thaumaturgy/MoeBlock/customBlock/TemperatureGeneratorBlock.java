package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.TemperatureGeneratorBE;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class TemperatureGeneratorBlock extends AbstractGeneratorBlock {
    public static final MapCodec<TemperatureGeneratorBlock> CODEC = simpleCodec(TemperatureGeneratorBlock::new);
    public static final EnumProperty<WorkType> WORK_TYPE = EnumProperty.create("work_type", WorkType.class);
    public TemperatureGeneratorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WORK_TYPE, WorkType.NORMAL));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WORK_TYPE);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == MoeBlockEntities.TEMPERATURE_GENERATOR_BLOCK_BE.get() ? createTickerHelper(blockEntityType, MoeBlockEntities.TEMPERATURE_GENERATOR_BLOCK_BE.get(), TemperatureGeneratorBE::tick) : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TemperatureGeneratorBE(blockPos, blockState);
    }

    public enum WorkType implements StringRepresentable {
        NORMAL("normal"),
        HOT("hot"),
        COLD("cold"),
        WORK_A("work_a"),
        WORK_B("work_b"),;

        private final String name;

        private WorkType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.getSerializedName();
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
