package agus4402.core.entity.ai;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;

public class FlyPathNavigator extends GroundPathNavigation {
    private final Mob mob;
    private float yMobOffset = 0;

    public FlyPathNavigator(Mob pMob, Level pLevel, float yMobOffset) {
        super(pMob, pLevel);
        this.mob = pMob;
        this.yMobOffset = yMobOffset;
    }

    public FlyPathNavigator(Mob pMob, Level pLevel) {
        this(pMob, pLevel,0);
    }


/**
         * Increments the tick counter for the FlyPathNavigator.
         * This method is called to update the internal tick counter,
         * which can be used to track the passage of time or the number of updates.
         */
        public void tick(){
            ++this.tick;
        }

   /**
     * Moves the mob to the specified coordinates with a given speed.
     *
     * @param x The target x-coordinate to move towards.
     * @param y The target y-coordinate to move towards.
     * @param z The target z-coordinate to move towards.
     * @param speedIn The speed at which the mob should move.
     * @return Always returns true, indicating the move command was successfully issued.
     */
    public boolean moveTo(double x, double y, double z, double speedIn) {
        mob.getMoveControl().setWantedPosition(x, y, z, speedIn);
        return true;
    }

/**
     * Moves the mob to the specified entity's position with a given speed.
     *
     * @param entityIn The target entity to move towards.
     * @param speedIn The speed at which the mob should move.
     * @return Always returns true, indicating the move command was successfully issued.
     */
    public boolean moveTo(Entity entityIn, double speedIn) {
        mob.getMoveControl().setWantedPosition(entityIn.getX(), entityIn.getY() + yMobOffset, entityIn.getZ(), speedIn);
        return true;
    }
}
