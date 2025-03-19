package agus4402.urumod.entity;

import agus4402.urumod.Urumod;
import agus4402.urumod.entity.custom.CapybaraEntity;
import agus4402.urumod.entity.custom.MockingbirdEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Urumod.MOD_ID);

    // * REGISTER ENTITIES HERE * //

    public static final RegistryObject<EntityType<CapybaraEntity>> CAPYBARA =
            ENTITY_TYPES.register("capybara", () ->
                    EntityType.Builder.of(CapybaraEntity::new, MobCategory.CREATURE)
                            .sized(1, 1)
                            .build(new ResourceLocation(Urumod.MOD_ID, "capybara").toString())
            );

    public static final RegistryObject<EntityType<MockingbirdEntity>> MOCKINGBIRD =
            ENTITY_TYPES.register("mockingbird", () ->
                    EntityType.Builder.of(MockingbirdEntity::new, MobCategory.CREATURE)
                            .sized(.3f, .3f)
                            .build(new ResourceLocation(Urumod.MOD_ID, "mockingbird").toString())
            );

    // * -------------------------- * //

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
