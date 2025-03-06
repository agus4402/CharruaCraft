package agus4402.urumod.processor;

import agus4402.urumod.Urumod;
import agus4402.urumod.processor.processors.IslandFoundationProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS =
            DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, Urumod.MOD_ID);

    // * NEW PROCESSORS GO HERE * //

    public static final RegistryObject<StructureProcessorType<IslandFoundationProcessor>> ISLAND_FOUNDATION =
            PROCESSORS.register("island_foundation", () -> () -> IslandFoundationProcessor.CODEC);

    // * ---------------------- * //

}
