package one.devos.nautical.blocky_bass.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;

import net.minecraft.world.level.block.state.BlockState;

import one.devos.nautical.blocky_bass.BlockyBass;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(JukeboxBlockEntity.class)
public class JukeboxBlockEntityMixin {
	@ModifyExpressionValue(
			method = "<init>",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/level/block/entity/BlockEntityType;JUKEBOX:Lnet/minecraft/world/level/block/entity/BlockEntityType;"
			)
	)
	private static BlockEntityType<?> useRightTypeForBass(BlockEntityType<?> original, BlockPos pos, BlockState state) {
		return state.is(BlockyBass.BLOCK) ? BlockyBass.BLOCK_ENTITY : original;
	}
}
