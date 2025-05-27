package net.Gmaj7.magic_of_electromagnetic;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MagicCastItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class MoeTabs {
    public static final String MOE_TAB_STRING = "moe_tab";
    public static final DeferredRegister<CreativeModeTab> MOE_CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MagicOfElectromagnetic.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOE_TAB = MOE_CREATIVE_TABS.register("moe_tab",
            () -> CreativeModeTab.builder().icon(() -> setFullEnergyItem(getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get())))
                    .title(Component.translatable(MOE_TAB_STRING))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get()));
                        output.accept(setFullEnergyItem(getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get())));
                        output.accept(getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_BOOK.get()));
                        output.accept(setFullEnergyItem(getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_BOOK.get())));
                        output.accept(MoeItems.RAY_MODULE.get());
                        output.accept(MoeItems.PULSED_PLASMA_MODULE.get());
                        output.accept(MoeItems.EXCITING_MODULE.get());
                        output.accept(MoeItems.PROTECT_MODULE.get());
                        output.accept(MoeItems.ELECTRIC_FIELD_DOMAIN_MODULE.get());
                        output.accept(MoeItems.ATTRACT_MODULE.get());
                        output.accept(MoeItems.CHAIN_MODULE.get());
                        output.accept(MoeItems.REFRACTION_MODULE.get());
                        output.accept(MoeItems.ELECTRIC_ENERGY_RELEASE_MODULE.get());
                        output.accept(MoeItems.PLASMA_TORCH_MODULE.get());
                        output.accept(MoeItems.ELECTROMAGNETIC_ASSAULT_MODULE.get());
                        output.accept(MoeItems.ENTROPY_MAGNET_UPHEAVAL_MODULE.get());
                        output.accept(MoeItems.ST_ELMO_S_FIRE_MODULE.get());
                        output.accept(MoeItems.MAGMA_LIGHTING_MODULE.get());

                        output.accept(MoeItems.COOLDOWN_ENHANCE.get());
                        output.accept(MoeItems.STRENGTH_ENHANCE.get());
                        output.accept(MoeItems.EFFICIENCY_ENHANCE.get());
                        output.accept(MoeItems.POTENTIAL_DIFFERENCE_ENHANCE.get());
                        output.accept(MoeItems.BIOELECTRIC_STOP_ENHANCE.get());

                        output.accept(MoeItems.IRON_LC.get());
                        output.accept(MoeItems.GOLD_LC.get());
                        output.accept(MoeItems.COPPER_LC.get());
                        output.accept(MoeItems.SUPERCONDUCTING_LC.get());
                        output.accept(MoeItems.IRON_POWER.get());
                        output.accept(MoeItems.GOLD_POWER.get());
                        output.accept(MoeItems.COPPER_POWER.get());
                        output.accept(MoeItems.SUPERCONDUCTING_POWER.get());
                        output.accept(MoeItems.FE_CU_CARROT_BATTERY.get());
                        output.accept(MoeItems.FE_CU_POTATO_BATTERY.get());
                        output.accept(MoeItems.FE_CU_SOLUTION_BATTERY.get());
                        output.accept(MoeItems.ENERGY_CORE.get());
                        output.accept(MoeItems.IRON_SHEET.get());
                        output.accept(MoeItems.COPPER_SHEET.get());
                        output.accept(MoeItems.CAPACITOR.get());
                        output.accept(MoeItems.INDUCTANCE.get());
                        output.accept(MoeItems.BJT.get());
                        output.accept(MoeItems.BOARD.get());
                        output.accept(MoeItems.SUPERCONDUCTING_UPDATE.get());

                        output.accept(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE);
                        output.accept(MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE);
                        output.accept(MoeBlocks.ENERGY_BLOCK);
                        output.accept(MoeBlocks.TEMPERATURE_ENERGY_MAKER_BLOCK);
                        output.accept(MoeBlocks.PHOTOVOLTAIC_ENERGY_MAKER_BLOCK);
                        output.accept(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK);
                    }))
                    .build());

    public static ItemStack setFullEnergyItem(ItemStack itemStack){
        IEnergyStorage energyStorage = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
        energyStorage.receiveEnergy(energyStorage.getMaxEnergyStored(), false);
        return itemStack;
    }

    public static ItemStack getDefaultMagicUse(ItemLike item){
        ItemStack itemStack = new ItemStack(item);
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(MoeItems.SUPERCONDUCTING_POWER.get()));
        list.add(new ItemStack(MoeItems.SUPERCONDUCTING_LC.get()));
        if (item == MoeItems.ELECTROMAGNETIC_ROD.get()){
            list.add(new ItemStack(MoeItems.RAY_MODULE.get()));
        }
        else {
            list.add(new ItemStack(MoeItems.PULSED_PLASMA_MODULE.get()));
        }
        int i = 3;
        for ( ; i < MagicCastItem.getMaxMagicSlots(); i ++){
            list.add(new ItemStack(MoeItems.EMPTY_MODULE.get()));
        }
        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(list));
        return itemStack;
    }
}
