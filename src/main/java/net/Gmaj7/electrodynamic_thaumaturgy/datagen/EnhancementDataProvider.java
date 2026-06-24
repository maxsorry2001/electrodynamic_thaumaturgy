package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtRegistries;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.EnhancementData;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.custom.IMoeMagic;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.data.JsonCodecProvider;

import java.util.concurrent.CompletableFuture;

public class EnhancementDataProvider extends JsonCodecProvider<EnhancementData> {
    /**
     * @param output         {@linkplain PackOutput} provided by the {}.
     * @param lookupProvider
     */
    public EnhancementDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, PackOutput.Target.DATA_PACK, "enhancement_data", EnhancementData.CODEC, lookupProvider, ElectrodynamicThaumaturgy.MODID);
    }

    @Override
    protected void gather() {
        register("tt", 1F, 1F, 1F);
    }

    private void register(String name, float strength, float coolDown, float efficiency){
        Identifier identifier = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, name);
        EnhancementData enhancementData = new EnhancementData(strength, coolDown, efficiency);
        unconditional(identifier, enhancementData);
    }
}
