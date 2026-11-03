package net.kirks.exoticores;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ExoticOresConfig {
    private static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue RADIATION_ENABLED;
    public static final ModConfigSpec.DoubleValue RADIATION_RADIUS_MULT;
    public static final ModConfigSpec.DoubleValue RADIATION_HIT_MULT;

    public static final ModConfigSpec.BooleanValue POISON_HIT_ENABLED;
    public static final ModConfigSpec.IntValue POISON_HIT_DURATION_SECONDS;
    public static final ModConfigSpec.IntValue POISON_HIT_AMPLIFIER;

    static final ModConfigSpec SERVER_SPEC;

    static {
        SERVER_BUILDER.push("radiation");

        RADIATION_ENABLED = SERVER_BUILDER
                .comment("Whether the radiation mechanic is enabled.")
                .define("enabled", true);

        RADIATION_RADIUS_MULT = SERVER_BUILDER
                .comment("Multiplies the range for radiation applying objects/blocks.")
                .defineInRange("multiplier", 1, 0.1, 10);

        RADIATION_HIT_MULT = SERVER_BUILDER
                .comment("Multiplies the intensity of the radiation effect applied to entities hit by radiation inducing tools/items.")
                .defineInRange("multiplier", 1, 0.1, 5);

        SERVER_BUILDER.pop();

        SERVER_BUILDER.push("poison");

        POISON_HIT_ENABLED = SERVER_BUILDER
                .comment("Whether lead tools apply poison to entities they hit.")
                .define("enabled", true);

        POISON_HIT_DURATION_SECONDS = SERVER_BUILDER
                .comment("Duration (in seconds) of the poison applied by lead tools.")
                .defineInRange("duration_seconds", 5, 1, 120);

        POISON_HIT_AMPLIFIER = SERVER_BUILDER
                .comment("Poison level applied by lead tools (0 = Poison I, 1 = Poison II, ...).")
                .defineInRange("amplifier", 0, 0, 4);

        SERVER_BUILDER.pop();

        SERVER_SPEC = SERVER_BUILDER.build();
    }
}
