package cofh.thermal.core.util.recipes.machine;

import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nonnull;

import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.SMELTER_CATALYST_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.SMELTER_CATALYST;

public class SmelterCatalyst extends ThermalCatalyst {

    public SmelterCatalyst(Ingredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        super(ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {

        return SMELTER_CATALYST_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {

        return SMELTER_CATALYST.get();
    }

}
