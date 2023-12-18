package one.devos.nautical.blocky_bass.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import one.devos.nautical.blocky_bass.BlockyBass;
import one.devos.nautical.blocky_bass.mixin.BlockEntityAccessor;

public class BlockyBassBlockEntity extends JukeboxBlockEntity {
	public BlockyBassBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
		((BlockEntityAccessor) this).setType(BlockyBass.BLOCK_ENTITY);
	}
}
