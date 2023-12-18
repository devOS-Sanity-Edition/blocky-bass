package one.devos.nautical.blocky_bass.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import one.devos.nautical.blocky_bass.BlockyBass;
import one.devos.nautical.blocky_bass.mixin.BlockEntityAccessor;

import org.jetbrains.annotations.Nullable;

public class BlockyBassBlockEntity extends JukeboxBlockEntity {
	public final BassPartRotation head;
	public final BassPartRotation mouth;
	public final BassPartRotation tail;

	public BlockyBassBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
		((BlockEntityAccessor) this).setType(BlockyBass.BLOCK_ENTITY);
		this.head = new BassPartRotation();
		this.mouth = new BassPartRotation();
		this.tail = new BassPartRotation();
	}

	public boolean isActive() {
		return this.isRecordPlaying() || this.getBlockState().getValue(BlockyBassBlock.POWERED);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithoutMetadata();
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, BlockyBassBlockEntity bass) {
		if (state.getValue(JukeboxBlock.HAS_RECORD)) {
			JukeboxBlockEntity.playRecordTick(level, pos, state, bass);
		}

		if (!level.isClientSide)
			return;

		// update state
		if (bass.isActive()) {
			bass.head.tick(level.random);
			bass.mouth.tick(level.random);
			bass.tail.tick(level.random);
		} else {
			bass.head.tickInactive();
			bass.mouth.tickInactive();
			bass.tail.tickInactive();
		}
	}

	public static class BassPartRotation {
		private static final float max = 2 * Mth.PI / 6;
		private static final float switchDirectionChance = 1 / 20f;
		private static final float speed = 0.25f;

		private boolean forwards = true;
		private float current;
		private float target;

		public void tick(RandomSource random) {
			this.current = this.next();
			if (random.nextFloat() < switchDirectionChance) {
				this.forwards = !this.forwards;
				this.target = forwards ? max : 0;
			}
		}

		public void tickInactive() {
			this.forwards = false;
			this.target = 0;
			this.current = this.next();
		}

		private float next() {
			if (forwards) {
				return Math.min(this.current + speed, target);
			} else {
				return Math.max(this.current - speed, target);
			}
		}

		public float value(float partialTicks) {
			if (this.current == this.target)
				return this.current;
			return Mth.lerp(partialTicks, this.current, this.next());
		}
	}
}
