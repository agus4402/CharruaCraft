package agus4402.core.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Debug {

    public static void spawnDebugBlock(Level level, Vec3 pos, BlockState blockState){
        BlockPos debugPos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
        if (level.getBlockState(debugPos.below()).isSolid()) {
            level.setBlock(debugPos, blockState, 3);
        }
        else{
            level.setBlock(debugPos, blockState, 3);
        }
    }

}
