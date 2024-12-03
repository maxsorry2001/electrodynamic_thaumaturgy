package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MagicUseItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.entity.PartEntity;

import java.util.ArrayList;
import java.util.List;

public class MoeFunction {
    public static HitResult checkEntityIntersecting(Entity entity, Vec3 start, Vec3 end, float bbInflation) {
        Vec3 hitPos = null;
        if (entity.isMultipartEntity()) {
            for (PartEntity p : entity.getParts()) {
                var hit = p.getBoundingBox().inflate(bbInflation).clip(start, end).orElse(null);
                if (hit != null) {
                    hitPos = hit;
                    break;
                }
            }
        } else {
            hitPos = entity.getBoundingBox().inflate(bbInflation).clip(start, end).orElse(null);
        }
        if (hitPos != null)
            return new EntityHitResult(entity, hitPos);
        else
            return BlockHitResult.miss(end, Direction.UP, BlockPos.containing(end));

    }
    public static RayHitResult getRayHitResult(Level level, Entity source, Vec3 start, Vec3 end, boolean checkForBlocks, float bbInflation) {
        BlockHitResult blockHitResult;
        if (checkForBlocks) {
            blockHitResult = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, source));
            end = blockHitResult.getLocation();
        }
        AABB range = source.getBoundingBox().expandTowards(end.subtract(start));
        List<HitResult> hits = new ArrayList<>();
        List<? extends Entity> entities = level.getEntities(source, range);
        for (Entity target : entities) {
            HitResult hit = checkEntityIntersecting(target, start, end, bbInflation);
            if (hit.getType() != HitResult.Type.MISS) {
                hits.add(hit);
            }
        }
        return new RayHitResult(end, hits);
    }

    public static class RayHitResult{
        private Vec3 end;
        private List<HitResult> targets;

        public RayHitResult(Vec3 end, List<HitResult> hits) {
            this.end = end;
            this.targets = hits;
        }

        public List<HitResult> getTargets() {
            return targets;
        }

        public Vec3 getEnd() {
            return end;
        }
    }

    public static ItemContainerContents setEmpty(){
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(MoeItems.EMPTY_POWER.get()));
        list.add(new ItemStack(MoeItems.EMPTY_LC.get()));
        for (int i = 2; i < MagicUseItem.getMaxMagicSlots(); i ++){
                list.add(new ItemStack(MoeItems.EMPTY_MODULE.get()));
        }
        return ItemContainerContents.fromItems(list);
    }
}
