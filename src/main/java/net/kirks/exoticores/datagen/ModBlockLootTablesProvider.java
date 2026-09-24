package net.kirks.exoticores.datagen;

import net.kirks.exoticores.registry.ModBlocks;
import net.kirks.exoticores.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.util.Set;

public class ModBlockLootTablesProvider extends BlockLootSubProvider {
    protected ModBlockLootTablesProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected void generate() {
        // SELF-DROPS
        dropSelf(ModBlocks.THORITE_BLOCK.get());

        // ORE DROPS
        add(
                ModBlocks.THORITE_ORE.get(),
                createOreDrop(ModBlocks.THORITE_ORE.get(), ModItems.RAW_THORITE.get())
        );
    }

    @Override
    protected @NonNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries()
                .stream()
                .map(entry -> (Block) entry.get())
                .toList();
    }
}
