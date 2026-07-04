package net.Gmaj7.electrodynamic_thaumaturgy.item.custom.weapon;

import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.FocusBulletEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.ElectromagneticTierItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class FocusGun extends ElectromagneticWeaponItem{
    private static int shootUse = 1024;
    public FocusGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        if(usedHand == InteractionHand.OFF_HAND && player.getMainHandItem().getItem() instanceof ElectromagneticTierItem item) {
            changeModule(level, player, item.getChangeSlot());
            return InteractionResult.CONSUME;
        }
        ItemStack itemStack = player.getItemInHand(usedHand);
        if(itemStack.getItem() instanceof FocusGun && itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack)).getCapacityAsInt() > shootUse)
            player.startUsingItem(usedHand);
        return InteractionResult.CONSUME;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return 12000;
    }

    public void shoot(Level level, Player player, ItemStack itemStack){
        EnergyHandler energyHandler = itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack));
        int pattern = itemStack.getOrDefault(EtDataComponentTypes.WEAPON_WORK_PATTERN, 0),
            energyUse = (int) ((pattern == 0 ? shootUse / 20 : shootUse) / Function.getEfficiency(itemStack));
        if(energyHandler.getAmountAsInt() < energyUse) return;
        float damage = Function.getResultAmount(itemStack);
        FocusBulletEntity bullet = new FocusBulletEntity(level, player, damage, pattern);
        bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 5.0F, 0.0F);
        level.addFreshEntity(bullet);
        if(pattern != 0) player.getCooldowns().addCooldown(itemStack, (int) (20 / Function.getCoolDownRate(itemStack)));
        try (Transaction transaction = Transaction.openRoot()){
            energyHandler.extract(energyUse, transaction);
            transaction.commit();
        }
    }

    public static class Open implements IClientItemExtensions{
        @Override
        public void renderFirstPersonOverlay(ItemStack stack, EquipmentSlot equipmentSlot, Player player, GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
            // 1. 判断是否应该显示开镜
            if (!(stack.getItem() instanceof FocusGun)) return;
            if (!Minecraft.getInstance().options.keyUse.isDown()) return;

            // 3. 获取屏幕尺寸
            int screenWidth = guiGraphics.guiWidth();
            int screenHeight = guiGraphics.guiHeight();

            // 4. 设定覆盖图大小（可根据需要调整，这里使用屏幕较小边的一半）
            float baseSize = Math.min(screenWidth, screenHeight) * 0.6F;
            int size = Mth.floor(baseSize);

            // 5. 计算绘制区域（居中）
            int left = (screenWidth - size) / 2;
            int top = (screenHeight - size) / 2;
            int right = left + size;
            int bottom = top + size;

            // 7. 绘制自定义瞄准镜纹理（这里使用你指定的纹理）
            Identifier scopeTexture = IClientItemExtensions.super.getScopeOverlayTexture(stack); // 或自定义
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, scopeTexture, left, top, 0.0F, 0.0F, size, size, size, size);
            // 上方
            guiGraphics.fill(RenderPipelines.GUI, 0, 0, screenWidth, top, -16777216);
            // 下方
            guiGraphics.fill(RenderPipelines.GUI, 0, bottom, screenWidth, screenHeight, -16777216);
            // 左方
            guiGraphics.fill(RenderPipelines.GUI, 0, top, left, bottom, -16777216);
            // 右方
            guiGraphics.fill(RenderPipelines.GUI, right, top, screenWidth, bottom, -16777216);
        }

        private float getChargeProgress(Player player, ItemStack bow) {
            int useDuration = bow.getUseDuration(player);
            int ticksUsing = useDuration - player.getUseItemRemainingTicks();
            float progress = (float) ticksUsing / 20.0F; // 弓满蓄力20刻
            return Math.min(progress, 1.0F);
        }
    }
}
