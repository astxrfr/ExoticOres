package net.kirks.exoticores.datagen;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.registry.ModBlocks;
import net.kirks.exoticores.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jspecify.annotations.NonNull;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        var items = registries.lookupOrThrow(Registries.ITEM);

        toolRecipes(items);
        armorRecipes(items);
        storageRecipes(items);
        cookingRecipes(items);
    }

    private void armorRecipes(HolderLookup.RegistryLookup<Item> items) {
        // THORITE HELMET
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ModItems.THORITE_HELMET)
                .pattern("III")
                .pattern("I I")
                .define('I', ModItems.THORITE_SHARD)
                .unlockedBy("has_thorite_shard", has(ModItems.THORITE_SHARD))
                .save(output);

        // THORITE CHESTPLATE
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ModItems.THORITE_CHESTPLATE)
                .pattern("I I")
                .pattern("III")
                .pattern("III")
                .define('I', ModItems.THORITE_SHARD)
                .unlockedBy("has_thorite_shard", has(ModItems.THORITE_SHARD))
                .save(output);

        // THORITE LEGGINGS
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ModItems.THORITE_LEGGINGS)
                .pattern("III")
                .pattern("I I")
                .pattern("I I")
                .define('I', ModItems.THORITE_SHARD)
                .unlockedBy("has_thorite_shard", has(ModItems.THORITE_SHARD))
                .save(output);

        // THORITE BOOTS
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ModItems.THORITE_BOOTS)
                .pattern("I I")
                .pattern("I I")
                .define('I', ModItems.THORITE_SHARD)
                .unlockedBy("has_thorite_shard", has(ModItems.THORITE_SHARD))
                .save(output);

        // RAD SUIT HELMET
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ModItems.RADIATION_SUIT_HELMET)
                .pattern("III")
                .pattern("I I")
                .define('I', ModItems.LEAD_INGOT)
                .unlockedBy("has_lead_ingot", has(ModItems.LEAD_INGOT))
                .save(output);

        // RAD SUIT CHESTPLATE
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ModItems.RADIATION_SUIT_CHESPLATE)
                .pattern("I I")
                .pattern("III")
                .pattern("III")
                .define('I', ModItems.LEAD_INGOT)
                .unlockedBy("has_lead_ingot", has(ModItems.LEAD_INGOT))
                .save(output);

        // RAD SUIT LEGGINGS
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ModItems.RADIATION_SUIT_LEGGINGS)
                .pattern("III")
                .pattern("I I")
                .pattern("I I")
                .define('I', ModItems.LEAD_INGOT)
                .unlockedBy("has_lead_ingot", has(ModItems.LEAD_INGOT))
                .save(output);

        // RAD SUIT BOOTS
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ModItems.RADIATION_SUIT_BOOTS)
                .pattern("I I")
                .pattern("I I")
                .define('I', ModItems.LEAD_INGOT)
                .unlockedBy("has_lead_ingot", has(ModItems.LEAD_INGOT))
                .save(output);
    }

    private void storageRecipes(HolderLookup.RegistryLookup<Item> items) {
        // 1 THORITE_BLOCK
        ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, ModBlocks.THORITE_BLOCK)
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', ModItems.THORITE_SHARD)
                .unlockedBy("has_thorite_ingot", has(ModItems.THORITE_SHARD))
                .save(output);

        // 9 THORITE SHARDS
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.THORITE_SHARD, 9)
                .requires(ModBlocks.THORITE_BLOCK)
                .unlockedBy("has_thorite_block", has(ModBlocks.THORITE_BLOCK))
                .save(output);
    }

    private void cookingRecipes(HolderLookup.RegistryLookup<Item> items) {
        // LEAD
        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(ModItems.RAW_LEAD.get()),
                RecipeCategory.MISC,
                CookingBookCategory.MISC,
                ModItems.LEAD_INGOT.get(),
                0.5F,
                200)
                .unlockedBy("has_raw_lead", has(ModItems.RAW_LEAD))
                .save(output, ExoticOres.MODID+":lead_ingot_from_smelting");
        SimpleCookingRecipeBuilder.blasting(
                Ingredient.of(ModItems.RAW_LEAD.get()),
                RecipeCategory.MISC,
                CookingBookCategory.MISC,
                ModItems.LEAD_INGOT.get(),
                0.5F,
                100)
                .unlockedBy("has_raw_lead", has(ModItems.RAW_LEAD))
                .save(output, ExoticOres.MODID+":lead_ingot_from_blasting");
    }

    private void toolRecipes(HolderLookup.RegistryLookup<Item> items) {
        // Thorite tools
        toolRecipe(items, RecipeCategory.TOOLS,
                ModItems.THORITE_AXE,
                ModItems.THORITE_SHARD.asItem(),
                Items.BLAZE_ROD,
                "II",
                "IB",
                " B");

        toolRecipe(items, RecipeCategory.TOOLS,
                ModItems.THORITE_SHOVEL,
                ModItems.THORITE_SHARD.asItem(),
                Items.BLAZE_ROD,
                "I",
                "B",
                "B");

        toolRecipe(items, RecipeCategory.COMBAT,
                ModItems.THORITE_SWORD,
                ModItems.THORITE_SHARD.asItem(),
                Items.BLAZE_ROD,
                "I",
                "I",
                "B");

        toolRecipe(items, RecipeCategory.TOOLS,
                ModItems.THORITE_PICKAXE,
                ModItems.THORITE_SHARD.asItem(),
                Items.BLAZE_ROD,
                "III",
                " B ",
                " B ");

        toolRecipe(items, RecipeCategory.TOOLS,
                ModItems.THORITE_HOE,
                ModItems.THORITE_SHARD.asItem(),
                Items.BLAZE_ROD,
                "II",
                " B",
                " B");

        toolRecipe(items, RecipeCategory.COMBAT,
                ModItems.THORITE_SPEAR,
                ModItems.THORITE_SHARD.asItem(),
                Items.BLAZE_ROD,
                "  I",
                " B ",
                "B  ");

        // lead tools
        toolRecipe(items, RecipeCategory.TOOLS,
                ModItems.LEAD_AXE,
                ModItems.LEAD_INGOT.asItem(),
                Items.STICK,
                "II",
                "IB",
                " B");

        toolRecipe(items, RecipeCategory.TOOLS,
                ModItems.LEAD_SHOVEL,
                ModItems.LEAD_INGOT.asItem(),
                Items.STICK,
                "I",
                "B",
                "B");

        toolRecipe(items, RecipeCategory.COMBAT,
                ModItems.LEAD_SWORD,
                ModItems.LEAD_INGOT.asItem(),
                Items.STICK,
                "I",
                "I",
                "B");

        toolRecipe(items, RecipeCategory.TOOLS,
                ModItems.LEAD_PICKAXE,
                ModItems.LEAD_INGOT.asItem(),
                Items.STICK,
                "III",
                " B ",
                " B ");

        toolRecipe(items, RecipeCategory.TOOLS,
                ModItems.LEAD_HOE,
                ModItems.LEAD_INGOT.asItem(),
                Items.STICK,
                "II",
                " B",
                " B");

        toolRecipe(items, RecipeCategory.COMBAT,
                ModItems.LEAD_SPEAR,
                ModItems.LEAD_INGOT.asItem(),
                Items.STICK,
                "  I",
                " B ",
                "B  ");
    }

    private void toolRecipe(
            HolderLookup.RegistryLookup<Item> items,
            RecipeCategory category,
            ItemLike result,
            Item ingot,
            Item stick,
            String... pattern) {

        var recipe = ShapedRecipeBuilder.shaped(items, category, result);

        for (String row : pattern) {
            recipe.pattern(row);
        }

        recipe.define('I', ingot)
                .define('B', stick)
                .unlockedBy("has_thorite_shard", has(ingot))
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
