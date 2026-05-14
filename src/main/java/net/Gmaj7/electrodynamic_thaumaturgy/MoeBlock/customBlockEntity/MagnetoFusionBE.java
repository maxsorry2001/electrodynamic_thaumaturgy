package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MagnetoFusionBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MagicEncodeRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MagnetoFusionRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MagnetoFusionRecipeInput;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MoeRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MagnetoFusionBE extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity, MenuProvider {
    private final MoeBlockEntityEnergyHandler energy = new MoeBlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };

    private final MoeBlockEntityItemHandler itemHandler = new MoeBlockEntityItemHandler(4){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    public MagnetoFusionBE(BlockPos worldPosition, BlockState blockState) {
        super(MoeBlockEntities.MAGNETO_FUSION_BE.get(), worldPosition, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagnetoFusionBE magnetoFusionBE){
        if(level.isClientSide()) return;
        ServerLevel serverLevel = (ServerLevel)level;
        MagnetoFusionRecipeInput input = new MagnetoFusionRecipeInput(magnetoFusionBE.getInputs());

        RecipeHolder<MagnetoFusionRecipe> recipe = serverLevel.recipeAccess()
                .getRecipeFor(MoeRecipes.MAGNO_FUSION_TYPE.get(), input, serverLevel)
                .orElse(null);
        if(recipe != null){
            ItemStack result = recipe.value().assemble(input);
            if (!result.isItemEnabled(serverLevel.enabledFeatures())) {
                result = ItemStack.EMPTY;
            }
            if(!result.isEmpty())
                try (Transaction transaction = Transaction.openRoot()){
                    int insert = magnetoFusionBE.itemHandler.insert(3, ItemResource.of(result), result.count(), transaction);
                    if(insert > 0){
                        for (int i = 0; i < 3; i++)
                            if(!magnetoFusionBE.itemHandler.getStackInSlot(i).isEmpty())
                                magnetoFusionBE.itemHandler.extract(i, magnetoFusionBE.itemHandler.getResource(i), 1, transaction);
                        transaction.commit();
                    }
                }
        }
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
    public MoeBlockEntityItemHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.size());
        for (int i = 0; i < itemHandler.size(); i++){
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.electrodynamic_thaumaturgy.magneto_fusion_machine_block");
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
            ItemStack itemStack = itemHandler.getStackInSlot(i);
            if(!itemStack.isEmpty()) list.add(Ingredient.of(itemStack.getItem()));
        }
        return list;
    }

    public List<ItemStack> getInputs(){
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ItemStack itemStack = itemHandler.getStackInSlot(i);
            if(!itemStack.isEmpty()) list.add(itemStack);
        }
        return list;
    }
}
