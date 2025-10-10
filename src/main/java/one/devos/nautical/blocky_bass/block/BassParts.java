package one.devos.nautical.blocky_bass.block;

import net.minecraft.util.RandomSource;

public record BassParts(BassPartRotation head, BassPartRotation mouth, BassPartRotation tail) {
	public BassParts() {
		this(new BassPartRotation(), new BassPartRotation(), new BassPartRotation.RandomlyFlap());
	}

	public void tick(RandomSource random) {
		this.head.tick(random);
		this.mouth.tick(random);
		this.tail.tick(random);
	}

	public void tickInactive() {
		this.head.tickInactive();
		this.mouth.tickInactive();
		this.tail.tickInactive();
	}
}
