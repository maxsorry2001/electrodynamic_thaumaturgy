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
    REFRACTION,
    PLASMA_TORCH,
    ELECTROMAGNETIC_ASSAULT,
    ENTROPY_MAGNET_UPHEAVAL,
    ST_ELMO_S_FIRE,
    MAGMA_LIGHTING,
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
            case REFRACTION -> translate = "moe_refraction";
            case PLASMA_TORCH -> translate = "moe_plasma_torch";
            case ELECTROMAGNETIC_ASSAULT -> translate = "moe_electromagnetic_assault";
            case ENTROPY_MAGNET_UPHEAVAL -> translate = "moe_entropy_magnet_upheaval";
            case ST_ELMO_S_FIRE -> translate = "moe_st_elmo_s_fire";
            case MAGMA_LIGHTING -> translate = "moe_magma_lighting";
            default -> translate = "moe_no_magic";
        }
        return Component.translatable(translate);
    }

    public static boolean isEmpty(MoeMagicType type){
        if(type == EMPTY || type == ERROR) return true;
        else return false;
    }
}
