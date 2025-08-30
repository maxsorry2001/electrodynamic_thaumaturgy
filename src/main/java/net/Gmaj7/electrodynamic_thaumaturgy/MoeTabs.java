package net.Gmaj7.electrodynamic_thaumaturgy;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom.MagicCastItem;
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
                        output.accept(MoeItems.EMPTY_PRIMARY_MODULE.get());
                        output.accept(MoeItems.EMPTY_INTERMEDIATE_MODULE.get());
                        output.accept(MoeItems.EMPTY_ADVANCED_MODULE.get());

                        output.accept(MoeItems.RAY_MODULE.get());
                        output.accept(MoeItems.ATTRACT_MODULE.get());
                        output.accept(MoeItems.LIGHTING_STRIKE_MODULE.get());
                        output.accept(MoeItems.PULSED_PLASMA_MODULE.get());
                        output.accept(MoeItems.ELECTRIC_ENERGY_RELEASE_MODULE.get());
                        output.accept(MoeItems.ELECTRIC_FIELD_DOMAIN_MODULE.get());
                        output.accept(MoeItems.ELECTROMAGNETIC_ASSAULT_MODULE.get());
                        output.accept(MoeItems.PROTECTING_MODULE.get());
                        output.accept(MoeItems.TREE_CURRENT_MODULE.get());

                        output.accept(MoeItems.EXCITING_MODULE.get());
                        output.accept(MoeItems.DISTURBING_BY_HIGH_INTENSITY_MAGNETIC_MODULE.get());
                        output.accept(MoeItems.COULOMB_DOMAIN_MODULE.get());
                        output.accept(MoeItems.MAGMA_LIGHTING_MODULE.get());
                        output.accept(MoeItems.REFRACTION_MODULE.get());
                        output.accept(MoeItems.BLOCK_NERVE_MODULE.get());
                        output.accept(MoeItems.DOMAIN_RECONSTRUCTION_MODULE.get());
                        output.accept(MoeItems.MAGNETIC_FLUX_CASCADE_MODULE.get());

                        output.accept(MoeItems.MAGNET_RESONANCE_MODULE.get());
                        output.accept(MoeItems.MAGNETIC_RECOMBINATION_CANNON_MODULE.get());
                        output.accept(MoeItems.ST_ELMO_S_FIRE_MODULE.get());
                        output.accept(MoeItems.HYDROGEN_BOND_FRACTURE_MODULE.get());
                        output.accept(MoeItems.MIRAGE_PURSUIT_MODULE.get());
                        output.accept(MoeItems.FREQUENCY_DIVISION_ARROW_RAIN.get());

                        output.accept(MoeItems.ENHANCE_MODEM_BASEBOARD.get());
                        output.accept(MoeItems.COOLDOWN_ENHANCE.get());
                        output.accept(MoeItems.STRENGTH_ENHANCE.get());
                        output.accept(MoeItems.EFFICIENCY_ENHANCE.get());
                        output.accept(MoeItems.ENTROPY_ENHANCE.get());
                        output.accept(MoeItems.LIFE_EXTRACTION_ENHANCE.get());

                        output.accept(MoeItems.IRON_LC.get());
                        output.accept(MoeItems.GOLD_LC.get());
                        output.accept(MoeItems.COPPER_LC.get());
                        output.accept(MoeItems.SUPERCONDUCTING_LC.get());
                        output.accept(MoeItems.IRON_POWER.get());
                        output.accept(MoeItems.GOLD_POWER.get());
                        output.accept(MoeItems.COPPER_POWER.get());
                        output.accept(MoeItems.SUPERCONDUCTING_POWER.get());
                        output.accept(MoeItems.CARROT_BATTERY.get());
                        output.accept(MoeItems.POTATO_BATTERY.get());
                        output.accept(MoeItems.SOLUTION_BATTERY.get());
                        output.accept(MoeItems.POWER_BANK.get());
                        output.accept(MoeItems.ENERGY_CORE.get());
                        output.accept(MoeItems.IRON_SHEET.get());
                        output.accept(MoeItems.COPPER_SHEET.get());
                        output.accept(MoeItems.CAPACITOR.get());
                        output.accept(MoeItems.INDUCTANCE.get());
                        output.accept(MoeItems.BJT.get());
                        output.accept(MoeItems.SI.get());
                        output.accept(MoeItems.SUPERCONDUCTING_UPDATE.get());
                        output.accept(MoeItems.GENETIC_RECORDER.get());

                        output.accept(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE);
                        output.accept(MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE);
                        output.accept(MoeBlocks.MAGIC_LITHOGRAPHY_TABLE);
                        output.accept(MoeBlocks.ENERGY_BLOCK);
                        output.accept(setFullEnergyItem(new ItemStack(MoeItems.ENERGY_BLOCK.get())));
                        output.accept(MoeBlocks.TEMPERATURE_GENERATOR_BLOCK);
                        output.accept(MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK);
                        output.accept(MoeBlocks.THERMAL_GENERATOR_BLOCK);
                        output.accept(MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK);
                        output.accept(MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK);
                        output.accept(MoeBlocks.GEOLOGICAL_METAL_EXCAVATOR_MACHINE_BLOCK);
                        output.accept(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK);
                        output.accept(MoeBlocks.HARMONIC_CORE_BLOCK);

                        output.accept(MoeItems.HARMONIC_SOVEREIGN_SPAWN_EGG);
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
        for (int i = 3 ; i < MagicCastItem.getMaxMagicSlots(); i ++){
            list.add(new ItemStack(MoeItems.EMPTY_PRIMARY_MODULE.get()));
        }
        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(list));
        return itemStack;
    }

    public static final ItemStack getIronRod(){
        return getRod(MoeItems.IRON_LC.get(), MoeItems.IRON_POWER.get());
    }
    public static final ItemStack getGoldRod(){
        return getRod(MoeItems.GOLD_LC.get(), MoeItems.GOLD_POWER.get());
    }public static final ItemStack getCopperRod(){
        return getRod(MoeItems.COPPER_LC.get(), MoeItems.COPPER_POWER.get());
    }public static final ItemStack getSuperconductingRod(){
        return getRod(MoeItems.SUPERCONDUCTING_LC.get(), MoeItems.SUPERCONDUCTING_POWER.get());
    }

    public static final ItemStack getRod(ItemLike lc, ItemLike power){
        ItemStack itemStack = new ItemStack(MoeItems.ELECTROMAGNETIC_ROD.get());
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(lc));
        list.add(new ItemStack(power));
        for (int i = 2 ; i < MagicCastItem.getMaxMagicSlots(); i ++){
            list.add(new ItemStack(MoeItems.EMPTY_PRIMARY_MODULE.get()));
        }
        itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(list));
        return itemStack;
    }
}
