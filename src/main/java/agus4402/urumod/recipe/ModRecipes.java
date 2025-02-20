package agus4402.urumod.recipe;

import agus4402.urumod.Urumod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Urumod.MOD_ID);

    // * NEW RECIPE SERIALIZERS HERE * //
    public static final RegistryObject<RecipeSerializer<PanRecipe>> PAN_COOKING =
            SERIALIZERS.register("pan_cooking", () -> PanRecipe.Serializer.INSTANCE);

    // * ------------------------ * //

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
