package one.devos.nautical.blocky_bass;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import one.devos.nautical.blocky_bass.block.BlockyBassBlock;
import one.devos.nautical.blocky_bass.block.BlockyBassBlockEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockyBass implements ModInitializer {
	public static final String ID = "blocky_bass";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static final Block BLOCK = new BlockyBassBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque());

	public static final BlockEntityType<BlockyBassBlockEntity> BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(BlockyBassBlockEntity::new)
					.addBlock(BLOCK)
					.build();

	public static final Item ITEM = new BlockItem(BLOCK, new FabricItemSettings());

	@Override
	public void onInitialize() {
		ResourceLocation id = id("blocky_bass");
		Registry.register(BuiltInRegistries.BLOCK, id, BLOCK);
		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, BLOCK_ENTITY);
		Registry.register(BuiltInRegistries.ITEM, id, ITEM);

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
				.register(entries -> entries.addAfter(Blocks.JUKEBOX, ITEM));

		LootTableEvents.MODIFY.register(BlockyBass::modifyLoot);
	}

	private static void modifyLoot(ResourceManager manager, LootDataManager loot, ResourceLocation id, LootTable.Builder builder, LootTableSource source) {
		if (id.equals(BuiltInLootTables.FISHING_TREASURE)) {
			// don't add a new pool, only fish up 1 item
			builder.modifyPools(pool -> pool.add(LootItem.lootTableItem(ITEM)));
		}
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}
