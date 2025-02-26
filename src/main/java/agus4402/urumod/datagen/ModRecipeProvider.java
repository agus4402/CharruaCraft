package agus4402.urumod.datagen;

import agus4402.urumod.Urumod;
import agus4402.urumod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        // * NEW RECIPES HERE * //

        ShapedRecipeBuilder
                .shaped(RecipeCategory.FOOD, ModItems.RAW_TORTA_FRITA.get())
                .pattern("W")
                .pattern("E")
                .pattern("M")
                .define('W', Items.WHEAT)
                .define('E', Items.EGG)
                .define('M', Items.WATER_BUCKET)
                .unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT))
                .unlockedBy(getHasName(Items.EGG), has(Items.EGG))
                .unlockedBy(getHasName(Items.WATER_BUCKET), has(Items.WATER_BUCKET))
                .save(pWriter);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.FOOD, ModItems.RAW_EMPANADA.get())
                .pattern("W")
                .pattern("E")
                .define('W', Items.BEEF)
                .define('E', ModItems.EMPANADA_DOUGH.get())
                .unlockedBy(getHasName(Items.BEEF), has(Items.BEEF))
                .unlockedBy(getHasName(ModItems.EMPANADA_DOUGH.get()), has(ModItems.EMPANADA_DOUGH.get()))
                .save(pWriter);

        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.FOOD, ModItems.EMPANADA_DOUGH.get(), 4)
                .requires(Items.WHEAT, 3)
                .requires(Items.WATER_BUCKET)
                .unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT))
                .save(pWriter);

        // * ---------------- * //

        // ? SHAPED EXAMPLE:
        // ? 		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TEST_BLOCK.get())
        // ?				.pattern("IX ")
        // ?				.pattern(" I ")
        // ?				.pattern(" II")
        // ?				.define('X', ModItems.TEST_ITEM.get())
        // ?				.define('I', Items.IRON_INGOT)
        // ?				.unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
        // ?				.unlockedBy(getHasName(ModItems.MYSTERY_ITEM.get()), has(ModItems.MYSTERY_ITEM.get()))
        // ?				.save(pWriter);

        // ? SHAPELESS EXAMPLE:
        // ?		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MYSTERY_ITEM.get(), 9)
        // ?				.requires(ModBlocks.MYSTERY_BLOCK.get())
        // ?				.unlockedBy(getHasName(ModBlocks.MYSTERY_BLOCK.get()), has(ModBlocks.MYSTERY_BLOCK.get()))
        // ?				.save(pWriter);

    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients,
                                      RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme,
                                      String pGroup) {
        oreCooking(
                pFinishedRecipeConsumer,
                RecipeSerializer.SMELTING_RECIPE,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTIme,
                pGroup,
                "_from_smelting"
        );
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients,
                                      RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime,
                                      String pGroup) {
        oreCooking(
                pFinishedRecipeConsumer,
                RecipeSerializer.BLASTING_RECIPE,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTime,
                pGroup,
                "_from_blasting"
        );
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                     RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
                                     List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience,
                                     int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder
                    .generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(
                            pFinishedRecipeConsumer,
                            Urumod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike)
                    );
        }
    }
}
