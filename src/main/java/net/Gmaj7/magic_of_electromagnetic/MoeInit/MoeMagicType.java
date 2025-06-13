package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import net.minecraft.network.chat.Component;

public enum MoeMagicType {
    RAY,
    ATTRACT,
    LIGHTING_STRIKE,
    PULSED_PLASMA,
    ELECTRIC_FIELD_DOMAIN,
    ELECTRIC_ENERGY_RELEASE,
    NERVE_BLOCKING,
    EXCITING,
    PROTECT,
    TREE_CURRENT,
    REFRACTION,
    PLASMA_TORCH,
    ELECTROMAGNETIC_ASSAULT,
    ST_ELMO_S_FIRE,
    MAGMA_LIGHTING,
    HYDROGEN_BOND_FRACTURE,
    MAGNET_RESONANCE,
    ERROR,
    EMPTY;
    private MoeMagicType(){

    }

    public static Component getTranslate(MoeMagicType type){
        String translate;
        switch (type){
            case RAY -> translate = "moe_ray";
            case ATTRACT -> translate = "moe_attract";
            case LIGHTING_STRIKE -> translate = "moe_lighting_strike";
            case PULSED_PLASMA -> translate = "moe_pulsed_plasma";
            case ELECTRIC_FIELD_DOMAIN -> translate = "moe_electric_field_domain";
            case EXCITING -> translate = "moe_exciting";
            case PROTECT -> translate = "moe_protecting";
            case TREE_CURRENT -> translate = "moe_tree_current";
            case NERVE_BLOCKING -> translate = "moe_nerve_blocking";
            case ELECTRIC_ENERGY_RELEASE -> translate = "moe_electric_energy_release";
            case REFRACTION -> translate = "moe_refraction";
            case PLASMA_TORCH -> translate = "moe_plasma_torch";
            case ELECTROMAGNETIC_ASSAULT -> translate = "moe_electromagnetic_assault";
            case ST_ELMO_S_FIRE -> translate = "moe_st_elmo_s_fire";
            case MAGMA_LIGHTING -> translate = "moe_magma_lighting";
            case MAGNET_RESONANCE -> translate = "item.magic_of_electromagnetic.magnet_resonance_module";
            case HYDROGEN_BOND_FRACTURE -> translate = "moe_hydrogen_bond_fracture";

            default -> translate = "moe_no_magic";
        }
        return Component.translatable(translate);
    }

    public static boolean isEmpty(MoeMagicType type){
        if(type == EMPTY || type == ERROR) return true;
        else return false;
    }
}
