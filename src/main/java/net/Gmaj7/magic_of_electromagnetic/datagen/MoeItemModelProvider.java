package net.Gmaj7.magic_of_electromagnetic.datagen;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MoeItemModelProvider extends ItemModelProvider {
    public MoeItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MagicOfElectromagnetic.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(MoeItems.EMPTY_MODULE.get());
        basicItem(MoeItems.RAY_MODULE.get());
        basicItem(MoeItems.PULSED_PLASMA_MODULE.get());
        basicItem(MoeItems.EXCITING_MODULE.get());
        basicItem(MoeItems.PROTECT_MODULE.get());
        basicItem(MoeItems.ELECTRIC_FIELD_DOMAIN_MODULE.get());
        basicItem(MoeItems.IRON_POWER.get());
        basicItem(MoeItems.GOLD_POWER.get());
        basicItem(MoeItems.COPPER_POWER.get());
        basicItem(MoeItems.NETHERITE_POWER.get());
        basicItem(MoeItems.EMPTY_POWER.get());
        basicItem(MoeItems.IRON_LC.get());
        basicItem(MoeItems.GOLD_LC.get());
        basicItem(MoeItems.COPPER_LC.get());
        basicItem(MoeItems.NETHERITE_LC.get());
        basicItem(MoeItems.EMPTY_LC.get());

        handheldItem(MoeItems.ELECTROMAGNETIC_ROD.get());
        handheldItem(MoeItems.ELECTROMAGNETIC_BOOK.get());
    }
}
