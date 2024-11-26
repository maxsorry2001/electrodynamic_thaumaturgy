package net.Gmaj7.magic_of_electromagnetic;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
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
                        output.accept(MoeItems.PLASMA_MODULE.get());
                        output.accept(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get());
                    }))
                    .build());

    private static ItemStack setFullEnergyItem(ItemStack itemStack){
        IEnergyStorage energyStorage = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
        energyStorage.receiveEnergy(energyStorage.getMaxEnergyStored(), false);
        return itemStack;
    }

    private static ItemStack getDefaultMagicUse(ItemLike item){
        ItemStack itemStack = new ItemStack(item);
        if (item == MoeItems.ELECTROMAGNETIC_ROD.get()){
            List<ItemStack> list = new ArrayList<ItemStack>();
            list.add(0, new ItemStack(MoeItems.RAY_MODULE.get()));
            itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(list));
        }
        else {
            List<ItemStack> list = new ArrayList<ItemStack>();
            list.add(0, new ItemStack(MoeItems.PLASMA_MODULE.get()));
            itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(list));
        }
        return itemStack;
    }
}
