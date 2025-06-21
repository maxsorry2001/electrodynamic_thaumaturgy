package net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.MoeBlocks;
import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlock.EnergyTransmissionAtennaBlock;
import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.EnergyTransmissionAntennaBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeMagicType;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.MoeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.List;

public class MagicCastItem extends Item {
    private static final int maxMagicSlots = 10;
    private static final int magicBaseSlots = 2;
    private static final int powerNum = 0;
    private static final int lcNum = 1;
    public MagicCastItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        ItemStack typeStack = getMagic(itemStack);
        MoeMagicType type = getType(itemStack);
        int energy = itemStack.get(MoeDataComponentTypes.MOE_ENERGY.get());
        if(!MoeMagicType.isEmpty(type) && typeStack.getItem() instanceof MoeMagicTypeModuleItem item
                && energy >= item.getBaseEnergyCost() && !player.getCooldowns().isOnCooldown(item)
                && !(usedHand == InteractionHand.OFF_HAND && player.getMainHandItem().getItem() instanceof BatteryItem)
                && item.success(player, itemStack)) {
            item.cast(player, itemStack);
            itemStack.set(MoeDataComponentTypes.MOE_ENERGY, energy - (int)(item.getBaseEnergyCost() * MoeFunction.getEfficiency(itemStack)));
            player.getCooldowns().addCooldown(item, (int) (item.getBaseCooldown() * MoeFunction.getCoolDownRate(itemStack)));
            player.swing(usedHand);
            return InteractionResultHolder.consume(itemStack);
        }
        else return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        ItemStack itemStack = context.getItemInHand();
        BlockState blockState = context.getLevel().getBlockState(blockPos);
        Player player = context.getPlayer();
        if(blockState.is(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK)) {
            if (!itemStack.has(MoeDataComponentTypes.LINK_POS) || !context.getLevel().getBlockState(itemStack.get(MoeDataComponentTypes.LINK_POS)).is(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK)) {
                itemStack.set(MoeDataComponentTypes.LINK_POS, blockPos);
                player.swing(context.getHand());
                return InteractionResult.SUCCESS;
            }
            else {
                BlockPos targetPos = itemStack.get(MoeDataComponentTypes.LINK_POS);
                if(targetPos == blockPos) {
                    itemStack.remove(MoeDataComponentTypes.LINK_POS);
                    player.swing(context.getHand());
                }
                else {
                    BlockState targetState = context.getLevel().getBlockState(targetPos);
                    if(targetState.is(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK)){
                        if(blockState.getValue(EnergyTransmissionAtennaBlock.SEND) && !targetState.getValue(EnergyTransmissionAtennaBlock.SEND)){
                            BlockEntity blockEntity = context.getLevel().getBlockEntity(blockPos);
                            ((EnergyTransmissionAntennaBE)blockEntity).getReceivePos().add(targetPos);
                            itemStack.remove(MoeDataComponentTypes.LINK_POS);
                            player.swing(context.getHand());
                            return InteractionResult.SUCCESS;
                        }
                        else if (!blockState.getValue(EnergyTransmissionAtennaBlock.SEND) && targetState.getValue(EnergyTransmissionAtennaBlock.SEND)){
                            BlockEntity blockEntity = context.getLevel().getBlockEntity(targetPos);
                            ((EnergyTransmissionAntennaBE)blockEntity).getReceivePos().add(blockPos);
                            itemStack.remove(MoeDataComponentTypes.LINK_POS);
                            player.swing(context.getHand());
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable(MoeMagicType.getTranslate(getType(stack))));
        IEnergyStorage energyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        int i = energyStorage.getEnergyStored(),j = energyStorage.getMaxEnergyStored();
        tooltipComponents.add(Component.translatable("moe_show_energy").append(i + " FE / " + j + " FE"));
        if(stack.has(MoeDataComponentTypes.LINK_POS)){
            BlockPos blockPos = stack.get(MoeDataComponentTypes.LINK_POS);
            tooltipComponents.add(Component.translatable("binding").append(blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ()));
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored() < stack.getCapability(Capabilities.EnergyStorage.ITEM).getMaxEnergyStored();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int i = stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored();
        int stackMaxEnergy = stack.getCapability(Capabilities.EnergyStorage.ITEM).getMaxEnergyStored();
        return Math.round(13.0F - (stackMaxEnergy - i) * 13.0F / stackMaxEnergy);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int i = stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored();
        int stackMaxEnergy = stack.getCapability(Capabilities.EnergyStorage.ITEM).getMaxEnergyStored();
        float f = Math.max(0.0F, (float) i / stackMaxEnergy);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    private MoeMagicType getType(ItemStack itemStack){
        MoeMagicType result = MoeMagicType.EMPTY;
        ItemStack typeStack = getMagic(itemStack);
        Item item = typeStack.getItem();
        if(item instanceof MoeMagicTypeModuleItem) result = ((MoeMagicTypeModuleItem) item).getMagicType();
        return result;
    }

    private ItemStack getMagic(ItemStack itemStack){
        if(itemStack.has(DataComponents.CONTAINER) && itemStack.has(MoeDataComponentTypes.MAGIC_SELECT)) {
            ItemContainerContents contents = itemStack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
            ItemStack typeStack = contents.getStackInSlot(itemStack.get(MoeDataComponentTypes.MAGIC_SELECT));
            return typeStack;
        }
        else return new ItemStack(MoeItems.EMPTY_PRIMARY_MODULE.get());
    }

    public static int getMaxMagicSlots() {
        return maxMagicSlots;
    }

    public static int getPowerNum() {
        return powerNum;
    }

    public static int getLcNum() {
        return lcNum;
    }
}
