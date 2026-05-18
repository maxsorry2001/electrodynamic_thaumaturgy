package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID)
public class MagicDefinitionLoader extends SimpleJsonResourceReloadListener<MagicDefinition> {
    private static final FileToIdConverter CONVERTER = FileToIdConverter.json("magic_definition");
    private static Map<Identifier, MagicDefinition> definitionMap = new HashMap<>();

    public MagicDefinitionLoader() {
        super(MagicDefinition.CODEC, CONVERTER);
    }

    @Override
    protected void apply(Map<Identifier, MagicDefinition> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {

    }

    @Override
    protected Map<Identifier, MagicDefinition> prepare(ResourceManager manager, ProfilerFiller profiler) {
        Map<Identifier, MagicDefinition> map = super.prepare(manager, profiler);
        definitionMap.putAll(map);
        return map;
    }

    public static MagicDefinition get(Identifier id){
        return definitionMap.getOrDefault(id, null);
    }

    @SubscribeEvent
    public static void addReloadListeners(AddServerReloadListenersEvent event){
        event.addListener(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magic_definition_loader"), new MagicDefinitionLoader());
    }
}
