package cofh.thermal.core.init.registries;

import cofh.core.common.item.EnergyContainerItem;
import cofh.core.common.item.ItemCoFH;
import cofh.core.common.item.SpawnEggItemCoFH;
import cofh.core.util.filter.FilterRegistry;
import cofh.core.util.helpers.AugmentDataHelper;
import cofh.lib.common.block.TntBlockCoFH;
import cofh.lib.common.item.ArmorMaterialCoFH;
import cofh.thermal.core.common.item.*;
import cofh.thermal.lib.common.item.AugmentItem;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.energy.IEnergyStorage;

import static cofh.lib.util.Constants.BUCKET_VOLUME;
import static cofh.lib.util.Utils.itemProperties;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.registries.TCoreEntities.*;
import static cofh.thermal.core.init.registries.ThermalCreativeTabs.*;
import static cofh.thermal.core.util.RegistrationHelper.*;
import static cofh.thermal.lib.util.ThermalAugmentRules.flagUniqueAugment;
import static cofh.thermal.lib.util.ThermalFlags.*;
import static cofh.thermal.lib.util.ThermalIDs.*;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;

public class TCoreItems {

    private TCoreItems() {

    }

    public static void register() {

        registerResources();
        registerMaterials();
        registerParts();
        registerAugments();
        registerTools();
        registerArmor();

        registerSpawnEggs();
    }

    public static void setup() {

        DetonatorItem.registerTNT(Blocks.TNT, PrimedTnt::new);

        DetonatorItem.registerTNT(BLOCKS.get(ID_SLIME_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_SLIME_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_REDSTONE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_REDSTONE_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_GLOWSTONE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_GLOWSTONE_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_ENDER_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_ENDER_TNT))).getFactory());

        DetonatorItem.registerTNT(BLOCKS.get(ID_FIRE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_FIRE_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_EARTH_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_EARTH_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_ICE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_ICE_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_LIGHTNING_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_LIGHTNING_TNT))).getFactory());

        DetonatorItem.registerTNT(BLOCKS.get(ID_PHYTO_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_PHYTO_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_NUKE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_NUKE_TNT))).getFactory());

        ((DivingArmorItem) ITEMS.get(ID_DIVING_HELMET)).setup();
        ((DivingArmorItem) ITEMS.get(ID_DIVING_CHESTPLATE)).setup();
        ((DivingArmorItem) ITEMS.get(ID_DIVING_LEGGINGS)).setup();
        ((DivingArmorItem) ITEMS.get(ID_DIVING_BOOTS)).setup();

        flagUniqueAugment(ITEMS.get("rs_control_augment"));
        flagUniqueAugment(ITEMS.get("side_config_augment"));
        flagUniqueAugment(ITEMS.get("xp_storage_augment"));

        flagUniqueAugment(ITEMS.get("upgrade_augment_1"));
        flagUniqueAugment(ITEMS.get("upgrade_augment_2"));
        flagUniqueAugment(ITEMS.get("upgrade_augment_3"));

        flagUniqueAugment(ITEMS.get("rf_coil_augment"));
        flagUniqueAugment(ITEMS.get("rf_coil_storage_augment"));
        flagUniqueAugment(ITEMS.get("rf_coil_xfer_augment"));
        flagUniqueAugment(ITEMS.get("rf_coil_creative_augment"));

        flagUniqueAugment(ITEMS.get("fluid_tank_augment"));
        flagUniqueAugment(ITEMS.get("fluid_tank_creative_augment"));

        flagUniqueAugment(ITEMS.get("fluid_filter_augment"));
        flagUniqueAugment(ITEMS.get("item_filter_augment"));

        flagUniqueAugment(ITEMS.get("machine_efficiency_creative_augment"));
        flagUniqueAugment(ITEMS.get("machine_catalyst_creative_augment"));
        flagUniqueAugment(ITEMS.get("machine_cycle_augment"));
        flagUniqueAugment(ITEMS.get("machine_null_augment"));

        flagUniqueAugment(ITEMS.get("dynamo_throttle_augment"));
    }

    // region HELPERS
    private static void registerResources() {

        itemsTab(registerItem("apatite"));
        itemsTab(registerItem("apatite_dust"));
        itemsTab(registerItem("cinnabar"));
        itemsTab(registerItem("cinnabar_dust"));
        itemsTab(registerItem("niter"));
        itemsTab(registerItem("niter_dust"));
        itemsTab(registerItem("sulfur", () -> new ItemCoFH(itemProperties()).setBurnTime(1200)));
        itemsTab(registerItem("sulfur_dust", () -> new ItemCoFH(itemProperties()).setBurnTime(1200)));

        itemsTab(registerItem("sawdust"));
        itemsTab(registerItem("coal_coke", () -> new ItemCoFH(itemProperties()).setBurnTime(3200)));
        itemsTab(registerItem("bitumen", () -> new ItemCoFH(itemProperties()).setBurnTime(1600)));
        itemsTab(registerItem("tar", () -> new ItemCoFH(itemProperties()).setBurnTime(800)));
        itemsTab(registerItem("rosin", () -> new ItemCoFH(itemProperties()).setBurnTime(800)));
        itemsTab(registerItem("rubber"));
        itemsTab(registerItem("cured_rubber"));
        itemsTab(registerItem("slag"));
        itemsTab(registerItem("rich_slag"));

        foodsTab(registerItem("syrup_bottle", () -> new HoneyBottleItem(itemProperties().craftRemainder(GLASS_BOTTLE).food(Foods.HONEY_BOTTLE).stacksTo(16))));

        //        registerItem("biomass");
        //        registerItem("rich_biomass");

        itemsTab(registerItem("basalz_rod"));
        itemsTab(registerItem("basalz_powder"));
        itemsTab(registerItem("blitz_rod"));
        itemsTab(registerItem("blitz_powder"));
        itemsTab(registerItem("blizz_rod"));
        itemsTab(registerItem("blizz_powder"));

        itemsTab(registerItem("beekeeper_fabric"), getFlag(FLAG_BEEKEEPER_ARMOR));
        itemsTab(registerItem("diving_fabric"), getFlag(FLAG_DIVING_ARMOR));
        itemsTab(registerItem("hazmat_fabric"), getFlag(FLAG_HAZMAT_ARMOR));
    }

    private static void registerParts() {

        itemsTab(registerItem("redstone_servo"));
        itemsTab(registerItem("rf_coil"));

        itemsTab(registerItem("drill_head", () -> new ItemCoFH(itemProperties())), getFlag(FLAG_TOOL_COMPONENTS));
        itemsTab(registerItem("saw_blade", () -> new ItemCoFH(itemProperties())), getFlag(FLAG_TOOL_COMPONENTS));

        registerItem("laser_diode", () -> new ItemCoFH(itemProperties()));//.setShowInGroups(getFeature(FLAG_TOOL_COMPONENTS))); // TODO: Implement
    }

    private static void registerMaterials() {

        itemsTab(registerItem("ender_pearl_dust"));

        registerVanillaMetalSet("iron");
        registerVanillaMetalSet("gold");
        registerVanillaMetalSet("copper");
        registerVanillaMetalSet("netherite");

        registerVanillaGemSet("lapis");
        registerVanillaGemSet("diamond");
        registerVanillaGemSet("emerald");
        registerVanillaGemSet("quartz");

        Rarity rarity = Rarity.UNCOMMON;

        registerAlloySet("signalum", rarity);
        registerAlloySet("lumium", rarity);
        registerAlloySet("enderium", rarity);
    }

    private static void registerTools() {

        toolsTab(registerItem(ID_WRENCH, () -> new WrenchItem(itemProperties().stacksTo(1))));
        toolsTab(registerItem(ID_REDPRINT, () -> new RedprintItem(itemProperties().stacksTo(1))));
        toolsTab(registerItem(ID_RF_POTATO, () -> new EnergyContainerItem(itemProperties().stacksTo(1), 100000, 40) {

            @Override
            public Capability<? extends IEnergyStorage> getEnergyCapability() {

                return ThermalEnergyHelper.getBaseEnergySystem();
            }
        }));
        toolsTab(registerItem(ID_XP_CRYSTAL, () -> new XpCrystalItem(itemProperties().stacksTo(1), 10000)));
        toolsTab(registerItem(ID_LOCK, () -> new LockItem(itemProperties())));
        toolsTab(registerItem(ID_SATCHEL, () -> new SatchelItem(itemProperties().stacksTo(1), 9)));
        toolsTab(registerItem(ID_DETONATOR, () -> new DetonatorItem(itemProperties().stacksTo(1))));

        toolsTab(60, registerItem(ID_FLORB, () -> new FlorbItem(itemProperties(), BUCKET_VOLUME, (e) -> !e.getFluid().defaultFluidState().createLegacyBlock().isAir())));
        toolsTab(60, registerItem("earth_charge", () -> new EarthChargeItem(itemProperties())));
        toolsTab(60, registerItem("ice_charge", () -> new IceChargeItem(itemProperties())));
        toolsTab(60, registerItem("lightning_charge", () -> new LightningChargeItem(itemProperties())));

        toolsTab(80, registerItem("compost", () -> new FertilizerItem(itemProperties(), 2)));
        toolsTab(80, registerItem("phytogro", () -> new FertilizerItem(itemProperties())));
        // toolsTab(registerItem("fluxed_phytogro", () -> new FertilizerItem(properties(), 5)));

        toolsTab(90, registerItem("junk_net"), getFlag(ID_DEVICE_FISHER));
        toolsTab(90, registerItem("aquachow"), getFlag(ID_DEVICE_FISHER));
        toolsTab(90, registerItem("deep_aquachow"), getFlag(ID_DEVICE_FISHER));
        //        registerItem("rich_aquachow");
        //        registerItem("fluxed_aquachow");
    }

    private static void registerArmor() {

        toolsTab(50, registerItem(ID_BEEKEEPER_HELMET, () -> new BeekeeperArmorItem(BEEKEEPER, ArmorItem.Type.HELMET, itemProperties())), getFlag(FLAG_BEEKEEPER_ARMOR));
        toolsTab(50, registerItem(ID_BEEKEEPER_CHESTPLATE, () -> new BeekeeperArmorItem(BEEKEEPER, ArmorItem.Type.CHESTPLATE, itemProperties())), getFlag(FLAG_BEEKEEPER_ARMOR));
        toolsTab(50, registerItem(ID_BEEKEEPER_LEGGINGS, () -> new BeekeeperArmorItem(BEEKEEPER, ArmorItem.Type.LEGGINGS, itemProperties())), getFlag(FLAG_BEEKEEPER_ARMOR));
        toolsTab(50, registerItem(ID_BEEKEEPER_BOOTS, () -> new BeekeeperArmorItem(BEEKEEPER, ArmorItem.Type.BOOTS, itemProperties())), getFlag(FLAG_BEEKEEPER_ARMOR));

        toolsTab(50, registerItem(ID_DIVING_HELMET, () -> new DivingArmorItem(DIVING, ArmorItem.Type.HELMET, itemProperties())), getFlag(FLAG_DIVING_ARMOR));
        toolsTab(50, registerItem(ID_DIVING_CHESTPLATE, () -> new DivingArmorItem(DIVING, ArmorItem.Type.CHESTPLATE, itemProperties())), getFlag(FLAG_DIVING_ARMOR));
        toolsTab(50, registerItem(ID_DIVING_LEGGINGS, () -> new DivingArmorItem(DIVING, ArmorItem.Type.LEGGINGS, itemProperties())), getFlag(FLAG_DIVING_ARMOR));
        toolsTab(50, registerItem(ID_DIVING_BOOTS, () -> new DivingArmorItem(DIVING, ArmorItem.Type.BOOTS, itemProperties())), getFlag(FLAG_DIVING_ARMOR));

        toolsTab(50, registerItem(ID_HAZMAT_HELMET, () -> new HazmatArmorItem(HAZMAT, ArmorItem.Type.HELMET, itemProperties())), getFlag(FLAG_HAZMAT_ARMOR));
        toolsTab(50, registerItem(ID_HAZMAT_CHESTPLATE, () -> new HazmatArmorItem(HAZMAT, ArmorItem.Type.CHESTPLATE, itemProperties())), getFlag(FLAG_HAZMAT_ARMOR));
        toolsTab(50, registerItem(ID_HAZMAT_LEGGINGS, () -> new HazmatArmorItem(HAZMAT, ArmorItem.Type.LEGGINGS, itemProperties())), getFlag(FLAG_HAZMAT_ARMOR));
        toolsTab(50, registerItem(ID_HAZMAT_BOOTS, () -> new HazmatArmorItem(HAZMAT, ArmorItem.Type.BOOTS, itemProperties())), getFlag(FLAG_HAZMAT_ARMOR));
    }

    // region AUGMENTS
    private static void registerAugments() {

        registerUpgradeAugments();
        registerFeatureAugments();
        registerStorageAugments();
        registerFilterAugments();
        registerMachineAugments();
        registerDynamoAugments();
        registerAreaAugments();
        registerPotionAugments();
    }

    private static void registerUpgradeAugments() {

        final float[] upgradeMods = new float[]{1.0F, 2.0F, 3.0F, 4.0F, 6.0F, 8.5F};
        // final float[] upgradeMods = new float[]{1.0F, 1.5F, 2.0F, 2.5F, 3.0F, 3.5F};

        for (int i = 1; i <= 3; ++i) {
            int tier = i;
            itemsTab(registerItem("upgrade_augment_" + i, () -> new AugmentItem(itemProperties(),
                    AugmentDataHelper.builder()
                            .type(TAG_AUGMENT_TYPE_UPGRADE)
                            .mod(TAG_AUGMENT_BASE_MOD, upgradeMods[tier])
                            .build())));
        }
    }

    private static void registerFeatureAugments() {

        itemsTab(registerItem("rs_control_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .mod(TAG_AUGMENT_FEATURE_RS_CONTROL, 1.0F)
                        .build())), getFlag(FLAG_RS_CONTROL_AUGMENT));

        itemsTab(registerItem("side_config_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .mod(TAG_AUGMENT_FEATURE_SIDE_CONFIG, 1.0F)
                        .build())), getFlag(FLAG_SIDE_CONFIG_AUGMENT));

        itemsTab(registerItem("xp_storage_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .mod(TAG_AUGMENT_FEATURE_XP_STORAGE, 1.0F)
                        .build())), getFlag(FLAG_XP_STORAGE_AUGMENT));
    }

    private static void registerStorageAugments() {

        itemsTab(registerItem("rf_coil_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_RF)
                        .mod(TAG_AUGMENT_RF_STORAGE, 4.0F)
                        .mod(TAG_AUGMENT_RF_XFER, 4.0F)
                        .build())), getFlag(FLAG_STORAGE_AUGMENTS));

        itemsTab(registerItem("rf_coil_storage_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_RF)
                        .mod(TAG_AUGMENT_RF_STORAGE, 6.0F)
                        .mod(TAG_AUGMENT_RF_XFER, 2.0F)
                        .build())), getFlag(FLAG_STORAGE_AUGMENTS));

        itemsTab(registerItem("rf_coil_xfer_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_RF)
                        .mod(TAG_AUGMENT_RF_STORAGE, 2.0F)
                        .mod(TAG_AUGMENT_RF_XFER, 6.0F)
                        .build())), getFlag(FLAG_STORAGE_AUGMENTS));

        itemsTab(registerItem("rf_coil_creative_augment", () -> new AugmentItem(itemProperties().rarity(Rarity.EPIC),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_RF)
                        .mod(TAG_AUGMENT_RF_STORAGE, 16.0F)
                        .mod(TAG_AUGMENT_RF_XFER, 16.0F)
                        .mod(TAG_AUGMENT_RF_CREATIVE, 1.0F)
                        .build())), getFlag(FLAG_CREATIVE_STORAGE_AUGMENTS));

        itemsTab(registerItem("fluid_tank_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_FLUID)
                        .mod(TAG_AUGMENT_FLUID_STORAGE, 4.0F)
                        .build())), getFlag(FLAG_STORAGE_AUGMENTS));

        itemsTab(registerItem("fluid_tank_creative_augment", () -> new AugmentItem(itemProperties().rarity(Rarity.EPIC),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_FLUID)
                        .mod(TAG_AUGMENT_FLUID_STORAGE, 16.0F)
                        .mod(TAG_AUGMENT_FLUID_CREATIVE, 1.0F)
                        .build())), getFlag(FLAG_CREATIVE_STORAGE_AUGMENTS));
    }

    private static void registerFilterAugments() {

        itemsTab(registerItem("item_filter_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_FILTER)
                        .feature(TAG_FILTER_TYPE, FilterRegistry.ITEM_FILTER_TYPE)
                        .build())), getFlag(FLAG_FILTER_AUGMENTS));

        itemsTab(registerItem("fluid_filter_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_FILTER)
                        .feature(TAG_FILTER_TYPE, FilterRegistry.FLUID_FILTER_TYPE)
                        .build())), getFlag(FLAG_FILTER_AUGMENTS));
        //
        //        registerItem("dual_filter_augment", () -> new AugmentItem(properties().group(group),
        //                AugmentDataHelper.builder()
        //                        .type(TAG_AUGMENT_TYPE_FILTER)
        //                        .feature(TAG_FILTER_TYPE, FilterRegistry.DUAL_FILTER_TYPE)
        //                        .build()).setShowInGroups(getFlag(FLAG_FILTER_AUGMENTS)));
    }

    private static void registerMachineAugments() {

        itemsTab(registerItem("machine_speed_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_POWER, 1.0F)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 1.1F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_efficiency_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_SPEED, -0.1F)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 0.9F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_efficiency_creative_augment", () -> new AugmentItem(itemProperties().rarity(Rarity.EPIC),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 0.0F)
                        .build())), getFlag(FLAG_CREATIVE_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_output_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_SECONDARY, 0.15F)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 1.25F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_catalyst_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_CATALYST, 0.8F)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 1.25F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_catalyst_creative_augment", () -> new AugmentItem(itemProperties().rarity(Rarity.EPIC),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_CATALYST, 0.0F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_cycle_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_FEATURE_CYCLE_PROCESS, 1.0F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));

        itemsTab(registerItem("machine_null_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_FEATURE_SECONDARY_NULL, 1.0F)
                        .build())), getFlag(FLAG_MACHINE_AUGMENTS));
    }

    private static void registerDynamoAugments() {

        itemsTab(registerItem("dynamo_output_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_DYNAMO)
                        .mod(TAG_AUGMENT_DYNAMO_POWER, 1.0F)
                        .mod(TAG_AUGMENT_DYNAMO_ENERGY, 0.9F)
                        .build())), getFlag(FLAG_DYNAMO_AUGMENTS));

        itemsTab(registerItem("dynamo_fuel_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_DYNAMO)
                        .mod(TAG_AUGMENT_DYNAMO_ENERGY, 1.1F)
                        .build())), getFlag(FLAG_DYNAMO_AUGMENTS));

        itemsTab(registerItem("dynamo_throttle_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_DYNAMO)
                        .mod(TAG_AUGMENT_DYNAMO_THROTTLE, 1.0F)
                        .build())), getFlag(FLAG_DYNAMO_AUGMENTS));
    }

    private static void registerAreaAugments() {

        itemsTab(registerItem("area_radius_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_AREA_EFFECT)
                        .mod(TAG_AUGMENT_RADIUS, 1.0F)
                        .build())), getFlag(FLAG_AREA_AUGMENTS));
    }

    private static void registerPotionAugments() {

        itemsTab(registerItem("potion_amplifier_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_POTION)
                        .mod(TAG_AUGMENT_POTION_AMPLIFIER, 1.0F)
                        .mod(TAG_AUGMENT_POTION_DURATION, -0.25F)
                        .build())), getFlag(FLAG_POTION_AUGMENTS));

        itemsTab(registerItem("potion_duration_augment", () -> new AugmentItem(itemProperties(),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_POTION)
                        .mod(TAG_AUGMENT_POTION_DURATION, 1.0F)
                        .build())), getFlag(FLAG_POTION_AUGMENTS));
    }
    // endregion

    private static void registerSpawnEggs() {

        itemsTab(registerItem("basalz_spawn_egg", () -> new SpawnEggItemCoFH(BASALZ::get, 0x363840, 0x080407, itemProperties())));
        itemsTab(registerItem("blizz_spawn_egg", () -> new SpawnEggItemCoFH(BLIZZ::get, 0xD8DBE5, 0x91D9FC, itemProperties())));
        itemsTab(registerItem("blitz_spawn_egg", () -> new SpawnEggItemCoFH(BLITZ::get, 0xC9EEFF, 0xFFD97E, itemProperties())));
    }
    // endregion

    public static final ArmorMaterialCoFH BEEKEEPER = new ArmorMaterialCoFH("thermal:beekeeper", 4, new int[]{1, 2, 3, 1}, 16, SoundEvents.ARMOR_EQUIP_ELYTRA, 0.0F, 0.0F, () -> Ingredient.of(ITEMS.get("beekeeper_fabric")));
    public static final ArmorMaterialCoFH DIVING = new ArmorMaterialCoFH("thermal:diving", 12, new int[]{1, 4, 5, 2}, 20, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, () -> Ingredient.of(ITEMS.get("diving_fabric")));
    public static final ArmorMaterialCoFH HAZMAT = new ArmorMaterialCoFH("thermal:hazmat", 6, new int[]{1, 4, 5, 2}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(ITEMS.get("hazmat_fabric")));

}
