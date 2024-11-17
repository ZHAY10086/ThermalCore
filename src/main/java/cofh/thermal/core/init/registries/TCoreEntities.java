package cofh.thermal.core.init.registries;

import cofh.thermal.core.common.entity.explosive.DetonateUtils;
import cofh.thermal.core.common.entity.monster.Basalz;
import cofh.thermal.core.common.entity.monster.Blitz;
import cofh.thermal.core.common.entity.monster.Blizz;
import cofh.thermal.core.common.entity.projectile.BasalzProjectile;
import cofh.thermal.core.common.entity.projectile.BlitzProjectile;
import cofh.thermal.core.common.entity.projectile.BlizzProjectile;
import cofh.thermal.core.common.entity.projectile.ThrownFlorb;
import cofh.thermal.lib.common.entity.AugmentableMinecart;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;

import static cofh.lib.util.FlagManager.getFlag;
import static cofh.thermal.core.ThermalCore.ENTITIES;
import static cofh.thermal.core.init.registries.ThermalCreativeTabs.toolsTab;
import static cofh.thermal.core.util.RegistrationHelper.registerGrenade;
import static cofh.thermal.core.util.RegistrationHelper.registerTNT;
import static cofh.thermal.lib.util.ThermalFlags.*;
import static cofh.thermal.lib.util.ThermalIDs.*;

public class TCoreEntities {

    private TCoreEntities() {

    }

    public static void register() {

        toolsTab(200, registerGrenade(ID_EXPLOSIVE_GRENADE, DetonateUtils::explosive), getFlag(FLAG_BASIC_EXPLOSIVES));

        toolsTab(200, registerGrenade(ID_ENDER_GRENADE, DetonateUtils::ender), getFlag(FLAG_BASIC_EXPLOSIVES));
        toolsTab(200, registerGrenade(ID_GLOWSTONE_GRENADE, DetonateUtils::glow), getFlag(FLAG_BASIC_EXPLOSIVES));
        toolsTab(200, registerGrenade(ID_REDSTONE_GRENADE, DetonateUtils::redstone), getFlag(FLAG_BASIC_EXPLOSIVES));
        toolsTab(200, registerGrenade(ID_SLIME_GRENADE, DetonateUtils::slime), getFlag(FLAG_BASIC_EXPLOSIVES));
        toolsTab(200, registerGrenade(ID_FIRE_GRENADE, DetonateUtils::fire), getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        toolsTab(200, registerGrenade(ID_ICE_GRENADE, DetonateUtils::ice), getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        toolsTab(200, registerGrenade(ID_LIGHTNING_GRENADE, DetonateUtils::lightning), getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        toolsTab(200, registerGrenade(ID_EARTH_GRENADE, DetonateUtils::earth), getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        toolsTab(200, registerGrenade(ID_PHYTO_GRENADE, DetonateUtils::phyto), getFlag(FLAG_PHYTOGRO_EXPLOSIVES));
        toolsTab(200, registerGrenade(ID_NUKE_GRENADE, DetonateUtils::nuke), getFlag(FLAG_NUCLEAR_EXPLOSIVES));

        toolsTab(201, registerTNT(ID_ENDER_TNT, DetonateUtils::ender), getFlag(FLAG_BASIC_EXPLOSIVES));
        toolsTab(201, registerTNT(ID_GLOWSTONE_TNT, DetonateUtils::glow), getFlag(FLAG_BASIC_EXPLOSIVES));
        toolsTab(201, registerTNT(ID_REDSTONE_TNT, DetonateUtils::redstone), getFlag(FLAG_BASIC_EXPLOSIVES));
        toolsTab(201, registerTNT(ID_SLIME_TNT, DetonateUtils::slime), getFlag(FLAG_BASIC_EXPLOSIVES));
        toolsTab(201, registerTNT(ID_FIRE_TNT, DetonateUtils::fire), getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        toolsTab(201, registerTNT(ID_ICE_TNT, DetonateUtils::ice), getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        toolsTab(201, registerTNT(ID_LIGHTNING_TNT, DetonateUtils::lightning), getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        toolsTab(201, registerTNT(ID_EARTH_TNT, DetonateUtils::earth), getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        toolsTab(201, registerTNT(ID_PHYTO_TNT, DetonateUtils::phyto), getFlag(FLAG_PHYTOGRO_EXPLOSIVES));
        toolsTab(201, registerTNT(ID_NUKE_TNT, DetonateUtils::nuke), getFlag(FLAG_NUCLEAR_EXPLOSIVES));
    }

    public static void setup() {

        AugmentableMinecart.setup();
    }

    public static final DeferredHolder<EntityType<?>, EntityType<Basalz>> BASALZ = ENTITIES.register(ID_BASALZ, () -> EntityType.Builder.of(Basalz::new, MobCategory.MONSTER).sized(0.6F, 1.8F).fireImmune().build(ID_BASALZ));
    public static final DeferredHolder<EntityType<?>, EntityType<Blizz>> BLIZZ = ENTITIES.register(ID_BLIZZ, () -> EntityType.Builder.of(Blizz::new, MobCategory.MONSTER).sized(0.6F, 1.8F).build(ID_BLIZZ));
    public static final DeferredHolder<EntityType<?>, EntityType<Blitz>> BLITZ = ENTITIES.register(ID_BLITZ, () -> EntityType.Builder.of(Blitz::new, MobCategory.MONSTER).sized(0.6F, 1.8F).build(ID_BLITZ));

    public static final DeferredHolder<EntityType<?>, EntityType<BasalzProjectile>> BASALZ_PROJECTILE = ENTITIES.register(ID_BASALZ_PROJECTILE, () -> EntityType.Builder.<BasalzProjectile>of(BasalzProjectile::new, MobCategory.MISC).sized(0.3125F, 0.3125F).build(ID_BASALZ_PROJECTILE));
    public static final DeferredHolder<EntityType<?>, EntityType<BlizzProjectile>> BLIZZ_PROJECTILE = ENTITIES.register(ID_BLIZZ_PROJECTILE, () -> EntityType.Builder.<BlizzProjectile>of(BlizzProjectile::new, MobCategory.MISC).sized(0.3125F, 0.3125F).build(ID_BLIZZ_PROJECTILE));
    public static final DeferredHolder<EntityType<?>, EntityType<BlitzProjectile>> BLITZ_PROJECTILE = ENTITIES.register(ID_BLITZ_PROJECTILE, () -> EntityType.Builder.<BlitzProjectile>of(BlitzProjectile::new, MobCategory.MISC).sized(0.3125F, 0.3125F).build(ID_BLITZ_PROJECTILE));

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownFlorb>> THROWN_FLORB = ENTITIES.register(ID_FLORB, () -> EntityType.Builder.<ThrownFlorb>of(ThrownFlorb::new, MobCategory.MISC).sized(0.25F, 0.25F).build(ID_FLORB));

}
