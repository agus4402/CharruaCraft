package agus4402.urumod.block;

import agus4402.urumod.block.custom.CampfireWithPan;
import agus4402.urumod.block.custom.Pan;
import agus4402.urumod.item.ModItems;
import agus4402.urumod.Urumod;
import agus4402.urumod.sound.ModSounds;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Urumod.MOD_ID);

	public static final DeferredRegister<Block> VANILLA_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			"minecraft");



	// * NEW BLOCKS HERE * //

	public static final RegistryObject<Block> MYSTERY_BLOCK = registerBlock("mystery_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
					.requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> PAN = registerBlock("pan",
			() -> new Pan(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().sound(ModSounds.PAN_SOUNDS)));

	public static final RegistryObject<Block> CAMPFIRE_WITH_PAN = registerVanillaBlock("campfire",
			() -> new CampfireWithPan(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE).noOcclusion()));

	// * ---------------- * //

	// ? EXAMPLE (Advanced Block):
	// ? public static final RegistryObject<Block> ADVANCED_TEST_BLOCK = registerBlock("advanced_test_block",
	// ?		() -> new AdvancedBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
	// ?				.requiresCorrectToolForDrops()));

	// ? EXAMPLE (Block):
	// ? public static final RegistryObject<BlockItem> TEST_BLOCK_ITEM = registerBlock("test_block",
	// ?		() -> new Block(new Item.Properties()));

	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<T> registerVanillaBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = VANILLA_BLOCKS.register(name, block);
		registerVanillaBlockItem(name, toReturn);
		return toReturn;
	}

	public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static <T extends Block> RegistryObject<Item> registerVanillaBlockItem(String name, RegistryObject<T> block) {
		return ModItems.VANILLA_ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}

}