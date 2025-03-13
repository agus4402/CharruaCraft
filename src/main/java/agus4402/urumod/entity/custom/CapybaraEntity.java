package agus4402.urumod.entity.custom;

import agus4402.core.entity.VariantGeoEntity;
import agus4402.urumod.config.ModConfig;
import agus4402.urumod.entity.ModEntities;
import agus4402.urumod.entity.variant.CapybaraVariant;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CapybaraEntity extends Animal implements VariantGeoEntity<CapybaraVariant> {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT;

    static {
        DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.INT);
    }

    public CapybaraEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2003021313f)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25));
        if (ModConfig.COMMON.MOBS.capybaraLooksIntoPlayer.get()) this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel,
                                        DifficultyInstance pDifficulty,
                                        MobSpawnType pReason,
                                        @Nullable SpawnGroupData pSpawnData,
                                        @Nullable CompoundTag pDataTag) {
        RandomSource randomsource = pLevel.getRandom();
        CapybaraVariant v = Util.getRandom(CapybaraVariant.values(), randomsource);
        setVariant(v);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        CapybaraEntity capybara = ModEntities.CAPYBARA.get().create(serverLevel);
        if (capybara != null) {
            CapybaraVariant v = Util.getRandom(CapybaraVariant.values(), this.random);
            capybara.setVariant(v);
        }
        return capybara;
    }

    @Override
    public void setVariant(CapybaraVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId());
    }

    public CapybaraVariant getVariant() {
        return CapybaraVariant.byId(getTypeVariant());
    }

    public int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, 0, this::predicate));
    }

    private PlayState predicate(AnimationState<CapybaraEntity> capybaraEntityAnimationState) {
        if (capybaraEntityAnimationState.isMoving()) {
            return capybaraEntityAnimationState.setAndContinue(RawAnimation.begin().thenLoop("animation.capybara.walk"));
        }
        return capybaraEntityAnimationState.setAndContinue(RawAnimation.begin().thenLoop("animation.capybara.idle"));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
