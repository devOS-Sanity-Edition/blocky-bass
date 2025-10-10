package one.devos.nautical.blocky_bass.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class BlockyBassBlockEntity extends JukeboxBlockEntity {
	public final BassParts parts;

	public BlockyBassBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state); // type is fixed in JukeboxBlockEntityMixin
		this.parts = new BassParts();
	}

	public boolean isActive() {
		return this.getSongPlayer().isPlaying() || this.getBlockState().getValue(BlockyBassBlock.POWERED);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return this.saveWithoutMetadata(registries);
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, BlockyBassBlockEntity bass) {
		if (state.getValue(JukeboxBlock.HAS_RECORD)) {
			JukeboxBlockEntity.tick(level, pos, state, bass);
		}

		if (!level.isClientSide())
			return;

		// update state
		if (bass.isActive()) {
			bass.parts.tick(level.random);
		} else {
			bass.parts.tickInactive();
		}
	}
}
