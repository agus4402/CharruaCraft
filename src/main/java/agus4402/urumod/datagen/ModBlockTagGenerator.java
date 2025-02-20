package agus4402.urumod.datagen;

import agus4402.urumod.Urumod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
	public ModBlockTagGenerator(
			PackOutput output,
			CompletableFuture<HolderLookup.Provider> lookupProvider,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Urumod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.@NotNull Provider provider) {
		// * NEW BLOCKS HERE * //



		// * ---------------- * //

		// ? EXAMPLE: this.tag(ModTags.Blocks.MINEABLE_WITH_PICKAXE)
		// ?			.add(ModBlocks.MYSTERY_BLOCK.get())
		// ?			.addTags(Tags.Blocks.ORES);
	}
}
