package agus4402.urumod.datagen;

import agus4402.urumod.Urumod;
import agus4402.urumod.item.ModItems;
import agus4402.urumod.utils.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
	public ModItemTagGenerator(
			PackOutput p_275343_,
			CompletableFuture<HolderLookup.Provider> p_275729_,
			CompletableFuture<TagLookup<Block>> p_275322_,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(p_275343_, p_275729_, p_275322_, Urumod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		// * NEW ITEMS HERE * //

		this.tag(ModTags.Items.PAN_OIL)
				.add(ModItems.OIL_BOTTLE.get())
				.add(ModItems.FAT.get());

		// * -------------- * //

		// ? EXAMPLE: this.tag(ModTags.Items.MINEABLE_WITH_PICKAXE)
		// ?			.add(ModItems.TEST_ITEM.get())
		// ?			.addTags(Tags.Items.ORES);
	}
}
