package agus4402.urumod;

import agus4402.urumod.block.ModBlocks;
import agus4402.urumod.block.entity.ModBlockEntities;
import agus4402.urumod.config.ModConfig;
import agus4402.urumod.entity.ModEntities;
import agus4402.urumod.entity.client.CapybaraRenderer;
import agus4402.urumod.item.ModItems;
import agus4402.urumod.processor.ModProcessors;
import agus4402.urumod.recipe.ModRecipes;
import agus4402.urumod.screen.ModMenuTypes;
import agus4402.urumod.screen.PanScreen;
import agus4402.urumod.sound.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Urumod.MOD_ID)
public class Urumod {

    public static final String MOD_ID = "urumod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Urumod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();
        ModConfig.register(ModLoadingContext.get());

        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModProcessors.PROCESSORS.register(modEventBus);

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ServerModEvents {
        @SubscribeEvent
        public void onModConfigEvent(final ModConfigEvent event) {
            System.out.println("Config for" + Urumod.MOD_ID + "loaded");
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void addItemsToCreativeTab(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
                event.accept(ModItems.TORTA_FRITA);
            }
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.PAN_MENU.get(), PanScreen::new);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CAMPFIRE_WITH_PAN.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PAN.get(), RenderType.translucent());
            EntityRenderers.register(ModEntities.CAPYBARA.get(), CapybaraRenderer::new);
        }
    }
}
