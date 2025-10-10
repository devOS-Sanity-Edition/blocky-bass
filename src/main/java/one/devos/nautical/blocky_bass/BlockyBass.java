package one.devos.nautical.blocky_bass;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import one.devos.nautical.blocky_bass.block.BlockyBassBlock;
import one.devos.nautical.blocky_bass.block.BlockyBassBlockEntity;

public class BlockyBass implements ModInitializer {
	public static final String ID = "blocky_bass";

	private static final ResourceLocation id = id("blocky_bass");

	public static final Block BLOCK = Registry.register(
			BuiltInRegistries.BLOCK, id, new BlockyBassBlock(
					BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
							.setId(ResourceKey.create(Registries.BLOCK, id))
							.noOcclusion()
			)
	);

	public static final BlockEntityType<BlockyBassBlockEntity> BLOCK_ENTITY = Registry.register(
			BuiltInRegistries.BLOCK_ENTITY_TYPE, id,
			FabricBlockEntityTypeBuilder.create(
					BlockyBassBlockEntity::new, BLOCK
			).build()
	);

	public static final Item ITEM = Registry.register(
			BuiltInRegistries.ITEM, id,
			new BlockItem(BLOCK, new Item.Properties()
					.setId(ResourceKey.create(Registries.ITEM, id))
					.useBlockDescriptionPrefix()
			)
	);

	@Override
	public void onInitialize() {
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
				.register(entries -> entries.addAfter(Blocks.JUKEBOX, ITEM));

		LootTableEvents.MODIFY.register(BlockyBass::modifyLoot);
	}

	private static void modifyLoot(ResourceKey<LootTable> key, LootTable.Builder builder, LootTableSource source, HolderLookup.Provider registries) {
		if (key == BuiltInLootTables.FISHING_TREASURE) {
			// don't add a new pool, only fish up 1 item
			builder.modifyPools(pool -> pool.add(LootItem.lootTableItem(ITEM)));
		}
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}
}
