package net.Gmaj7.electrodynamic_thaumaturgy.eventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.weapon.ElectromagneticWeaponItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID)
public class ClickDispose {

    @SubscribeEvent
    public static void entityDispose(PlayerInteractEvent.EntityInteractSpecific event){
        Player player = event.getEntity();
        Entity target = event.getTarget();
        InteractionHand hand = event.getHand();
        ItemStack handStack = player.getItemInHand(hand);
        if(handStack.is(EtItems.GENETIC_RECORDER.get()) && target instanceof LivingEntity && target.isAlive()){
            handStack.set(EtDataComponentTypes.ENTITY_TYPE, target.getType());
            player.swing(event.getHand());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void rightClickBlockDispose(PlayerInteractEvent.RightClickBlock event){
        Player player = event.getEntity();
        if(!player.level().isClientSide()) {
            ItemStack itemStack = player.getItemInHand(event.getHand());
            BlockHitResult hitResult = event.getHitVec();
            if(player.level().getBlockState(hitResult.getBlockPos()).is(Blocks.LODESTONE) && itemStack.is(Items.IRON_INGOT)) {
                player.addItem(new ItemStack(EtItems.MAGNO_INGOT.asItem()));
                itemStack.shrink(1);
            }
        }
    }

    @SubscribeEvent
    public static void leftClickBlockDispose(InputEvent.InteractionKeyMappingTriggered event){
        if(event.isAttack()){
            Player player = Minecraft.getInstance().player;
            if (player != null && player.getMainHandItem().getItem() instanceof ElectromagneticWeaponItem) {
                event.setSwingHand(false);
                event.setCanceled(true);
            }
        }
    }
}
