package net.Gmaj7.electrodynamic_thaumaturgy.Init;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class EtTags {
    public static class etItemTags {
        public static final TagKey<Item> EMPTY_MAGIC_MODULE = creat("empty_magic_module");
        public static TagKey<Item> creat(String name){
            return ItemTags.create(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, name));
        }
    }
    public static class etBlockTags {
        public static final TagKey<Block> HOT_BLOCK = creat("hot");
        public static final TagKey<Block> COLD_BLOCK = creat("cold");
        public static TagKey<Block> creat(String name){
            return BlockTags.create(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, name));
        }
    }
}
