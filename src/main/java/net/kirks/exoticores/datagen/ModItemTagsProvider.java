package net.kirks.exoticores.datagen;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.registry.ModItems;
import net.kirks.exoticores.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ExoticOres.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider registries) {
        tag(ModTags.Items.RADIATION_PROTECTIVE)
                .add(ModItems.THORITE_HELMET.getKey())
                .add(ModItems.THORITE_CHESTPLATE.getKey())
                .add(ModItems.THORITE_LEGGINGS.getKey())
                .add(ModItems.THORITE_BOOTS.getKey());
    }
}
