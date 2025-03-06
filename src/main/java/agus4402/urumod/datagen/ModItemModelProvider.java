package agus4402.urumod.datagen;

import agus4402.urumod.Urumod;
import agus4402.urumod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(
			PackOutput output, ExistingFileHelper existingFileHelper
	) {
		super(output, Urumod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		// * NEW ITEMS HERE * //
		simpleItem(ModItems.TORTA_FRITA);
		simpleItem(ModItems.RAW_TORTA_FRITA);
		simpleItem(ModItems.CHIVITO);
		simpleItem(ModItems.RICARDITO);
		simpleItem(ModItems.EMPANADA);
		simpleItem(ModItems.RAW_EMPANADA);
		simpleItem(ModItems.EMPANADA_DOUGH);
		simpleItem(ModItems.OIL_BOTTLE);

		// * -------------- * //

		// ? EXAMPLE: simpleItem(ModItems.TEST_ITEM);
	}

	private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId()
				.getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(
						Urumod.MOD_ID,
						"item/" + item.getId()
								.getPath()));
	}
}
