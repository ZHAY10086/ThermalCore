package cofh.thermal.core.util.managers.machine;

import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.recipes.machine.SawmillRecipe;
import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cofh.core.util.helpers.ItemHelper.cloneStack;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.SAWMILL_RECIPE;

public class SawmillRecipeManager extends SingleItemRecipeManager {

    private static final SawmillRecipeManager INSTANCE = new SawmillRecipeManager();
    protected static final int DEFAULT_ENERGY = 2000;

    protected boolean defaultLogRecipes = true;

    public static SawmillRecipeManager instance() {

        return INSTANCE;
    }

    private SawmillRecipeManager() {

        super(DEFAULT_ENERGY, 4, 0);
    }

    public void setDefaultLogRecipes(boolean defaultLogRecipes) {

        this.defaultLogRecipes = defaultLogRecipes;
    }

    @Override
    protected void clear() {

        recipeMap.clear();
        convertedRecipes.clear();
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.byType(SAWMILL_RECIPE.get());
        for (var entry : recipes.entrySet()) {
            addRecipe(entry.getValue().value());
        }

        if (defaultLogRecipes) {
            ThermalCore.LOG.debug("Adding default Log processing recipes to the Sawmill...");
            createConvertedRecipes(recipeManager);
            for (ThermalRecipe recipe : getConvertedRecipes()) {
                addRecipe(recipe);
            }
        }
    }
    // endregion

    // region CONVERSION
    protected List<SawmillRecipe> convertedRecipes = new ArrayList<>();

    public List<SawmillRecipe> getConvertedRecipes() {

        return convertedRecipes;
    }

    protected void createConvertedRecipes(RecipeManager recipeManager) {

        for (var recipe : recipeManager.byType(RecipeType.CRAFTING).values()) {
            if (recipe.value() instanceof ShapelessRecipe shapeless && recipe.value().getResultItem(RegistryAccess.EMPTY).is(ItemTags.PLANKS)) {
                createConvertedRecipe(shapeless);
            }
        }
    }

    protected boolean createConvertedRecipe(ShapelessRecipe recipe) {

        if (recipe.isSpecial() || recipe.getIngredients().size() > 1) {
            return false;
        }
        Ingredient log = recipe.getIngredients().get(0);
        ItemStack plank = recipe.getResultItem(RegistryAccess.EMPTY);

        for (ItemStack logStack : log.getItems()) {
            if (!logStack.is(ItemTags.LOGS) || validRecipe(logStack)) {
                return false;
            }
        }
        if (!plank.is(ItemTags.PLANKS)) {
            return false;
        }
        convertedRecipes.add(convert(log, plank));
        return true;
    }

    protected SawmillRecipe convert(Ingredient log, ItemStack planks) {

        return new SawmillRecipe(getDefaultEnergy() / 2, 0.15F,
                Collections.singletonList(log),
                Collections.emptyList(), // no fluid input
                Arrays.asList(cloneStack(planks, (int) (planks.getCount() * 1.5F)), new ItemStack(ITEMS.get("sawdust"))),
                Arrays.asList(-1.0F, 1.25F), // output chances
                Collections.emptyList()); // no fluid output
    }
    // endregion
}
