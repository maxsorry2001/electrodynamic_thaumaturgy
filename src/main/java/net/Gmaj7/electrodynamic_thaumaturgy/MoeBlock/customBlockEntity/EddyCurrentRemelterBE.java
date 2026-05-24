package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.EddyCurrentRemelterBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EddyCurrentRemelterBE extends BlockEntity implements IMoeEnergyBlockEntity, IMoeDirectionItemBlockEntity, MenuProvider {

    private final MoeBlockEntityEnergyHandler energy = new MoeBlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };

    private final MoeBlockEntityItemHandler itemHandlerInput = new MoeBlockEntityItemHandler(1){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            ItemStack current = stacks.get(0);
            if(!ItemStack.isSameItem(current, previousContents)){
                progress = 0;
                if(current.isEmpty()) maxProgress = 0;
                else {
                    RecipeHolder<SmeltingRecipe> recipe = getRecipe(getInput());
                    maxProgress = recipe != null ? recipe.value().cookingTime() / 2 : 0;
                }
            }
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private final MoeBlockEntityItemHandler itemHandlerOutput = new MoeBlockEntityItemHandler(1){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    protected final ContainerData data = new ContainerData() {
        {
        Objects.requireNonNull(EddyCurrentRemelterBE.this);
        }
        @Override
        public int get(int i) {
            return switch (i){
                case 0 -> progress;
                case 1 -> maxProgress;
                case 2 -> MoeFunction.encodeDirection(directionOutputSet);
                default -> 0;
            };
        }

        @Override
        public void set(int dataId, int value) {
            switch (dataId){
                case 0 -> EddyCurrentRemelterBE.this.progress = value;
                case 1 -> EddyCurrentRemelterBE.this.maxProgress = value;
                case 2 -> EddyCurrentRemelterBE.this.directionOutputSet = MoeFunction.decodeDirection(value);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    private Map<Direction, Boolean> directionOutputSet;
    private static final int tickUse = 1024;
    private int progress = 0;
    private int maxProgress = 0;
    public EddyCurrentRemelterBE(BlockPos worldPosition, BlockState blockState) {
        super(MoeBlockEntities.EDDY_CURRENT_REMELTER_BE.get(), worldPosition, blockState);
        this.directionOutputSet = MoeFunction.decodeDirection(0x03);
    }

    @Override
    public StacksResourceHandler<ItemStack, ItemResource> getItemHandlerWithDirection(Direction direction) {
        if(direction == null) return itemHandlerOutput;
        return directionOutputSet.getOrDefault(direction, true) ? getItemHandlerOutput() : getItemHandlerInput();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EddyCurrentRemelterBE blockEntity){
        if(level.isClientSide()) return;
        if(blockEntity.itemHandlerInput.getStackInSlot(0).isEmpty()) {
            if(blockEntity.progress != 0) {
                blockEntity.progress = 0;
                blockEntity.maxProgress = 0;
            }
            return;
        }
        ServerLevel serverLevel = (ServerLevel)level;
        SingleRecipeInput input = blockEntity.getInput();

        RecipeHolder<SmeltingRecipe> recipe = blockEntity.getRecipe(input);
        if(recipe == null) return;
        if(blockEntity.maxProgress != recipe.value().cookingTime() / 2) blockEntity.maxProgress = recipe.value().cookingTime() / 2;
        ItemStack result = recipe.value().assemble(input);
        if (result.isEmpty() || !result.isItemEnabled(serverLevel.enabledFeatures())) return;
        try (Transaction transaction = Transaction.openRoot()) {
            //int energyUse = blockEntity.energy.extract(tickUse, transaction);
            //if(energyUse != tickUse) return;
            if (blockEntity.progress + 1 >= blockEntity.maxProgress) {
                int insert = blockEntity.itemHandlerOutput.insert(0, ItemResource.of(result), result.getCount(), transaction);
                if (insert == result.getCount()) {
                    int extracted = blockEntity.itemHandlerInput.extract(0, blockEntity.itemHandlerInput.getResource(0), 1, transaction);
                    if (extracted == 1) {
                        transaction.commit();
                        blockEntity.progress = 0;
                        setChanged(level, pos, state);
                    }
                }
            } else {
                transaction.commit();
                blockEntity.progress++;
                setChanged(level, pos, state);
            }
        }
    }

    private RecipeHolder<SmeltingRecipe> getRecipe(SingleRecipeInput input){
        if(level.isClientSide()) return null;
        return ((ServerLevel)level).recipeAccess()
                .getRecipeFor(RecipeType.SMELTING, input, level)
                .orElse(null);
    }

    private SingleRecipeInput getInput(){
        return new SingleRecipeInput(itemHandlerInput.getStackInSlot(0));
    }

    @Override
    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandlerInput.size() + itemHandlerOutput.size());
        container.setItem(0, itemHandlerInput.getStackInSlot(0));
        container.setItem(1, itemHandlerOutput.getStackInSlot(0));
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public EnergyHandler getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        this.energy.setEnergy(i);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.electrodynamic_thaumaturgy.energy_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new EddyCurrentRemelterBlockMenu(i, ContainerLevelAccess.create(level, getBlockPos()), inventory, this);
    }

    public MoeBlockEntityItemHandler getItemHandlerOutput() {
        return itemHandlerOutput;
    }

    public MoeBlockEntityItemHandler getItemHandlerInput() {
        return itemHandlerInput;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public ContainerData getData() {
        return data;
    }

    @Override
    public void changeDirectionSet(Direction direction){
        this.directionOutputSet.put(direction, !directionOutputSet.get(direction));
        setChanged();
        if(!level.isClientSide())
            invalidateCapabilities();
    }
}
