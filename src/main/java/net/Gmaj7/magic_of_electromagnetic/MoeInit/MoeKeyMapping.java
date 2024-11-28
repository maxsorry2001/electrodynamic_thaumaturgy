package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class MoeKeyMapping {
    public static final String KEY_CATEGORY_MOE = "key.category.magic_of_electromagnetic.moe";
    public static final String KEY_SWITCH_MAGIC = "key.magic_of_electromagnetic.switch_magic";

    public static final KeyMapping SWITCH_MAGIC = new KeyMapping(KEY_SWITCH_MAGIC, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_MOE);
}
