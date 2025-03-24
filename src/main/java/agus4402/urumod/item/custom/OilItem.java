package agus4402.urumod.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class OilItem extends Item {
    private final int burnTime;

    public OilItem(Properties pProperties, int pBurnTime) {
        super(pProperties);
        this.burnTime = pBurnTime;
    }

    public int getBurnTime() {
        return burnTime;
    }
}
