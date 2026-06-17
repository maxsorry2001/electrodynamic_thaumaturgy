package net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.menu.MagnetoFusionBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.BlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.BlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.EnergySetPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.custom.MagnetoFusionRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.custom.MagnetoFusionRecipeInput;
import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.EtRecipes;
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
import net.minecraft.world.item.crafting.Ingredient;
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

import java.util.*;

public class MagnetoFusionBE extends BlockEntity implements IEnergyBlockEntity, IDirectionItemBlockEntity, MenuProvider {
    private final BlockEntityEnergyHandler energy = new BlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };

    private final BlockEntityItemHandler itemHandlerInput = new BlockEntityItemHandler(3){

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
            Objects.requireNonNull(MagnetoFusionBE.this);
        }
        @Override
        public int get(int i) {
            return switch (i){
                case 0 -> Function.encodeDirection(directionOutputSet);
                default -> 0;
            };
        }

        @Override
        public void set(int dataId, int value) {
            switch (dataId){
                case 0 -> MagnetoFusionBE.this.directionOutputSet = Function.decodeDirection(value);
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };
    private static final int tickUse = 128;

    private Map<Direction, Boolean> directionOutputSet = new HashMap<>();

    public MagnetoFusionBE(BlockPos worldPosition, BlockState blockState) {
        super(EtBlockEntities.MAGNETO_FUSION_BE.get(), worldPosition, blockState);
        this.directionOutputSet = Function.decodeDirection(0x03);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagnetoFusionBE magnetoFusionBE){
        if(level.isClientSide()) return;
        ServerLevel serverLevel = (ServerLevel)level;
        MagnetoFusionRecipeInput input = new MagnetoFusionRecipeInput(magnetoFusionBE.getInputs());

        RecipeHolder<MagnetoFusionRecipe> recipe = serverLevel.recipeAccess()
                .getRecipeFor(EtRecipes.MAGNO_FUSION_TYPE.get(), input, serverLevel)
                .orElse(null);
        if(recipe != null){
            ItemStack result = recipe.value().assemble(input);
            if (!result.isItemEnabled(serverLevel.enabledFeatures())) {
                result = ItemStack.EMPTY;
            }
            if(!result.isEmpty())
                try (Transaction transaction = Transaction.openRoot()){
                    int insert = magnetoFusionBE.itemHandlerOutput.insert(0, ItemResource.of(result), result.count(), transaction), energyUse = magnetoFusionBE.energy.extract(tickUse, transaction);
                    if(insert > 0 && energyUse == tickUse){
                        for (int i = 0; i < 3; i++)
                            if(!magnetoFusionBE.itemHandlerInput.getStackInSlot(i).isEmpty())
                                magnetoFusionBE.itemHandlerInput.extract(i, magnetoFusionBE.itemHandlerInput.getResource(i), 1, transaction);
                        transaction.commit();
                    }
                }
        }
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
        try (Transaction transaction = Transaction.openRoot()){
            this.energy.setEnergy(i);
            transaction.commit();
        }
    }

    @Override
    public StacksResourceHandler<ItemStack, ItemResource> getItemHandlerWithDirection(Direction direction) {
        if(direction == null) return getItemHandlerOutput();
        return directionOutputSet.getOrDefault(direction, true) ? getItemHandlerOutput() : getItemHandlerInput();
    }

    public BlockEntityItemHandler getItemHandlerInput() {
        return itemHandlerInput;
    }

    public BlockEntityItemHandler getItemHandlerOutput() {
        return itemHandlerOutput;
    }

    @Override
    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandlerInput.size() + itemHandlerOutput.size());
        container.setItem(0, itemHandlerInput.getStackInSlot(0));
        container.setItem(0, itemHandlerOutput.getStackInSlot(0));
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.electrodynamic_thaumaturgy.magneto_fusion_machine");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MagnetoFusionBlockMenu(i, inventory, this);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        this.drops();
    }

    public List<Ingredient> getIngredients(){
        List<Ingredient> list = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ItemStack itemStack = itemHandlerInput.getStackInSlot(i);
            if(!itemStack.isEmpty()) list.add(Ingredient.of(itemStack.getItem()));
        }
        return list;
    }

    public List<ItemStack> getInputs(){
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ItemStack itemStack = itemHandlerInput.getStackInSlot(i);
            if(!itemStack.isEmpty()) list.add(itemStack);
        }
        return list;
    }

    @Override
    public void changeDirectionSet(Direction direction){
        this.directionOutputSet.put(direction, !directionOutputSet.get(direction));
        setChanged();
        if(!level.isClientSide())
            invalidateCapabilities();
    }

    public ContainerData getData() {
        return data;
    }
}
