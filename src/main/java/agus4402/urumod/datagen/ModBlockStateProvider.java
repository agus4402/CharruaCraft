package agus4402.urumod.datagen;

import agus4402.urumod.Urumod;
import agus4402.urumod.block.ModBlocks;
import agus4402.urumod.block.custom.CampfireWithPan;
import agus4402.urumod.block.custom.Pan;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.BiFunction;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(
			PackOutput output, ExistingFileHelper exFileHelper
	) {
		super(output, Urumod.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		// * NEW BLOCKS HERE * //

		blockWithItem(ModBlocks.MYSTERY_BLOCK);
//		blockWithModel("block/pan", ModBlocks.PAN.get());
//		generateCampfireVariants(ModBlocks.CAMPFIRE_WITH_PAN.get());
		modelBlockWith3States(ModBlocks.CAMPFIRE_WITH_PAN.get(), BlockStateProperties.LIT, CampfireWithPan.PAN_SUPPORTS, (property, property2, state, state2) -> {
			if (state && state2) {
				return "block/campfire_with_support";
			} else if (state && !state2) {
				return "block/campfire";
			} else if (!state && state2) {
				return "block/campfire_with_support_off";
			} else {
				return "block/campfire_off";
			}
		},true);
		modelBlockWith2States(ModBlocks.PAN.get(), Pan.FRITANGA, (property, state) -> !state ? "block/pan" : "block/pan_with_oil");

		// * ---------------- * //

		// ? EXAMPLE:	blockWithItem(ModBlocks.TEST_BLOCK);
	}

	private void blockWithModel(String modLoc, Block block) {
		simpleBlockWithItem(block, new ModelFile.UncheckedModelFile(modLoc(modLoc)));
	}

	private void generateCampfireVariants(Block block) {
		VariantBlockStateBuilder builder = getVariantBuilder(block);
		// Loop through all possible FACING values (NORTH, SOUTH, EAST, WEST)
		for (var facing : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
			// Loop through all possible LIT values (true, false)
			for (boolean lit : new boolean[]{true, false}) {
				// Construct the model name based on the LIT state
				String modelName = lit ? "block/campfire" : "block/campfire_off";

				// Calculate the Y rotation based on the FACING value
				int yRotation = switch (facing) {
					case EAST -> 270;
					case WEST -> 90;
					case NORTH -> 180;
					case SOUTH -> 0;
					default -> 0; // Shouldn't happen, but provide a default
				};

				simpleBlockItem(block, new ModelFile.UncheckedModelFile(modLoc(modelName)));
				// Add the variant to the builder
				builder.partialState()
						.with(BlockStateProperties.HORIZONTAL_FACING, facing)
						.with(BlockStateProperties.LIT, lit)
						.modelForState()
						.modelFile(new ModelFile.UncheckedModelFile(mcLoc(modelName)))
						.rotationY(yRotation)
						.addModel();
			}
		}
	}

	private void modelBlockWith2States(Block block, BooleanProperty property, BiFunction<BooleanProperty, Boolean, String> modelNameFunction) {
		VariantBlockStateBuilder builder = getVariantBuilder(block);

		for (var facing : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
			for (boolean state : new boolean[]{true, false}) {
				String modelName = modelNameFunction.apply(property, state);
				int yRotation = switch (facing) {
					case EAST -> 270;
					case WEST -> 90;
					case NORTH -> 180;
					case SOUTH -> 0;
					default -> 0;
				};

				simpleBlockItem(block, new ModelFile.UncheckedModelFile(modLoc(modelName)));

				builder.partialState()
						.with(BlockStateProperties.HORIZONTAL_FACING, facing)
						.with(property, state)
						.modelForState()
						.modelFile(new ModelFile.UncheckedModelFile(modLoc(modelName)))
						.rotationY(yRotation)
						.addModel();
			}
		}
	}

	private void modelBlockWith3States(Block block, BooleanProperty property, BooleanProperty property2, PropertyDispatch.QuadFunction<BooleanProperty, BooleanProperty, Boolean, Boolean, String> modelNameFunction, boolean isVanilla) {
		VariantBlockStateBuilder builder = getVariantBuilder(block);

		for (var facing : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
			for (boolean state : new boolean[]{true, false}) {
				for (boolean state2 : new boolean[]{true, false}) {
					String modelName = modelNameFunction.apply(property, property2, state, state2);
					int yRotation = switch (facing) {
						case EAST -> 270;
						case WEST -> 90;
						case NORTH -> 180;
						case SOUTH -> 0;
						default -> 0;
					};

					simpleBlockItem(block, new ModelFile.UncheckedModelFile(modLoc(modelName)));

					builder.partialState()
							.with(BlockStateProperties.HORIZONTAL_FACING, facing)
							.with(property, state)
							.with(property2, state2)
							.modelForState()
							.modelFile(new ModelFile.UncheckedModelFile(isVanilla ? mcLoc(modelName) : modLoc(modelName)))
							.rotationY(yRotation)
							.addModel();
				}
			}
		}
	}

	private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
		simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
	}
}
