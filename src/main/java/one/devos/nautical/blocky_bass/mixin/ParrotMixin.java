package one.devos.nautical.blocky_bass.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import one.devos.nautical.blocky_bass.BlockyBass;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(Parrot.class)
public class ParrotMixin {
	@WrapOperation(
			method = "aiStep",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"
			)
	)
	private boolean bassIsJukeboxToo(BlockState state, Block jukebox, Operation<Boolean> original) {
		return original.call(state, jukebox) || state.is(BlockyBass.BLOCK);
	}
}
