package agus4402.urumod.block.entity;

import agus4402.urumod.block.ModBlocks;
import agus4402.urumod.block.custom.Pan;
import agus4402.urumod.item.custom.FuelItem;
import agus4402.urumod.recipe.PanRecipe;
import agus4402.urumod.screen.PanMenu;
import agus4402.urumod.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PanBlockEntity extends BlockEntity implements MenuProvider {

    private static final int CONTAINER_SIZE = 3;
    private final ItemStackHandler itemHandler = new ItemStackHandler(CONTAINER_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (level == null || level.isClientSide()) {
                return true; // Validar en el cliente
            }

            if (slot == OUTPUT_SLOT) {
                return false;
            }

            if (slot == INPUT_SLOT) {
                return isValidIngredientForAnyRecipe(stack, 0);
            }

            if (slot == OIL_SLOT) {
                return stack.getItem() instanceof FuelItem;
            }

            return super.isItemValid(slot, stack);
        }
    };

    private boolean isValidIngredientForAnyRecipe(ItemStack stack, int ingredientIndex) {
        List<PanRecipe> allRecipes = level.getRecipeManager().getAllRecipesFor(PanRecipe.Type.INSTANCE);

        for (Recipe<?> recipe : allRecipes) {
            if (recipe instanceof PanRecipe) {
                List<Ingredient> ingredients = ((PanRecipe) recipe).getIngredients();
                if (ingredients.size() > ingredientIndex) {
                    if (ingredients.get(ingredientIndex).test(stack)) {
                        return true;
                    }
                }
            }
        }
        return false; // The stack is not a valid ingredient for any recipe.
    }

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int OIL_SLOT = 2;

    private final ContainerData data;
    private int progress = 0;
    private int totalProgress = 200;
    private int oilBurnTime = 0;
    private int totalOilBurnTime = 0;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public PanBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PAN_BE.get(), pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> PanBlockEntity.this.progress;
                    case 1 -> PanBlockEntity.this.totalProgress;
                    case 2 -> PanBlockEntity.this.oilBurnTime;
                    case 3 -> PanBlockEntity.this.totalOilBurnTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int i1) {
                switch (i) {
                    case 0 -> PanBlockEntity.this.progress = i1;
                    case 1 -> PanBlockEntity.this.totalProgress = i1;
                    case 2 -> PanBlockEntity.this.oilBurnTime = i1;
                    case 3 -> PanBlockEntity.this.totalOilBurnTime = i1;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public ItemStack getRenderStack() {
        if (itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty()) {
            return itemHandler.getStackInSlot(INPUT_SLOT);
        } else {
            return itemHandler.getStackInSlot(OUTPUT_SLOT);
        }
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (lazyItemHandler.isPresent()) {
                return lazyItemHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.urumod.pan");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new PanMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("urumod.pan.progress", progress);
        pTag.putInt("urumod.pan.oilBurnTime", oilBurnTime);
        pTag.putInt("urumod.pan.totalOilBurnTime", totalOilBurnTime);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("urumod.pan.progress");
        oilBurnTime = pTag.getInt("urumod.pan.oilBurnTime");
        totalOilBurnTime = pTag.getInt("urumod.pan.totalOilBurnTime");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        refillOil();
        if (hasRecipe() && hasFuel(blockPos) && hasOil()) {
            playIdleSound(level, blockPos, blockState);
            increaseProgress();
            increaseOilBurnTime();
            Pan pan = (Pan) blockState.getBlock();
            pan.setValue(blockState, level, blockPos, true);
            setChanged(level, blockPos, blockState);
            if (hasProgressFinished()) {
                convertItem();
                playItemConvertedSound(level, blockPos, blockState);
                resetProgress();
            }
        } else {
            Block blockBelow = level.getBlockState(blockPos.below()).getBlock();
            if (blockBelow == ModBlocks.CAMPFIRE_WITH_PAN.get()) {
                Pan pan = (Pan) blockState.getBlock();
                pan.setValue(blockState, level, blockPos, false);
                setChanged(level, blockPos, blockState);
            }
            resetProgress();
        }
    }

    private boolean hasOil() {
        return oilBurnTime > 0;
    }

    private void playItemConvertedSound(Level level, BlockPos blockPos, BlockState blockState) {
        level.playSound(null, blockPos, ModSounds.PAN_ITEM_CONVERTED.get(), SoundSource.BLOCKS, 0.6f, 1.0f);
    }

    private void playIdleSound(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.getGameTime() % 20 == 0) {
            level.playSound(null, blockPos, ModSounds.PAN_IDLE.get(), SoundSource.BLOCKS, 0.6f, 1.0f);
        }
    }

    private boolean hasFuel(BlockPos blockPos) {
        Block blockBelow = level.getBlockState(blockPos.below()).getBlock();
        return blockBelow == ModBlocks.CAMPFIRE_WITH_PAN.get() && level
                .getBlockState(blockPos.below())
                .getValue(BlockStateProperties.LIT);
    }

    private boolean hasRecipe() {
        Optional<PanRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());
        return recipe.isPresent() && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<PanRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(PanRecipe.Type.INSTANCE, inventory, level);
    }

    private void convertItem() {
        Optional<PanRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);
        this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT,
                                        new ItemStack(result.getItem(),
                                                      result.getCount() + this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount()
                                        )
        );
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler
                .getStackInSlot(OUTPUT_SLOT)
                .getMaxStackSize();
    }

    private void resetProgress() {
        progress = 0;
    }

    private void resetOilBurnTime() {
        oilBurnTime = 0;
    }

    private boolean hasProgressFinished() {
        return progress >= totalProgress;
    }

    private void increaseOilBurnTime() {
        if (oilBurnTime > 0) {
            oilBurnTime--;
        } else {
            refillOil();
        }
    }

    private void refillOil() {
        ItemStack fuel = itemHandler.getStackInSlot(OIL_SLOT);
        if (!fuel.isEmpty() && oilBurnTime <= 0) {
            oilBurnTime = ((FuelItem) fuel.getItem()).getBurnTime();
            totalOilBurnTime = oilBurnTime;
            itemHandler.extractItem(OIL_SLOT, 1, false);
        }
    }

    private void increaseProgress() {
        progress++;
    }

}
