package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.*;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.AbstractSovereignEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeData.MoeDataGet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MoePacket{
    public static class MoeSelectMagicPacket implements CustomPacketPayload {
        private final int magicSelect;
        private final InteractionHand hand;
        public static final CustomPacketPayload.Type<MoeSelectMagicPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magic_select"));
        public static final StreamCodec<RegistryFriendlyByteBuf, MoeSelectMagicPacket> STREAM_CODEC = CustomPacketPayload.codec(MoeSelectMagicPacket::write, MoeSelectMagicPacket::new);

        public MoeSelectMagicPacket(int magicSelect, InteractionHand hand) {
            this.magicSelect = magicSelect;
            this.hand = hand;
        }

        public MoeSelectMagicPacket(FriendlyByteBuf buf){
            this.magicSelect = buf.readInt();
            this.hand = buf.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        }

        public void write(FriendlyByteBuf buf){
            buf.writeInt(magicSelect);
            buf.writeBoolean(hand == InteractionHand.MAIN_HAND);
        }

        public static void handle(MoeSelectMagicPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if(context.player() instanceof ServerPlayer serverPlayer){
                    ItemStack itemInHand = serverPlayer.getItemInHand(packet.hand);
                    itemInHand.set(MoeDataComponentTypes.MAGIC_SELECT, packet.magicSelect);
                }
            });
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static class ProtectingPacket implements CustomPacketPayload{
        float protectNum;
        public static final CustomPacketPayload.Type<ProtectingPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "protecting"));
        public static final StreamCodec<RegistryFriendlyByteBuf, ProtectingPacket> STREAM_CODEC = CustomPacketPayload.codec(ProtectingPacket::write, ProtectingPacket::new);

        public ProtectingPacket(float protectNum){
            this.protectNum = protectNum;
        }

        public ProtectingPacket(FriendlyByteBuf buf){
            this.protectNum = buf.readFloat();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeFloat(protectNum);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void handle(ProtectingPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if(context.player().level().isClientSide()){
                    ((MoeDataGet)context.player()).getProtective().setProtecting(packet.protectNum);
                }
            });
        }
    }

    public static class EnergySetPacket implements CustomPacketPayload{
        int energy;
        BlockPos blockPos;
        public static final CustomPacketPayload.Type<EnergySetPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "energy_set"));
        public static final StreamCodec<RegistryFriendlyByteBuf, EnergySetPacket> STREAM_CODEC = CustomPacketPayload.codec(EnergySetPacket::write, EnergySetPacket::new);

        public EnergySetPacket(int energy, BlockPos blockPos){
            this.energy = energy;
            this.blockPos = blockPos;
        }

        public EnergySetPacket(FriendlyByteBuf buf){
            this.energy = buf.readInt();
            this.blockPos = buf.readBlockPos();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeInt(energy);
            buf.writeBlockPos(blockPos);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void handle(EnergySetPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if(context.player().level().isClientSide()){
                    BlockEntity blockEntity = context.player().level().getBlockEntity(packet.blockPos);
                    if(blockEntity instanceof IMoeEnergyBlockEntity) {
                        ((IMoeEnergyBlockEntity) blockEntity).setEnergy(packet.energy);
                    }
                }
            });
        }
    }

    public static class ThermalSetPacket implements CustomPacketPayload{
        int tick;
        int burn;
        BlockPos blockPos;
        public static final CustomPacketPayload.Type<ThermalSetPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "thermal_set"));
        public static final StreamCodec<RegistryFriendlyByteBuf, ThermalSetPacket> STREAM_CODEC = CustomPacketPayload.codec(ThermalSetPacket::write, ThermalSetPacket::new);

        public ThermalSetPacket(int tick, int burn, BlockPos blockPos){
            this.tick = tick;
            this.burn = burn;
            this.blockPos = blockPos;
        }

        public ThermalSetPacket(FriendlyByteBuf buf){
            this.tick = buf.readInt();
            this.burn = buf.readInt();
            this.blockPos = buf.readBlockPos();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeInt(tick);
            buf.writeInt(burn);
            buf.writeBlockPos(blockPos);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void handle(ThermalSetPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if(context.player().level().isClientSide()){
                    BlockEntity blockEntity = context.player().level().getBlockEntity(packet.blockPos);
                    if(blockEntity instanceof ThermalGeneratorBE) {
                        ((ThermalGeneratorBE) blockEntity).setBurnTime(packet.tick);
                        ((ThermalGeneratorBE) blockEntity).setFullBurnTime(packet.burn);
                    }
                }
            });
        }
    }

    public static class BiomassSetPacket implements CustomPacketPayload{
        int tick;
        int biomass;
        BlockPos blockPos;
        public static final CustomPacketPayload.Type<BiomassSetPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "biomass_set"));
        public static final StreamCodec<RegistryFriendlyByteBuf, BiomassSetPacket> STREAM_CODEC = CustomPacketPayload.codec(BiomassSetPacket::write, BiomassSetPacket::new);

        public BiomassSetPacket(int tick, int biomass, BlockPos blockPos){
            this.tick = tick;
            this.biomass = biomass;
            this.blockPos = blockPos;
        }

        public BiomassSetPacket(FriendlyByteBuf buf){
            this.tick = buf.readInt();
            this.biomass = buf.readInt();
            this.blockPos = buf.readBlockPos();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeInt(tick);
            buf.writeInt(biomass);
            buf.writeBlockPos(blockPos);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void handle(BiomassSetPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if(context.player().level().isClientSide()){
                    BlockEntity blockEntity = context.player().level().getBlockEntity(packet.blockPos);
                    if(blockEntity instanceof BiomassGeneratorBE) {
                        ((BiomassGeneratorBE) blockEntity).setBiomassTime(packet.tick);
                        ((BiomassGeneratorBE) blockEntity).setFullBiomassTime(packet.biomass);
                    }
                }
            });
        }
    }

    public static class CastTickPacket implements CustomPacketPayload{
        public int entityId;
        public int castTick;
        public static final CustomPacketPayload.Type<CastTickPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "cast_tick"));
        public static final StreamCodec<RegistryFriendlyByteBuf, CastTickPacket> STREAM_CODEC = CustomPacketPayload.codec(CastTickPacket::write, CastTickPacket::new);

        public CastTickPacket(int entityId, int castTick){
            this.entityId = entityId;
            this.castTick = castTick;
        }

        public CastTickPacket(FriendlyByteBuf buf){
            this.entityId = buf.readInt();
            this.castTick = buf.readInt();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeInt(entityId);
            buf.writeInt(castTick);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void handle(CastTickPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if(context.player().level().isClientSide()){
                    Entity entity = context.player().level().getEntity(packet.entityId);
                    if(entity instanceof AbstractSovereignEntity) {
                        ((AbstractSovereignEntity) entity).setCastTick(packet.castTick);
                        ((AbstractSovereignEntity) entity).castAnimationState.start(entity.tickCount);
                    }
                }
            });
        }
    }

    public static class ExtractorPacket implements CustomPacketPayload{
        int width;
        int depth;
        BlockPos blockPos;
        public static final CustomPacketPayload.Type<ExtractorPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "extractor"));
        public static final StreamCodec<RegistryFriendlyByteBuf, ExtractorPacket> STREAM_CODEC = CustomPacketPayload.codec(ExtractorPacket::write, ExtractorPacket::new);

        public ExtractorPacket(int width, int depth, BlockPos blockPos){
            this.width = width;
            this.depth = depth;
            this.blockPos = blockPos;
        }

        public ExtractorPacket(FriendlyByteBuf buf){
            this.width = buf.readInt();
            this.depth = buf.readInt();
            this.blockPos = buf.readBlockPos();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeInt(width);
            buf.writeInt(depth);
            buf.writeBlockPos(blockPos);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void handle(ExtractorPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                BlockEntity blockEntity = context.player().level().getBlockEntity(packet.blockPos);
                if(blockEntity instanceof ElectromagneticExtractorBE) {
                    ((ElectromagneticExtractorBE) blockEntity).setWidth(packet.width);
                    ((ElectromagneticExtractorBE) blockEntity).setDepth(packet.depth);
                }
            });
        }
    }

    public static class AtomicPacket implements CustomPacketPayload{
        BlockPos blockPos;
        int progress;
        public static final CustomPacketPayload.Type<AtomicPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "atomic"));
        public static final StreamCodec<RegistryFriendlyByteBuf, AtomicPacket> STREAM_CODEC = CustomPacketPayload.codec(AtomicPacket::write, AtomicPacket::new);

        public AtomicPacket(BlockPos blockPos, int progress){
            this.blockPos = blockPos;
            this.progress = progress;
        }

        public AtomicPacket(FriendlyByteBuf buf){
            this.progress = buf.readInt();
            this.blockPos = buf.readBlockPos();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeInt(progress);
            buf.writeBlockPos(blockPos);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void handle(AtomicPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                BlockEntity blockEntity = context.player().level().getBlockEntity(packet.blockPos);
                if(blockEntity instanceof AtomicReconstructionBE) {
                    ((AtomicReconstructionBE) blockEntity).setProgress(packet.progress);
                }
            });
        }
    }
}
