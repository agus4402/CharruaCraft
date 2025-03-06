package agus4402.urumod;

import agus4402.urumod.block.ModBlocks;
import agus4402.urumod.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
			Urumod.MOD_ID);

	// * NEW TABS HERE * //
	public static final RegistryObject<CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register("mod_tab",
			() -> CreativeModeTab.builder()
					.icon(() -> new ItemStack(ModItems.TORTA_FRITA.get())) // ? EXAMPLE: new ItemStack(Items.DIAMOND_SWORD) (Diamond tab icon)
					.title(Component.translatable("tab.mod_tab.title")) // ? EXAMPLE: Component.translatable("tab.example_tab.title") (Change in language)
					.displayItems(((itemDisplayParameters, output) -> {
						// * NEW ITEMS HERE * //
						// ? EXAMPLE: output.accept(new ItemStack(Items.DIAMOND_SWORD));
						output.accept(new ItemStack(ModItems.TORTA_FRITA.get()));
						output.accept(new ItemStack(ModItems.RAW_TORTA_FRITA.get()));
						output.accept(new ItemStack(ModItems.CHIVITO.get()));
						output.accept(new ItemStack(ModItems.RICARDITO.get()));
						output.accept(new ItemStack(ModItems.EMPANADA.get()));
						output.accept(new ItemStack(ModItems.RAW_EMPANADA.get()));
						output.accept(new ItemStack(ModItems.EMPANADA_DOUGH.get()));
						output.accept(new ItemStack(ModBlocks.PAN.get()));
						output.accept(new ItemStack(ModItems.OIL_BOTTLE.get()));
						output.accept(new ItemStack(ModItems.FAT.get()));

						// * -------------- * //
					}))
					.build());
	// * -------------- * //

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}
}
