package net.Gmaj7.magic_of_electromagnetic;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoeTabs {
    public static final String MOE_TAB_STRING = "moe_tab";
    public static final DeferredRegister<CreativeModeTab> MOE_CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MagicOfElectromagnetic.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOE_TAB = MOE_CREATIVE_TABS.register("moe_tab",
            () -> CreativeModeTab.builder().icon(() -> setFullEnergyItem(MoeItems.ELECTROMAGNETIC_ROD.get()))
                    .title(Component.translatable(MOE_TAB_STRING))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(MoeItems.ELECTROMAGNETIC_ROD.get());
                        output.accept(setFullEnergyItem(MoeItems.ELECTROMAGNETIC_ROD.get()));
                        output.accept(MoeItems.ELECTROMAGNETIC_BOOK.get());
                        output.accept(setFullEnergyItem(MoeItems.ELECTROMAGNETIC_BOOK.get()));
                        output.accept(MoeItems.RAY_MODULE.get());
                        output.accept(MoeItems.PLASMA_MODULE.get());
                        output.accept(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get());
                    }))
                    .build());

    private static ItemStack setFullEnergyItem(ItemLike item){
        ItemStack itemStack = new ItemStack(item);
        IEnergyStorage energyStorage = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
        energyStorage.receiveEnergy(energyStorage.getMaxEnergyStored(), false);
        return itemStack;
    }
}
