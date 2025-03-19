package agus4402.urumod.processor.processors;

import agus4402.urumod.processor.ModProcessors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Optional;

public class ModifyHorseProcessor extends StructureProcessor {
    public static final Codec<ModifyHorseProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("variant").forGetter(p -> p.variant)
    ).apply(instance, ModifyHorseProcessor::new));

    private final Optional<Integer> variant;

    public ModifyHorseProcessor(Optional<Integer> variant) {
        this.variant = variant;
    }

    @Override
    public StructureTemplate.StructureEntityInfo processEntity(LevelReader world,
                                                               BlockPos seedPos,
                                                               StructureTemplate.StructureEntityInfo rawEntityInfo,
                                                               StructureTemplate.StructureEntityInfo entityInfo,
                                                               StructurePlaceSettings placementSettings,
                                                               StructureTemplate template) {
        int v1 = variant.orElse(-1);

        if(v1 == -1){
            return super.processEntity(world,seedPos,rawEntityInfo,entityInfo,placementSettings,template);
        }

        if (entityInfo.nbt.getString("id").equals("minecraft:horse")) {
            entityInfo.nbt.putInt("variant", v1);
        }

        return entityInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.ISLAND_FOUNDATION.get();
    }
}
