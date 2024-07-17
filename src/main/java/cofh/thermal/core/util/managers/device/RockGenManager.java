package cofh.thermal.core.util.managers.device;

import cofh.thermal.core.util.recipes.device.RockGenMapping;
import cofh.thermal.lib.util.managers.AbstractManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.IdentityHashMap;
import java.util.Map;

import static cofh.thermal.core.init.registries.TCoreRecipeTypes.ROCK_GEN_MAPPING;

public class RockGenManager extends AbstractManager {

    private static final RockGenManager INSTANCE = new RockGenManager();
    protected static final RockGenRecipe DEFAULT_RECIPE = new RockGenRecipe(instance().getDefaultEnergy(), Blocks.AIR, Blocks.AIR, ItemStack.EMPTY);

    protected Map<Block, Map<Block, RockGenRecipe>> recipeMap = new IdentityHashMap<>();
    protected Map<Block, RockGenRecipe> defaultRecipeMap = new IdentityHashMap<>();

    protected RockGenManager() {

        super(20);
    }

    public static RockGenManager instance() {

        return INSTANCE;
    }

    protected void clear() {

        recipeMap.clear();
        defaultRecipeMap.clear();
    }

    // region MAPPINGS
    public RockGenRecipe getResult(Block below, Block[] adjacent) {

        Map<Block, RockGenRecipe> baseMap = recipeMap.get(below);

        if (baseMap != null) {
            for (Block adj : adjacent) {
                if (baseMap.containsKey(adj)) {
                    return baseMap.getOrDefault(adj, DEFAULT_RECIPE);
                }
            }
        }
        baseMap = defaultRecipeMap;
        for (Block adj : adjacent) {
            if (baseMap.containsKey(adj)) {
                return baseMap.getOrDefault(adj, DEFAULT_RECIPE);
            }
        }
        return DEFAULT_RECIPE;
    }

    public boolean addMapping(int time, Block below, Block adjacent, ItemStack stack) {

        if (adjacent == Blocks.AIR || stack.isEmpty()) {
            return false;
        }
        if (below == Blocks.AIR) {
            defaultRecipeMap.put(adjacent, new RockGenRecipe(time, below, adjacent, stack));
            return true;
        }
        if (!recipeMap.containsKey(below)) {
            recipeMap.put(below, new Object2ObjectOpenHashMap<>());
        }
        recipeMap.get(below).put(adjacent, new RockGenRecipe(time, below, adjacent, stack));
        return true;
    }

    public void addMapping(RockGenMapping mapping) {

        addMapping(mapping.getTime(), mapping.getBelow(), mapping.getAdjacent(), mapping.getResult());
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var mappings = recipeManager.byType(ROCK_GEN_MAPPING.get());
        for (var entry : mappings.entrySet()) {
            addMapping(entry.getValue().value());
        }
    }
    // endregion

    // region RECIPE
    public static class RockGenRecipe {

        protected final int time;
        protected final Block below;
        protected final Block adjacent;
        protected final ItemStack result;

        public RockGenRecipe(int time, Block below, Block adjacent, ItemStack result) {

            this.time = time;
            this.below = below;
            this.adjacent = adjacent;
            this.result = result;
        }

        public int getTime() {

            return time;

        }

        public Block getAdjacent() {

            return adjacent;
        }

        public Block getBelow() {

            return below;
        }

        public ItemStack getResult() {

            return result;
        }

    }
    // endregion
}
