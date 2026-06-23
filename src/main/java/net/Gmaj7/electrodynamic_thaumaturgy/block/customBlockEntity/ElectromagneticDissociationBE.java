package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.menu.ElectromagneticDissociationBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.EnergySetPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.EtRecipes;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom.ElectromagneticDissociationRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom.ElectromagneticDissociationRecipeInput;
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
import net.minecraft.world.item.Items;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ElectromagneticDissociationBE extends BlockEntity implements IEnergyBlockEntity, IDirectionItemBlockEntity, MenuProvider{

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

    private final BlockEntityItemHandler itemHandlerOutput = new BlockEntityItemHandler(3){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private final BlockEntityItemHandler itemHandlerCatalyst = new BlockEntityItemHandler(1) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide())
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    };

    protected final ContainerData data = new ContainerData() {
        {
            Objects.requireNonNull(ElectromagneticDissociationBE.this);
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
                case 0 -> ElectromagneticDissociationBE.this.directionOutputSet = Function.decodeDirection(value);
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    private Map<Direction, Boolean> directionOutputSet = new HashMap<>();
    private static final int tickUse = 1024;

    public ElectromagneticDissociationBE(BlockPos worldPosition, BlockState blockState) {
        super(EtBlockEntities.ELECTROMAGNETIC_DISSOCIATION_BE.get(), worldPosition, blockState);
        directionOutputSet = Function.decodeDirection(0x03);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ElectromagneticDissociationBE blockEntity){
        if(level.isClientSide()) return;
        if(blockEntity.energy.getAmountAsLong() < tickUse) return;
        ServerLevel serverLevel = (ServerLevel)level;
        ElectromagneticDissociationRecipeInput input = new ElectromagneticDissociationRecipeInput(blockEntity.getInput());

        RecipeHolder<ElectromagneticDissociationRecipe> recipe = serverLevel.recipeAccess()
                .getRecipeFor(EtRecipes.ELECTROMAGNETIC_DISSOCIATION_RECIPE_TYPE.get(), input, serverLevel)
                .orElse(null);
        if(recipe != null && (!recipe.value().needCatalyst() || blockEntity.itemHandlerCatalyst.getStackInSlot(0).is(Items.NETHER_STAR))){
            List<ItemStackTemplate> result = recipe.value().outputs();
            if(!result.isEmpty())
                try (Transaction transaction = Transaction.openRoot()){
                    boolean commit = true;
                    for (int i = 0; i < result.size(); i ++){
                        int insert = blockEntity.itemHandlerOutput.insert(i, ItemResource.of(result.get(i)), result.get(i).count(), transaction);
                        if(insert != result.get(i).count()) {
                            commit = false;
                            break;
                        }
                    }
                    if(commit && !blockEntity.itemHandlerInput.getStackInSlot(0).isEmpty()) {
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
        output.putInt("direction_set", Function.encodeDirection(directionOutputSet));
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        itemHandlerInput.deserializeWithKey("input_item", input);
        itemHandlerOutput.deserializeWithKey("output_item", input);
        energy.deserialize(input);
        directionOutputSet = Function.decodeDirection(input.getIntOr("direction_set", 0x03));
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
        SimpleContainer container = new SimpleContainer(itemHandlerInput.size() + itemHandlerOutput.size() + itemHandlerCatalyst.size());
        container.setItem(0, itemHandlerInput.getStackInSlot(0));
        for (int i = 0; i < itemHandlerOutput.size(); i ++) {
            if(itemHandlerOutput.getStackInSlot(i).isEmpty()) continue;
            container.setItem(i + 1, itemHandlerOutput.getStackInSlot(i));
        }
        container.setItem(4, itemHandlerCatalyst.getStackInSlot(0));
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

    public BlockEntityItemHandler getItemHandlerInput() {
        return itemHandlerInput;
    }

    public BlockEntityItemHandler getItemHandlerOutput() {
        return itemHandlerOutput;
    }

    @Override
    public void changeItemDirectionSet(Direction direction){
        this.directionOutputSet.put(direction, !directionOutputSet.get(direction));
        setChanged();
        if(!level.isClientSide())
            invalidateCapabilities();
    }

    public BlockEntityItemHandler getItemHandlerCatalyst() {
        return itemHandlerCatalyst;
    }

    public ContainerData getData() {
        return data;
    }
}
