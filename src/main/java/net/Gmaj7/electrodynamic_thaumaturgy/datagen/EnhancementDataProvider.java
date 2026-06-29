package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.EnhancementData;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
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
        register("base_strength", 0.2F, 0F, 0F, 0F, 0F);
        register("base_cooldown", 0F, 0.2F, 0F, 0F, 0F);
        register("base_efficiency", 0F, 0F, 0.2F, 0F, 0F);
        register("base_critical_rate", 0F, 0F, 0F, 0.2F, 0F);
        register("base_critical_damage", 0F, 0F, 0F, 0F, 0.2F);
    }

    private void register(String name, float strength, float coolDown, float efficiency, float criticalRate, float criticalDamage){
        Identifier identifier = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, name);
        EnhancementData enhancementData = new EnhancementData(strength, coolDown, efficiency, criticalRate, criticalDamage,name);
        unconditional(identifier, enhancementData);
    }
}
