package cofh.thermal.core.util.recipes.device;

import cofh.lib.common.block.BlockIngredient;
import cofh.lib.util.Utils;
import cofh.lib.util.recipes.SerializableRecipe;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;
import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.TREE_EXTRACTOR_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.TREE_EXTRACTOR_MAPPING;

public class TreeExtractorMapping extends SerializableRecipe {

    protected final Block sapling;
    protected final BlockIngredient trunk;
    protected final BlockIngredient leaves;
    protected final FluidStack fluid;
    protected final int minHeight;
    protected final int maxHeight;
    protected final int minLeaves;
    protected final int maxLeaves;

    public TreeExtractorMapping(ResourceLocation recipeId, BlockIngredient trunk, BlockIngredient leaves, Block sapling, FluidStack fluid, int minHeight, int maxHeight, int minLeaves, int maxLeaves) {

        super(recipeId);

        this.trunk = trunk;
        this.leaves = leaves;
        this.sapling = sapling;
        this.fluid = fluid;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minLeaves = minLeaves;
        this.maxLeaves = maxLeaves;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return TREE_EXTRACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {

        return TREE_EXTRACTOR_MAPPING.get();
    }

    // region GETTERS
    public BlockIngredient getTrunk() {

        return trunk;
    }

    public BlockIngredient getLeaves() {

        return leaves;
    }

    public Block getSapling() {

        return sapling;
    }

    public FluidStack getFluid() {

        return fluid;
    }

    public int getMinLeaves() {

        return minLeaves;
    }

    public int getMaxLeaves() {

        return maxLeaves;
    }

    public int getMinHeight() {

        return minHeight;
    }

    public int getMaxHeight() {

        return maxHeight;
    }
    // endregion

    // region SERIALIZER
    public static class Serializer implements RecipeSerializer<TreeExtractorMapping> {

        @Override
        public TreeExtractorMapping fromJson(ResourceLocation recipeId, JsonObject json) {

            BlockIngredient logs = BlockIngredient.EMPTY;
            BlockIngredient leaves = BlockIngredient.EMPTY;
            Block sapling = Blocks.AIR;
            FluidStack fluid = FluidStack.EMPTY;
            int minLeaves = 3;
            int maxLeaves = 3;
            int minHeight = 3;
            int maxHeight = 3;

            if (json.has(TRUNK)) {
                logs = getAsBlockIngredient(json, TRUNK);
            }

            if (json.has(LEAF)) {
                leaves = getAsBlockIngredient(json, LEAF);
            } else if (json.has(LEAVES)) {
                leaves = getAsBlockIngredient(json, LEAVES);
            }

            if (json.has(SAPLING)) {
                sapling = parseBlock(json.get(SAPLING));
            }

            if (json.has(RESULT)) {
                fluid = parseFluidStack(json.get(RESULT));
            } else if (json.has(FLUID)) {
                fluid = parseFluidStack(json.get(FLUID));
            }

            if (json.has(MIN_HEIGHT)) {
                minHeight = json.get(MIN_HEIGHT).getAsInt();
            }
            if (json.has(MAX_HEIGHT)) {
                maxHeight = json.get(MAX_HEIGHT).getAsInt();
            }
            if (json.has(MIN_LEAVES)) {
                minLeaves = json.get(MIN_LEAVES).getAsInt();
            }
            if (json.has(MAX_LEAVES)) {
                maxLeaves = json.get(MAX_LEAVES).getAsInt();
            }
            return new TreeExtractorMapping(recipeId, logs, leaves, sapling, fluid, minHeight, Math.max(maxHeight, minHeight), minLeaves, Math.max(maxLeaves, minLeaves));
        }

        @Nullable
        @Override
        public TreeExtractorMapping fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

            BlockIngredient logs = BlockIngredient.fromNetwork(buffer);
            BlockIngredient leaves = BlockIngredient.fromNetwork(buffer);
            Block sapling = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
            FluidStack fluid = buffer.readFluidStack();
            int minHeight = buffer.readInt();
            int maxHeight = buffer.readInt();
            int minLeaves = buffer.readInt();
            int maxLeaves = buffer.readInt();

            return new TreeExtractorMapping(recipeId, logs, leaves, sapling, fluid, minHeight, maxHeight, minLeaves, maxLeaves);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TreeExtractorMapping recipe) {

            recipe.trunk.toNetwork(buffer);
            recipe.leaves.toNetwork(buffer);
            buffer.writeResourceLocation(Utils.getRegistryName(recipe.sapling));
            buffer.writeFluidStack(recipe.fluid);
            buffer.writeInt(recipe.minHeight);
            buffer.writeInt(recipe.maxHeight);
            buffer.writeInt(recipe.minLeaves);
            buffer.writeInt(recipe.maxLeaves);
        }

    }
    // endregion
}
