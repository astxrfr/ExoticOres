package net.kirks.exoticores.worldgen;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.registry.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> THORITE_ORE =
            registerKey("thorite_ore");
    private static final int THORITE_VEIN_SIZE = 3;

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context) {
        List<OreConfiguration.TargetBlockState> targets = List.of(
                OreConfiguration.target(
                        new BlockMatchTest(Blocks.END_STONE),
                        ModBlocks.THORITE_ORE.get().defaultBlockState()
                )
        );

        context.register(THORITE_ORE, new ConfiguredFeature<>(
                Feature.SCATTERED_ORE,
                new OreConfiguration(targets, THORITE_VEIN_SIZE)
        ));
    }

    private static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                Identifier.fromNamespaceAndPath(ExoticOres.MODID, name));
    }
}
