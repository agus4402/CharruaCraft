package agus4402.urumod.block.entity;

import agus4402.urumod.Urumod;
import agus4402.urumod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Urumod.MOD_ID);

    // * NEW BLOCK ENTITIES HERE * //

    public static final RegistryObject<BlockEntityType<PanBlockEntity>> PAN_BE =
            BLOCK_ENTITIES.register("pan_be", () ->
                    BlockEntityType.Builder.of(
                            PanBlockEntity::new,
                            ModBlocks.PAN.get()).build(null));

    // * ---------------- * //

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
