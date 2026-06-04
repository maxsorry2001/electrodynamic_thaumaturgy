package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.EnergyPipeNetMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class EnergyPipeNet extends PipeNet{
    public static final Codec<EnergyPipeNet> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("net_id").forGetter(EnergyPipeNet::getNetId),
            BlockPos.CODEC.listOf().xmap(Set::copyOf, ArrayList::new).fieldOf("poses").forGetter(EnergyPipeNet::getPosSet),
            Codec.unboundedMap(Codec.STRING.xmap(EnergyPipeNet::keyToPos, EnergyPipeNet::posToKey), BlockPos.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("adj").forGetter(EnergyPipeNet::getAdj),
            Codec.unboundedMap(Codec.STRING.xmap(EnergyPipeNet::keyToPos, EnergyPipeNet::posToKey), Direction.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("insert").forGetter(EnergyPipeNet::getInsert),
            Codec.unboundedMap(Codec.STRING.xmap(EnergyPipeNet::keyToPos, EnergyPipeNet::posToKey),
                    Codec.unboundedMap(Direction.CODEC, TransferMode.CODEC)).fieldOf("extract").forGetter(EnergyPipeNet::getExtract),
            Codec.INT.fieldOf("tick_counter").forGetter(EnergyPipeNet::getTickCounter)
    ).apply(i, EnergyPipeNet::new));

    // 为每个连接的机器存储其对应的缓存
    private LinkedHashMap<BlockPos, LinkedHashMap<Direction, BlockCapabilityCache<EnergyHandler, Direction>>> extractCaches;
    private LinkedHashMap<BlockPos, LinkedHashMap<Direction, BlockCapabilityCache<EnergyHandler, Direction>>> insertCaches;
    public EnergyPipeNet(int id) {
        super(id);
        this.insertCaches = new LinkedHashMap<>();
        this.extractCaches = new LinkedHashMap<>();
    }

    public EnergyPipeNet(int id, Set<BlockPos> posSet, Map<BlockPos, Set<BlockPos>> adj, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Map<Direction, TransferMode>> extract, int tickCounter) {
        super(id, posSet, adj, insert, extract, tickCounter);
        this.insertCaches = new LinkedHashMap<>();
        this.extractCaches = new LinkedHashMap<>();
    }

    @Override
    public void removePosCache(BlockPos blockPos) {
        insertCaches.remove(blockPos);
        extractCaches.remove(blockPos);
    }

    @Override
    public void work() {
        if(extract.isEmpty() || insert.isEmpty()) return;
        if(insertCaches.isEmpty() || extractCaches.isEmpty()) return;
        List<PosAndEnergyHandler> extractors = new ArrayList<>();
        List<TransferMode> transferModes = new ArrayList<>();
        for (Map.Entry<BlockPos, LinkedHashMap<Direction, BlockCapabilityCache<EnergyHandler, Direction>>> entry : extractCaches.entrySet()) {
            for (Map.Entry<Direction, BlockCapabilityCache<EnergyHandler, Direction>> entry1 : entry.getValue().entrySet()) {
                EnergyHandler h = entry1.getValue().getCapability();
                if (h != null) {
                    extractors.add(new PosAndEnergyHandler(entry.getKey(), h));
                    transferModes.add(extract.get(entry.getKey()).get(entry1.getKey()));
                }
            }
        }
        if(extractors.isEmpty()) return;
        int total = extractors.size();
        int base = total / 20, remaining = total % 20, processedBefore = tickCounter * base + Math.min(remaining, tickCounter);
        if (processedBefore >= total) return; // 本 tick 无任务
        int count = (tickCounter < remaining) ? base + 1 : base, end = Math.min(processedBefore + count, total);
        for (; processedBefore < end; processedBefore ++){
            var extractSet = extractors.get(processedBefore);
            TransferMode transferMode = transferModes.get(processedBefore);
            if(transferMode != TransferMode.POLLING){
                int insertCount = 0;
                EnergyHandler insertHandler = null;
                try (Transaction transaction = Transaction.openRoot()) {
                    EnergyHandler extractHandler = extractSet.getEnergyHandler();
                    int extracted = extractHandler.extract(16777216, transaction);
                    if (extracted > 0) {
                        int size = distances.get(extractSet.getPos()).size();
                        // 按距离顺序尝试插入
                        outer:
                        for (int i = 0; i < size; i++) {
                            BlockPos checkPos = getNearestInsert(extractSet.getPos(), transferMode == TransferMode.NEAREST ? i : size - i);
                            Map<Direction, BlockCapabilityCache<EnergyHandler, Direction>> dirMap = insertCaches.get(checkPos);
                            if (dirMap == null) continue;
                            for (Map.Entry<Direction, BlockCapabilityCache<EnergyHandler, Direction>> entry : dirMap.entrySet()) {
                                EnergyHandler inserter = entry.getValue().getCapability();
                                if (inserter == null) continue;
                                int inserted = inserter.insert(extracted, transaction);
                                if (inserted == extracted) {
                                    insertHandler = inserter;
                                    insertCount = inserted;
                                    break outer;
                                }
                            }
                        }
                    }
                }
                if (insertCount == 0) break;
                try (Transaction transaction = Transaction.openRoot()) {
                    extractSet.getEnergyHandler().extract(insertCount, transaction);
                    insertHandler.insert(insertCount, transaction);
                    transaction.commit();
                }
            }
            else {
                Set<BlockPos> list = distances.get(extractSet.getPos()).keySet();
                List<EnergyHandler> inserters = new ArrayList<>();
                for (BlockPos pos : list){
                    for (Map.Entry<Direction, BlockCapabilityCache<EnergyHandler, Direction>> entry : insertCaches.get(pos).entrySet()){
                        inserters.add(entry.getValue().getCapability());
                    }
                }
                if(inserters.isEmpty()) break;
                int trueExtract = 0;
                int[] insertCounts = new int[inserters.size()];
                List<Integer> available = new ArrayList<>();
                for (int i = 0; i < inserters.size(); i++)
                    available.add((pollingIndexes.get(extractSet.getPos()) + i) % inserters.size());
                int testPolling = (pollingIndexes.get(extractSet.getPos()));
                try (Transaction transaction = Transaction.openRoot()) {
                    EnergyHandler extractHandler = extractSet.getEnergyHandler();
                    int extracted = extractHandler.extract(16777216, transaction);
                    while (extracted > 0) {
                        if(available.isEmpty()) break;
                        int baseInsertCount = extracted / available.size(), remainingCount = extracted % available.size();
                        if(baseInsertCount > 0) {
                            Iterator<Integer> iterator = available.iterator();
                            while (iterator.hasNext()){
                                testPolling = iterator.next();
                                var insertHandler = inserters.get(testPolling);
                                int inserted = insertHandler.insert(baseInsertCount, transaction);
                                insertCounts[testPolling] += inserted;
                                trueExtract += inserted;
                                extracted -= inserted;
                                if (inserted < baseInsertCount) {
                                    iterator.remove();
                                }
                            }
                        }
                        else {
                            Iterator<Integer> iterator = available.iterator();
                            while (iterator.hasNext()){
                                if(extracted == 0) break;
                                testPolling = iterator.next();
                                var insertHandler = inserters.get(testPolling);
                                int inserted = insertHandler.insert(1, transaction);
                                insertCounts[testPolling] += inserted;
                                trueExtract += inserted;
                                extracted -= inserted;
                                if (inserted == 0) {
                                    iterator.remove();
                                }
                            }
                        }
                    }
                }
                if(trueExtract == 0) break;
                pollingIndexes.put(extractSet.getPos(), (testPolling + 1) % inserters.size());
                try (Transaction transaction = Transaction.openRoot()){
                    extractSet.getEnergyHandler().extract(trueExtract, transaction);
                    for (int i = 0; i < insertCounts.length; i++){
                        inserters.get(i).insert(insertCounts[i], transaction);
                    }
                    transaction.commit();
                }
            }
        }
    }

    @Override
    public void removeExtractCache(BlockPos pos, Direction direction) {
        Map<Direction, BlockCapabilityCache<EnergyHandler, Direction>> map = extractCaches.get(pos);
        if (map != null) {
            map.remove(direction);
            if (map.isEmpty()) extractCaches.remove(pos);
        }
    }

    @Override
    public void removeInsertCache(BlockPos pos, Direction direction) {
        Map<Direction, BlockCapabilityCache<EnergyHandler, Direction>> map = insertCaches.get(pos);
        if (map != null) {
            map.remove(direction);
            if (map.isEmpty()) insertCaches.remove(pos);
        }
    }

    public void addExtractCache(ServerLevel level, BlockPos pipePos, Direction pipeSide) {
        BlockPos machinePos = pipePos.relative(pipeSide);
        Direction machineSide = pipeSide.getOpposite();
        extractCaches.computeIfAbsent(pipePos, k -> new LinkedHashMap<>()).put(pipeSide,
                BlockCapabilityCache.create(
                        Capabilities.Energy.BLOCK,
                        level,
                        machinePos,
                        machineSide,
                        () -> this.posSet.contains(pipePos), // 当管道节点还在网络中时，缓存有效
                        () -> {}  // 能力失效时的回调
                )
        );
    }

    public void addInsertCache(ServerLevel level, BlockPos pipePos, Direction pipeSide) {
        BlockPos machinePos = pipePos.relative(pipeSide);
        Direction machineSide = pipeSide.getOpposite();
        insertCaches.computeIfAbsent(pipePos, k -> new LinkedHashMap<>()).put(pipeSide,
                BlockCapabilityCache.create(
                        Capabilities.Energy.BLOCK,
                        level,
                        machinePos,
                        machineSide,
                        () -> this.posSet.contains(pipePos), // 当管道节点还在网络中时，缓存有效
                        () -> {} // 能力失效时的回调
                )
        );
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new EnergyPipeNetMenu(i, inventory, extract, insert);
    }

    @Override
    protected void ensureCachesInitialized(ServerLevel level) {
        if (extractCaches.size() != extract.size() && !extract.isEmpty()) {
            // 根据已有的 extract 映射创建缓存
            for (Map.Entry<BlockPos, Map<Direction, TransferMode>> entry : extract.entrySet()) {
                BlockPos pipePos = entry.getKey();
                for (Direction dir : entry.getValue().keySet()) {
                    addExtractCache(level, pipePos, dir);
                }
            }
        }
        if(insertCaches.size() != insert.size() && ! insert.isEmpty()){
            // 根据已有的 insert 映射创建缓存
            for (Map.Entry<BlockPos, Set<Direction>> entry : insert.entrySet()) {
                BlockPos pipePos = entry.getKey();
                for (Direction dir : entry.getValue()) {
                    addInsertCache(level, pipePos, dir);
                }
            }
        }
        if(distances.isEmpty())
            checkChange();
    }

    protected static class PosAndEnergyHandler{
        protected BlockPos pos;
        protected EnergyHandler energyHandler;

        protected PosAndEnergyHandler(BlockPos pos, EnergyHandler energyHandler){
            this.pos = pos;
            this.energyHandler = energyHandler;
        }

        protected BlockPos getPos() {
            return pos;
        }

        protected EnergyHandler getEnergyHandler() {
            return energyHandler;
        }

    }
}
