package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeGeologicalMetalExcavatorBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class GeologicalMetalExcavatorBE extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity, MenuProvider {
    private static int fullTick = 20;
    private int excavatorTick = 0;
    private static int tickUse =1024;
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(1048576) {
        @Override
        public void change(int i) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };

    private final ItemStackHandler itemHandler = new ItemStackHandler(27){

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public GeologicalMetalExcavatorBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.GEOLOGICAL_METAL_EXCAVATOR_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GeologicalMetalExcavatorBE geologicalMetalExcavatorBE){
        if(level.isClientSide() || geologicalMetalExcavatorBE.getEnergy().getEnergyStored() < tickUse) return;
        geologicalMetalExcavatorBE.excavatorTick ++;
        geologicalMetalExcavatorBE.getEnergy().extractEnergy(tickUse, false);
        if(geologicalMetalExcavatorBE.excavatorTick < fullTick) return;
        geologicalMetalExcavatorBE.excavatorTick = 0;
        String path;
        ResourceKey dimension = level.dimension();
        if(dimension == null) return;
        else if (dimension == Level.NETHER) path = "nether";
        //else if (dimension == Level.END) path = "end";
        else  path = "overworld";
        LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "machine/metal_extract_" + path)));
        LootParams lootParams = new LootParams.Builder((ServerLevel) level).create(LootContextParamSets.EMPTY);
        List<ItemStack> list = lootTable.getRandomItems(lootParams);
        Iterator<ItemStack> iterator = list.iterator();
        while(iterator.hasNext()){
            ItemStack itemStack = iterator.next().copy();
            for (int i = 0; i < geologicalMetalExcavatorBE.itemHandler.getSlots(); i++) {
                ItemStack result = geologicalMetalExcavatorBE.getItemHandler().insertItem(i, itemStack, false);
                if(!result.isEmpty()) itemStack = result.copy();
                else break;
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getEnergyStored());
        tag.put("item_handler", itemHandler.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        setEnergy(tag.getInt("energy"));
        itemHandler.deserializeNBT(registries, tag.getCompound("item_handler"));
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public IEnergyStorage getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
    }

    @Override
    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.electrodynamic_thaumaturgy.energy_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MoeGeologicalMetalExcavatorBlockMenu(i, inventory, this);
    }
}
