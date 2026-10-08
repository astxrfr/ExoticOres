package net.kirks.exoticores;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ExoticOresConfig {
    private static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue RADIATION_ENABLED;
    public static final ModConfigSpec.DoubleValue RADIATION_RADIUS_MULT;

    static final ModConfigSpec SERVER_SPEC;

    static {
        SERVER_BUILDER.push("radiation");

        RADIATION_ENABLED = SERVER_BUILDER
                .comment("Whether the radiation mechanic is enabled.")
                .define("enabled", true);

        RADIATION_RADIUS_MULT = SERVER_BUILDER
                .comment("Multiplies the range for radiation applying objects/blocks.")
                .defineInRange("multiplier", 1, 0.1, 10);

        SERVER_BUILDER.pop();

        SERVER_SPEC = SERVER_BUILDER.build();
    }
}
