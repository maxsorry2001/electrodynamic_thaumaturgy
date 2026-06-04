package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoeMenuType {
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(Registries.MENU, ElectrodynamicThaumaturgy.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<AssemblyTableMenu>> ASSEMBLY_TABLE_MENU = MENU_TYPE.register("assembly_table_menu",
            () -> new MenuType<>(AssemblyTableMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<ModemTableMenu>> MODEM_TABLE_MENU = MENU_TYPE.register("modem_table_menu",
            () -> new MenuType<>(ModemTableMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<MagicEncodeTableMenu>> MAGIC_ENCODE_TABLE_MENU = MENU_TYPE.register("magic_encode_table_menu",
            () -> new MenuType<>(MagicEncodeTableMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<EnergyBlockMenu>> ENERGY_BLOCK_MENU = registerMenuType("energy_block_menu", EnergyBlockMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<ThermalGeneratorMenu>> THERMAL_GENERATOR_MENU = registerMenuType("thermal_generator_menu", ThermalGeneratorMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<BiomassGeneratorMenu>> BIOMASS_GENERATOR_MENU = registerMenuType("biomass_generator_menu", BiomassGeneratorMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<ElectromagneticDriverBlockMenu>> ELECTROMAGNETIC_DRIVER_MACHINE_MENU = registerMenuType("electromagnetic_driver_machine_menu", ElectromagneticDriverBlockMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<EntityCloneBlockMenu>> BIO_REPLICATION_VAT_MACHINE_MENU = registerMenuType("bio_replication_vat_machine_menu", EntityCloneBlockMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<ElectromagneticExtractorBlockMenu>> GEOLOGICAL_METAL_EXCAVATOR_MENU = registerMenuType("geological_metal_excavator_menu", ElectromagneticExtractorBlockMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<AtomicReconstructionBlockMenu>> ATOMIC_RECONSTRUCTION_BLOCK_MENU = registerMenuType("atomic_reconstruction_machine_menu", AtomicReconstructionBlockMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<MagnetoFusionBlockMenu>> MAGNETO_FUSION_BLOCK_MENU = registerMenuType("maneto_fusion_machine_menu", MagnetoFusionBlockMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<ElectromagneticDissociationBlockMenu>> ELECTROMAGNETIC_DISSOCIATION_BLOCK_MENU = registerMenuType("electromagnetic_dissociation_machine_block_menu", ElectromagneticDissociationBlockMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<EddyCurrentRemelterBlockMenu>> EDDY_CURRENT_REMELTER_BLOCK_MENU = registerMenuType("eddy_current_remelter_block_menu", EddyCurrentRemelterBlockMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<ItemPipeNetMenu>> ITEM_PIPE_NET_MENU = registerMenuType("item_pipe_net_menu", ItemPipeNetMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<EnergyPipeNetMenu>> ENERGY_PIPE_NET_MENU = registerMenuType("energy_pipe_net_menu", EnergyPipeNetMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory){
        return MENU_TYPE.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENU_TYPE.register(eventBus);
    }
}
