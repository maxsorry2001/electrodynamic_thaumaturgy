package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MoeTags {
    public class moeBlockTags{
        public static final TagKey<Block> HOT_BLOCK = creat("hot_block");
        public static final TagKey<Block> COLD_BLOCK = creat("cold_block");
        public static TagKey<Block> creat(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, name));
        }
    }
}
