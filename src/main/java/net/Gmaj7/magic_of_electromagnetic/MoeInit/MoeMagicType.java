package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import net.minecraft.network.chat.Component;

public enum MoeMagicType {
    RAY,
    PULSED_PLASMA,
    BALL_PLASMA,
    GLOWING,
    PROTECT,
    SHOCK,
    ERROR,
    EMPTY;
    private MoeMagicType(){

    }

    public static Component getTranslate(MoeMagicType type){
        String translate;
        switch (type){
            case RAY -> translate = "moe_ray";
            case PULSED_PLASMA -> translate = "moe_pulsed_plasma";
            case BALL_PLASMA -> translate = "moe_ball_plasma";
            case GLOWING -> translate = "moe_glowing";
            case PROTECT -> translate = "moe_protect";
            case SHOCK -> translate = "moe_shock";
            default -> translate = "moe_no_magic";
        }
        return Component.translatable(translate);
    }
}
