package net.Gmaj7.electrodynamic_thaumaturgy.Init;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class KeyMapping {
    public static final String KEY_CATEGORY_MOE = "key.category.electrodynamic_thaumaturgy.et";
    public static final String KEY_SWITCH_MAGIC = "key.electrodynamic_thaumaturgy.switch_magic";

    public static final net.minecraft.client.KeyMapping SELECT_MAGIC = new net.minecraft.client.KeyMapping(KEY_SWITCH_MAGIC,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, net.minecraft.client.KeyMapping.Category.MISC);
}
