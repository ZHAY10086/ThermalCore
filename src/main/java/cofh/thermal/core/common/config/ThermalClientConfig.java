package cofh.thermal.core.common.config;

import cofh.core.common.config.IBaseConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.function.Supplier;

import static cofh.lib.util.Constants.TRUE;

public class ThermalClientConfig implements IBaseConfig {

    @Override
    public void apply(ModConfigSpec.Builder builder) {

        builder.push("Sounds");

        blockAmbientSounds = builder
                .comment("If TRUE, some 'Thermal Series' Blocks will have ambient sounds.")
                .define("Ambient Block Sounds", blockAmbientSounds);

        mobAmbientSounds = builder
                .comment("If TRUE, some 'Thermal Series' Mobs will have ambient sounds.")
                .define("Ambient Mob Sounds", mobAmbientSounds);

        builder.pop();

        builder.push("Holidays");

        festiveMobs = builder
                .comment("If TRUE, some Mobs will have festive hats on certain days. Disabling this will disable it for ALL mobs, including vanilla. May require client restart.")
                .define("Festive Mobs", festiveMobs);

        festiveVanillaMobs = builder
                .comment("If TRUE, some vanilla Mobs will also have festive hats on certain days. May require client restart.")
                .define("Festive Vanilla Mobs", festiveMobs);

        builder.pop();
    }

    // region VARIABLES
    public static Supplier<Boolean> jeiBucketTanks = TRUE;

    public static Supplier<Boolean> blockAmbientSounds = TRUE;
    public static Supplier<Boolean> mobAmbientSounds = TRUE;

    public static Supplier<Boolean> festiveMobs = TRUE;
    public static Supplier<Boolean> festiveVanillaMobs = TRUE;
    // endregion
}
