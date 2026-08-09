package net.Gmaj7.electrodynamic_thaumaturgy.item.custom.weapon;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
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

public class FocusGun extends AbstractEnergyGun{
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
        return InteractionResult.CONSUME;
    }

    @Override
    public void shoot(Level level, Player player, ItemStack itemStack){
        EnergyHandler energyHandler = itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack));
        int pattern = itemStack.getOrDefault(EtDataComponentTypes.WEAPON_WORK_PATTERN, 0),
            energyUse = (int) ((pattern == 0 ? shootUse / 20 : shootUse) / Function.getEfficiency(itemStack));
        if(energyHandler.getAmountAsInt() < energyUse) return;
        float damage = Function.getResultAmount(itemStack);
        FocusBulletEntity bullet = new FocusBulletEntity(level, player, damage, pattern);
        bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, pattern == 2 ? 0.5F : 5.0F, 0.0F);
        level.addFreshEntity(bullet);
        if(pattern != 0) player.getCooldowns().addCooldown(itemStack, (int) (20 / Function.getCoolDownRate(itemStack)));
        try (Transaction transaction = Transaction.openRoot()){
            energyHandler.extract(energyUse, transaction);
            transaction.commit();
        }
    }

    @Override
    public Identifier getIcon(int pattern) {
        return switch (pattern){
            case 1 -> Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "container/weapon/focus_gun_semi");
            case 2 -> Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "container/weapon/focus_gun_chain");
            default -> Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "container/weapon/focus_gun_fast");
        };
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

            // 4. 设定覆盖图大小（根据需要调整）
            float baseSize = Math.min(screenWidth, screenHeight) * 0.9F;
            int size = Mth.floor(baseSize);

            // 5. 计算绘制区域（居中）
            int left = (screenWidth - size) / 2;
            int top = (screenHeight - size) / 2;

            // 7. 绘制自定义瞄准镜纹理
            Identifier scopeTexture = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/focus_gun_scope.png");
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, scopeTexture, left, top, 0.0F, 0.0F, size, size, size, size);
        }
    }
}
