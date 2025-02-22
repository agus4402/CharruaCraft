package agus4402.urumod.event;

import agus4402.urumod.block.ModBlocks;
import agus4402.urumod.block.custom.CampfireWithPan;
import agus4402.urumod.block.custom.Pan;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    @SubscribeEvent
    public static void onBlockRemoved(BlockEvent.BreakEvent event) {
        Level world = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof Pan) {
            BlockPos belowPos = pos.below();
            BlockState belowState = world.getBlockState(belowPos);

            if (belowState.getBlock() instanceof CampfireWithPan) {
                world.setBlock(belowPos, Blocks.CAMPFIRE.defaultBlockState(), 3);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        Level world = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState placedBlock = event.getPlacedBlock();

        if (placedBlock.getBlock() instanceof CampfireBlock) {
            BlockPos abovePos = pos.above();
            BlockState aboveState = world.getBlockState(abovePos);

            if (aboveState.getBlock() instanceof Pan) {
                world.setBlock(pos, ModBlocks.CAMPFIRE_WITH_PAN.get().defaultBlockState().setValue(BlockStateProperties.LIT, placedBlock.getValue(BlockStateProperties.LIT)), 3);
            }
        }

        if (placedBlock.getBlock() instanceof Pan) {
            BlockPos belowPos = pos.below();
            BlockState belowState = world.getBlockState(belowPos);

            if (belowState.getBlock() instanceof CampfireBlock) {
                world.setBlock(belowPos, ModBlocks.CAMPFIRE_WITH_PAN.get().defaultBlockState().setValue(BlockStateProperties.LIT, belowState.getValue(BlockStateProperties.LIT)), 3);
            }
        }
    }
}