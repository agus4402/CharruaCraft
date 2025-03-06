package agus4402.urumod.world.village;

import agus4402.urumod.Urumod;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Urumod.MOD_ID)
public class VillageAddition {
    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(Registries.PROCESSOR_LIST, new ResourceLocation("minecraft", "empty"));

    /**
     * Adds the building to the targeted pool.
     * We will call this in addNewVillageBuilding method further down to add to every village.
     * <p>
     * Note: This is an additive operation which means multiple mods can do this and they stack with each other safely.
     */
    private static void addBuildingToPool(Registry<StructureTemplatePool> templatePoolRegistry, Registry<StructureProcessorList> processorListRegistry, ResourceLocation poolRL, String nbtPieceRL, int weight, ResourceLocation processor) {

        // Grabs the processor list we want to use along with our piece.
        // This is a requirement as using the ProcessorLists.EMPTY field will cause the game to throw errors.
        // The reason why is the empty processor list in the world's registry is not the same instance as in that field once
        // the world is started up.
        Holder<StructureProcessorList> emptyProcessorList = processorListRegistry.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);

        // Grab the pool we want to add to
        StructureTemplatePool pool = templatePoolRegistry.get(poolRL);
        if (pool == null) return;

        Holder<StructureProcessorList> processorList = processorListRegistry.getHolder(ResourceKey.create(Registries.PROCESSOR_LIST, processor))
                .orElseThrow(() -> new IllegalStateException("Processor List no encontrado: " + processor));

        // Grabs the nbt piece and creates a SinglePoolElement of it that we can add to a structure's pool.
        // Use .legacy( for villages/outposts and .single( for everything else
        SinglePoolElement piece = SinglePoolElement.legacy(nbtPieceRL, processorList).apply(StructureTemplatePool.Projection.RIGID);

        // Use AccessTransformer or Accessor Mixin to make StructureTemplatePool's templates field public for us to see.
        // Weight is handled by how many times the entry appears in this list.
        // We do not need to worry about immutability as this field is created using Lists.newArrayList(); which makes a
        // mutable list.
        for (int i = 0; i < weight; i++) {
            pool.templates.add(piece); //changed at accesstransformer
        }

        // Use AccessTransformer or Accessor Mixin to make StructureTemplatePool's rawTemplates field public for us to see.
        // This list of pairs of pieces and weights is not used by vanilla by default but another mod may need it for efficiency.
        // So lets add to this list for completeness. We need to make a copy of the array as it can be an immutable list.
        List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
        listOfPieceEntries.add(new Pair<>(piece, weight));
        pool.rawTemplates = listOfPieceEntries; //changed at accesstransformer
    }

    /**
     * We use FMLServerAboutToStartEvent as the dynamic registry exists now and all JSON worldgen files were parsed.
     * Mod compat is best done here.
     */
    @SubscribeEvent
    public static void villageAdditions(final ServerAboutToStartEvent event) {
        Registry<StructureTemplatePool> templatePoolRegistry = event.getServer().registryAccess().registry(Registries.TEMPLATE_POOL).orElseThrow();
        Registry<StructureProcessorList> processorListRegistry = event.getServer().registryAccess().registry(Registries.PROCESSOR_LIST).orElseThrow();

        // Adds our piece to all village houses pool
        // Note, the resource location is getting the pool files from the data folder. Not assets folder.
        addBuildingToPool(
                templatePoolRegistry,
                processorListRegistry,
                new ResourceLocation("minecraft:village/plains/houses"),
                "urumod:farm_1",
                100,
                new ResourceLocation("minecraft:farm_plains")
        );
        addBuildingToPool(
                templatePoolRegistry,
                processorListRegistry,
                new ResourceLocation("minecraft:village/plains/houses"),
                "urumod:farm_medium_1",
                250,
                new ResourceLocation("minecraft:farm_plains")
        );

        StructureProcessor cropProcessor = new RuleProcessor(List.of(
//                new ProcessorRule(new RandomBlockMatchTest(Blocks.WHEAT, 0.70F), AlwaysTrueTest.INSTANCE, Blocks.BEETROOTS.defaultBlockState()),
//                new ProcessorRule(new RandomBlockMatchTest(Blocks.WHEAT, 0.30F), AlwaysTrueTest.INSTANCE, Blocks.CARROTS.defaultBlockState())
        ));

        addNewRuleToProcessorList(ResourceLocation.tryParse("minecraft:farm_plains"), cropProcessor, processorListRegistry);
    }

    private static void addNewRuleToProcessorList(ResourceLocation targetProcessorList, StructureProcessor processorToAdd, Registry<StructureProcessorList> processorListRegistry) {
        processorListRegistry.getOptional(targetProcessorList).ifPresent(processorList -> {
            List<StructureProcessor> newSafeList = new ArrayList<>(processorList.list());
            newSafeList.add(processorToAdd);
            processorList.list = newSafeList; //changed at accesstransformer
        });
    }
}
