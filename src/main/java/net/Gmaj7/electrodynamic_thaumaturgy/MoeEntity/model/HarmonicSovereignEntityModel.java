package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.AbstractSovereignEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.renderState.SovereignRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.neoforge.client.entity.animation.json.AnimationHolder;

public class HarmonicSovereignEntityModel<T extends AbstractSovereignEntity> extends HumanoidModel<SovereignRenderState> {
    public static final AnimationHolder WALK_SOVEREIGN = Model.getAnimation(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "walk_sovereign"));
    public static final AnimationHolder CAST_1 = Model.getAnimation(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "cast_1"));

    private final KeyframeAnimation walk;
    private final KeyframeAnimation cast_1;
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "harmonic_sovereign"), "main");
    private final ModelPart bone;
    private final ModelPart leg;
    private final ModelPart left;
    private final ModelPart right;
    private final ModelPart arm;
    private final ModelPart left2;
    private final ModelPart upper;
    private final ModelPart lower;
    private final ModelPart right2;
    private final ModelPart upper2;
    private final ModelPart lower2;
    private final ModelPart body;
    private final ModelPart head;

    public HarmonicSovereignEntityModel(ModelPart root) {
        super(root);
        this.bone = root.getChild("bone");
        this.leg = this.bone.getChild("leg");
        this.left = this.leg.getChild("left");
        this.right = this.leg.getChild("right");
        this.arm = this.bone.getChild("arm");
        this.left2 = this.arm.getChild("left2");
        this.upper = this.left2.getChild("upper");
        this.lower = this.left2.getChild("lower");
        this.right2 = this.arm.getChild("right2");
        this.upper2 = this.right2.getChild("upper2");
        this.lower2 = this.right2.getChild("lower2");
        this.body = this.bone.getChild("body");
        this.head = this.bone.getChild("head");
        this.walk = WALK_SOVEREIGN.get().bake(root);
        this.cast_1 = CAST_1.get().bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leg = bone.addOrReplaceChild("leg", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition left = leg.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 48).addBox(0.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right = leg.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition arm = bone.addOrReplaceChild("arm", CubeListBuilder.create(), PartPose.offset(0.0F, -23.0F, 0.0F));

        PartDefinition left2 = arm.addOrReplaceChild("left2", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, 0.0F));

        PartDefinition upper = left2.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(16, 46).addBox(0.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(0.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lower = left2.addOrReplaceChild("lower", CubeListBuilder.create().texOffs(16, 36).addBox(0.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 46).addBox(0.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 5.0F, 0.0F));

        PartDefinition right2 = arm.addOrReplaceChild("right2", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, 0.0F));

        PartDefinition upper2 = right2.addOrReplaceChild("upper2", CubeListBuilder.create().texOffs(16, 46).addBox(-4.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 36).addBox(-4.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lower2 = right2.addOrReplaceChild("lower2", CubeListBuilder.create().texOffs(16, 36).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 46).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 5.0F, 0.0F));

        PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 16).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -18.0F, 0.0F));

        PartDefinition head = bone.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(SovereignRenderState state) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = state.xRot * ((float)Math.PI / 180F);
        this.head.yRot = state.yRot * ((float)Math.PI / 180F);

        this.walk.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 1, 1);
        this.cast_1.apply(state.castAnimationState, state.ageInTicks);
    }



    //@Override
    //public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
    //    bone.render(poseStack, buffer, packedLight, packedOverlay, color);
    //}
//
    //@Override
    //public ModelPart root() {
    //    return this.bone;
    //}
//
    //@Override
    //public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
    //    this.getArm(humanoidArm).translateAndRotate(poseStack);
    //}

    @Override
    public ModelPart getArm(HumanoidArm side) {
        return side == HumanoidArm.LEFT ? this.left2 : this.right2;
    }

    @Override
    public ModelPart getHead() {
        return this.head;
    }
}
