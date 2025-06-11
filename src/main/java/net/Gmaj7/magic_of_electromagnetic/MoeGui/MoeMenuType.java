package net.Gmaj7.magic_of_electromagnetic.MoeGui;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.menu.*;
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
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(Registries.MENU, MagicOfElectromagnetic.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<MoeAssemblyTableMenu>> ASSEMBLY_TABLE_MENU = MENU_TYPE.register("assembly_table_menu",
            () -> new MenuType<>(MoeAssemblyTableMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<MoeModemTableMenu>> MODEM_TABLE_MENU = MENU_TYPE.register("modem_table_menu",
            () -> new MenuType<>(MoeModemTableMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<MoeMagicLithographyTableMenu>> MAGIC_LITHOGRAPHY_TABLE_MENU = MENU_TYPE.register("magic_lithography_table_menu",
            () -> new MenuType<>(MoeMagicLithographyTableMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<MoeEnergyBlockMenu>> ENERGY_BLOCK_MENU = registerMenuType("energy_block_menu", MoeEnergyBlockMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<MoeThermalEnergyMakerMenu>> THERMAL_ENERGY_MAKER_MENU = registerMenuType("thermal_energy_maker_menu", MoeThermalEnergyMakerMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory){
        return MENU_TYPE.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENU_TYPE.register(eventBus);
    }
}
