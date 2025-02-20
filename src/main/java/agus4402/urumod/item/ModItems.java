package agus4402.urumod.item;

import agus4402.urumod.Urumod;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			Urumod.MOD_ID);

	// * NEW ITEMS HERE * //

	public static final RegistryObject<Item> TORTA_FRITA = ITEMS.register("torta_frita",
			() -> new Item(new Item.Properties().food(ModFoods.TORTA_FRITA)));

	public static final RegistryObject<Item> RAW_TORTA_FRITA= ITEMS.register("raw_torta_frita",
			() -> new Item(new Item.Properties().food(ModFoods.TORTA_FRITA)));

	public static final RegistryObject<Item> CHIVITO = ITEMS.register("chivito",
			() -> new Item(new Item.Properties().food(ModFoods.CHIVITO)));

	// * -------------- * //

	// ? EXAMPLE ITEM
	// ?	public static final RegistryObject<Item> FOOD_ITEM = ITEMS.register("food_item",
	// ?			() -> new Item(new Item.Properties()
	// ?					.stacksTo(16)
	// ?					.food(new FoodProperties.Builder()
	// ?							.nutrition(4)
	// ?							.saturationMod(0.5f)
	// ?							.effect(() -> new MobEffectInstance(MobEffects.HEAL, 200, 0), 1.0f)
	// ?							.build())
	// ?					.rarity(Rarity.RARE)));

	// ? EXAMPLE ADVANCED ITEM
	// ?	public static final RegistryObject<Item> advanced_item = ITEMS.register("advanced_item",
	// ?			() -> new AdvancedItem(new Item.Properties()
	// ?					.durability(500)
	// ?					.rarity(Rarity.RARE)));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
