package agus4402.core.entity.ai;

import agus4402.core.entity.Bird;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class AIFlyIdle extends Goal {
    protected double x;
    protected double y;
    protected double z;
    private boolean flightTarget;

    private final Bird mob;

    public AIFlyIdle(Bird mob) {
        super();
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        RandomSource random = mob.getRandom();
        if (mob.isVehicle() || (mob.getTarget() != null && mob.getTarget().isAlive()) || mob.isPassenger() || mob.timeGrounded < mob.getFlyCooldown()) {
            return false;
        } else {
            if (mob.getRandom().nextInt(45) != 0 && !mob.isFlying()) {
                return false;
            }
            if (mob.onGround()) {
                this.flightTarget = random.nextBoolean();
            } else {
                this.flightTarget = random.nextInt(5) > 0 && mob.timeFlying < 200;
            }
            Vec3 lvt_1_1_ = this.getPosition();
            if (lvt_1_1_ == null) {
                return false;
            } else {
                this.x = lvt_1_1_.x;
                this.y = lvt_1_1_.y;
                this.z = lvt_1_1_.z;
                return true;
            }
        }
    }

    public void tick() {
        if (flightTarget) {
            mob.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1F);
        } else {
            mob.getNavigation().moveTo(this.x, this.y, this.z, 1F);
        }
        if (!flightTarget && mob.isFlying() && mob.onGround()) {
            mob.setFlying(false);
        }
        if (mob.isFlying() && mob.onGround() && mob.timeFlying > 10) {
            mob.setFlying(false);
        }
    }

    @javax.annotation.Nullable
    protected Vec3 getPosition() {
        Vec3 vector3d = mob.position();

        if (mob.isOverWaterOrVoid()) {
            flightTarget = true;
        }
        if (flightTarget) {
            if (mob.timeFlying < 200 || mob.isOverWaterOrVoid()) {
                return mob.getBlockInViewAway(vector3d, 0);
            } else {
                return mob.getBlockGrounding(vector3d);
            }
        } else {
            return LandRandomPos.getPos(mob, 10, 7);
        }
    }

    public boolean canContinueToUse() {
        if (flightTarget) {
            return mob.isFlying() && mob.distanceToSqr(x, y, z) > 5F;
        } else {
            return (!mob.getNavigation().isDone()) && !mob.isVehicle();
        }
    }

    public void start() {
        if (flightTarget) {
            mob.setFlying(true);
            mob.getMoveControl().setWantedPosition(x, y, z, 1F);
        } else {
            mob.getNavigation().moveTo(this.x, this.y, this.z, 1F);
        }
    }

    public void stop() {
        mob.getNavigation().stop();
        x = 0;
        y = 0;
        z = 0;
        super.stop();
    }
}