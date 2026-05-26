package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

import java.util.*;

public class PipeNet {
    private final int netId;
    private Set<BlockPos> posSet = new HashSet<>();
    private Map<BlockPos, Set<BlockPos>> adj = new HashMap<>();

    public static final Codec<PipeNet> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("net_id").forGetter(PipeNet::getNetId),
            BlockPos.CODEC.listOf().xmap(Set::copyOf, ArrayList::new).fieldOf("poses").forGetter(PipeNet::getPosSet),
            Codec.unboundedMap(Codec.STRING.xmap(PipeNet::keyToPos, PipeNet::posToKey), BlockPos.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("adj").forGetter(PipeNet::getAdj)
    ).apply(i, PipeNet::new));

    public PipeNet(int id){
        this.netId = id;
    }

    public PipeNet(int id, Set<BlockPos> posSet, Map<BlockPos, Set<BlockPos>> adj){
        this.netId = id;
        this.posSet = posSet;
        this.adj = adj;
    }

    public void addPos(BlockPos blockPos, Set<BlockPos> links){
        posSet.add(blockPos);
        adj.put(blockPos, links);
    }

    public void removePos(BlockPos blockPos){
        posSet.remove(blockPos);
        adj.remove(blockPos);
    }

    public Set<BlockPos> getPosSet() {
        return posSet;
    }

    public int getNetId() {
        return netId;
    }

    public Map<BlockPos, Set<BlockPos>> getAdj() {
        return adj;
    }

    private static String posToKey(BlockPos pos){
        return pos.getX() + "_" + pos.getY() + "_" + pos.getZ();
    }

    private static BlockPos keyToPos(String key){
        String[] parts = key.split("_");
        return new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    public boolean containPos(BlockPos pos){
        return posSet.contains(pos);
    }
}
