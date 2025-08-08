package net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public abstract class AbstractMagicBE extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity{
    protected Entity owner;
    protected UUID ownerUUID;
    public AbstractMagicBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AbstractMagicBE abstractMagicBE){
        if(abstractMagicBE.canCast()) {
            abstractMagicBE.cast();
        }
    }

    protected abstract boolean canCast();

    protected abstract void cast();

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.ownerUUID != null) {
            tag.putUUID("Owner", this.ownerUUID);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.hasUUID("Owner")) {
            this.ownerUUID = tag.getUUID("Owner");
            this.owner = null;
        }
    }

    public Entity getOwner() {
        if (this.owner != null && !this.owner.isRemoved()) {
            return this.owner;
        } else {
            if (this.ownerUUID != null) {
                Level var2 = this.getLevel();
                if (var2 instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel)var2;
                    this.owner = serverlevel.getEntity(this.ownerUUID);
                    return this.owner;
                }
            }
            return null;
        }
    }

    public void setOwner(Entity owner) {
        if (owner != null) {
            this.ownerUUID = owner.getUUID();
            this.owner = owner;
        }
    }
}
