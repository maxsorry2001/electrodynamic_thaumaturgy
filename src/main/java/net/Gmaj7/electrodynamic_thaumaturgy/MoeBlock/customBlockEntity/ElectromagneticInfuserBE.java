package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.ElectromagneticInfuserBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityFluidHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePackets.EnergySetPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.ElectromagneticInfusionRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.ElectromagneticInfusionRecipeInput;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MoeRecipes;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ElectromagneticInfuserBE extends BlockEntity implements IMoeEnergyBlockEntity, IMoeDirectionItemBlockEntity, IMoeDirectionFluidBlockEntity, MenuProvider{

    private final MoeBlockEntityEnergyHandler energy = new MoeBlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };

    private final MoeBlockEntityItemHandler itemHandlerInput = new MoeBlockEntityItemHandler(1){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private final MoeBlockEntityFluidHandler fluidHandlerInput = new MoeBlockEntityFluidHandler(1, 5000) {

        @Override
        protected void onContentsChanged(int index, FluidStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private final MoeBlockEntityItemHandler itemHandlerOutput = new MoeBlockEntityItemHandler(3){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private Map<Direction, Boolean> directionOutputSet = new HashMap<>();
    private static final int tickUse = 1024;

    public ElectromagneticInfuserBE(BlockPos worldPosition, BlockState blockState) {
        super(MoeBlockEntities.ELECTROMAGNETIC_INFUSER_BE.get(), worldPosition, blockState);
        directionOutputSet = MoeFunction.decodeDirection(0x03);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ElectromagneticInfuserBE blockEntity){
        if(level.isClientSide()) return;
        if(blockEntity.energy.getAmountAsLong() < tickUse) return;
        ServerLevel serverLevel = (ServerLevel)level;
        ElectromagneticInfusionRecipeInput input = new ElectromagneticInfusionRecipeInput(blockEntity.getInputItem(), blockEntity.getInputFluid());

        RecipeHolder<ElectromagneticInfusionRecipe> recipe = serverLevel.recipeAccess()
                .getRecipeFor(MoeRecipes.ELECTROMAGNETIC_INFUSION_RECIPE_TYPE.get(), input, serverLevel)
                .orElse(null);
        if(recipe != null && (recipe.value().fluidCost() <= blockEntity.getInputFluid().amount())){
            ItemStackTemplate result = recipe.value().output();
            try (Transaction transaction = Transaction.openRoot()){
                boolean commit = true;
                int insert = blockEntity.itemHandlerOutput.insert(0, ItemResource.of(result), result.count(), transaction);
                if(insert != result.count()) {
                    commit = false;
                }
                if(commit && !blockEntity.itemHandlerInput.getStackInSlot(0).isEmpty()) {
                    blockEntity.itemHandlerInput.extract(0, blockEntity.itemHandlerInput.getResource(0), 1, transaction);
                    blockEntity.fluidHandlerInput.extract(0, blockEntity.fluidHandlerInput.getResource(0), recipe.value().fluidCost(), transaction);
                    transaction.commit();
                }
            }
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        itemHandlerInput.serializeWithKey("input_item", output);
        itemHandlerOutput.serializeWithKey("output_item", output);
        energy.serialize(output);
        fluidHandlerInput.serialize(output);
        output.putInt("direction_set", MoeFunction.encodeDirection(directionOutputSet));
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        itemHandlerInput.deserializeWithKey("input_item", input);
        itemHandlerOutput.deserializeWithKey("output_item", input);
        energy.deserialize(input);
        fluidHandlerInput.deserialize(input);
        directionOutputSet = MoeFunction.decodeDirection(input.getIntOr("direction_set", 0x03));
    }

    @Override
    public EnergyHandler getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
    }

    public ItemStack getInputItem(){
        return itemHandlerInput.getStackInSlot(0).isEmpty() ? ItemStack.EMPTY : itemHandlerInput.getStackInSlot(0);
    }

    public FluidStack getInputFluid(){
        return fluidHandlerInput.getStackInSlot(0).isEmpty() ? FluidStack.EMPTY : fluidHandlerInput.getStackInSlot(0);
    }

    @Override
    public StacksResourceHandler<ItemStack, ItemResource> getItemHandlerWithDirection(Direction direction) {
        if(direction == null) return getItemHandlerOutput();
        return directionOutputSet.getOrDefault(direction, true) ? getItemHandlerOutput() : getItemHandlerInput();
    }

    @Override
    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandlerInput.size() + itemHandlerOutput.size());
        container.setItem(0, itemHandlerInput.getStackInSlot(0));
        container.setItem(1, itemHandlerOutput.getStackInSlot(0));
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.electrodynamic_thaumaturgy.energy_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ElectromagneticInfuserBlockMenu(i, inventory, this);
    }

    public MoeBlockEntityItemHandler getItemHandlerInput() {
        return itemHandlerInput;
    }

    public MoeBlockEntityItemHandler getItemHandlerOutput() {
        return itemHandlerOutput;
    }

    @Override
    public void changeDirectionSet(Direction direction){
        this.directionOutputSet.put(direction, !directionOutputSet.get(direction));
        setChanged();
        if(!level.isClientSide())
            invalidateCapabilities();
    }

    @Override
    public StacksResourceHandler<FluidStack, FluidResource> getFluidHandlerWithDirection(Direction direction) {
        return fluidHandlerInput;
    }
}
