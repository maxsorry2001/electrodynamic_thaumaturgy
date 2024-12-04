package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class glowing implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.GLOWING;
    }

    @Override
    public void cast(Player player, ItemStack itemStack) {
        List<LivingEntity> list = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(20));
        for (LivingEntity target : list){
            if(target instanceof Enemy)
                target.addEffect(new MobEffectInstance(MobEffects.GLOWING, (int) MoeFunction.getMagicAmount(itemStack) * 10));
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
