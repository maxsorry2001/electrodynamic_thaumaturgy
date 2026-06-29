package net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas;

import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class EnhancementDataLoader extends SimpleJsonResourceReloadListener<EnhancementData> {
    private static final FileToIdConverter CONVERTER = FileToIdConverter.json("enhancement_data");
    private static Map<Identifier, EnhancementData> dataMap = new HashMap<>();
    public EnhancementDataLoader() {
        super(EnhancementData.CODEC, CONVERTER);
    }

    @Override
    protected Map<Identifier, EnhancementData> prepare(ResourceManager manager, ProfilerFiller profiler) {
        Map<Identifier, EnhancementData> map = super.prepare(manager, profiler);
        dataMap.putAll(map);
        return map;
    }

    public static EnhancementData get(Identifier identifier){
        return dataMap.get(identifier);
    }

    @Override
    protected void apply(Map<Identifier, EnhancementData> identifierEnhancementDataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {

    }

    public static Map<Identifier, EnhancementData> getDataMap() {
        return dataMap;
    }
}
