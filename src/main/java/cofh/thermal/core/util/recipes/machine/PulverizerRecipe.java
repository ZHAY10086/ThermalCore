package cofh.thermal.core.util.recipes.machine;

import cofh.lib.common.fluid.FluidIngredient;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.managers.machine.PulverizerRecipeManager;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.PULVERIZER_RECIPE_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.PULVERIZER_RECIPE;

public class PulverizerRecipe extends ThermalRecipe {

    public PulverizerRecipe(int energy, float experience, List<Ingredient> inputItems, List<FluidIngredient> inputFluids, List<ItemStack> outputItems, List<Float> outputItemChances, List<FluidStack> outputFluids) {

        super(energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);

        if (this.energy <= 0) {
            int defaultEnergy = PulverizerRecipeManager.instance().getDefaultEnergy();
            ThermalCore.LOG.warn("Energy value for a Pulverizer recipe was out of allowable range and has been set to a default value of " + defaultEnergy + ".");
            this.energy = defaultEnergy;
        }
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {

        return PULVERIZER_RECIPE_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {

        return PULVERIZER_RECIPE.get();
    }

}
