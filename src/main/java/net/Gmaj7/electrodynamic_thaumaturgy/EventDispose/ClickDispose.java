package net.Gmaj7.electrodynamic_thaumaturgy.EventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = EelectrodynamicThaumaturgy.MODID)
public class ClickDispose {

    @SubscribeEvent
    public static void entityDispose(PlayerInteractEvent.EntityInteractSpecific event){
        Player player = event.getEntity();
        Entity target = event.getTarget();
        InteractionHand hand = event.getHand();
        ItemStack handStack = player.getItemInHand(hand);
        if(handStack.is(MoeItems.GENETIC_RECORDER.get()) && target instanceof LivingEntity && target.isAlive()){
            CompoundTag compoundTag = new CompoundTag();
            ((LivingEntity) target).addAdditionalSaveData(compoundTag);
            compoundTag.remove("UUID");
            handStack.set(MoeDataComponentTypes.ENTITY_TYPE, EntityType.getKey(target.getType()));
            handStack.set(MoeDataComponentTypes.ENTITY_DATA, compoundTag);
            player.swing(event.getHand());
            event.setCanceled(true);
        }
    }
}
