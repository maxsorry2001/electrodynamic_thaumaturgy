package net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoePacket;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.MoeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;

public class LivingEntityCloneBE extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity{
    private static final int SPAWN_NEED = 32768;

    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(1048576) {
        @Override
        public void change(int i) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };

    private final ItemStackHandler itemHandler = new ItemStackHandler(1){

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public LivingEntityCloneBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.LIVING_ENTITY_CLONE_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, LivingEntityCloneBE livingEntityCloneBE){
        if(livingEntityCloneBE.canSpawn() && !level.isClientSide()){
            ItemStack itemStack = livingEntityCloneBE.getItemHandler().getStackInSlot(0);
            Entity entity =  BuiltInRegistries.ENTITY_TYPE.getOptional(itemStack.get(MoeDataComponentTypes.ENTITY_TYPE)).get().create(level);
            if(entity instanceof LivingEntity) {
                ((LivingEntity) entity).readAdditionalSaveData(itemStack.get(MoeDataComponentTypes.ENTITY_DATA));
                entity.teleportTo(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
                level.addFreshEntity(entity);
                livingEntityCloneBE.getEnergy().extractEnergy(SPAWN_NEED, false);
            }
        }
    }

    private boolean canSpawn() {
        return this.itemHandler.getStackInSlot(0).is(MoeItems.GENETIC_RECORDER.get())
                && this.itemHandler.getStackInSlot(0).has(MoeDataComponentTypes.ENTITY_TYPE)
                && this.itemHandler.getStackInSlot(0).has(MoeDataComponentTypes.ENTITY_DATA)
                && this.energy.getEnergyStored() > SPAWN_NEED;
    }

    @Override
    public IEnergyStorage getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        this.energy.setEnergy(i);
    }

    @Override
    public IItemHandler getItemHandler() {
        return itemHandler;
    }


    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }
}
