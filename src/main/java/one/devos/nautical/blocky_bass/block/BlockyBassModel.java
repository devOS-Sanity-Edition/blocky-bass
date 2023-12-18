package one.devos.nautical.blocky_bass.block;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import one.devos.nautical.blocky_bass.BlockyBass;

public class BlockyBassModel extends Model {
	public static final ResourceLocation TEXTURE = BlockyBass.id("textures/entity/blocky_bass.png");
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "blocky_bass"), "main");
	private final ModelPart le_fishe;
	private final ModelPart head;
	private final ModelPart mouth;
	private final ModelPart tail;

	public BlockyBassModel(ModelPart root) {
		super(RenderType::entitySolid);
		this.le_fishe = root.getChild("le_fishe");
		this.head = le_fishe.getChild("head");
		this.mouth = head.getChild("lower_mouth");
		this.tail = le_fishe.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition le_fishe = partdefinition.addOrReplaceChild("le_fishe", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, -1.0F));

		PartDefinition tail = le_fishe.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(12, 11).addBox(0.0F, -2.0F, 0.0F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -0.25F, 7.0F));

		PartDefinition body = le_fishe.addOrReplaceChild("body", CubeListBuilder.create().texOffs(15, 15).addBox(-3.0198F, -2.2226F, -1.0167F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5198F, -0.0274F, 7.0167F));

		PartDefinition top_front_fin_r1 = body.addOrReplaceChild("top_front_fin_r1", CubeListBuilder.create().texOffs(6, 11).addBox(0.0F, 0.5F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3948F, -3.7226F, 0.0333F, 0.0F, 0.0F, 0.3927F));

		PartDefinition top_back_fin_r1 = body.addOrReplaceChild("top_back_fin_r1", CubeListBuilder.create().texOffs(6, 13).addBox(-0.5F, -0.5F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1198F, -2.7226F, 0.0333F, 0.0F, 0.0F, 0.3927F));

		PartDefinition bottom_fin_r1 = body.addOrReplaceChild("bottom_fin_r1", CubeListBuilder.create().texOffs(10, 11).addBox(-0.25F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2302F, 2.2774F, -0.0167F, 0.0F, 0.0F, -0.7854F));

		PartDefinition head = le_fishe.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 15).addBox(-4.0F, -2.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -0.25F, 7.0F));

		PartDefinition upper_mouth = head.addOrReplaceChild("upper_mouth", CubeListBuilder.create().texOffs(9, 21).addBox(-5.0F, -1.275F, -0.975F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -0.25F, 0.0F));

		PartDefinition lower_mouth = head.addOrReplaceChild("lower_mouth", CubeListBuilder.create().texOffs(0, 21).addBox(-2.5F, -0.525F, -0.975F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		le_fishe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}


	public void setRotations(float head, float mouth, float tail) {
		this.head.yRot = head;
		this.mouth.xRot = mouth;
		this.tail.yRot = tail;
	}
}
