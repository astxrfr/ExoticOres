package net.kirks.exoticores;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue RADIATION_ENABLED;
    public static final ModConfigSpec.DoubleValue RADIATION_RADIUS_MULT;

    static final ModConfigSpec SERVER_SPEC;

    static {
        BUILDER.push("radiation");

        RADIATION_ENABLED = BUILDER
                .comment("Whether the radiation mechanic is enabled.")
                .define("enabled", true);

        RADIATION_RADIUS_MULT = BUILDER
                .comment("Multiplies the range for radiation applying objects/blocks.")
                .defineInRange("multiplier", 1, 0.1, 10);

        BUILDER.pop();

        SERVER_SPEC = BUILDER.build();
    }
}
