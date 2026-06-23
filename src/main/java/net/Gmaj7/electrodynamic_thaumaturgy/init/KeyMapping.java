package net.Gmaj7.electrodynamic_thaumaturgy.init;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class KeyMapping {
    public static final String KEY_CATEGORY_MOE = "key.category.electrodynamic_thaumaturgy.et";
    public static final String KEY_TOOL_SWITCH = "key.electrodynamic_thaumaturgy.tool_switch";

    public static final net.minecraft.client.KeyMapping TOOL_SWITCH = new net.minecraft.client.KeyMapping(KEY_TOOL_SWITCH,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, net.minecraft.client.KeyMapping.Category.MISC);
}
