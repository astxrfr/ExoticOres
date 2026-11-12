package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.worldgen.feature.CorruptedScatteredOreFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, ExoticOres.MODID);

    public static final DeferredHolder<Feature<?>, CorruptedScatteredOreFeature> CORRUPTED_SCATTERED_ORE = FEATURES.register("corrupted_scattered_ore", CorruptedScatteredOreFeature::new);

}
