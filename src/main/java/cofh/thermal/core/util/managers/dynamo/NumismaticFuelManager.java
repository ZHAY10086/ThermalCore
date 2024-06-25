package cofh.thermal.core.util.managers.dynamo;

import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import static cofh.thermal.core.init.registries.TCoreRecipeTypes.NUMISMATIC_FUEL;

public class NumismaticFuelManager extends SingleItemFuelManager {

    private static final NumismaticFuelManager INSTANCE = new NumismaticFuelManager();
    protected static final int DEFAULT_ENERGY = 16000;

    public static NumismaticFuelManager instance() {

        return INSTANCE;
    }

    private NumismaticFuelManager() {

        super(DEFAULT_ENERGY);
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.byType(NUMISMATIC_FUEL.get());
        for (var entry : recipes.entrySet()) {
            addFuel(entry.getValue());
        }
    }
    // endregion
}
