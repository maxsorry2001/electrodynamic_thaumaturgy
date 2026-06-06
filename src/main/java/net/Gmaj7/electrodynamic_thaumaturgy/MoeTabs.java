package net.Gmaj7.electrodynamic_thaumaturgy;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom.MagicCastItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MoeTabs {
    public static final String MOE_TAB_STRING = "moe_tab";
    public static final DeferredRegister<CreativeModeTab> MOE_CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ElectrodynamicThaumaturgy.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOE_TAB = MOE_CREATIVE_TABS.register("moe_tab",
            () -> CreativeModeTab.builder().icon(() -> setFullEnergyItem(getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get())))
                    .title(Component.translatable(MOE_TAB_STRING))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(MoeItems.MAGNO_INGOT);
                        output.accept(MoeItems.RADIANT_MAGNO_INGOT);
                        output.accept(MoeItems.STELLAR_MAGNO_INGOT);

                        output.accept(getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD));
                        output.accept(setFullEnergyItem(getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get())));
                        output.accept(MoeItems.PRIMARY_CODE_MODULE);
                        output.accept(MoeItems.INTERMEDIATE_CODE_MODULE);
                        output.accept(MoeItems.ADVANCED_CODE_MODULE);

                        output.accept(MoeItems.RAY_MODULE);
                        output.accept(MoeItems.ATTRACT_MODULE);
                        output.accept(MoeItems.LIGHTING_STRIKE_MODULE);
                        output.accept(MoeItems.PULSED_PLASMA_MODULE);
                        output.accept(MoeItems.ELECTRIC_ENERGY_RELEASE_MODULE);
                        output.accept(MoeItems.ELECTRIC_FIELD_DOMAIN_MODULE);
                        output.accept(MoeItems.ELECTROMAGNETIC_ASSAULT_MODULE);
                        output.accept(MoeItems.PROTECTING_MODULE);
                        output.accept(MoeItems.TREE_CURRENT_MODULE);

                        output.accept(MoeItems.EXCITING_MODULE);
                        output.accept(MoeItems.DISTURBING_BY_HIGH_INTENSITY_MAGNETIC_MODULE);
                        output.accept(MoeItems.COULOMB_DOMAIN_MODULE);
                        output.accept(MoeItems.MAGMA_LIGHTING_MODULE);
                        output.accept(MoeItems.REFRACTION_MODULE);
                        output.accept(MoeItems.BLOCK_NERVE_MODULE);
                        output.accept(MoeItems.DOMAIN_RECONSTRUCTION_MODULE);
                        output.accept(MoeItems.MAGNETIC_FLUX_CASCADE_MODULE);

                        output.accept(MoeItems.MAGNET_RESONANCE_MODULE);
                        output.accept(MoeItems.MAGNETIC_RECOMBINATION_CANNON_MODULE);
                        output.accept(MoeItems.ST_ELMO_S_FIRE_MODULE);
                        output.accept(MoeItems.HYDROGEN_BOND_FRACTURE_MODULE);
                        output.accept(MoeItems.MIRAGE_PURSUIT_MODULE);
                        output.accept(MoeItems.FREQUENCY_DIVISION_ARROW_RAIN_MODULE);
                        output.accept(MoeItems.PHOTOACOUSTIC_PULSE_MODULE);
                        output.accept(MoeItems.PHOTO_CORROSIVE_NOVA_MODULE);
                        output.accept(MoeItems.SAGE_S_MAGNETISM_SEAL);

                        output.accept(MoeItems.ENHANCE_MODEM_BASEBOARD);
                        output.accept(MoeItems.COOLDOWN_ENHANCE);
                        output.accept(MoeItems.STRENGTH_ENHANCE);
                        output.accept(MoeItems.EFFICIENCY_ENHANCE);
                        output.accept(MoeItems.ENTROPY_ENHANCE);
                        output.accept(MoeItems.LIFE_EXTRACTION_ENHANCE);

                        output.accept(MoeItems.PRIMARY_LC);
                        output.accept(MoeItems.INTERMEDIATE_LC);
                        output.accept(MoeItems.ADVANCED_LC);
                        output.accept(MoeItems.SUPERCONDUCTING_LC);
                        output.accept(MoeItems.PRIMARY_POWER);
                        output.accept(MoeItems.INTERMEDIATE_POWER);
                        output.accept(MoeItems.ADVANCED_POWER);
                        output.accept(MoeItems.SUPERCONDUCTING_POWER);
                        output.accept(MoeItems.CARROT_BATTERY);
                        output.accept(MoeItems.POTATO_BATTERY);
                        output.accept(MoeItems.SOLUTION_BATTERY);
                        output.accept(MoeItems.POWER_BANK);
                        output.accept(setFullEnergyItem(new ItemStack(MoeItems.POWER_BANK.get())));
                        output.accept(MoeItems.ENERGY_CORE);
                        output.accept(MoeItems.SUPERCONDUCTING_UPDATE);
                        output.accept(MoeItems.GENETIC_RECORDER);

                        output.accept(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE);
                        output.accept(MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE);
                        output.accept(MoeBlocks.MAGIC_ENCODE_TABLE);
                        output.accept(MoeBlocks.ENERGY_BLOCK);
                        output.accept(setFullEnergyItem(new ItemStack(MoeBlocks.ENERGY_BLOCK)));
                        output.accept(MoeBlocks.TEMPERATURE_GENERATOR_BLOCK);
                        output.accept(MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK);
                        output.accept(MoeBlocks.BIOMASS_GENERATOR_BLOCK);
                        output.accept(MoeBlocks.THERMAL_GENERATOR_BLOCK);
                        output.accept(MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK);
                        output.accept(MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK);
                        output.accept(MoeBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE_BLOCK);
                        output.accept(MoeBlocks.ATOMIC_RECONSTRUCTION_MACHINE_BLOCK);
                        output.accept(MoeBlocks.MAGNETO_FUSION_MACHINE_BLOCK);
                        output.accept(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK);
                        output.accept(MoeBlocks.EDDY_CURRENT_REMELTER_MACHINE_BLOCK);
                        output.accept(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK);
                        output.accept(MoeBlocks.MAGNETO_CORE_BLOCK);

                        output.accept(MoeItems.COPPER_DUST);
                        output.accept(MoeItems.IRON_DUST);
                        output.accept(MoeItems.GOLD_DUST);
                        output.accept(MoeItems.NETHERITE_DUST);

                        output.accept(MoeItems.MAGNETO_ENTROPY_WITCH_ENTITY_SPAWN_EGG);

                        output.accept(MoeItems.MAGNO_WRENCH);
                        output.accept(MoeItems.FILTER_SETTING);
                        output.accept(MoeBlocks.ENERGY_PIPE);
                        output.accept(MoeBlocks.ITEM_PIPE);
                    }))
                    .build());

    public static ItemStack setFullEnergyItem(ItemStack itemStack){
        EnergyHandler energyStorage = itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack));
        try (Transaction transaction = Transaction.openRoot()){
            energyStorage.insert(energyStorage.getCapacityAsInt(), transaction);
            transaction.commit();
        }
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
        for (int i = 3 ; i < MagicCastItem.getMaxMagicSlots(); i ++){
            list.add(new ItemStack(MoeItems.EMPTY_MAGIC_MODULE.get()));
        }
        itemStack.set(MoeDataComponentTypes.MOE_CONTAINER.get(), ItemContainerContents.fromItems(list));
        return itemStack;
    }

    public static final ItemStack getIronRod(){
        return getRod(MoeItems.PRIMARY_LC.get(), MoeItems.PRIMARY_POWER.get());
    }
    public static final ItemStack getGoldRod(){
        return getRod(MoeItems.INTERMEDIATE_LC.get(), MoeItems.INTERMEDIATE_POWER.get());
    }public static final ItemStack getCopperRod(){
        return getRod(MoeItems.ADVANCED_LC.get(), MoeItems.ADVANCED_POWER.get());
    }public static final ItemStack getSuperconductingRod(){
        return getRod(MoeItems.SUPERCONDUCTING_LC.get(), MoeItems.SUPERCONDUCTING_POWER.get());
    }

    public static final ItemStack getRod(ItemLike lc, ItemLike power){
        ItemStack itemStack = new ItemStack(MoeItems.ELECTROMAGNETIC_ROD.get());
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(lc));
        list.add(new ItemStack(power));
        for (int i = 2 ; i < MagicCastItem.getMaxMagicSlots(); i ++){
            list.add(new ItemStack(MoeItems.EMPTY_MAGIC_MODULE.get()));
        }
        itemStack.set(MoeDataComponentTypes.MOE_CONTAINER.get(), ItemContainerContents.fromItems(list));
        return itemStack;
    }
}
