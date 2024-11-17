package cofh.thermal.core.compat.patchouli;

import cofh.thermal.core.util.recipes.machine.SmelterRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class SmelterProcessor implements IComponentProcessor {

    private SmelterRecipe recipe;

    @Override
    public void setup(Level level, IVariableProvider variables) {

        if (!variables.has("recipe"))
            return;
        ResourceLocation recipeId = new ResourceLocation(variables.get("recipe").asString());
        Optional<? extends RecipeHolder<?>> recipe = level.getRecipeManager().byKey(recipeId);
        if (recipe.isPresent() && recipe.get().value() instanceof SmelterRecipe) {
            this.recipe = (SmelterRecipe) recipe.get().value();
        } else {
            LogManager.getLogger().warn("Thermalpedia missing the smelter recipe: " + recipeId);
        }
    }

    @Override
    public IVariable process(Level level, String key) {

        if (recipe == null)
            return null;
        if (key.equals("out"))
            return IVariable.from(recipe.getOutputItems().get(0));
        if (key.startsWith("in")) {
            int index = Integer.parseInt(key.substring(key.length() - 1)) - 1;
            if (recipe.getInputItems().size() <= index)
                return null;
            return IVariable.wrapList(Arrays.stream(recipe.getInputItems().get(index).getItems()).map(IVariable::from).collect(Collectors.toList()));
        }
        return null;
    }

}
