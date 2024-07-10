package cofh.thermal.core.init.registries;

import cofh.thermal.core.common.block.entity.ChargeBenchBlockEntity;
import cofh.thermal.core.common.block.entity.TinkerBenchBlockEntity;
import cofh.thermal.core.common.block.entity.device.*;
import cofh.thermal.core.common.block.entity.storage.EnergyCellBlockEntity;
import cofh.thermal.core.common.block.entity.storage.FluidCellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.RegistryObject;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.BLOCK_ENTITIES;
import static cofh.thermal.lib.util.ThermalIDs.*;

public class TCoreBlockEntities {

    private TCoreBlockEntities() {

    }

    public static void register() {

        // TILE_ENTITIES.register(ID_CHUNK_LOADER, () -> TileEntityType.Builder.of(DeviceChunkLoaderTile::new, CHUNK_LOADER_BLOCK).build(null));

        // TILE_ENTITIES.register(ID_ITEM_CELL, () -> TileEntityType.Builder.of(ItemCellTile::new, ITEM_CELL_BLOCK).build(null));
    }

    public static final RegistryObject<BlockEntityType<?>> DEVICE_HIVE_EXTRACTOR_TILE = BLOCK_ENTITIES.register(ID_DEVICE_HIVE_EXTRACTOR, () -> BlockEntityType.Builder.of(DeviceHiveExtractorBlockEntity::new, BLOCKS.get(ID_DEVICE_HIVE_EXTRACTOR)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_TREE_EXTRACTOR_TILE = BLOCK_ENTITIES.register(ID_DEVICE_TREE_EXTRACTOR, () -> BlockEntityType.Builder.of(DeviceTreeExtractorBlockEntity::new, BLOCKS.get(ID_DEVICE_TREE_EXTRACTOR)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_FISHER_TILE = BLOCK_ENTITIES.register(ID_DEVICE_FISHER, () -> BlockEntityType.Builder.of(DeviceFisherBlockEntity::new, BLOCKS.get(ID_DEVICE_FISHER)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_COMPOSTER_TILE = BLOCK_ENTITIES.register(ID_DEVICE_COMPOSTER, () -> BlockEntityType.Builder.of(DeviceComposterBlockEntity::new, BLOCKS.get(ID_DEVICE_COMPOSTER)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_SOIL_INFUSER_TILE = BLOCK_ENTITIES.register(ID_DEVICE_SOIL_INFUSER, () -> BlockEntityType.Builder.of(DeviceSoilInfuserBlockEntity::new, BLOCKS.get(ID_DEVICE_SOIL_INFUSER)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_WATER_GEN_TILE = BLOCK_ENTITIES.register(ID_DEVICE_WATER_GEN, () -> BlockEntityType.Builder.of(DeviceWaterGenBlockEntity::new, BLOCKS.get(ID_DEVICE_WATER_GEN)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_ROCK_GEN_TILE = BLOCK_ENTITIES.register(ID_DEVICE_ROCK_GEN, () -> BlockEntityType.Builder.of(DeviceRockGenBlockEntity::new, BLOCKS.get(ID_DEVICE_ROCK_GEN)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_COLLECTOR_TILE = BLOCK_ENTITIES.register(ID_DEVICE_COLLECTOR, () -> BlockEntityType.Builder.of(DeviceCollectorBlockEntity::new, BLOCKS.get(ID_DEVICE_COLLECTOR)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_XP_CONDENSER_TILE = BLOCK_ENTITIES.register(ID_DEVICE_XP_CONDENSER, () -> BlockEntityType.Builder.of(DeviceXpCondenserBlockEntity::new, BLOCKS.get(ID_DEVICE_XP_CONDENSER)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_NULLIFIER_TILE = BLOCK_ENTITIES.register(ID_DEVICE_NULLIFIER, () -> BlockEntityType.Builder.of(DeviceNullifierBlockEntity::new, BLOCKS.get(ID_DEVICE_NULLIFIER)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_POTION_DIFFUSER_TILE = BLOCK_ENTITIES.register(ID_DEVICE_POTION_DIFFUSER, () -> BlockEntityType.Builder.of(DevicePotionDiffuserBlockEntity::new, BLOCKS.get(ID_DEVICE_POTION_DIFFUSER)).build(null));

    public static final RegistryObject<BlockEntityType<?>> TINKER_BENCH_TILE = BLOCK_ENTITIES.register(ID_TINKER_BENCH, () -> BlockEntityType.Builder.of(TinkerBenchBlockEntity::new, BLOCKS.get(ID_TINKER_BENCH)).build(null));
    public static final RegistryObject<BlockEntityType<?>> CHARGE_BENCH_TILE = BLOCK_ENTITIES.register(ID_CHARGE_BENCH, () -> BlockEntityType.Builder.of(ChargeBenchBlockEntity::new, BLOCKS.get(ID_CHARGE_BENCH)).build(null));

    public static final RegistryObject<BlockEntityType<?>> ENERGY_CELL_TILE = BLOCK_ENTITIES.register(ID_ENERGY_CELL, () -> BlockEntityType.Builder.of(EnergyCellBlockEntity::new, BLOCKS.get(ID_ENERGY_CELL)).build(null));
    public static final RegistryObject<BlockEntityType<?>> FLUID_CELL_TILE = BLOCK_ENTITIES.register(ID_FLUID_CELL, () -> BlockEntityType.Builder.of(FluidCellBlockEntity::new, BLOCKS.get(ID_FLUID_CELL)).build(null));

}
