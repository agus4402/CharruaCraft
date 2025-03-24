package agus4402.urumod.compat;

import agus4402.urumod.Urumod;
import agus4402.urumod.block.ModBlocks;
import agus4402.urumod.item.custom.OilItem;
import agus4402.urumod.recipe.PanRecipe;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.util.Arrays;

public class PanCookingCategory implements IRecipeCategory<PanRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Urumod.MOD_ID, "pan_cooking");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Urumod.MOD_ID, "textures/gui/pan_jei_gui.png");

    public static final RecipeType<PanRecipe> PAN_COOKING_TYPE =
            new RecipeType<>(UID, PanRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final LoadingCache<Integer, IDrawableAnimated> oilProgress;
    private final LoadingCache<Integer, IDrawableAnimated> cookingProgress;

    public PanCookingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 83);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.PAN.get()));
        IDrawableStatic progressDrawable = helper.createDrawable(TEXTURE, 78, 176, 19, 13);
        this.oilProgress = createAnimated(helper, 176, 13, 18, 4, IDrawableAnimated.StartDirection.RIGHT, true);
        this.cookingProgress = createAnimated(helper, 176, 0, 19, 13, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @NotNull
    private static LoadingCache<Integer, IDrawableAnimated> createAnimated(IGuiHelper helper,
                                                                           int xOffset,
                                                                           int yOffset,
                                                                           int xSize,
                                                                           int ySize,
                                                                           IDrawableAnimated.StartDirection direction,
                                                                           boolean reverse) {
        return CacheBuilder.newBuilder().maximumSize(25L).build(new CacheLoader<>() {
            @Override
            public IDrawableAnimated load(Integer coolingTime) {
                return helper.drawableBuilder(TEXTURE, xOffset, yOffset, xSize, ySize).buildAnimated(coolingTime, direction, reverse);
            }
        });
    }

    @Override
    public RecipeType<PanRecipe> getRecipeType() {
        return PAN_COOKING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.urumod.pan");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(PanRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        int burnTime = ((OilItem) recipeSlotsView.getSlotViews().get(2).getDisplayedItemStack().get().getItem()).getBurnTime();
        System.out.println(recipeSlotsView.getSlotViews().get(2).getDisplayedItemStack().get().getDisplayName() + ": " + burnTime);

        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        Component smeltCountText = createSmeltCountText(burnTime);
        int textX = 86 - font.width(smeltCountText) / 2;
        guiGraphics.drawString(font, smeltCountText, textX, 68, 0xFF808080, false);
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        oilProgress.getUnchecked(Math.max(1, 200)).draw(guiGraphics, 78, 40);
        cookingProgress.getUnchecked(Math.max(1, 200)).draw(guiGraphics, 79, 19);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, PanRecipe recipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 51, 18).addIngredients(recipe.getIngredients().get(0));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 109, 18).addItemStack(recipe.getResultItem(null));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.CATALYST, 79, 48).addItemStacks(Arrays.stream(recipe.getFuel().getItems()).toList());
    }

    private static Component createSmeltCountText(int burnTime) {
        if (burnTime == 200) {
            return Component.translatable("gui.jei.category.fuel.smeltCount.single");
        } else {
            NumberFormat numberInstance = NumberFormat.getNumberInstance();
            numberInstance.setMaximumFractionDigits(2);
            String smeltCount = numberInstance.format(burnTime / 200f);
            return Component.translatable("gui.urumod.category.fuel.smeltCount", smeltCount);
        }
    }
}
