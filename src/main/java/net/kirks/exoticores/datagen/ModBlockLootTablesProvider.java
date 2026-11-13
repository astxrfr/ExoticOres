package net.kirks.exoticores.datagen;

import net.kirks.exoticores.block.RadioactiveMushroomBlock;
import net.kirks.exoticores.registry.ModBlocks;
import net.kirks.exoticores.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
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
        dropSelf(ModBlocks.THORITE_CRYSTAL_BLOCK.get());
        dropSelf(ModBlocks.CATALYZER_TABLE_BLOCK.get());
        dropSelf(ModBlocks.LEAD_BLOCK.get());
        dropSelf(ModBlocks.RAW_LEAD_BLOCK.get());
        dropSelf(ModBlocks.KYANITE_BLOCK.get());

        // ORE DROPS
        add(
                ModBlocks.THORITE_ORE.get(),
                createOreDrop(ModBlocks.THORITE_ORE.get(), ModItems.RAW_THORITE.get())
        );
        add(
                ModBlocks.LEAD_ORE.get(),
                createOreDrop(ModBlocks.LEAD_ORE.get(), ModItems.RAW_LEAD.get())
        );
        add(
                ModBlocks.DEEPSLATE_LEAD_ORE.get(),
                createOreDrop(ModBlocks.DEEPSLATE_LEAD_ORE.get(), ModItems.RAW_LEAD.get())
        );

        // SHROOM
        var mushroom = ModBlocks.RADIOACTIVE_MUSHROOM.get();
        add(mushroom, LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(applyExplosionDecay(mushroom, LootItem.lootTableItem(mushroom)
                                .apply(CopyBlockState.copyState(mushroom).copy(RadioactiveMushroomBlock.AGE))))
        ));
    }

    @Override
    protected @NonNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries()
                .stream()
                .map(entry -> (Block) entry.get())
                .toList();
    }
}
