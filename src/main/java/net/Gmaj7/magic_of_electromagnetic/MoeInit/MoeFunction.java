package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.LcOscillatorModuleItem;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MagicCastItem;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.PowerAmplifierItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class MoeFunction {
    public static float getMagicAmount(ItemStack itemStack){
        float result = getBaseAmount(itemStack) * getBasePower(itemStack) * getStrengthRate(itemStack);
        return result;
    }
    private static float getBaseAmount(ItemStack itemStack){
        float amount = 0;
        if(itemStack.has(DataComponents.CONTAINER)){
            ItemContainerContents contents = itemStack.get(DataComponents.CONTAINER);
            ItemStack lcModule = contents.getStackInSlot(MagicCastItem.getLcNum());
            Item item = lcModule.getItem();
            if(item instanceof LcOscillatorModuleItem) amount = ((LcOscillatorModuleItem) item).getBasicAmount();
        }
        return amount;
    }

    private static float getBasePower(ItemStack itemStack){
        float power = 1;
        if(itemStack.has(DataComponents.CONTAINER)){
            ItemContainerContents contents = itemStack.get(DataComponents.CONTAINER);
            ItemStack powerModule = contents.getStackInSlot(MagicCastItem.getPowerNum());
            Item item = powerModule.getItem();
            if(item instanceof PowerAmplifierItem) power = ((PowerAmplifierItem) item).getMagnification();
        }
        return power;
    }

    public static float getStrengthRate(ItemStack itemStack){
        return itemStack.get(MoeDataComponentTypes.ENHANCEMENT_DATA).strength();
    }

    public static float getCoolDownRate(ItemStack itemStack){
        return itemStack.get(MoeDataComponentTypes.ENHANCEMENT_DATA).coolDown();
    }

    public static float getEfficiency(ItemStack itemStack){
        return itemStack.get(MoeDataComponentTypes.ENHANCEMENT_DATA).efficiency();
    }

    public static void checkPotentialDifference(ItemStack itemStack, LivingEntity livingEntity){
        int level = itemStack.get(MoeDataComponentTypes.ENHANCEMENT_DATA).potential_difference();
        if(level > 0 && livingEntity.level().isClientSide()){
            if(livingEntity.hasEffect(MoeEffects.POTENTIAL_DIFFERENCE)){
                level = livingEntity.getEffect(MoeEffects.POTENTIAL_DIFFERENCE).getAmplifier() + level;
                if(level >= 4) {
                    PacketDistributor.sendToServer(new MoePacket.LightingPacket(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()));
                    level = level - 5;
                    livingEntity.removeEffect(MoeEffects.POTENTIAL_DIFFERENCE);
                }
                if(level > -1) livingEntity.addEffect(new MobEffectInstance(MoeEffects.POTENTIAL_DIFFERENCE, 200, level));
            }
            else livingEntity.addEffect(new MobEffectInstance(MoeEffects.POTENTIAL_DIFFERENCE, level - 1));
        }
    }

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
    public static RayHitResult getLineHitResult(Level level, Entity source, Vec3 start, Vec3 end, boolean checkForBlocks, float bbInflation) {
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

        public HitResult getNearest(LivingEntity livingEntity){
            HitResult nearest = null;
            double distance = 0;
            for (HitResult target : getTargets()){
                if(nearest == null) {
                    nearest = target;
                    distance = target.distanceTo(livingEntity);
                }
                else {
                    nearest =  target.distanceTo(livingEntity) < distance ? target : nearest;
                }
            }
            return nearest;
        }

        public Vec3 getEnd() {
            return end;
        }
    }

    public static ItemContainerContents setEmpty(){
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(MoeItems.EMPTY_POWER.get()));
        list.add(new ItemStack(MoeItems.EMPTY_LC.get()));
        for (int i = 2; i < MagicCastItem.getMaxMagicSlots(); i ++)
            list.add(new ItemStack(MoeItems.EMPTY_MODULE.get()));
        return ItemContainerContents.fromItems(list);
    }
}
