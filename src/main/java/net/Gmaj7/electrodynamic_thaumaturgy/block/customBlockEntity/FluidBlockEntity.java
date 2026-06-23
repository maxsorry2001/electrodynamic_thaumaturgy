package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.menu.FluidBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityFluidHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.FluidSetPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;


public class FluidBlockEntity extends BlockEntity implements IDirectionFluidBlockEntity, IDirectionItemBlockEntity, MenuProvider {
    private final BlockEntityItemHandler itemHandler = new BlockEntityItemHandler(2){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private final BlockEntityFluidHandler fluidHandler = new BlockEntityFluidHandler(1, 50000) {
        @Override
        public FluidStack getStackInSlot(int slot) {
            return super.getStackInSlot(slot);
        }

        @Override
        protected void onContentsChanged(int index, FluidStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new FluidSetPacket(getStackInSlot(0), getBlockPos()));
            }
        }
    };

    protected final ContainerData data = new ContainerData() {
        {
            Objects.requireNonNull(FluidBlockEntity.this);
        }
        @Override
        public int get(int i) {
            return switch (i){
                case 0 -> Function.encodeDirection(directionItemOutputSet);
                case 1 -> Function.encodeDirection(directionFluidOutputSet);
                default -> 0;
            };
        }

        @Override
        public void set(int dataId, int value) {
            switch (dataId){
                case 0 -> FluidBlockEntity.this.directionItemOutputSet = Function.decodeDirection(value);
                case 1 -> FluidBlockEntity.this.directionFluidOutputSet = Function.decodeDirection(value);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    private Map<Direction, Boolean> directionItemOutputSet;
    private Map<Direction, Boolean> directionFluidOutputSet;
    public FluidBlockEntity(BlockPos pos, BlockState blockState) {
        super(EtBlockEntities.FLUID_BLOCK_BE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        fluidHandler.serializeWithKey("fluid_handler", output);
        itemHandler.serializeWithKey("item_handler", output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        fluidHandler.deserializeWithKey("fluid_handler", input);
        itemHandler.deserializeWithKey("item_handler", input);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FluidBlockEntity energyBlockEntity){
        BlockEntityItemHandler itemHandler = energyBlockEntity.itemHandler;
        ResourceHandler<FluidResource> fluidHandler = energyBlockEntity.getFluidHandler();
        ItemStack inStack = itemHandler.getStackInSlot(1), outStack = itemHandler.getStackInSlot(0);
        ResourceHandler<FluidResource> inStorage = null, outStorage = null;
        if(!inStack.isEmpty())
            inStorage= inStack.getCapability(Capabilities.Fluid.ITEM, ItemAccess.forHandlerIndex(itemHandler, 1));
        if(!outStack.isEmpty())
            outStorage  = outStack.getCapability(Capabilities.Fluid.ITEM, ItemAccess.forHandlerIndex(itemHandler, 0));
        if(outStorage != null && !outStack.isEmpty() && !fluidHandler.getResource(0).isEmpty()){
            int amount;
            FluidResource resource = fluidHandler.getResource(0);
            if(resource.isEmpty()) return;
            try(Transaction transactionOut = Transaction.openRoot()) {
                amount = Math.min(outStorage.insert(resource, 1000, transactionOut), fluidHandler.extract(resource, 1000, transactionOut));
            }
            if(amount <= 0) return;
            try (Transaction transaction = Transaction.openRoot()){
                outStorage.insert(resource, amount, transaction);
                fluidHandler.extract(resource, amount, transaction);
                transaction.commit();
            }
        }
        if (inStorage != null){
            int amount;
            FluidResource resource = inStorage.getResource(0);
            if(resource.isEmpty()) return;
            try(Transaction transactionIn = Transaction.openRoot()) {
                int e = inStorage.extract(resource, 1000, transactionIn), h = fluidHandler.insert(resource, 1000, transactionIn);
                amount = Math.min(e, h);
            }
            if(amount <= 0) return;
            try(Transaction transaction = Transaction.openRoot()){
                inStorage.extract(resource, amount, transaction);
                fluidHandler.insert(resource, amount, transaction);
                transaction.commit();;
            }
        }
    }
    
    public void clearContents() {
        for (int i = 0; i < itemHandler.size(); i++){
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public BlockEntityFluidHandler getFluidHandler() {
        return fluidHandler;
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.size());
        for (int i = 0; i < itemHandler.size(); i++){
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.electrodynamic_thaumaturgy.energy_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FluidBlockMenu(i, inventory, this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public void handleUpdateTag(ValueInput input) {
        super.handleUpdateTag(input);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return super.getUpdatePacket();
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        this.drops();
    }
    
    @Override
    public StacksResourceHandler<ItemStack, ItemResource> getItemHandlerWithDirection(Direction direction) {
        return itemHandler;
    }

    @Override
    public void changeItemDirectionSet(Direction direction){
        this.directionItemOutputSet.put(direction, !directionItemOutputSet.get(direction));
        setChanged();
        if(!level.isClientSide())
            invalidateCapabilities();
    }

    @Override
    public void changeFluidDirectionSet(Direction direction) {
        this.directionFluidOutputSet.put(direction, !directionFluidOutputSet.get(direction));
        setChanged();
        if(!level.isClientSide())
            invalidateCapabilities();
    }

    @Override
    public StacksResourceHandler<FluidStack, FluidResource> getFluidHandlerWithDirection(Direction direction) {
        return fluidHandler;
    }

    public BlockEntityItemHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public void setFluid(FluidStack fluidStack) {
        this.fluidHandler.setStackInSlot(0, fluidStack);
    }

    public ContainerData getData() {
        return data;
    }
}
