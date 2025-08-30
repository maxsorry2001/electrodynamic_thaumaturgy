package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class MoeKeyMapping {
    public static final String KEY_CATEGORY_MOE = "key.category.electrodynamic_thaumaturgy.moe";
    public static final String KEY_SWITCH_MAGIC = "key.electrodynamic_thaumaturgy.switch_magic";

    public static final KeyMapping SELECT_MAGIC = new KeyMapping(KEY_SWITCH_MAGIC, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_MOE);
}
