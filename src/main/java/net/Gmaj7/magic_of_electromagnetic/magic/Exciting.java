package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeAttachmentType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class Exciting implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.EXCITING;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(20));
        for (LivingEntity target : list){
            if(target instanceof Enemy) {
                target.addEffect(new MobEffectInstance(MoeEffects.EXCITING, 200));
                target.setData(MoeAttachmentType.EXCITING_DAMAGE, MoeFunction.getMagicAmount(itemStack) / 10);
            }
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 200;
    }

    @Override
    public int getBaseCooldown() {
        return 20;
    }
}
