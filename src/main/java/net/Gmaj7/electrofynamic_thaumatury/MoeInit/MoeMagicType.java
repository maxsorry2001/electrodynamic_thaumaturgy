package net.Gmaj7.electrofynamic_thaumatury.MoeInit;

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
    MAGNETIC_RECOMBINATION_CANNON,
    ELECTROMAGNETIC_ASSAULT,
    ST_ELMO_S_FIRE,
    MAGMA_LIGHTING,
    HYDROGEN_BOND_FRACTURE,
    MAGNET_RESONANCE,
    DISTURBING_BY_HIGH_INTENSITY_MAGNETIC,
    COULOMB_DOMAIN,
    DOMAIN_RECONSTRUCTION,
    MIRAGE_PURSUIT,
    ERROR,
    EMPTY;
    private MoeMagicType(){

    }

    public static String getTranslate(MoeMagicType type){
        String translate;
        switch (type){
            case RAY -> translate = "item.electrofynamic_thaumatury.ray_module";
            case ATTRACT -> translate = "item.electrofynamic_thaumatury.attract_module";
            case LIGHTING_STRIKE -> translate = "item.electrofynamic_thaumatury.lighting_strike_module";
            case PULSED_PLASMA -> translate = "item.electrofynamic_thaumatury.pulsed_plasma_module";
            case ELECTRIC_FIELD_DOMAIN -> translate = "item.electrofynamic_thaumatury.electric_field_domain_module";
            case EXCITING -> translate = "item.electrofynamic_thaumatury.exciting_module";
            case PROTECT -> translate = "item.electrofynamic_thaumatury.protecting_module";
            case TREE_CURRENT -> translate = "item.electrofynamic_thaumatury.tree_current_module";
            case NERVE_BLOCKING -> translate = "item.electrofynamic_thaumatury.block_nerve_module";
            case ELECTRIC_ENERGY_RELEASE -> translate = "item.electrofynamic_thaumatury.electric_energy_release_module";
            case REFRACTION -> translate = "item.electrofynamic_thaumatury.refraction_module";
            case MAGNETIC_RECOMBINATION_CANNON -> translate = "item.electrofynamic_thaumatury.magnetic_recombination_cannon_module";
            case ELECTROMAGNETIC_ASSAULT -> translate = "item.electrofynamic_thaumatury.electromagnetic_assault_module";
            case ST_ELMO_S_FIRE -> translate = "item.electrofynamic_thaumatury.st_elmo_s_fire_module";
            case MAGMA_LIGHTING -> translate = "item.electrofynamic_thaumatury.magma_lighting_module";
            case HYDROGEN_BOND_FRACTURE -> translate = "item.electrofynamic_thaumatury.hydrogen_bond_fracture_module";
            case DISTURBING_BY_HIGH_INTENSITY_MAGNETIC -> translate = "item.electrofynamic_thaumatury.disturbing_by_high_intensity_magnetic_module";
            case COULOMB_DOMAIN -> translate = "item.electrofynamic_thaumatury.coulomb_domain_module";
            case MAGNET_RESONANCE -> translate = "item.electrofynamic_thaumatury.magnet_resonance_module";
            case DOMAIN_RECONSTRUCTION -> translate = "item.electrofynamic_thaumatury.domain_reconstruction_module";
            case MIRAGE_PURSUIT -> translate = "item.electrofynamic_thaumatury.mirage_pursuit_module";
            default -> translate = "moe_no_magic";
        }
        return translate;
    }

    public static String getDescription(MoeMagicType type){
        return getTranslate(type) + ".description";
    }

    public static boolean isEmpty(MoeMagicType type){
        if(type == EMPTY || type == ERROR) return true;
        else return false;
    }
}
