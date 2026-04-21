package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class PhotovoltaicGeneratorBE extends AbstractGeneratorBE {
    private final MoeBlockEntityEnergyHandler energy = new MoeBlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };
    public PhotovoltaicGeneratorBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.PHOTOVOLTAIC_GENERATOR_BE.get(), pos, blockState);
    }

    @Override
    protected void energyMake(AbstractGeneratorBE blockEntity) {
        int i = Math.max( level.getBrightness(LightLayer.SKY, getBlockPos().above()) - level.getSkyDarken(),  level.getBrightness(LightLayer.BLOCK, getBlockPos().above()));
        try(Transaction transaction = Transaction.openRoot()) {
            int insert = blockEntity.getEnergy().insert(i * 64, transaction);
            if(insert > 0) transaction.commit();
        }
    }

    protected boolean canEnergyMake() {
        int blockLight = level.getBrightness(LightLayer.BLOCK, getBlockPos().above());
        return blockLight > 0;
    }

    @Override
    public EnergyHandler getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        energy.serialize(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energy.deserialize(input);
    }
}
