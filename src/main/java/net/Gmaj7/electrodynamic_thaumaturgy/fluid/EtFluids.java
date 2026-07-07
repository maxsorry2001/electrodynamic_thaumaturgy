package net.Gmaj7.electrodynamic_thaumaturgy.fluid;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EtFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, ElectrodynamicThaumaturgy.MODID);

    public static final Supplier<FlowingFluid> MAGNETIC_FLUX_SOURCE = FLUIDS.register("magnetic_flux_source",
            () -> new BaseFlowingFluid.Source(FluidProperties.MAGNETIC_FLUX_PROPERTIES));
    public static final Supplier<FlowingFluid> MAGNETIC_FLUX_FLOWING = FLUIDS.register("magnetic_flux_flowing",
            () -> new BaseFlowingFluid.Flowing(FluidProperties.MAGNETIC_FLUX_PROPERTIES));

    public static final Supplier<FlowingFluid> THERMAL_MAGNO_FLUX_SOURCE = FLUIDS.register("thermal_magno_flux_source",
            () -> new BaseFlowingFluid.Source(FluidProperties.THERMAL_MAGNO_FLUX_PROPERTIES));
    public static final Supplier<FlowingFluid> THERMAL_MAGNO_FLUX_FLOWING = FLUIDS.register("thermal_magno_flux_flowing",
            () -> new BaseFlowingFluid.Flowing(FluidProperties.THERMAL_MAGNO_FLUX_PROPERTIES));

    public static class FluidProperties{
        protected static final BaseFlowingFluid.Properties MAGNETIC_FLUX_PROPERTIES = new BaseFlowingFluid.Properties(
                EtFluidTypes.MAGNETIC_FLUX_FLUID_TYPE, MAGNETIC_FLUX_SOURCE, MAGNETIC_FLUX_FLOWING).block(EtBlocks.MAGNETIC_FLUX_FLUID_BLOCK).bucket(EtItems.MAGNETIC_FLUX_BUCKET);
        protected static final BaseFlowingFluid.Properties THERMAL_MAGNO_FLUX_PROPERTIES = new BaseFlowingFluid.Properties(
                EtFluidTypes.THERMAL_MAGNO_FLUX_FLUID_TYPE, THERMAL_MAGNO_FLUX_SOURCE, THERMAL_MAGNO_FLUX_FLOWING).block(EtBlocks.THERMAL_MAGNO_FLUX_FLUID_BLOCK).bucket(EtItems.THERMAL_MAGNO_FLUX_BUCKET);
    }

    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);
    }
}
