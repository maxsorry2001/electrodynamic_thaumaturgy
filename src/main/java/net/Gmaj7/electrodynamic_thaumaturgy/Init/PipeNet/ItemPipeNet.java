package net.Gmaj7.electrodynamic_thaumaturgy.Init.PipeNet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.menu.ItemPipeNetMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Item.EtItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class ItemPipeNet extends PipeNet{
    public static final Codec<ItemPipeNet> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("net_id").forGetter(ItemPipeNet::getNetId),
            BlockPos.CODEC.listOf().xmap(Set::copyOf, ArrayList::new).fieldOf("poses").forGetter(ItemPipeNet::getPosSet),
            Codec.unboundedMap(Codec.STRING.xmap(ItemPipeNet::keyToPos, ItemPipeNet::posToKey), BlockPos.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("adj").forGetter(ItemPipeNet::getAdj),
            Codec.unboundedMap(Codec.STRING.xmap(ItemPipeNet::keyToPos, ItemPipeNet::posToKey), Direction.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("insert").forGetter(ItemPipeNet::getInsert),
            Codec.unboundedMap(Codec.STRING.xmap(ItemPipeNet::keyToPos, ItemPipeNet::posToKey),
                    Codec.unboundedMap(Direction.CODEC, TransferMode.CODEC)).fieldOf("extract").forGetter(ItemPipeNet::getExtract),
            Codec.INT.fieldOf("tick_counter").forGetter(ItemPipeNet::getTickCounter),
            Codec.unboundedMap(Codec.STRING.xmap(ItemPipeNet::keyToPos, ItemPipeNet::posToKey),
                    Codec.unboundedMap(Direction.CODEC, ItemStack.CODEC.listOf().xmap(List::copyOf, ArrayList::new))).fieldOf("filter").forGetter(ItemPipeNet::getFilter)
    ).apply(i, ItemPipeNet::new));

    // 为每个连接的机器存储其对应的缓存
    private LinkedHashMap<BlockPos, LinkedHashMap<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>>> extractCaches;
    private LinkedHashMap<BlockPos, LinkedHashMap<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>>> insertCaches;
    private LinkedHashMap<BlockPos, Map<Direction, List<ItemStack>>> filter;
    public ItemPipeNet(int id) {
        super(id, PipeNetType.ITEM);
        this.insertCaches = new LinkedHashMap<>();
        this.extractCaches = new LinkedHashMap<>();
        this.filter = new LinkedHashMap<>();
    }

    public ItemPipeNet(int id, Set<BlockPos> posSet, Map<BlockPos, Set<BlockPos>> adj, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Map<Direction, TransferMode>> extract, int tickCounter, Map<BlockPos, Map<Direction, List<ItemStack>>> filter) {
        super(id, posSet, adj, insert, extract, tickCounter, PipeNetType.ITEM);
        this.insertCaches = new LinkedHashMap<>();
        this.extractCaches = new LinkedHashMap<>();
        this.filter = new LinkedHashMap<>();
        for (Map.Entry<BlockPos, Map<Direction, List<ItemStack>>> entry : filter.entrySet()) {
            BlockPos pos = entry.getKey();
            Map<Direction, List<ItemStack>> innerMap = entry.getValue();
            Map<Direction, List<ItemStack>> newInnerMap = new HashMap<>();
            for (Map.Entry<Direction, List<ItemStack>> innerEntry : innerMap.entrySet()) {
                Direction dir = innerEntry.getKey();
                List<ItemStack> originalList = innerEntry.getValue();
                // 复制为 ArrayList（可变）
                List<ItemStack> newList = new ArrayList<>(originalList);
                newInnerMap.put(dir, newList);
            }
            this.filter.put(pos, newInnerMap);
        }
    }
    public ItemPipeNet(int id, Set<BlockPos> posSet, Map<BlockPos, Set<BlockPos>> adj, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Map<Direction, TransferMode>> extract, int tickCounter) {
        this(id, posSet, adj, insert, extract, tickCounter, new HashMap<>());
    }

    public Map<BlockPos, Map<Direction, List<ItemStack>>> getFilter() {
        return filter;
    }

    @Override
    public void removeExtractCache(BlockPos pos, Direction direction) {
        Map<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>> map = extractCaches.get(pos);
        if (map != null) {
            map.remove(direction);
            if (map.isEmpty()) extractCaches.remove(pos);
        }
    }

    @Override
    public void removeInsertCache(BlockPos pos, Direction direction) {
        Map<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>> map = insertCaches.get(pos);
        if (map != null) {
            map.remove(direction);
            if (map.isEmpty()) insertCaches.remove(pos);
        }
    }

    @Override
    public void addExtractCache(ServerLevel level, BlockPos pipePos, Direction pipeSide) {
        BlockPos machinePos = pipePos.relative(pipeSide);
        Direction machineSide = pipeSide.getOpposite();
        extractCaches.computeIfAbsent(pipePos, k -> new LinkedHashMap<>()).put(pipeSide,
                BlockCapabilityCache.create(
                        Capabilities.Item.BLOCK,
                        level,
                        machinePos,
                        machineSide,
                        () -> this.posSet.contains(pipePos), // 当管道节点还在网络中时，缓存有效
                        () -> {}  // 能力失效时的回调
                )
        );
    }

    @Override
    public void addInsertCache(ServerLevel level, BlockPos pipePos, Direction pipeSide) {
        BlockPos machinePos = pipePos.relative(pipeSide);
        Direction machineSide = pipeSide.getOpposite();
        insertCaches.computeIfAbsent(pipePos, k -> new LinkedHashMap<>()).put(pipeSide,
                BlockCapabilityCache.create(
                        Capabilities.Item.BLOCK,
                        level,
                        machinePos,
                        machineSide,
                        () -> this.posSet.contains(pipePos), // 当管道节点还在网络中时，缓存有效
                        () -> {} // 能力失效时的回调
                )
        );
    }

    @Override
    public void removePosCache(BlockPos blockPos) {
        insertCaches.remove(blockPos);
        extractCaches.remove(blockPos);
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

    @Override
    protected void work() {
        if(extract.isEmpty() || insert.isEmpty()) return;
        if(insertCaches.isEmpty() || extractCaches.isEmpty()) return;
        List<ResourceExtractSet<ItemResource>> extractors = new ArrayList<>();
        List<TransferMode> transferModes = new ArrayList<>();
        for (Map.Entry<BlockPos, LinkedHashMap<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>>> entry : extractCaches.entrySet()) {
            for (Map.Entry<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>> entry1 : entry.getValue().entrySet()) {
                ResourceHandler<ItemResource> h = entry1.getValue().getCapability();
                if (h != null) {
                    extractors.add(new ResourceExtractSet<>(entry.getKey(), entry1.getKey(), h));
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
            ItemResource resource = ItemResource.EMPTY;
            var extractSet = extractors.get(processedBefore);
            TransferMode transferMode = transferModes.get(processedBefore);
            FilterSetting extractFilter = getFilterSetting(extractSet.getPos(), extractSet.direction);
            if(transferMode != TransferMode.POLLING){
                if (dealDistance(extractSet, extractFilter, resource, transferMode)) break;
            }
            else {
                if (dealPolling(extractSet, extractFilter, resource)) break;
            }
        }
    }

    private boolean dealPolling(ResourceExtractSet<ItemResource> extractSet, FilterSetting filterSetting, ItemResource resource) {
        List<ResourceHandler<ItemResource>> inserters = new ArrayList<>();
        List<PosAndDirection> posAndDirections = new ArrayList<>();
        for (BlockPos pos : distances.get(extractSet.getPos()).keySet()){
            for (Map.Entry<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>> entry : insertCaches.get(pos).entrySet()){
                inserters.add(entry.getValue().getCapability());
                posAndDirections.add(new PosAndDirection(pos, entry.getKey()));
            }
        }
        if(inserters.isEmpty()) return true;
        int trueExtract = 0, order = 0;
        int[] insertCounts = new int[inserters.size()];
        List<Integer> availableInsert = new ArrayList<>();
        for (int i = 0; i < inserters.size(); i++)
            availableInsert.add((pollingIndexes.get(extractSet.getPos()) + i) % inserters.size());
        int testPolling = (pollingIndexes.get(extractSet.getPos()));
        try (Transaction transaction = Transaction.openRoot()) {
            while (trueExtract == 0){
                List<Integer> available = new ArrayList<>(availableInsert);
                ResourceHandler<ItemResource> extractHandler = extractSet.getResourceHandler();
                ResourceAndIndex resourceAndIndex = getAvailableExtract(filterSetting, extractHandler, order);
                resource = resourceAndIndex.resource;
                order = resourceAndIndex.index;
                if (!resource.isEmpty()) {
                    int extracted = extractHandler.extract(resource, resource.getMaxStackSize(), transaction);
                    while (extracted > 0) {
                        if (available.isEmpty()) break;
                        int baseInsertCount = extracted / available.size();
                        Iterator<Integer> iterator = available.iterator();
                        while (iterator.hasNext()) {
                            testPolling = iterator.next();
                            var insertHandler = inserters.get(testPolling);
                            FilterSetting insertFilter = getFilterSetting(posAndDirections.get(testPolling).pos, posAndDirections.get(testPolling).direction);
                            int inserted = checkFilter(insertFilter, resource) ? insertHandler.insert(resource, Math.max(baseInsertCount, 1), transaction) : 0;
                            insertCounts[testPolling] += inserted;
                            trueExtract += inserted;
                            extracted -= inserted;
                            boolean flag = baseInsertCount > 0;
                            if ((flag && inserted < baseInsertCount) || (!flag && inserted == 0)) {
                                iterator.remove();
                            }
                            if(extracted <= 0) break;
                        }
                    }
                }
                if(resourceAndIndex.index() >= extractHandler.size()) break;
            }
        }
        if(trueExtract == 0) return true;
        pollingIndexes.put(extractSet.getPos(), (testPolling + 1) % inserters.size());
        try (Transaction transaction = Transaction.openRoot()){
            extractSet.resourceHandler.extract(resource, trueExtract, transaction);
            for (int i = 0; i < insertCounts.length; i++){
                inserters.get(i).insert(resource, insertCounts[i], transaction);
            }
            transaction.commit();
        }
        return false;
    }

    private boolean dealDistance(ResourceExtractSet<ItemResource> extractSet, FilterSetting filterSetting, ItemResource resource, TransferMode transferMode) {
        int insertCount = 0, order = 0;
        ResourceHandler<ItemResource> insertHandler = null;
        try (Transaction transaction = Transaction.openRoot()) {
            while (insertCount == 0){
                ResourceHandler<ItemResource> extractHandler = extractSet.getResourceHandler();
                ResourceAndIndex resourceAndIndex = getAvailableExtract(filterSetting, extractHandler, order);
                resource = resourceAndIndex.resource;
                order = resourceAndIndex.index;
                if (!resource.isEmpty()) {
                    // 先模拟提取（最大尝试一组，但实际提取量可能小于maxStackSize）
                    int extracted = extractHandler.extract(resource, resource.getMaxStackSize(), transaction);
                    if (extracted > 0) {
                        int size = distances.get(extractSet.getPos()).size();
                        // 按距离顺序尝试插入
                        outer:
                        for (int i = 0; i < size; i++) {
                            BlockPos checkPos = getNearestInsert(extractSet.getPos(), transferMode == TransferMode.NEAREST ? i : size - i);
                            Map<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>> dirMap = insertCaches.get(checkPos);
                            if (dirMap == null) continue;
                            for (Map.Entry<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>> entry : dirMap.entrySet()) {
                                ResourceHandler<ItemResource> inserter = entry.getValue().getCapability();
                                if (inserter == null) continue;
                                FilterSetting insertFilter = getFilterSetting(checkPos, entry.getKey());
                                int inserted = checkFilter(insertFilter, resource) ? inserter.insert(resource, extracted, transaction) : 0;
                                if (inserted == extracted) {
                                    insertHandler = inserter;
                                    insertCount = inserted;
                                    break outer;
                                }
                            }
                        }
                    }
                }
                if(order >= extractHandler.size()) break;
            }
        }
        if (resource.isEmpty() || insertCount == 0) return true;
        try (Transaction transaction = Transaction.openRoot()) {
            extractSet.getResourceHandler().extract(resource, insertCount, transaction);
            insertHandler.insert(resource, insertCount, transaction);
            transaction.commit();
        }
        return false;
    }

    private boolean checkFilter(FilterSetting filterSetting, ItemResource resource) {
        if(filterSetting.isEmpty()) return true;
        boolean flagWhite = filterSetting.whiteEmpty(), flagBlack = false;
        if(!flagWhite){
            for (ItemStack itemStack : filterSetting.white()) {
                if (itemStack.is(resource.getItem())) {
                    flagWhite = true;
                    break;
                }
            }
        }
        for (ItemStack itemStack : filterSetting.black()){
            if(itemStack.is(resource.getItem())){
                flagBlack = true;
                break;
            }
        }
        return flagWhite && !flagBlack;
    }

    private FilterSetting getFilterSetting(BlockPos pos, Direction direction){
        if(filter.containsKey(pos) && filter.get(pos).containsKey(direction)) {
            List<ItemStack> white = new ArrayList<>(), black = new ArrayList<>();
            for (ItemStack itemStack : filter.get(pos).get(direction)){
                if(!itemStack.is(EtItems.FILTER_SETTING)) white.add(itemStack.copy());
                else {
                    ItemContainerContents contents = itemStack.get(EtDataComponentTypes.FILTER_CONTAINER);
                    List<ItemStack> list = new ArrayList<>(contents.allItemsCopyStream().toList());
                    if(itemStack.getOrDefault(EtDataComponentTypes.FILTER_WHITE.get(), true)) white.addAll(list);
                    else black.addAll(list);
                }
            }
            return new FilterSetting(white, black);
        }
        return new FilterSetting(new ArrayList<>(), new ArrayList<>());
    }

    private ResourceAndIndex getAvailableExtract(FilterSetting filterSetting, ResourceHandler<ItemResource> extractHandler, int index){
        // 找到第一个非空槽位
        ItemResource checkResource = ItemResource.EMPTY;
        for (; index < extractHandler.size(); index++) {
            checkResource = extractHandler.getResource(index);
            if (!checkResource.isEmpty() && checkFilter(filterSetting, checkResource)) {
                index ++;
                break;
            }
        }
        return new ResourceAndIndex(checkResource, index);
    }

    @Override
    public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
        super.writeClientSideData(menu, buffer);
        buffer.writeMap(filter, (buf, pos) -> buf.writeBlockPos(pos),
                (buf, map) -> buf.writeMap(map,
                        (b, direction) -> b.writeEnum(direction),
                        (b, list) -> {
                            RegistryFriendlyByteBuf b1 = (RegistryFriendlyByteBuf)b;// 编码 List<ItemStack>
                            b1.writeVarInt(list.size());     // 写入列表大小
                            for (ItemStack stack : list) {
                                ItemStack.STREAM_CODEC.encode(b1, stack); // 编码每个 ItemStack
                            }
                        }));
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        addLookingPlayer((ServerPlayer) player);
        return new ItemPipeNetMenu(i, inventory, extract, insert, filter, netId);
    }

    public void addFilter(BlockPos pos, Direction direction, ItemStack itemStack, int slot){
        if(slot < 0 || slot > 2) return;
        if(itemStack.isEmpty()){
            if(filter.isEmpty() || !filter.containsKey(pos)) return;
            Map<Direction, List<ItemStack>> posFilter = filter.get(pos);
            if(!posFilter.containsKey(direction)) return;
            List<ItemStack> dirFilter = posFilter.get(direction);
            if(dirFilter.size() < slot) return;
            dirFilter.remove(slot);
            if(dirFilter.isEmpty()){
                posFilter.remove(direction);
                if(posFilter.isEmpty()) {
                    this.filter.remove(pos);
                }
            }
            return;
        }
        ItemStack filterItem = itemStack.copy();
        filterItem.setCount(1);
        if(filter.isEmpty() || !filter.containsKey(pos)){
            List<ItemStack> filters = new ArrayList<>();
            filters.add(filterItem);
            Map<Direction, List<ItemStack>> map = new HashMap<>();
            map.put(direction, filters);
            this.filter.put(pos, map);
            return;
        }
        Map<Direction, List<ItemStack>> posFilter = filter.get(pos);
        if(!posFilter.containsKey(direction)){
            List<ItemStack> filters = new ArrayList<>();
            filters.add(filterItem);
            filter.get(pos).put(direction, filters);
            return;
        }
        List<ItemStack> dirFilter = posFilter.get(direction);
        if(dirFilter.size() > slot)
            dirFilter.set(slot, filterItem);
        else dirFilter.add(filterItem);
    }

    private record ResourceAndIndex(ItemResource resource, int index){}

    private record PosAndDirection(BlockPos pos, Direction direction){}

    private record FilterSetting(List<ItemStack> white, List<ItemStack> black){
        private boolean isEmpty(){
            return this.white.isEmpty() && this.black.isEmpty();
        }

        private boolean whiteEmpty(){
            return this.white.isEmpty();
        }

        private boolean blackEmpty(){
            return this.black.isEmpty();
        }
    }
}
