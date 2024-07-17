package cofh.thermal.core.util.recipes.dynamo;

import cofh.lib.common.fluid.FluidIngredient;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.managers.dynamo.LapidaryFuelManager;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.LAPIDARY_FUEL_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.LAPIDARY_FUEL;

public class LapidaryFuel extends ThermalFuel {

    public LapidaryFuel(int energy, @Nullable List<Ingredient> inputItems, @Nullable List<FluidIngredient> inputFluids) {

        super(energy, inputItems, inputFluids);

        int minEnergy = LapidaryFuelManager.MIN_ENERGY;
        int maxEnergy = LapidaryFuelManager.MAX_ENERGY;

        if (this.energy < minEnergy || this.energy > maxEnergy) {
            ThermalCore.LOG.warn("Energy value for a Lapidary fuel was out of allowable range and has been clamped between + " + minEnergy + " and " + maxEnergy + ".");
            this.energy = MathHelper.clamp(this.energy, minEnergy, maxEnergy);
        }
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {

        return LAPIDARY_FUEL_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {

        return LAPIDARY_FUEL.get();
    }

}
