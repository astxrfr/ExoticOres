package net.kirks.exoticores.datagen;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.registry.ModBlocks;
import net.kirks.exoticores.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        var items = registries.lookupOrThrow(Registries.ITEM);

        // 1 THORITE_BLOCK
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.THORITE_BLOCK)
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', ModItems.THORITE_SHARD)
                .unlockedBy("has_thorite_ingot", has(ModItems.RAW_THORITE))
                .save(output);

        // 9 THORITE SHARDS
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.THORITE_SHARD, 9)
                .requires(ModBlocks.THORITE_BLOCK)
                .unlockedBy("has_thorite_block", has(ModBlocks.THORITE_BLOCK))
                .save(output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public @NonNull String getName() {
            return ExoticOres.MODID+"RecipeProvider";
        }
    }
}
