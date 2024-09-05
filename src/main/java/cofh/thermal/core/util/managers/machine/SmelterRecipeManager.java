package cofh.thermal.core.util.managers.machine;

import cofh.lib.api.fluid.IFluidStackHolder;
import cofh.lib.api.inventory.IItemStackHolder;
import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.recipes.machine.SmelterRecipe;
import cofh.thermal.lib.util.managers.AbstractManager;
import cofh.thermal.lib.util.managers.CatalyzedRecipeManager;
import cofh.thermal.lib.util.managers.IRecipeManager;
import cofh.thermal.lib.util.recipes.IThermalInventory;
import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import cofh.thermal.lib.util.recipes.internal.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.*;

import static cofh.core.util.helpers.ItemHelper.cloneStack;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.*;
import static java.util.Arrays.asList;

public class SmelterRecipeManager extends AbstractManager implements IRecipeManager, CatalyzedRecipeManager {

    private static final SmelterRecipeManager INSTANCE = new SmelterRecipeManager();
    protected static final int DEFAULT_ENERGY = 3200;

    protected boolean defaultFurnaceRecipes = true;

    protected Map<SmelterMapWrapper, IMachineRecipe> recipeMap = new Object2ObjectOpenHashMap<>();
    protected Map<ComparableItemStack, IRecipeCatalyst> catalystMap = new Object2ObjectOpenHashMap<>();
    protected Set<ComparableItemStack> validItems = new ObjectOpenHashSet<>();

    protected int maxInputItems;
    protected int maxOutputItems;
    protected int maxOutputFluids;

    public static SmelterRecipeManager instance() {

        return INSTANCE;
    }

    private SmelterRecipeManager() {

        super(DEFAULT_ENERGY);
        this.maxInputItems = 3;
        this.maxOutputItems = 4;
        this.maxOutputFluids = 0;
    }

    public void setDefaultFurnaceRecipes(boolean defaultFurnaceRecipes) {

        this.defaultFurnaceRecipes = defaultFurnaceRecipes;
    }

    public void addRecipe(ThermalRecipe recipe, BaseMachineRecipe.RecipeType type) {

        switch (recipe.getInputItems().size()) {
            case 1 -> {
                for (ItemStack firstInput : recipe.getInputItems().get(0).getItems()) {
                    addRecipe(recipe.getEnergy(), recipe.getXp(), Collections.singletonList(firstInput), Collections.emptyList(), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids(), type);
                }
            }
            case 2 -> {
                for (ItemStack firstInput : recipe.getInputItems().get(0).getItems()) {
                    for (ItemStack secondInput : recipe.getInputItems().get(1).getItems()) {
                        addRecipe(recipe.getEnergy(), recipe.getXp(), asList(firstInput, secondInput), Collections.emptyList(), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids(), type);
                    }
                }
            }
            case 3 -> {
                for (ItemStack firstInput : recipe.getInputItems().get(0).getItems()) {
                    for (ItemStack secondInput : recipe.getInputItems().get(1).getItems()) {
                        for (ItemStack thirdInput : recipe.getInputItems().get(2).getItems()) {
                            addRecipe(recipe.getEnergy(), recipe.getXp(), asList(firstInput, secondInput, thirdInput), Collections.emptyList(), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids(), type);
                        }
                    }
                }
            }
            default -> {
            }
        }
    }

    public boolean validItem(ItemStack item) {

        return validItems.contains(makeNBTComparable(item)) || validItems.contains(makeComparable(item));
    }

    protected void clear() {

        recipeMap.clear();
        catalystMap.clear();
        validItems.clear();
        convertedRecipes.clear();
    }

    // region RECIPES
    protected IMachineRecipe getRecipe(List<? extends IItemStackHolder> inputSlots, List<? extends IFluidStackHolder> inputTanks) {

        if (inputSlots.isEmpty()) {
            return null;
        }
        List<ComparableItemStack> convertedItems = new ArrayList<>(maxInputItems);
        for (int i = 0; i < maxInputItems; ++i) {
            if (!inputSlots.get(i).isEmpty()) {
                ComparableItemStack compStack = makeNBTComparable(inputSlots.get(i).getItemStack());
                convertedItems.add(compStack);
            }
        }
        if (convertedItems.isEmpty()) {
            return null;
        }
        IMachineRecipe ret = recipeMap.get(new SmelterMapWrapper(convertedItems));

        if (ret == null) {
            convertedItems.clear();
            for (int i = 0; i < maxInputItems; ++i) {
                if (!inputSlots.get(i).isEmpty()) {
                    ComparableItemStack compStack = makeComparable(inputSlots.get(i).getItemStack());
                    convertedItems.add(compStack);
                }
            }
            if (convertedItems.isEmpty()) {
                return null;
            }
            ret = recipeMap.get(new SmelterMapWrapper(convertedItems));
        }
        return ret;
    }

    protected IMachineRecipe addRecipe(int energy, float experience, List<ItemStack> inputItems, List<FluidStack> inputFluids, List<ItemStack> outputItems, List<Float> chance, List<FluidStack> outputFluids, BaseMachineRecipe.RecipeType type) {

        if (inputItems.isEmpty() || outputItems.isEmpty() || outputItems.size() > maxOutputItems || outputFluids.size() > maxOutputFluids || energy <= 0) {
            return null;
        }
        for (ItemStack stack : inputItems) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        for (ItemStack stack : outputItems) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        List<ComparableItemStack> convertedItems = new ArrayList<>(inputItems.size());
        for (ItemStack stack : inputItems) {
            if (!inputItems.isEmpty()) {
                ComparableItemStack compStack = type == BaseMachineRecipe.RecipeType.DISENCHANT ? makeComparable(stack) : makeNBTComparable(stack);
                validItems.add(compStack);
                convertedItems.add(compStack);
            }
        }
        energy = (int) (energy * getDefaultScale());

        IMachineRecipe recipe;
        if (type == BaseMachineRecipe.RecipeType.DISENCHANT) {
            recipe = new DisenchantMachineRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        } else {
            recipe = new InternalSmelterRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        }
        recipeMap.put(new SmelterMapWrapper(convertedItems), recipe);
        return recipe;
    }
    // endregion

    @Override
    public List<ItemStack> getCatalysts() {

        List<ItemStack> ret = new ArrayList<>(catalystMap.size());
        catalystMap.keySet().forEach(stack -> ret.add(stack.toItemStack()));
        return ret;
    }

    // region CATALYSTS
    @Override
    public IRecipeCatalyst getCatalyst(IItemStackHolder input) {

        return catalystMap.get(makeNBTComparable(input.getItemStack()));
    }

    @Override
    public IRecipeCatalyst getCatalyst(ItemStack input) {

        return catalystMap.get(makeNBTComparable(input));
    }

    public void addCatalyst(ThermalCatalyst catalyst) {

        for (ItemStack ingredient : catalyst.getIngredient().getItems()) {
            addCatalyst(ingredient, catalyst.getPrimaryMod(), catalyst.getSecondaryMod(), catalyst.getEnergyMod(), catalyst.getMinChance(), catalyst.getUseChance());
        }
    }

    public IRecipeCatalyst addCatalyst(ItemStack input, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        if (input == null || input.isEmpty()) {
            return null;
        }
        BaseMachineCatalyst catalyst = new BaseMachineCatalyst(primaryMod, secondaryMod, energyMod, minChance, useChance);
        catalystMap.put(makeNBTComparable(input), catalyst);
        return catalyst;
    }

    public boolean validCatalyst(ItemStack input) {

        return getCatalyst(input) != null;
    }

    public IRecipeCatalyst removeCatalyst(ItemStack input) {

        return catalystMap.remove(makeNBTComparable(input));
    }
    // endregion

    // region IRecipeManager
    @Override
    public IMachineRecipe getRecipe(IThermalInventory inventory) {

        return getRecipe(inventory.inputSlots(), inventory.inputTanks());
    }

    @Override
    public List<IMachineRecipe> getRecipeList() {

        return new ArrayList<>(recipeMap.values());
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.byType(SMELTER_RECIPE.get());
        for (var entry : recipes.entrySet()) {
            addRecipe(entry.getValue().value(), BaseMachineRecipe.RecipeType.CATALYZED);
        }
        var recycle = recipeManager.byType(SMELTER_RECYCLE_RECIPE.get());
        for (var entry : recycle.entrySet()) {
            addRecipe(entry.getValue().value(), BaseMachineRecipe.RecipeType.DISENCHANT);
        }
        var catalysts = recipeManager.byType(SMELTER_CATALYST.get());
        for (var entry : catalysts.entrySet()) {
            addCatalyst(entry.getValue().value());
        }

        if (defaultFurnaceRecipes) {
            ThermalCore.LOG.debug("Adding default Furnace-Based processing recipes to the Induction Smelter...");
            createConvertedRecipes(recipeManager);
            for (var recipe : getConvertedRecipes()) {
                addRecipe(recipe.value(), BaseMachineRecipe.RecipeType.CATALYZED);
            }
        }
    }
    // endregion

    // region WRAPPER CLASS
    protected static class SmelterMapWrapper {

        Set<Integer> itemHashes;
        int hashCode;

        SmelterMapWrapper(List<ComparableItemStack> itemStacks) {

            this.itemHashes = new ObjectOpenHashSet<>(itemStacks.size());
            for (ComparableItemStack itemStack : itemStacks) {
                if (itemStack.hashCode() != 0) {
                    this.itemHashes.add(itemStack.hashCode());
                    hashCode += itemStack.hashCode();
                }
            }
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SmelterMapWrapper that = (SmelterMapWrapper) o;
            return itemHashes.size() == that.itemHashes.size() && itemHashes.containsAll(that.itemHashes);
        }

        @Override
        public int hashCode() {

            return hashCode;
        }

    }
    // endregion

    // region CATALYZED RECIPE
    protected static class InternalSmelterRecipe extends CatalyzedMachineRecipe {

        public InternalSmelterRecipe(int energy, float experience, @Nullable List<ItemStack> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable List<ItemStack> outputItems, @Nullable List<Float> chance, @Nullable List<FluidStack> outputFluids) {

            super(3, energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        }

        @Override
        public IRecipeCatalyst getCatalyst(ItemStack input) {

            return instance().getCatalyst(input);
        }

    }
    // endregion

    // region CONVERSION
    protected List<RecipeHolder<SmelterRecipe>> convertedRecipes = new ArrayList<>();

    public List<RecipeHolder<SmelterRecipe>> getConvertedRecipes() {

        return convertedRecipes;
    }

    protected void createConvertedRecipes(RecipeManager recipeManager) {

        for (var recipe : recipeManager.byType(RecipeType.BLASTING).values()) {
            convertRecipe(recipe.value());
        }
    }

    protected boolean convertRecipe(AbstractCookingRecipe recipe) {

        if (recipe.isSpecial() || recipe.result.isEmpty()) {
            return false;
        }
        Ingredient input = recipe.getIngredients().get(0);
        ItemStack ingot = recipe.result;

        if (!ingot.is(Tags.Items.INGOTS)) {
            return false;
        }

        for (ItemStack inputStack : input.getItems()) {
            if (validItem(inputStack)) {
                return false;
            }
            if (inputStack.is(Tags.Items.DUSTS)) {
                convertedRecipes.add(convertDust(input, ingot));
                return true;
            }
            if (inputStack.is(Tags.Items.ORES)) {
                convertedRecipes.add(convertOre(input, ingot));
                return true;
            }
            if (inputStack.is(Tags.Items.RAW_MATERIALS)) {
                convertedRecipes.add(convertRaw(input, ingot));
                return true;
            }
        }
        return false;
    }

    protected RecipeHolder<SmelterRecipe> convertDust(Ingredient input, ItemStack ingot) {

        return new RecipeHolder<>(new ResourceLocation(ID_THERMAL, "smelter_dust_" + input.hashCode()),
                new SmelterRecipe(getDefaultEnergy() / 2, 0.0F,
                        Collections.singletonList(input),
                        Collections.emptyList(), // no fluid input
                        Collections.singletonList(cloneStack(ingot, 1)),
                        List.of(-1.0F), // output chances
                        Collections.emptyList())); // no fluid output
    }

    protected RecipeHolder<SmelterRecipe> convertOre(Ingredient input, ItemStack ingot) {

        return new RecipeHolder<>(new ResourceLocation(ID_THERMAL, "smelter_ore_" + input.hashCode()),
                new SmelterRecipe(getDefaultEnergy(), 0.5F,
                        Collections.singletonList(input),
                        Collections.emptyList(), // no fluid input
                        Arrays.asList(cloneStack(ingot, 1), new ItemStack(ITEMS.get("rich_slag"))),
                        Arrays.asList(1.0F, 0.2F), // output chances
                        Collections.emptyList())); // no fluid output
    }

    protected RecipeHolder<SmelterRecipe> convertRaw(Ingredient input, ItemStack ingot) {

        return new RecipeHolder<>(new ResourceLocation(ID_THERMAL, "smelter_raw_" + input.hashCode()),
                new SmelterRecipe(getDefaultEnergy(), 0.5F,
                        Collections.singletonList(input),
                        Collections.emptyList(), // no fluid input
                        Collections.singletonList(cloneStack(ingot, 1)),
                        List.of(-1.5F), // output chances
                        Collections.emptyList())); // no fluid output
    }
    // endregion
}
