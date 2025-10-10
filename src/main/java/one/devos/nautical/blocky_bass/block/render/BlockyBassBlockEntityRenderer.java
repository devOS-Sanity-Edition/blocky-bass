package one.devos.nautical.blocky_bass.block.render;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.math.Axis;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import one.devos.nautical.blocky_bass.block.BlockyBassBlock;
import one.devos.nautical.blocky_bass.block.BlockyBassBlockEntity;

import org.jetbrains.annotations.Nullable;

public class BlockyBassBlockEntityRenderer implements BlockEntityRenderer<BlockyBassBlockEntity, BassRenderState> {
	private final BlockyBassModel model;

	public BlockyBassBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
		this.model = new BlockyBassModel(ctx.bakeLayer(BlockyBassModel.LAYER_LOCATION));
	}

	@Override
	public BassRenderState createRenderState() {
		return new BassRenderState();
	}

	@Override
	public void extractRenderState(BlockyBassBlockEntity bass, BassRenderState state, float partialTicks, Vec3 cameraPos, @Nullable CrumblingOverlay crumbling) {
		BlockEntityRenderer.super.extractRenderState(bass, state, partialTicks, cameraPos, crumbling);
		state.rotations.head = bass.parts.head().value(partialTicks);
		state.rotations.mouth = bass.parts.mouth().value(partialTicks);
		state.rotations.tail = bass.parts.tail().value(partialTicks);
	}

	@Override
	public void submit(BassRenderState state, PoseStack matrices, SubmitNodeCollector collector, CameraRenderState camera) {
		matrices.pushPose();
		matrices.translate(0.5, 1.5, 0.5);
		float rotation = this.getRotationDeg(state);
		matrices.mulPose(Axis.YP.rotationDegrees(rotation));
		matrices.mulPose(Axis.XP.rotationDegrees(180));

		RenderType renderType = this.model.renderType(BlockyBassModel.TEXTURE);
		collector.submitModel(this.model, state.rotations, matrices, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, 0, null);

		matrices.popPose();
	}

	private float getRotationDeg(BassRenderState state) {
		Direction facing = state.blockState.getValue(BlockyBassBlock.FACING);
		return switch (facing) {
			case NORTH -> 180;
			case SOUTH -> 0;
			case EAST -> 90;
			case WEST -> 270;
			default -> throw new IllegalArgumentException("Invalid facing direction");
		};
	}
}
