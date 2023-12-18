package one.devos.nautical.blocky_bass;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import one.devos.nautical.blocky_bass.block.BlockyBassBlockEntityRenderer;
import one.devos.nautical.blocky_bass.block.BlockyBassModel;

public class BlockyBassClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRenderers.register(BlockyBass.BLOCK_ENTITY, BlockyBassBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(BlockyBassModel.LAYER_LOCATION, BlockyBassModel::createBodyLayer);
	}
}
