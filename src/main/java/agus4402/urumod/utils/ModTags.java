package agus4402.urumod.utils;

import agus4402.urumod.Urumod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
	public static class Blocks {
		// * NEW TAGS HERE * //



		// * ------------- * //

		// ? EXAMPLE: public static final TagKey<Block> EXAMPLE_TAG = createTag("example_tag");

		private static TagKey<Block> createTag(String name) {
			return BlockTags.create(new ResourceLocation(Urumod.MOD_ID, name));
		}
	}

	public static class Items {

		private static TagKey<Item> createTag(String name) {
			return ItemTags.create(new ResourceLocation(Urumod.MOD_ID, name));
		}
	}
}
