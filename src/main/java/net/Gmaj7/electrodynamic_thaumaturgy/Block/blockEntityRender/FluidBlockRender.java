package net.Gmaj7.electrodynamic_thaumaturgy.Block.blockEntityRender;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.QuadInstance;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.FluidBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;

public class FluidBlockRender implements BlockEntityRenderer<FluidBlockEntity, FluidBlockRenderState> {
    private static final float SIDE_WIDTH_RATE = 1 / 16f + 0.001f;
    public FluidBlockRender(BlockEntityRendererProvider.Context context) {
    }
    @Override
    public void extractRenderState(FluidBlockEntity blockEntity, FluidBlockRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.resourceHandler = blockEntity.getFluidHandler();
    }

    @Override
    public FluidBlockRenderState createRenderState() {
        return new FluidBlockRenderState();
    }

    @Override
    public void submit(FluidBlockRenderState state, PoseStack poseStack,
                       SubmitNodeCollector collector, CameraRenderState cameraState) {

        ResourceHandler<FluidResource> handler = state.resourceHandler;
        FluidResource resource = handler.getResource(0);
        if (resource.isEmpty()) return;

        long amount = handler.getAmountAsLong(0);
        long capacity = handler.getCapacityAsLong(0, resource);
        if (capacity <= 0 || amount <= 0) return;

        float fillRate = (float) amount / capacity;
        if (fillRate <= 0) return;

        var fluidModel = Minecraft.getInstance().getModelManager()
                .getFluidStateModelSet()
                .get(resource.getFluid().defaultFluidState());
        var sprite = fluidModel.stillMaterial().sprite();
        if (sprite == null) return;

        int color;
        var tintSource = fluidModel.fluidTintSource();
        if (tintSource != null) {
            color = tintSource.colorAsStack(resource.toStack(1));
        } else {
            color = -1;
        }

        int light = state.lightCoords;
        boolean lighterThanAir = resource.getFluidType().isLighterThanAir();

        collector.submitCustomGeometry(poseStack, Sheets.translucentBlockItemSheet(), (pose, consumer) -> {
            // pose 是 PoseStack.Pose 类型
            float margin = 1 / 16f + 0.001f;
            float x1 = margin;
            float x2 = 1 - margin;
            float z1 = margin;
            float z2 = 1 - margin;

            float bottomY, topY;
            if (lighterThanAir) {
                topY = 1 - margin;
                bottomY = 1 - fillRate * (1 - 2 * margin) - margin;
            } else {
                bottomY = margin;
                topY = margin + fillRate * (1 - 2 * margin);
            }

            buildFluidCube(consumer, pose, x1, bottomY, z1, x2, topY, z2, sprite, color, light);
        });
    }

    private void buildFluidCube(VertexConsumer consumer, PoseStack.Pose pose,
                                float x1, float y1, float z1, float x2, float y2, float z2,
                                TextureAtlasSprite sprite, int color, int light) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        int a = (color >> 24) & 0xFF;
        if (color == -1) { r = g = b = a = 255; }

        float u0 = sprite.getU0();
        float u1 = sprite.getU1();
        float v0 = sprite.getV0();
        float v1 = sprite.getV1();

        // DOWN
        addFace(consumer, pose,
                x1, y1, z1, x2, y1, z1, x2, y1, z2, x1, y1, z2,
                u0, v1, u1, v1, u1, v0, u0, v0,
                0, -1, 0, r, g, b, a, light);
        // UP
        addFace(consumer, pose,
                x1, y2, z1, x1, y2, z2, x2, y2, z2, x2, y2, z1,
                u0, v1, u0, v0, u1, v0, u1, v1,
                0, 1, 0, r, g, b, a, light);
        // NORTH
        addFace(consumer, pose,
                x1, y1, z1, x1, y2, z1, x2, y2, z1, x2, y1, z1,
                u0, v1, u0, v0, u1, v0, u1, v1,
                0, 0, -1, r, g, b, a, light);
        // SOUTH
        addFace(consumer, pose,
                x2, y1, z2, x2, y2, z2, x1, y2, z2, x1, y1, z2,
                u0, v1, u0, v0, u1, v0, u1, v1,
                0, 0, 1, r, g, b, a, light);
        // WEST
        addFace(consumer, pose,
                x2, y1, z1, x2, y2, z1, x2, y2, z2, x2, y1, z2,
                u0, v1, u0, v0, u1, v0, u1, v1,
                -1, 0, 0, r, g, b, a, light);
        // EAST
        addFace(consumer, pose,
                x1, y1, z2, x1, y2, z2, x1, y2, z1, x1, y1, z1,
                u0, v1, u0, v0, u1, v0, u1, v1,
                1, 0, 0, r, g, b, a, light);
    }

    private void addFace(VertexConsumer consumer, PoseStack.Pose pose,
                         float x0, float y0, float z0,
                         float x1, float y1, float z1,
                         float x2, float y2, float z2,
                         float x3, float y3, float z3,
                         float u0, float v0, float u1, float v1,
                         float u2, float v2, float u3, float v3,
                         float nx, float ny, float nz,
                         int r, int g, int b, int a, int light) {
        // 顶点0
        consumer.addVertex(pose, x0, y0, z0)
                .setColor(r, g, b, a)
                .setUv(u0, v0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, nx, ny, nz);
        // 顶点1
        consumer.addVertex(pose, x1, y1, z1)
                .setColor(r, g, b, a)
                .setUv(u1, v1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, nx, ny, nz);
        // 顶点2
        consumer.addVertex(pose, x2, y2, z2)
                .setColor(r, g, b, a)
                .setUv(u2, v2)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, nx, ny, nz);
        // 顶点3
        consumer.addVertex(pose, x3, y3, z3)
                .setColor(r, g, b, a)
                .setUv(u3, v3)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, nx, ny, nz);
    }
}
