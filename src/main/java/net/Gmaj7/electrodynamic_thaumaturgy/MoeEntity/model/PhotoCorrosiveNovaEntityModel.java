package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.model;// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.entity.animation.json.AnimationHolder;

public class PhotoCorrosiveNovaEntityModel extends EntityModel<EntityRenderState> {
	public static final AnimationHolder PHOTO_CORROSIVE_NOVA_ANIME = getAnimation(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "photo_corrosive_nova"));
	private final KeyframeAnimation keyframeAnimation;
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "photocorrosivenovaentity"), "main");
	private final ModelPart bone;

	public PhotoCorrosiveNovaEntityModel(ModelPart root) {
		super(root);
		this.bone = root.getChild("bone");
		this.keyframeAnimation = PHOTO_CORROSIVE_NOVA_ANIME.get().bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition top_end = bone.addOrReplaceChild("top_end", CubeListBuilder.create().texOffs(0, 5).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

		PartDefinition middle = bone.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(EntityRenderState state) {
		super.setupAnim(state);
		this.keyframeAnimation.applyStatic();
	}
}