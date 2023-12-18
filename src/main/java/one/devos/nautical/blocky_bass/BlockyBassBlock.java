package one.devos.nautical.blocky_bass;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import org.jetbrains.annotations.Nullable;

public class BlockyBassBlock extends JukeboxBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	protected BlockyBassBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(FACING));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		Direction face = ctx.getClickedFace();
		if (face.getAxis().isVertical()) {
			face = ctx.getHorizontalDirection().getOpposite();
		}
		return this.defaultBlockState().setValue(FACING, face);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BlockyBassBlockEntity(pos, state);
	}

	@Override
	public MapCodec<JukeboxBlock> codec() {
		throw new UnsupportedOperationException();
	}
}
