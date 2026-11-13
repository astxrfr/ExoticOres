package net.kirks.exoticores.datagen;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ExoticOres.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider registries) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.THORITE_ORE.getKey())
                .add(ModBlocks.THORITE_BLOCK.getKey())
                .add(ModBlocks.THORITE_CRYSTAL_BLOCK.getKey())
                .add(ModBlocks.LEAD_ORE.getKey())
                .add(ModBlocks.DEEPSLATE_LEAD_ORE.getKey())
                .add(ModBlocks.LEAD_BLOCK.getKey())
                .add(ModBlocks.RAW_LEAD_BLOCK.getKey())
                .add(ModBlocks.KYANITE_BLOCK.getKey());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.RADIATION_CHEST.getKey());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.RADIATION_CHEST.getKey());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.THORITE_ORE.getKey())
                .add(ModBlocks.THORITE_BLOCK.getKey());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.LEAD_ORE.getKey())
                .add(ModBlocks.DEEPSLATE_LEAD_ORE.getKey())
                .add(ModBlocks.LEAD_BLOCK.getKey());
    }
}
