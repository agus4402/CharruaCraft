package agus4402.urumod.event;

import agus4402.urumod.Urumod;
import agus4402.urumod.entity.ModEntities;
import agus4402.urumod.entity.custom.CapybaraEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Urumod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CAPYBARA.get(), CapybaraEntity.setAttributes());
        event.put(ModEntities.MOCKINGBIRD.get(), CapybaraEntity.setAttributes());
    }
}
