package cofh.thermal.core;

import cofh.core.client.event.CoreClientEvents;
import cofh.core.client.renderer.entity.TNTMinecartRendererCoFH;
import cofh.core.common.capability.CapabilityRedstoneFlux;
import cofh.core.common.config.ConfigManager;
import cofh.core.common.config.world.FeatureConfig;
import cofh.core.common.entity.AbstractGrenade;
import cofh.core.common.entity.AbstractTNTMinecart;
import cofh.lib.client.renderer.entity.TntRendererCoFH;
import cofh.lib.common.entity.PrimedTntCoFH;
import cofh.lib.util.DeferredRegisterCoFH;
import cofh.thermal.core.client.gui.ChargeBenchScreen;
import cofh.thermal.core.client.gui.TinkerBenchScreen;
import cofh.thermal.core.client.gui.device.*;
import cofh.thermal.core.client.gui.storage.EnergyCellScreen;
import cofh.thermal.core.client.gui.storage.FluidCellScreen;
import cofh.thermal.core.client.gui.storage.SatchelScreen;
import cofh.thermal.core.client.renderer.entity.*;
import cofh.thermal.core.client.renderer.entity.model.*;
import cofh.thermal.core.common.config.*;
import cofh.thermal.core.common.entity.explosive.DetonateUtils;
import cofh.thermal.core.common.entity.monster.Basalz;
import cofh.thermal.core.common.entity.monster.Blitz;
import cofh.thermal.core.common.entity.monster.Blizz;
import cofh.thermal.core.common.fluid.RedstoneFluid;
import cofh.thermal.core.init.registries.*;
import cofh.thermal.lib.util.ThermalFlags;
import cofh.thermal.lib.util.ThermalProxy;
import cofh.thermal.lib.util.ThermalProxyClient;
import com.mojang.serialization.Codec;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.fml.DistExecutor;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.init.registries.TCoreEntities.*;
import static cofh.thermal.core.init.registries.TCoreMenus.*;
import static cofh.thermal.lib.util.ThermalFlags.*;
import static cofh.thermal.lib.util.ThermalIDs.ID_TINKER_BENCH;

@Mod (ID_THERMAL)
public class ThermalCore {

    public static final Logger LOG = LogManager.getLogger(ID_THERMAL);
    public static final ThermalProxy PROXY = DistExecutor.unsafeRunForDist(() -> ThermalProxyClient::new, () -> ThermalProxy::new);
    public static final ConfigManager CONFIG_MANAGER = new ConfigManager();

    public static final DeferredRegisterCoFH<Block> BLOCKS = DeferredRegisterCoFH.create(ForgeRegistries.BLOCKS, ID_THERMAL);
    public static final DeferredRegisterCoFH<Item> ITEMS = DeferredRegisterCoFH.create(ForgeRegistries.ITEMS, ID_THERMAL);
    public static final DeferredRegisterCoFH<Fluid> FLUIDS = DeferredRegisterCoFH.create(ForgeRegistries.FLUIDS, ID_THERMAL);
    public static final DeferredRegisterCoFH<CreativeModeTab> CREATIVE_TABS = DeferredRegisterCoFH.create(Registries.CREATIVE_MODE_TAB, ID_THERMAL);
    public static final DeferredRegisterCoFH<MobEffect> EFFECTS = DeferredRegisterCoFH.create(ForgeRegistries.MOB_EFFECTS, ID_THERMAL);

    public static final DeferredRegisterCoFH<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ID_THERMAL);
    public static final DeferredRegisterCoFH<MenuType<?>> CONTAINERS = DeferredRegisterCoFH.create(ForgeRegistries.MENU_TYPES, ID_THERMAL);
    public static final DeferredRegisterCoFH<EntityType<?>> ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.ENTITY_TYPES, ID_THERMAL);
    public static final DeferredRegisterCoFH<Codec<? extends IGlobalLootModifier>> LOOT_SERIALIZERS = DeferredRegisterCoFH.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ID_THERMAL);
    public static final DeferredRegisterCoFH<RecipeType<?>> RECIPE_TYPES = DeferredRegisterCoFH.create(ForgeRegistries.RECIPE_TYPES, ID_THERMAL);
    public static final DeferredRegisterCoFH<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegisterCoFH.create(ForgeRegistries.RECIPE_SERIALIZERS, ID_THERMAL);
    public static final DeferredRegisterCoFH<SoundEvent> SOUND_EVENTS = DeferredRegisterCoFH.create(ForgeRegistries.SOUND_EVENTS, ID_THERMAL);

    public static final DeferredRegisterCoFH<Feature<?>> FEATURES = DeferredRegisterCoFH.create(Registries.FEATURE, ID_THERMAL);
    public static final DeferredRegisterCoFH<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegisterCoFH.create(Registries.PLACEMENT_MODIFIER_TYPE, ID_THERMAL);

    public static final DeferredRegisterCoFH<FluidType> FLUID_TYPES = DeferredRegisterCoFH.create(ForgeRegistries.Keys.FLUID_TYPES, ID_THERMAL);

    public ThermalCore() {

        setFeatureFlags();
        addOreConfigs();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CONFIG_MANAGER.register(modEventBus)
                .addClientConfig(new ThermalClientConfig())
                .addServerConfig(new ThermalCoreConfig())
                .addServerConfig(new ThermalDeviceConfig())
                .addServerConfig(new ThermalRecipeConfig())
                .addCommonConfig(new ThermalWorldConfig());

        modEventBus.addListener(this::entityAttributeSetup);
        modEventBus.addListener(this::entityLayerSetup);
        modEventBus.addListener(this::entityRendererSetup);
        modEventBus.addListener(this::spawnPlacementSetup);
        modEventBus.addListener(this::capSetup);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerLootData);
        modEventBus.addListener(this::registrySetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        FLUIDS.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);
        EFFECTS.register(modEventBus);

        BLOCK_ENTITIES.register(modEventBus);
        CONTAINERS.register(modEventBus);
        ENTITIES.register(modEventBus);
        LOOT_SERIALIZERS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        PLACEMENT_MODIFIERS.register(modEventBus);

        FLUID_TYPES.register(modEventBus);

        TCoreBlocks.register();
        TCoreItems.register();
        TCoreFluids.register();
        ThermalCreativeTabs.register();

        TCoreBlockEntities.register();
        TCoreMenus.register();
        TCoreEntities.register();
        TCoreRecipeSerializers.register();
        TCoreRecipeTypes.register();
        TCoreSounds.register();

        TCoreRecipeManagers.register();
        TCorePlacementModifiers.register();
    }

    private void setFeatureFlags() {

        setFlag(FLAG_FILTER_AUGMENTS, true);
        setFlag(FLAG_STORAGE_AUGMENTS, true);
        setFlag(FLAG_UPGRADE_AUGMENTS, true);

        setFlag(FLAG_CREATIVE_AUGMENTS, true);

        setFlag(ID_TINKER_BENCH, true);

        // setFlag(ID_CHUNK_LOADER, true);
    }

    private void addOreConfigs() {

        ThermalWorldConfig.addFeatureConfig("niter_ore", new FeatureConfig("Niter", getFlag(FLAG_RESOURCE_NITER)));
        ThermalWorldConfig.addFeatureConfig("sulfur_ore", new FeatureConfig("Sulfur", getFlag(FLAG_RESOURCE_SULFUR)));

        ThermalWorldConfig.addFeatureConfig("tin_ore", new FeatureConfig("Tin", getFlag(FLAG_RESOURCE_TIN)));
        ThermalWorldConfig.addFeatureConfig("lead_ore", new FeatureConfig("Lead", getFlag(FLAG_RESOURCE_LEAD)));
        ThermalWorldConfig.addFeatureConfig("silver_ore", new FeatureConfig("Silver", getFlag(FLAG_RESOURCE_SILVER)));
        ThermalWorldConfig.addFeatureConfig("nickel_ore", new FeatureConfig("Nickel", getFlag(FLAG_RESOURCE_NICKEL)));

        ThermalWorldConfig.addFeatureConfig("apatite_ore", new FeatureConfig("Apatite", getFlag(FLAG_RESOURCE_APATITE)));

        ThermalWorldConfig.addFeatureConfig("cinnabar_ore", new FeatureConfig("Cinnabar", getFlag(FLAG_RESOURCE_CINNABAR)));
        ThermalWorldConfig.addFeatureConfig("oil_sand", new FeatureConfig("Oil Sand", getFlag(FLAG_RESOURCE_OIL)));

        ThermalWorldConfig.addFeatureConfig("rubberwood_trees", new FeatureConfig("Rubberwood Trees", getFlag(FLAG_RESOURCE_RUBBERWOOD)));
    }

    // region INITIALIZATION
    private void registerLootData(final RegisterEvent event) {

        if (event.getRegistryKey() == ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS) {
            ThermalFlags.manager().setup();
        }
    }

    private void entityAttributeSetup(final EntityAttributeCreationEvent event) {

        event.put(BASALZ.get(), Basalz.registerAttributes().build());
        event.put(BLITZ.get(), Blitz.registerAttributes().build());
        event.put(BLIZZ.get(), Blizz.registerAttributes().build());
    }

    private void entityLayerSetup(final EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(BasalzModel.BASALZ_LAYER, BasalzModel::createBodyLayer);
        event.registerLayerDefinition(BlitzModel.BLITZ_LAYER, BlitzModel::createBodyLayer);
        event.registerLayerDefinition(BlizzModel.BLIZZ_LAYER, BlizzModel::createBodyLayer);

        event.registerLayerDefinition(SantaHatModel.HAT_LAYER, SantaHatModel::createBodyLayer);

        event.registerLayerDefinition(ElementalProjectileModel.PROJECTILE_LAYER, ElementalProjectileModel::createBodyLayer);
    }

    private void entityRendererSetup(final EntityRenderersEvent.RegisterRenderers event) {

        event.registerEntityRenderer(THROWN_FLORB.get(), ThrownItemRenderer::new);

        for (RegistryObject<EntityType<? extends AbstractGrenade>> grenade : DetonateUtils.GRENADES) {
            event.registerEntityRenderer(grenade.get(), ThrownItemRenderer::new);
        }
        for (RegistryObject<EntityType<? extends PrimedTntCoFH>> tnt : DetonateUtils.TNT) {
            event.registerEntityRenderer(tnt.get(), TntRendererCoFH::new);
        }
        for (RegistryObject<EntityType<? extends AbstractTNTMinecart>> cart : DetonateUtils.CARTS) {
            event.registerEntityRenderer(cart.get(), TNTMinecartRendererCoFH::new);
        }
        event.registerEntityRenderer(BASALZ.get(), BasalzRenderer::new);
        event.registerEntityRenderer(BLITZ.get(), BlitzRenderer::new);
        event.registerEntityRenderer(BLIZZ.get(), BlizzRenderer::new);

        event.registerEntityRenderer(BASALZ_PROJECTILE.get(), BasalzProjectileRenderer::new);
        event.registerEntityRenderer(BLITZ_PROJECTILE.get(), BlitzProjectileRenderer::new);
        event.registerEntityRenderer(BLIZZ_PROJECTILE.get(), BlizzProjectileRenderer::new);
    }

    private void spawnPlacementSetup(final SpawnPlacementRegisterEvent event) {

        event.register(BASALZ.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Basalz::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(BLITZ.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Blitz::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(BLIZZ.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Blizz::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    private void capSetup(RegisterCapabilitiesEvent event) {

        CapabilityRedstoneFlux.register(event);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(TCoreBlocks::setup);
        event.enqueueWork(TCoreItems::setup);
        event.enqueueWork(TCoreFluids::setup);
        event.enqueueWork(TCoreEntities::setup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        event.enqueueWork(this::registerGuiFactories);
        event.enqueueWork(this::registerRenderLayers);

        event.enqueueWork(() -> CoreClientEvents.addNamespace(ID_THERMAL));
    }

    private void registrySetup(final NewRegistryEvent event) {

        CONFIG_MANAGER.setupClient();
        CONFIG_MANAGER.setupServer();
        CONFIG_MANAGER.setupCommon();
    }
    // endregion

    // region HELPERS
    private void registerGuiFactories() {

        MenuScreens.register(DEVICE_HIVE_EXTRACTOR_CONTAINER.get(), DeviceHiveExtractorScreen::new);
        MenuScreens.register(DEVICE_TREE_EXTRACTOR_CONTAINER.get(), DeviceTreeExtractorScreen::new);
        MenuScreens.register(DEVICE_FISHER_CONTAINER.get(), DeviceFisherScreen::new);
        MenuScreens.register(DEVICE_COMPOSTER_CONTAINER.get(), DeviceComposterScreen::new);
        MenuScreens.register(DEVICE_SOIL_INFUSER_CONTAINER.get(), DeviceSoilInfuserScreen::new);
        MenuScreens.register(DEVICE_WATER_GEN_CONTAINER.get(), DeviceWaterGenScreen::new);
        MenuScreens.register(DEVICE_ROCK_GEN_CONTAINER.get(), DeviceRockGenScreen::new);
        MenuScreens.register(DEVICE_COLLECTOR_CONTAINER.get(), DeviceCollectorScreen::new);
        MenuScreens.register(DEVICE_XP_CONDENSER_CONTAINER.get(), DeviceXpCondenserScreen::new);
        MenuScreens.register(DEVICE_POTION_DIFFUSER_CONTAINER.get(), DevicePotionDiffuserScreen::new);
        MenuScreens.register(DEVICE_NULLIFIER_CONTAINER.get(), DeviceNullifierScreen::new);
        MenuScreens.register(TINKER_BENCH_CONTAINER.get(), TinkerBenchScreen::new);
        MenuScreens.register(CHARGE_BENCH_CONTAINER.get(), ChargeBenchScreen::new);
        MenuScreens.register(SATCHEL_CONTAINER.get(), SatchelScreen::new);
        MenuScreens.register(ENERGY_CELL_CONTAINER.get(), EnergyCellScreen::new);
        MenuScreens.register(FLUID_CELL_CONTAINER.get(), FluidCellScreen::new);

        // MenuScreens.register(ITEM_CELL_CONTAINER, ItemCellScreen::new);
    }

    private void registerRenderLayers() {

        ItemBlockRenderTypes.setRenderLayer(RedstoneFluid.instance().still().get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RedstoneFluid.instance().flowing().get(), RenderType.translucent());
    }
    // endregion
}
