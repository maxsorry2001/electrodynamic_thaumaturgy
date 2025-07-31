package net.Gmaj7.electrofynamic_thaumatury.MoeInit;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MoeTags {
    public class moeItemTags{
        public static final TagKey<Item> MAGIC_MODULE = creat("magic_module");
        public static final TagKey<Item> EMPTY_MAGIC_MODULE = creat("empty_magic_module");
        public static TagKey<Item> creat(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, name));
        }
    }
    public class moeBlockTags{
        public static final TagKey<Block> HOT_BLOCK = creat("hot_block");
        public static final TagKey<Block> COLD_BLOCK = creat("cold_block");
        public static TagKey<Block> creat(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, name));
        }
    }
}
