package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom.LcOscillatorModuleItem;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom.MagicCastItem;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom.PowerAmplifierItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.entity.PartEntity;

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

    public static void checkTargetEnhancement(ItemStack itemStack, LivingEntity livingEntity){
        EnhancementData enhancementData = itemStack.get(MoeDataComponentTypes.ENHANCEMENT_DATA);
        int entropy = enhancementData.entropy();
        if(entropy > 0){
            livingEntity.igniteForTicks(entropy * 20);
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

    public static <T> Holder<T> getHolder(Level level, ResourceKey<Registry<T>> registry, ResourceKey<T> resourceKey){
        return level.registryAccess().registryOrThrow(registry).getHolderOrThrow(resourceKey);
    }

    public static BlockHitResult getHitBlock(Level level, Entity source, Vec3 start, Vec3 end){
        return level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, source));
    }
    public static RayHitResult getLineHitResult(Level level, Entity source, Vec3 start, Vec3 end, boolean checkForBlocks, float bbInflation) {
        BlockHitResult blockHitResult;
        if (checkForBlocks) {
            blockHitResult = getHitBlock(level, source, start, end);
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

    public static RayHitResult getBlockLineHitResult(Level level, ElectromagneticDriverBE electromagneticDriverBE, Vec3 start, Vec3 end, boolean checkForBlocks, float bbInflation) {
        BlockHitResult blockHitResult;
        AABB range = new AABB(electromagneticDriverBE.getBlockPos()).expandTowards(end.subtract(start));
        List<HitResult> hits = new ArrayList<>();
        List<? extends Entity> entities = level.getEntities(electromagneticDriverBE.getOwner(), range);
        for (Entity target : entities) {
            HitResult hit = checkEntityIntersecting(target, start, end, bbInflation);
            if (hit.getType() != HitResult.Type.MISS) {
                hits.add(hit);
            }
        }
        return new RayHitResult(end, hits);
    }

    public static ItemContainerContents setEmpty(){
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(MoeItems.EMPTY_POWER.get()));
        list.add(new ItemStack(MoeItems.EMPTY_LC.get()));
        for (int i = 2; i < MagicCastItem.getMaxMagicSlots(); i ++)
            list.add(new ItemStack(MoeItems.EMPTY_PRIMARY_MODULE.get()));
        return ItemContainerContents.fromItems(list);
    }

    // 使用四元数旋转点列表 (YX顺序)
    public static List<Vec3> rotatePointsYX(List<Vec3> points, double xRot, double yRot) {
        List<Vec3> rotatedPoints = new ArrayList<>();

        // 创建旋转四元数 (先绕Y轴旋转yRot，再绕X轴旋转xRot)
        Quaternion rotation = Quaternion.fromEulerYX(yRot, xRot).normalize();

        // 旋转每个点
        for (Vec3 point : points) {
            rotatedPoints.add(rotation.rotateVector(point));
        }

        return rotatedPoints;
    }

    // 生成圆形点集 (在xy平面上)
    public static List<Vec3> getCirclePoints(int numPoints, double radius) {
        List<Vec3> points = new ArrayList<>();

        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            points.add(new Vec3(x, y, 0));
        }

        return points;
    }

    public static List<Vec3> getLinePoints(Vec3 start, Vec3 end, int count){
        List<Vec3> points = new ArrayList<>();
        Vec3 vec3 = end.subtract(start).scale((double) 1 / count);
        for (int i = 0; i < count; i++){
            Vec3 vec31 = start.add(vec3.scale(i));
            points.add(vec31);
        }
        return points;
    }

    public static List<Vec3> getPolygonVertices(int sides, double radius, float startAngle){
        List<Vec3> points = new ArrayList<>();
        float dAngle = 2 * Mth.PI / sides;
        for (int i = 0; i < sides; i++)
            points.add(new Vec3(radius * Mth.cos(startAngle + dAngle * i), radius * Mth.sin(startAngle + dAngle * i), 0));
        return points;
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

    public static class Quaternion {
        public double w, x, y, z;

        public Quaternion(double w, double x, double y, double z) {
            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        // 单位四元数
        public static Quaternion identity() {
            return new Quaternion(1, 0, 0, 0);
        }

        // 从欧拉角创建四元数 (YX顺序: 先绕Y轴，再绕X轴)
        public static Quaternion fromEulerYX(double yRot, double xRot) {
            double cy = Math.cos(yRot * 0.5);
            double sy = Math.sin(yRot * 0.5);
            double cx = Math.cos(xRot * 0.5);
            double sx = Math.sin(xRot * 0.5);

            double w = cy * cx;
            double x = cy * sx;
            double y = sy * cx;
            double z = -sy * sx;

            return new Quaternion(w, x, y, z);
        }

        // 四元数乘法
        public Quaternion multiply(Quaternion other) {
            double newW = w * other.w - x * other.x - y * other.y - z * other.z;
            double newX = w * other.x + x * other.w + y * other.z - z * other.y;
            double newY = w * other.y - x * other.z + y * other.w + z * other.x;
            double newZ = w * other.z + x * other.y - y * other.x + z * other.w;

            return new Quaternion(newW, newX, newY, newZ);
        }

        // 四元数共轭
        public Quaternion conjugate() {
            return new Quaternion(w, -x, -y, -z);
        }

        // 用四元数旋转向量
        public Vec3 rotateVector(Vec3 vector) {
            // 将向量转换为纯四元数 (w=0)
            Quaternion vecQuat = new Quaternion(0, vector.x, vector.y, vector.z);

            // 旋转计算: v' = q * v * q^-1
            // 对于单位四元数，q^-1 = 共轭
            Quaternion result = this.multiply(vecQuat).multiply(this.conjugate());

            return new Vec3(result.x, result.y, result.z);
        }

        // 归一化四元数
        public Quaternion normalize() {
            double magnitude = Math.sqrt(w*w + x*x + y*y + z*z);
            return new Quaternion(w/magnitude, x/magnitude, y/magnitude, z/magnitude);
        }

        public static Quaternion fromAxisAngle(Vec3 axis, double angle) {
            double halfAngle = angle * 0.5;
            double sinHalf = Math.sin(halfAngle);
            double cosHalf = Math.cos(halfAngle);

            // 确保轴是单位向量
            double length = Math.sqrt(axis.x * axis.x + axis.y * axis.y + axis.z * axis.z);
            if (length > 0) {
                axis = new Vec3(axis.x / length, axis.y / length, axis.z / length);
            }

            return new Quaternion(
                    cosHalf,
                    axis.x * sinHalf,
                    axis.y * sinHalf,
                    axis.z * sinHalf
            );
        }
    }
}
