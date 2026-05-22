package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.ElectromagneticDissociationBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.*;
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
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ElectromagneticDissociationBE extends BlockEntity implements IMoeEnergyBlockEntity, IMoeDirectionItemBlockEntity, MenuProvider{

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

    private Map<Direction, Boolean> directionOutputSet = new HashMap<>();
    private static final int tickUse = 1024;

    public ElectromagneticDissociationBE(BlockPos worldPosition, BlockState blockState) {
        super(MoeBlockEntities.ELECTROMAGNETIC_DISSOCIATION_BE.get(), worldPosition, blockState);
        directionOutputSet = MoeFunction.decodeDirection(0x03);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ElectromagneticDissociationBE blockEntity){
        if(level.isClientSide()) return;
        ServerLevel serverLevel = (ServerLevel)level;
        ElectromagneticDissociationRecipeInput input = new ElectromagneticDissociationRecipeInput(blockEntity.getInput());

        RecipeHolder<ElectromagneticDissociationRecipe> recipe = serverLevel.recipeAccess()
                .getRecipeFor(MoeRecipes.ELECTROMAGNETIC_DISSOCIATION_RECIPE_TYPE.get(), input, serverLevel)
                .orElse(null);
        if(recipe != null){
            ItemStack result = recipe.value().assemble(input);
            if (!result.isItemEnabled(serverLevel.enabledFeatures())) {
                result = ItemStack.EMPTY;
            }
            if(!result.isEmpty())
                try (Transaction transaction = Transaction.openRoot()){
                    int insert = blockEntity.itemHandlerOutput.insert(0, ItemResource.of(result), result.count(), transaction),
                            energyUse = blockEntity.energy.extract(tickUse, transaction);
                    if(insert == result.count() && energyUse >= tickUse){
                        if(!blockEntity.itemHandlerInput.getStackInSlot(0).isEmpty())
                            blockEntity.itemHandlerInput.extract(0, blockEntity.itemHandlerInput.getResource(0), 1, transaction);
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
        output.putInt("direction_set", MoeFunction.encodeDirection(directionOutputSet));
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        itemHandlerInput.deserializeWithKey("input_item", input);
        itemHandlerOutput.deserializeWithKey("output_item", input);
        energy.deserialize(input);
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

    public ItemStack getInput(){
        return itemHandlerInput.getStackInSlot(0).isEmpty() ? ItemStack.EMPTY : itemHandlerInput.getStackInSlot(0);
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
        return new ElectromagneticDissociationBlockMenu(i, inventory, this);
    }

    public MoeBlockEntityItemHandler getItemHandlerInput() {
        return itemHandlerInput;
    }

    public MoeBlockEntityItemHandler getItemHandlerOutput() {
        return itemHandlerOutput;
    }

    public void changeDirectionSet(Direction direction){
        this.directionOutputSet.put(direction, !directionOutputSet.get(direction));
        setChanged();
        if(!level.isClientSide())
            invalidateCapabilities();
    }
}
