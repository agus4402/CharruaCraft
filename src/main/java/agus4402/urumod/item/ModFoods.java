package agus4402.urumod.item;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties TORTA_FRITA =
            new FoodProperties.Builder().nutrition(4).saturationMod(0.3F).build();

    public static final FoodProperties TORTA_FRITA_RAW =
            new FoodProperties.Builder().nutrition(1).build();

    public static final FoodProperties CHIVITO =
            new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build();

    public static final FoodProperties EMPANADA =
            new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build();

    public static final FoodProperties RAW_EMPANADA =
            new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build();

    public static final FoodProperties RICARDITO =
            new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build();
}
