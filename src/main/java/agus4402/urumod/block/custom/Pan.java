package agus4402.urumod.block.custom;

import agus4402.urumod.block.ModBlocks;
import agus4402.urumod.block.entity.ModBlockEntities;
import agus4402.urumod.block.entity.PanBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class Pan extends BaseEntityBlock {
    public static final BooleanProperty FRITANGA;
    public static final DirectionProperty FACING;
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 6, 16);

    public Pan(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState((BlockState)this.stateDefinition.any().setValue(Pan.FRITANGA, false).setValue(FACING, Direction.NORTH));
    }

    public void setValue(BlockState pState, Level pLevel, BlockPos pPos, boolean pFritanga) {
        pLevel.setBlock(pPos, (BlockState)pState.setValue(FRITANGA, pFritanga), 3);
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        System.out.println("onNeighborChange");
        System.out.println(state.getBlock().getName());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof PanBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (PanBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PanBlockEntity) {
                ((PanBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide){
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.PAN_BE.get(),
                (level, blockPos, blockState, potBlockEntity) -> potBlockEntity.tick(level, blockPos, blockState));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PanBlockEntity(blockPos, blockState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(new Property[]{FRITANGA, FACING});
    }

    static {
        FRITANGA = BooleanProperty.create("fritanga");
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }
}
