package one.devos.nautical.blocky_bass.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import one.devos.nautical.blocky_bass.BlockyBass;

import org.jetbrains.annotations.Nullable;

public class BlockyBassBlock extends JukeboxBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public static final VoxelShape NORTH_SHAPE = shape(0, 12, 16, 16);
	public static final VoxelShape SOUTH_SHAPE = shape(0, 0, 16, 4);
	public static final VoxelShape EAST_SHAPE = shape(0, 0, 4, 16);
	public static final VoxelShape WEST_SHAPE = shape(12, 0, 16, 16);

	public BlockyBassBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any()
						.setValue(FACING, Direction.NORTH)
						.setValue(POWERED, false)
						.setValue(HAS_RECORD, false)
		);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(FACING, POWERED));
	}

	@Override
	@SuppressWarnings("deprecation")
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			case NORTH -> NORTH_SHAPE;
			case SOUTH -> SOUTH_SHAPE;
			case EAST -> EAST_SHAPE;
			case WEST -> WEST_SHAPE;
			default -> Shapes.empty();
		};
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!oldState.is(state.getBlock())) {
			this.checkPoweredState(world, pos, state);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		this.checkPoweredState(world, pos, state);
	}

	private void checkPoweredState(Level world, BlockPos pos, BlockState state) {
		boolean powered = world.hasNeighborSignal(pos);
		if (powered != state.getValue(POWERED)) {
			world.setBlockAndUpdate(pos, state.setValue(POWERED, powered));
		}
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

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return BaseEntityBlock.createTickerHelper(type, BlockyBass.BLOCK_ENTITY, BlockyBassBlockEntity::tick);
	}

	private static VoxelShape shape(double minX, double minZ, double maxX, double maxZ) {
		return Block.box(minX, 1, minZ, maxX, 15, maxZ);
	}
}
