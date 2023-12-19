package one.devos.nautical.blocky_bass.block;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class BassPartRotation {
	public static final float MAX = 2 * Mth.PI / 6;
	public static final float TOGGLE_CHANCE = 1 / 20f;
	public static final float SPEED = 0.25f;

	protected boolean forwards = true;
	protected float current;
	protected float target;

	public void tick(RandomSource random) {
		this.current = this.next();
		if (this.toggleDirection(random)) {
			this.forwards = !this.forwards;
			this.target = forwards ? MAX : 0;
		}
	}

	protected boolean toggleDirection(RandomSource random) {
		return random.nextFloat() < TOGGLE_CHANCE;
	}

	public void tickInactive() {
		this.forwards = false;
		this.target = 0;
		this.current = this.next();
	}

	private float next() {
		if (this.forwards) {
			return Math.min(this.current + SPEED, target);
		} else {
			return Math.max(this.current - SPEED, target);
		}
	}

	public float value(float partialTicks) {
		if (this.current == this.target)
			return this.current;
		return Mth.lerp(partialTicks, this.current, this.next());
	}

	public static class RandomlyFlap extends BassPartRotation {
		private boolean active = false;

		@Override
		public void tick(RandomSource random) {
			if (random.nextFloat() < TOGGLE_CHANCE) {
				this.active = !this.active;
			}
			if (this.active) {
				super.tick(random);
			} else {
				this.tickInactive();
			}
		}

		@Override
		protected boolean toggleDirection(RandomSource random) {
			return this.current == this.target;
		}
	}
}
