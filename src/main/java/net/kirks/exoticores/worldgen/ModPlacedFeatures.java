package net.kirks.exoticores.worldgen;

import net.kirks.exoticores.ExoticOres;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> THORITE_ORE_PLACED =
            registerKey("thorite_ore_placed");
    public static final ResourceKey<PlacedFeature> LEAD_ORE_PLACED =
            registerKey("lead_ore_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures =
                context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(THORITE_ORE_PLACED, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.THORITE_ORE),
                List.of(
                        CountPlacement.of(4), // Attempts per chunk
                        InSquarePlacement.spread(), // horizontal spread within the chunk
                        HeightRangePlacement.uniform(
                                VerticalAnchor.absolute(0),  // Range
                                VerticalAnchor.absolute(200)
                        ),
                        BiomeFilter.biome()
                )
        ));

        context.register(LEAD_ORE_PLACED, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.LEAD_ORE),
                List.of(
                        CountPlacement.of(6),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.of(
                                BiasedToBottomHeight.of(
                                    VerticalAnchor.absolute(-30),
                                    VerticalAnchor.absolute(200),
                                5
                                )
                        ),
                        BiomeFilter.biome()
                )
        ));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(ExoticOres.MODID, name));
    }
}
