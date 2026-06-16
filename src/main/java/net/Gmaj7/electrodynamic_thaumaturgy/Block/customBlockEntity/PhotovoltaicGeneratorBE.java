package net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.BlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.EnergySetPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class PhotovoltaicGeneratorBE extends AbstractGeneratorBE {
    private final BlockEntityEnergyHandler energy = new BlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };
    public PhotovoltaicGeneratorBE(BlockPos pos, BlockState blockState) {
        super(EtBlockEntities.PHOTOVOLTAIC_GENERATOR_BE.get(), pos, blockState);
    }

    @Override
    protected void energyMake(AbstractGeneratorBE blockEntity) {
        int i = getLight(level.getEffectiveSkyBrightness(getBlockPos()));
        try(Transaction transaction = Transaction.openRoot()) {
            int insert = blockEntity.getEnergy().insert(i * 64, transaction);
            if(insert > 0) transaction.commit();
        }
    }

    protected boolean canEnergyMake() {
        if(!level.getBlockState(getBlockPos().above()).isAir() || !level.canSeeSky(getBlockPos().above())) return false;
        int lightSky = getLight(level.getEffectiveSkyBrightness(getBlockPos().above()));
        return lightSky > 0;
    }

    private int getLight(int light){
        int returnLight = light;
        if(light > 0) {
            float sunAngle = level.environmentAttributes().getValue(EnvironmentAttributes.SUN_ANGLE, getBlockPos()) * Mth.PI / 180F;
            float offSet = sunAngle < (float) Math.PI ? 0.0F : ((float) Math.PI * 2F);
            sunAngle += (offSet - sunAngle) * 0.2F;
            returnLight = Math.round((float)light * Mth.cos(sunAngle));
        }
        return Mth.clamp(returnLight, 0, 15);
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
