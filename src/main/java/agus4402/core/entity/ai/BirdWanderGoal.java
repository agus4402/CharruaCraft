package agus4402.core.entity.ai;

import agus4402.core.entity.Bird;
import agus4402.core.utils.Debug;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BirdWanderGoal extends WaterAvoidingRandomFlyingGoal {
    private final Bird bird;
    private final float flyProbability;
    private boolean hasDestination;
    private boolean isLanding;

    public BirdWanderGoal(Bird mob, double speedModifier) {
        super(mob, speedModifier);
        this.bird = mob;
        this.flyProbability = this.probability;
    }

    public BirdWanderGoal(Bird mob, double speedModifier, float probability) {
        super(mob, speedModifier);
        this.bird = mob;
        this.flyProbability = probability;
    }

    protected Vec3 getPosition() {
        Vec3 vec3 = null;
        if (bird.isInWater()) {
            vec3 = getRandomLandPos();
        }

        if (bird.getRandom().nextFloat() >= this.flyProbability) {
            vec3 = this.getTreePos();
        }

        return vec3 == null ? super.getPosition() : vec3;
    }

    private Vec3 getTreePos() {
        BlockPos mobPos = this.mob.blockPosition();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutable1 = new BlockPos.MutableBlockPos();

        for (BlockPos pos : BlockPos.betweenClosed(
                Mth.floor(this.mob.getX() - 3.0D),
                Mth.floor(this.mob.getY() - 6.0D),
                Mth.floor(this.mob.getZ() - 3.0D),
                Mth.floor(this.mob.getX() + 3.0D),
                Mth.floor(this.mob.getY() + 6.0D),
                Mth.floor(this.mob.getZ() + 3.0D)
        )) {
            if (!mobPos.equals(pos)) {
                BlockState blockstate = this.mob.level().getBlockState(mutable1.setWithOffset(pos, Direction.DOWN));
                boolean flag = blockstate.getBlock() instanceof LeavesBlock || blockstate.is(BlockTags.LOGS);
                if (flag && this.mob.level().isEmptyBlock(pos) && this.mob.level().isEmptyBlock(mutable.setWithOffset(pos, Direction.UP))) {
                    return Vec3.atBottomCenterOf(pos.above());
                }
            }
        }

        return null;
    }

    private Vec3 getRandomLandPos() {
        return LandRandomPos.getPos(bird, 15, 15);
    }

    @Override
    public void tick() {
        super.tick();
        if (bird.onGround() && isLanding) {
            isLanding = false;
            onLand();
        }

        checkAndUnstuck();
    }

    private void onLand() {

    }

    private void checkAndUnstuck() {
        if (bird.timeFlying > 200 && !isLanding) {
            Vec3 vec3;
            vec3 = getRandomLandPos();
            if (vec3 != null) {
                isLanding = true;
                Debug.spawnDebugBlock(bird.level(), vec3, Blocks.DEAD_BUSH.defaultBlockState());
                bird.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.2F);
            }
        }
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }
}