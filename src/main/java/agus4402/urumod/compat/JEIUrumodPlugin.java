package agus4402.urumod.compat;

import agus4402.urumod.Urumod;
import agus4402.urumod.recipe.PanRecipe;
import agus4402.urumod.screen.PanScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIUrumodPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Urumod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new PanCookingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<PanRecipe> panRecipes = recipeManager.getAllRecipesFor(PanRecipe.Type.INSTANCE);
        registration.addRecipes(PanCookingCategory.PAN_COOKING_TYPE, panRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(PanScreen.class, 79, 30, 19, 13, PanCookingCategory.PAN_COOKING_TYPE);
    }
}
