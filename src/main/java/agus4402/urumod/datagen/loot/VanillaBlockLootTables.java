package agus4402.urumod.datagen.loot;

import agus4402.urumod.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class VanillaBlockLootTables extends BlockLootSubProvider {

	public VanillaBlockLootTables() {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags());
	}

	@Override
	protected void generate() {
		// * NEW LOOT TABLES HERE * //
		// ? EXAMPLE:
		// ? this.dropSelf(ModBlocks.MYSTERY_BLOCK.get());
		this.dropSelf(ModBlocks.MYSTERY_BLOCK.get());
		this.dropSelf(ModBlocks.PAN.get());
		this.dropOther(ModBlocks.CAMPFIRE_WITH_PAN.get(), Blocks.CAMPFIRE);

		// ? EXAMPLE:
		// ? this.add(ModBlocks.MYSTERY_BLOCK.get(),
		// ?	block -> createOreDrops(block, ModItems.MYSTERY_ITEM.get(), 1, 4));



		// * -------------------- * //
	}

	protected LootTable.Builder createOreDrops(Block pBlock, Item item, float pMin, float pMax) {
		return createSilkTouchDispatchTable(pBlock,
				this.applyExplosionDecay(pBlock,
						LootItem.lootTableItem(item)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(pMin, pMax)))
								.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))

				));
	}

	@Override
	protected @NotNull Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries()
				.stream()
				.map(RegistryObject::get)::iterator;
	}
}
