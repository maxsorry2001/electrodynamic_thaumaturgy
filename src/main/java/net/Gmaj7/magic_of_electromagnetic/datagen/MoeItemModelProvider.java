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
        basicItem(MoeItems.CHAIN_MODULE.get());
        basicItem(MoeItems.ATTRACT_MODULE.get());
        basicItem(MoeItems.ELECTRIC_ENERGY_RELEASE_MODULE.get());
        basicItem(MoeItems.REFRACTION_MODULE.get());
        basicItem(MoeItems.FORCE_RAMP_MODULE.get());
        basicItem(MoeItems.PLASMA_TORCH_MODULE.get());
        basicItem(MoeItems.IRON_POWER.get());
        basicItem(MoeItems.GOLD_POWER.get());
        basicItem(MoeItems.COPPER_POWER.get());
        basicItem(MoeItems.SUPERCONDUCTING_POWER.get());
        basicItem(MoeItems.EMPTY_POWER.get());
        basicItem(MoeItems.IRON_LC.get());
        basicItem(MoeItems.GOLD_LC.get());
        basicItem(MoeItems.COPPER_LC.get());
        basicItem(MoeItems.SUPERCONDUCTING_LC.get());
        basicItem(MoeItems.EMPTY_LC.get());
        basicItem(MoeItems.FE_CU_SOLUTION_BATTERY.get());
        basicItem(MoeItems.FE_CU_CARROT_BATTERY.get());
        basicItem(MoeItems.FE_CU_POTATO_BATTERY.get());
        basicItem(MoeItems.IRON_SHEET.get());
        basicItem(MoeItems.COPPER_SHEET.get());
        basicItem(MoeItems.ENERGY_CORE.get());
        basicItem(MoeItems.CAPACITOR.get());
        basicItem(MoeItems.INDUCTANCE.get());
        basicItem(MoeItems.BJT.get());
        basicItem(MoeItems.BOARD.get());
        basicItem(MoeItems.SUPERCONDUCTING_UPDATE.get());

        handheldItem(MoeItems.ELECTROMAGNETIC_ROD.get());
        handheldItem(MoeItems.ELECTROMAGNETIC_BOOK.get());
    }
}
