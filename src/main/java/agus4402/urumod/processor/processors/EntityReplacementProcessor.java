package agus4402.urumod.processor.processors;

import agus4402.urumod.processor.ModProcessors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class EntityReplacementProcessor extends StructureProcessor {
    public static final Codec<EntityReplacementProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("replace").forGetter(p -> p.replace),
            ResourceLocation.CODEC.fieldOf("with").forGetter(p -> p.with),
            Codec.DOUBLE.fieldOf("probability").forGetter(p -> p.probability)
    ).apply(instance, EntityReplacementProcessor::new));

    private final ResourceLocation replace;
    private final ResourceLocation with;
    private final Double probability;

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
         try {
             double p = 1;
             if(!Double.isNaN(this.probability)){
                p = this.probability;
             }
             if (Math.random() < p && entityInfo.nbt.getString("id").equals(replace.toString())) {
                 entityInfo.nbt.putString("id", with.toString());
             }
         }catch (Exception e){
                throw  new IllegalArgumentException(e.getMessage());
         }


        return entityInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.ISLAND_FOUNDATION.get();
    }
}
