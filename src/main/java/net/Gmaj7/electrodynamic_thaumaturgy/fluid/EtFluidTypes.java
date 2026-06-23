package net.Gmaj7.electrodynamic_thaumaturgy.fluid;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector4f;

import java.util.function.Supplier;

public class EtFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPE = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, ElectrodynamicThaumaturgy.MODID);

    public static final Supplier<FluidType> MAGNETIC_FLUX_FLUID_TYPE = FLUID_TYPE.register("magnetic_flux_fluid_type",
            () -> new FluidType(FluidType.Properties.create().isWaterLike(false)));

    public static IClientFluidTypeExtensions MAGNETIC_FLUX_EXTENSION = new IClientFluidTypeExtensions() {
        @Override
        public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
            fluidFogColor.set(0.83F, 0.16F, 0.16F);
            IClientFluidTypeExtensions.super.modifyFogColor(camera, partialTick, level, renderDistance, darkenWorldAmount, fluidFogColor);
        }
    };

    public static void register(IEventBus eventBus){
        FLUID_TYPE.register(eventBus);
    }
}
