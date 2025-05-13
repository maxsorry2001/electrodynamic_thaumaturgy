package net.Gmaj7.magic_of_electromagnetic.MoeGui;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.menu.MoeAssemblyTableMenu;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.menu.MoeModemTableMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MoeMenuType {
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(Registries.MENU, MagicOfElectromagnetic.MODID);

    public static final Supplier<MenuType<MoeAssemblyTableMenu>> ASSEMBLY_TABLE_MENU = MENU_TYPE.register("assembly_table_menu",
            () -> new MenuType<>(MoeAssemblyTableMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final Supplier<MenuType<MoeModemTableMenu>> MODEM_TABLE_MENU = MENU_TYPE.register("modem_table_menu",
            () -> new MenuType<>(MoeModemTableMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus eventBus){
        MENU_TYPE.register(eventBus);
    }
}
