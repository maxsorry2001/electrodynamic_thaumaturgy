package net.Gmaj7.magic_of_electromagnetic.MoeItem;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MagicUseItem;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MoeMagicTypeModuleItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MoeItems {
    public static final DeferredRegister.Items MOE_ITEM = DeferredRegister.createItems(MagicOfElectromagnetic.MODID);

    public static final Supplier<BlockItem> ELECTROMAGNETIC_ASSEMBLY_TABLE = MOE_ITEM.registerSimpleBlockItem("electromagnetic_assembly_table", MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE);

    public static final Supplier<Item> ELECTROMAGNETIC_ROD = MOE_ITEM.register("electromagnetic_rod",
            () -> new MagicUseItem(new Item.Properties().stacksTo(1)
                    .component(MoeDataComponentTypes.MOE_ENERGY.get(), 0)
                    .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)));
    public static final Supplier<Item> ELECTROMAGNETIC_BOOK = MOE_ITEM.register("electromagnetic_book",
            () -> new MagicUseItem(new Item.Properties().stacksTo(1)
                    .component(MoeDataComponentTypes.MOE_ENERGY.get(), 0)
                    .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)));

    public static final Supplier<Item> RAY_MODULE = MOE_ITEM.register("ray_module",
            () -> new MoeMagicTypeModuleItem(MoeMagicType.RAY, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PLASMA_MODULE = MOE_ITEM.register("plasma_module",
            () -> new MoeMagicTypeModuleItem(MoeMagicType.PLASMA, new Item.Properties().stacksTo(1)));
}
