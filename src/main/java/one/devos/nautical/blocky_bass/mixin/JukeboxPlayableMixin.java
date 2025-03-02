package one.devos.nautical.blocky_bass.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.world.item.JukeboxPlayable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import one.devos.nautical.blocky_bass.BlockyBass;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(JukeboxPlayable.class)
public class JukeboxPlayableMixin {
	@WrapOperation(
			method = "tryInsertIntoJukebox",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"
			)
	)
	private static boolean allowUseOnBass(BlockState state, Block jukebox, Operation<Boolean> original) {
		return original.call(state, jukebox) || state.is(BlockyBass.BLOCK);
	}
}
