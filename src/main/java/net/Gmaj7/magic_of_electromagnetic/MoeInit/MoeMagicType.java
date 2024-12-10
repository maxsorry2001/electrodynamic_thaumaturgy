package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import net.minecraft.network.chat.Component;

public enum MoeMagicType {
    RAY,
    ATTRACT,
    PULSED_PLASMA,
    ELECTRIC_FIELD_DOMAIN,
    ELECTRIC_ENERGY_RELEASE,
    EXCITING,
    PROTECT,
    CHAIN,
    ERROR,
    EMPTY;
    private MoeMagicType(){

    }

    public static Component getTranslate(MoeMagicType type){
        String translate;
        switch (type){
            case RAY -> translate = "moe_ray";
            case ATTRACT -> translate = "moe_attract";
            case PULSED_PLASMA -> translate = "moe_pulsed_plasma";
            case ELECTRIC_FIELD_DOMAIN -> translate = "electric_field_domain";
            case EXCITING -> translate = "moe_exciting";
            case PROTECT -> translate = "moe_protecting";
            case CHAIN -> translate = "moe_chain";
            case ELECTRIC_ENERGY_RELEASE -> translate = "moe_electric_energy_release";
            default -> translate = "moe_no_magic";
        }
        return Component.translatable(translate);
    }

    public static boolean isEmpty(MoeMagicType type){
        if(type == EMPTY || type == ERROR) return true;
        else return false;
    }
}
