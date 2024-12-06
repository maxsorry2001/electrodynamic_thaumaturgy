package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import net.minecraft.network.chat.Component;

public enum MoeMagicType {
    RAY,
    PULSED_PLASMA,
    BALL_PLASMA,
    EXCITING,
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
            case EXCITING -> translate = "moe_exciting";
            case PROTECT -> translate = "moe_protecting";
            case SHOCK -> translate = "moe_shock";
            default -> translate = "moe_no_magic";
        }
        return Component.translatable(translate);
    }

    public static boolean isEmpty(MoeMagicType type){
        if(type == EMPTY || type == ERROR) return true;
        else return false;
    }
}
