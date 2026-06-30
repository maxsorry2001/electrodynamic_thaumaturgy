package net.Gmaj7.electrodynamic_thaumaturgy.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class EtKeyMapping {
    public static final String KEY_CATEGORY_MOE = "key.category.electrodynamic_thaumaturgy.et";
    public static final String KEY_TOOL_SWITCH = "key.electrodynamic_thaumaturgy.tool_switch";

    public static final KeyMapping TOOL_SWITCH = new KeyMapping(KEY_TOOL_SWITCH,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KeyMapping.Category.MISC);
}
