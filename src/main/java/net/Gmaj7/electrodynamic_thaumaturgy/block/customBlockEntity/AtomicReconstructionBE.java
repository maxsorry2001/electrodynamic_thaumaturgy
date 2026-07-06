package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.menu.AtomicReconstructionBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.EnergySetPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.EtRecipes;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom.AtomicReconstructionRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom.AtomicReconstructionRecipeInput;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class AtomicReconstructionBE extends BlockEntity implements IEnergyBlockEntity, MenuProvider, IDirectionItemBlockEntity {
    private final BlockEntityEnergyHandler energy = new BlockEntityEnergyHandler(1048576) {
        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };

    private final BlockEntityItemHandler itemHandlerInput = new BlockEntityItemHandler(1){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private final BlockEntityItemHandler itemHandlerOutput = new BlockEntityItemHandler(1){

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
            Objects.requireNonNull(AtomicReconstructionBE.this);
        }
        @Override
        public int get(int i) {
            return switch (i){
                case 0 -> progress;
                case 1 -> maxProgress;
                case 2 -> Function.encodeDirection(directionOutputSet);
                default -> 0;
            };
        }

        @Override
        public void set(int dataId, int value) {
            switch (dataId){
                case 0 -> AtomicReconstructionBE.this.progress = value;
                case 1 -> AtomicReconstructionBE.this.maxProgress = value;
                case 2 -> AtomicReconstructionBE.this.directionOutputSet = Function.decodeDirection(value);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };
    private int progress = 0;
    private int maxProgress;
    private Map<Direction, Boolean> directionOutputSet;
    public AtomicReconstructionBE(BlockPos pos, BlockState blockState) {
        super(EtBlockEntities.ATOMIC_RECONSTRUCTION_BE.get(), pos, blockState);
        this.directionOutputSet = Function.decodeDirection(0x03);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AtomicReconstructionBE blockEntity) {
        if(level.isClientSide()) return;
        ServerLevel serverLevel = (ServerLevel)level;
        AtomicReconstructionRecipeInput input = new AtomicReconstructionRecipeInput(blockEntity.getInput());

        RecipeHolder<AtomicReconstructionRecipe> recipeHolder = serverLevel.recipeAccess()
                .getRecipeFor(EtRecipes.ATOMIC_RECONSTRUCTION_RECIPE_TYPE.get(), input, serverLevel)
                .orElse(null);
        if(recipeHolder != null){
            AtomicReconstructionRecipe recipe = recipeHolder.value();
            if(blockEntity.maxProgress != recipe.tickUse()) blockEntity.maxProgress = recipe.tickUse();
            try (Transaction transaction = Transaction.openRoot()) {
                blockEntity.energy.extract(Math.max(1, recipe.energyUse() / recipe.tickUse()), transaction);
                if (blockEntity.progress + 1 >= blockEntity.maxProgress) {
                    ItemStackTemplate result = recipe.output();
                    int insert = blockEntity.itemHandlerOutput.insert(0, ItemResource.of(result), result.count(), transaction);
                    if (insert == result.count()) {
                        int extracted = blockEntity.itemHandlerInput.extract(0, blockEntity.itemHandlerInput.getResource(0), recipe.itemCost(), transaction);
                        if (extracted == recipe.itemCost()) {
                            transaction.commit();
                            blockEntity.progress = 0;
                            setChanged(level, pos, state);
                        }
                    }
                }
                else {
                    transaction.commit();
                    blockEntity.progress++;
                    setChanged(level, pos, state);
                }
            }
        }
        else {
            blockEntity.maxProgress = 0;
            blockEntity.progress = 0;
        }
    }

    private ItemStack getInput() {
        return itemHandlerInput.getStackInSlot(0);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energy.deserialize(input);
        itemHandlerInput.deserializeWithKey("input_item", input);
        itemHandlerInput.deserializeWithKey("output_item", input);
        directionOutputSet = Function.decodeDirection(input.getIntOr("direction_set", 0x03));
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        energy.serialize(output);
        itemHandlerInput.serializeWithKey("input_item", output);
        itemHandlerInput.serializeWithKey("output_item", output);
        output.putInt("direction_set", Function.encodeDirection(directionOutputSet));
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
    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandlerInput.getStackInSlot(0), itemHandlerOutput.getStackInSlot(0));
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public void changeItemDirectionSet(Direction direction){
        this.directionOutputSet.put(direction, !directionOutputSet.get(direction));
        setChanged();
        if(!level.isClientSide())
            invalidateCapabilities();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.electrodynamic_thaumaturgy.energy_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new AtomicReconstructionBlockMenu(i, inventory, this);
    }

    @Override
    public StacksResourceHandler<ItemStack, ItemResource> getItemHandlerWithDirection(Direction direction){
        if(direction == null) return itemHandlerOutput;
        return directionOutputSet.getOrDefault(direction, true) ? itemHandlerOutput : itemHandlerInput;
    }

    public float getProgressPer(){
        return maxProgress == 0 ? 0 : (float) progress / maxProgress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public ContainerData getData() {
        return data;
    }

    public BlockEntityItemHandler getItemHandlerInput() {
        return itemHandlerInput;
    }

    public BlockEntityItemHandler getItemHandlerOutput() {
        return itemHandlerOutput;
    }
}
