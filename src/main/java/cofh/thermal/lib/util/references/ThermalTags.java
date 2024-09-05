package cofh.thermal.lib.util.references;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class ThermalTags {

    private ThermalTags() {

    }

    public static class Blocks {

        public static final TagKey<Block> TREE_EXTRACTOR_GROUND = thermalTag("devices/tree_extractor_ground");

        public static final TagKey<Block> LOGS_RUBBERWOOD = commonTag("rubberwood_logs");

        public static final TagKey<Block> DUCTS = thermalTag("ducts");
        public static final TagKey<Block> DYNAMOS = thermalTag("dynamos");
        public static final TagKey<Block> MACHINES = thermalTag("machines");
        public static final TagKey<Block> HARDENED_GLASS = thermalTag("glass/hardened");
        public static final TagKey<Block> ROCKWOOL = thermalTag("rockwool");

        public static final TagKey<Block> STORAGE_BLOCKS_APPLE = commonTag("storage_blocks/apple");
        public static final TagKey<Block> STORAGE_BLOCKS_CARROT = commonTag("storage_blocks/carrot");
        public static final TagKey<Block> STORAGE_BLOCKS_POTATO = commonTag("storage_blocks/potato");
        public static final TagKey<Block> STORAGE_BLOCKS_BEETROOT = commonTag("storage_blocks/beetroot");

        // region HELPERS
        private static TagKey<Block> thermalTag(String name) {

            return BlockTags.create(new ResourceLocation(ID_THERMAL, name));
        }

        private static TagKey<Block> commonTag(String name) {

            return BlockTags.create(new ResourceLocation("c", name));
        }
        // endregion
    }

    public static class Items {

        public static final TagKey<Item> LOGS_RUBBERWOOD = commonTag("rubberwood_logs");

        public static final TagKey<Item> BITUMEN = commonTag("bitumen");
        public static final TagKey<Item> COAL_COKE = commonTag("coal_coke");
        public static final TagKey<Item> ROSIN = commonTag("rosin");
        public static final TagKey<Item> SAWDUST = commonTag("sawdust");
        public static final TagKey<Item> SLAG = commonTag("slag");
        public static final TagKey<Item> TAR = commonTag("tar");

        public static final TagKey<Item> MACHINE_DIES = thermalTag("crafting/dies");
        public static final TagKey<Item> MACHINE_CASTS = thermalTag("crafting/casts");

        public static final TagKey<Item> DUCTS = thermalTag("ducts");
        public static final TagKey<Item> DYNAMOS = thermalTag("dynamos");
        public static final TagKey<Item> MACHINES = thermalTag("machines");
        public static final TagKey<Item> HARDENED_GLASS = thermalTag("glass/hardened");
        public static final TagKey<Item> ROCKWOOL = thermalTag("rockwool");

        public static final TagKey<Item> STORAGE_BLOCKS_APPLE = commonTag("storage_blocks/apple");
        public static final TagKey<Item> STORAGE_BLOCKS_CARROT = commonTag("storage_blocks/carrot");
        public static final TagKey<Item> STORAGE_BLOCKS_POTATO = commonTag("storage_blocks/potato");
        public static final TagKey<Item> STORAGE_BLOCKS_BEETROOT = commonTag("storage_blocks/beetroot");

        // region HELPERS
        private static TagKey<Item> thermalTag(String name) {

            return ItemTags.create(new ResourceLocation(ID_THERMAL, name));
        }

        private static TagKey<Item> commonTag(String name) {

            return ItemTags.create(new ResourceLocation("c", name));
        }
        // endregion
    }

    public static class Fluids {

        public static final TagKey<Fluid> REDSTONE = commonTag("redstone");
        public static final TagKey<Fluid> GLOWSTONE = commonTag("glowstone");
        public static final TagKey<Fluid> ENDER = commonTag("ender");

        public static final TagKey<Fluid> LATEX = commonTag("latex");

        public static final TagKey<Fluid> CREOSOTE = commonTag("creosote");
        public static final TagKey<Fluid> CRUDE_OIL = commonTag("crude_oil");

        // region HELPERS
        private static TagKey<Fluid> thermalTag(String name) {

            return FluidTags.create(new ResourceLocation(ID_THERMAL, name));
        }

        private static TagKey<Fluid> commonTag(String name) {

            return FluidTags.create(new ResourceLocation("c", name));
        }
        // endregion
    }

}
