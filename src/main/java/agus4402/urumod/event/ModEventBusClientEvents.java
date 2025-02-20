package agus4402.urumod.event;

import agus4402.urumod.Urumod;
import agus4402.urumod.block.entity.ModBlockEntities;
import agus4402.urumod.block.entity.renderer.PanBlockEnityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Urumod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.PAN_BE.get(), PanBlockEnityRenderer::new);
    }
}
