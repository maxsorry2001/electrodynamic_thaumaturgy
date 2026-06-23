package net.Gmaj7.electrodynamic_thaumaturgy;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.ItemContainerData;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.MagicCastItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class EtTabs {
    public static final String MOE_TAB_STRING = "moe_tab";
    public static final DeferredRegister<CreativeModeTab> MOE_CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ElectrodynamicThaumaturgy.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOE_TAB = MOE_CREATIVE_TABS.register("moe_tab",
            () -> CreativeModeTab.builder().icon(() -> setFullEnergyItem(getDefaultMagicUse(EtItems.ELECTROMAGNETIC_ROD.get())))
                    .title(Component.translatable(MOE_TAB_STRING))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(EtItems.MAGNO_INGOT);
                        output.accept(EtItems.RADIANT_MAGNO_INGOT);
                        output.accept(EtItems.STELLAR_MAGNO_INGOT);
                        output.accept(EtItems.RESONANT_CRYSTAL);
                        output.accept(EtItems.POLAR_CRYSTAL);
                        output.accept(EtItems.GLOWING_ESSENCE);
                        output.accept(EtItems.MAGNETIC_ESSENCE);
                        output.accept(EtItems.MONOPOLE_N);
                        output.accept(EtItems.MONOPOLE_S);

                        output.accept(getDefaultMagicUse(EtItems.ELECTROMAGNETIC_ROD));
                        output.accept(setFullEnergyItem(getDefaultMagicUse(EtItems.ELECTROMAGNETIC_ROD.get())));
                        output.accept(EtItems.PULSE_BOW);
                        output.accept(setFullEnergyItem(getDefaultBow(EtItems.PULSE_BOW)));
                        output.accept(EtItems.PRIMARY_CODE_MODULE);
                        output.accept(EtItems.INTERMEDIATE_CODE_MODULE);
                        output.accept(EtItems.ADVANCED_CODE_MODULE);

                        output.accept(EtItems.RAY_MODULE);
                        output.accept(EtItems.ATTRACT_MODULE);
                        output.accept(EtItems.LIGHTING_STRIKE_MODULE);
                        output.accept(EtItems.PULSED_PLASMA_MODULE);
                        output.accept(EtItems.ELECTRIC_ENERGY_RELEASE_MODULE);
                        output.accept(EtItems.ELECTRIC_FIELD_DOMAIN_MODULE);
                        output.accept(EtItems.ELECTROMAGNETIC_ASSAULT_MODULE);
                        output.accept(EtItems.PROTECTING_MODULE);
                        output.accept(EtItems.TREE_CURRENT_MODULE);

                        output.accept(EtItems.EXCITING_MODULE);
                        output.accept(EtItems.DISTURBING_BY_HIGH_INTENSITY_MAGNETIC_MODULE);
                        output.accept(EtItems.COULOMB_DOMAIN_MODULE);
                        output.accept(EtItems.MAGMA_LIGHTING_MODULE);
                        output.accept(EtItems.REFRACTION_MODULE);
                        output.accept(EtItems.BLOCK_NERVE_MODULE);
                        output.accept(EtItems.DOMAIN_RECONSTRUCTION_MODULE);
                        output.accept(EtItems.MAGNETIC_FLUX_CASCADE_MODULE);

                        output.accept(EtItems.MAGNET_RESONANCE_MODULE);
                        output.accept(EtItems.MAGNETIC_RECOMBINATION_CANNON_MODULE);
                        output.accept(EtItems.ST_ELMO_S_FIRE_MODULE);
                        output.accept(EtItems.HYDROGEN_BOND_FRACTURE_MODULE);
                        output.accept(EtItems.MIRAGE_PURSUIT_MODULE);
                        output.accept(EtItems.FREQUENCY_DIVISION_ARROW_RAIN_MODULE);
                        output.accept(EtItems.PHOTOACOUSTIC_PULSE_MODULE);
                        output.accept(EtItems.PHOTO_CORROSIVE_NOVA_MODULE);
                        output.accept(EtItems.SAGE_S_MAGNETISM_SEAL);

                        output.accept(EtItems.ENHANCE_MODEM_BASEBOARD);
                        output.accept(EtItems.COOLDOWN_ENHANCE);
                        output.accept(EtItems.STRENGTH_ENHANCE);
                        output.accept(EtItems.EFFICIENCY_ENHANCE);

                        output.accept(EtItems.PRIMARY_LC);
                        output.accept(EtItems.INTERMEDIATE_LC);
                        output.accept(EtItems.ADVANCED_LC);
                        output.accept(EtItems.SUPERCONDUCTING_LC);
                        output.accept(EtItems.PRIMARY_POWER);
                        output.accept(EtItems.INTERMEDIATE_POWER);
                        output.accept(EtItems.ADVANCED_POWER);
                        output.accept(EtItems.SUPERCONDUCTING_POWER);
                        output.accept(EtItems.CARROT_BATTERY);
                        output.accept(EtItems.POTATO_BATTERY);
                        output.accept(EtItems.SOLUTION_BATTERY);
                        output.accept(EtItems.POWER_BANK);
                        output.accept(setFullEnergyItem(new ItemStack(EtItems.POWER_BANK.get())));
                        output.accept(EtItems.ENERGY_CORE);
                        output.accept(EtItems.SUPERCONDUCTING_UPDATE);
                        output.accept(EtItems.GENETIC_RECORDER);

                        output.accept(EtBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE);
                        output.accept(EtBlocks.ELECTROMAGNETIC_MODEM_TABLE);
                        output.accept(EtBlocks.MAGIC_ENCODE_TABLE);
                        output.accept(EtBlocks.ENERGY_BLOCK);
                        output.accept(EtBlocks.FLUID_BLOCK);
                        output.accept(setFullEnergyItem(new ItemStack(EtBlocks.ENERGY_BLOCK)));
                        output.accept(EtBlocks.TEMPERATURE_GENERATOR);
                        output.accept(EtBlocks.PHOTOVOLTAIC_GENERATOR);
                        output.accept(EtBlocks.BIOMASS_GENERATOR);
                        output.accept(EtBlocks.THERMAL_GENERATOR);
                        output.accept(EtBlocks.ELECTROMAGNETIC_DRIVER_MACHINE);
                        output.accept(EtBlocks.BIO_REPLICATION_VAT_MACHINE);
                        output.accept(EtBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE);
                        output.accept(EtBlocks.ATOMIC_RECONSTRUCTION_MACHINE);
                        output.accept(EtBlocks.MAGNETO_FUSION_MACHINE);
                        output.accept(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE);
                        output.accept(EtBlocks.EDDY_CURRENT_REMELTER_MACHINE);
                        output.accept(EtBlocks.ELECTROMAGNETIC_INFUSER_MACHINE);
                        output.accept(EtBlocks.ENERGY_TRANSMISSION_ANTENNA);
                        output.accept(EtBlocks.MAGNETO_CORE);

                        output.accept(EtItems.COPPER_DUST);
                        output.accept(EtItems.IRON_DUST);
                        output.accept(EtItems.GOLD_DUST);
                        output.accept(EtItems.NETHERITE_DUST);

                        output.accept(EtItems.MAGNETO_ENTROPY_WITCH_ENTITY_SPAWN_EGG);

                        output.accept(EtItems.MAGNO_WRENCH);
                        output.accept(EtItems.FILTER_SETTING);
                        output.accept(EtBlocks.ENERGY_PIPE);
                        output.accept(EtBlocks.ITEM_PIPE);
                        output.accept(EtBlocks.FLUID_PIPE);

                        output.accept(EtItems.FLUID_FAKE_ITEM);
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
        itemStack.set(EtDataComponentTypes.ET_CONTAINER.get(), ItemContainerData.getDefaultRod());
        return itemStack;
    }

    public static ItemStack getDefaultBow(ItemLike item){
        ItemStack itemStack = new ItemStack(item);
        itemStack.set(EtDataComponentTypes.ET_CONTAINER.get(), ItemContainerData.getDefaultBow());
        return itemStack;
    }

    public static final ItemStack getIronRod(){
        return getRod(EtItems.PRIMARY_LC.get(), EtItems.PRIMARY_POWER.get());
    }
    public static final ItemStack getGoldRod(){
        return getRod(EtItems.INTERMEDIATE_LC.get(), EtItems.INTERMEDIATE_POWER.get());
    }
    public static final ItemStack getCopperRod(){
        return getRod(EtItems.ADVANCED_LC.get(), EtItems.ADVANCED_POWER.get());
    }
    public static final ItemStack getSuperconductingRod(){
        return getRod(EtItems.SUPERCONDUCTING_LC.get(), EtItems.SUPERCONDUCTING_POWER.get());
    }

    public static final ItemStack getRod(Item lc, Item power){
        ItemStack itemStack = new ItemStack(EtItems.ELECTROMAGNETIC_ROD.get());
        List<ItemStackTemplate> list = new ArrayList<>();
        list.add(new ItemStackTemplate(lc));
        list.add(new ItemStackTemplate(power));
        for (int i = 2 ; i < MagicCastItem.getMaxMagicSlots(); i ++){
            list.add(new ItemStackTemplate(EtItems.EMPTY_MAGIC_MODULE.get()));
        }
        itemStack.set(EtDataComponentTypes.ET_CONTAINER.get(), new ItemContainerData(list));
        return itemStack;
    }
}
