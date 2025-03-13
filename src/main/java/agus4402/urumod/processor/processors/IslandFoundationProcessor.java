package agus4402.urumod.processor.processors;

import agus4402.urumod.processor.ModProcessors;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class IslandFoundationProcessor extends StructureProcessor {
    public static final IslandFoundationProcessor INSTANCE =
            new IslandFoundationProcessor();
    public static final Codec<IslandFoundationProcessor> CODEC =
            Codec.unit(() -> INSTANCE);

    @Override
    @Nullable
    public StructureTemplate.StructureBlockInfo processBlock(
            LevelReader level,
            BlockPos pos,
            BlockPos pivot,
            StructureTemplate.StructureBlockInfo originalInfo,
            StructureTemplate.StructureBlockInfo transformedInfo,
            StructurePlaceSettings settings
    ) {
        if (level instanceof LevelAccessor levelAccessor) {
            BlockPos below = transformedInfo.pos().below();
            if (level.getBlockState(below).getBlock() == Blocks.WATER) {
                generateIsland(levelAccessor, below);
            }
        }
        return transformedInfo;
    }

    private void generateIsland(LevelAccessor level, BlockPos pos) {
        int radius = 3;
        int height = 2;

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = 0; y < height; y++) {
                    BlockPos newPos = pos.offset(x, y, z);

                    level.setBlock(newPos, Blocks.SAND.defaultBlockState(), 3);
                }
            }
        }
    }


    @Override
    protected StructureProcessorType<?> getType() {
        return ModProcessors.ISLAND_FOUNDATION.get();
    }
}
