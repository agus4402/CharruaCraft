package agus4402.urumod.processor.processors;

import agus4402.urumod.processor.ModProcessors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class EntityReplacementProcessor extends StructureProcessor {
    public static final Codec<EntityReplacementProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("replace").forGetter(p -> p.replace),
            ResourceLocation.CODEC.fieldOf("with").forGetter(p -> p.with),
            Codec.DOUBLE.fieldOf("probability").forGetter(p -> p.probability)
    ).apply(instance, EntityReplacementProcessor::new));

    private final ResourceLocation replace;
    private final ResourceLocation with;
    private final Double probability;

    // Set para almacenar entidades ya procesadas
    private static final Set<String> processedEntities = new HashSet<>();

    public EntityReplacementProcessor(ResourceLocation replace, ResourceLocation with, Double probability) {
        this.replace = replace;
        this.with = with;
        this.probability = probability;
    }

    @Override
    public StructureTemplate.StructureEntityInfo processEntity(LevelReader world,
                                                               BlockPos seedPos,
                                                               StructureTemplate.StructureEntityInfo rawEntityInfo,
                                                               StructureTemplate.StructureEntityInfo entityInfo,
                                                               StructurePlaceSettings placementSettings,
                                                               StructureTemplate template) {
        CompoundTag entityNBT = entityInfo.nbt.copy();
        try {
            double p = probability;
            double random = Math.random();
            String id = seedPos.toString() + entityInfo.nbt.get("UUID");
            if (!processedEntities.contains(id)) {
                System.out.println("id: " + id);
                System.out.println("probability: " + p);
                System.out.println("random: " + random + " pos: " + seedPos.toString());
                if (random < p && entityNBT.getString("id").equals(replace.toString())) {
                    entityNBT.putString("id", with.toString());
                }
                processedEntities.add(id);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }


        return new StructureTemplate.StructureEntityInfo(entityInfo.pos, entityInfo.blockPos, entityNBT);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.ISLAND_FOUNDATION.get();
    }
}
