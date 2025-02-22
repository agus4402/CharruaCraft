package agus4402.urumod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

public class CampfireWithPan extends CampfireBlock {
    public static final BooleanProperty PAN_SUPPORTS;

    public CampfireWithPan(Properties pProperties) {
        super(true, 3, pProperties);
        this.registerDefaultState((BlockState)this.stateDefinition.any().setValue(CampfireWithPan.PAN_SUPPORTS, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        if (level instanceof Level actualLevel && !actualLevel.isClientSide) {
            BlockPos abovePos = pos.above();
            BlockState aboveState = actualLevel.getBlockState(abovePos);
            if (aboveState.getBlock() instanceof Pan) {
                actualLevel.setBlock(pos, (BlockState) state.setValue(PAN_SUPPORTS, true), 3);
            } else if(!(aboveState.getBlock() instanceof Pan)) {
                actualLevel.setBlock(pos, (BlockState) state.setValue(PAN_SUPPORTS, false), 3);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.getItemInHand(pHand).getItem() instanceof FlintAndSteelItem){
            pPlayer.getItemInHand(pHand).hurtAndBreak(1, pPlayer, (p) ->
                    p.broadcastBreakEvent(pHand)
            );

            pLevel.setBlock(pPos, (BlockState)pState.setValue(BlockStateProperties.LIT, true), 11);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(PAN_SUPPORTS);
    }

    @Override
    protected void spawnDestroyParticles(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState) {
        super.spawnDestroyParticles(pLevel, pPlayer, pPos, pState);
    }

    static {
        PAN_SUPPORTS = BooleanProperty.create("pan_supports");
    }
}
