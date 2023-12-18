package one.devos.nautical.blocky_bass.block;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;

import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class BlockyBassBlockEntityRenderer implements BlockEntityRenderer<BlockyBassBlockEntity> {
	private final BlockyBassModel model;

	public BlockyBassBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
		this.model = new BlockyBassModel(ctx.bakeLayer(BlockyBassModel.LAYER_LOCATION));
	}

	@Override
	public void render(BlockyBassBlockEntity bass, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		RenderType renderType = this.model.renderType(BlockyBassModel.TEXTURE);
		VertexConsumer vertices = vertexConsumers.getBuffer(renderType);
		matrices.pushPose();
		matrices.translate(0.5, 1.5, 0.5);
		float rotation = this.getRotationDeg(bass);
		matrices.mulPose(Axis.YP.rotationDegrees(rotation));
		matrices.mulPose(Axis.XP.rotationDegrees(180));
		this.model.renderToBuffer(matrices, vertices, light, overlay, 1, 1, 1, 1);
		matrices.popPose();
	}

	private float getRotationDeg(BlockyBassBlockEntity bass) {
		Direction facing = bass.getBlockState().getValue(BlockyBassBlock.FACING);
		return switch (facing) {
			case NORTH -> 180;
			case SOUTH -> 0;
			case EAST -> 90;
			case WEST -> 270;
			default -> throw new IllegalArgumentException("Invalid facing direction");
		};
	}
}
