package agus4402.urumod.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.FurnaceBlock;
import org.jetbrains.annotations.Nullable;

import java.util.Properties;

public class FuelItem extends Item {
    private final int burnTime;

    public FuelItem(Properties pProperties, int pBurnTime) {
        super(pProperties);
        this.burnTime = pBurnTime;
    }

    public int getBurnTime() {
        return burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }
}
