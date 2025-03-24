package agus4402.urumod.recipe;

import agus4402.urumod.Urumod;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class PanRecipe implements Recipe<SimpleContainer> {

    private final static int INGREDIENTS_SIZE = 1;
    private final static String RECIPE_PATH = "pan_cooking";

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final Ingredient fuel;

    public PanRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id, Ingredient fuel) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
        this.fuel = fuel;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        if(level.isClientSide()) return false;
        return inputItems.get(0).test(simpleContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public Ingredient getFuel() {
        return fuel;
    }

    public int getBurnTime() {
        return -1;
    }

    public static class Type implements RecipeType<PanRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = RECIPE_PATH;
    }

    public static class Serializer implements RecipeSerializer<PanRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Urumod.MOD_ID, RECIPE_PATH);

        @Override
        public PanRecipe fromJson(ResourceLocation resourceLocation, JsonObject serializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "result"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(serializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(INGREDIENTS_SIZE, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            Ingredient fuel = Ingredient.fromJson(GsonHelper.getAsJsonObject(serializedRecipe, "fuel"));
            return new PanRecipe(inputs, output, resourceLocation, fuel);
        }

        @Override
        public @Nullable PanRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(friendlyByteBuf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromNetwork(friendlyByteBuf));
            }
            ItemStack output = friendlyByteBuf.readItem();
            Ingredient fuel = Ingredient.fromNetwork(friendlyByteBuf);
            return new PanRecipe(inputs, output, resourceLocation, fuel);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, PanRecipe recipe) {
            friendlyByteBuf.writeInt(recipe.inputItems.size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(friendlyByteBuf);
            }
            friendlyByteBuf.writeItemStack(recipe.getResultItem(null), false);
            recipe.getFuel().toNetwork(friendlyByteBuf);
        }
    }
}
