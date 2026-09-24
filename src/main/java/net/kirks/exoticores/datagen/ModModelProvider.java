package net.kirks.exoticores.datagen;

import net.kirks.exoticores.registry.ModBlocks;
import net.kirks.exoticores.registry.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        registerBlocks(blockModels);

        registerItems(itemModels);
    }

    private void registerBlocks(BlockModelGenerators blockModels) {
        // TRIVIAL CUBES
        blockModels.createTrivialCube(ModBlocks.THORITE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.THORITE_BLOCK.get());
    }

    private void registerItems(ItemModelGenerators itemModels) {
        // FLAT ITEMS
        itemModels.generateFlatItem(ModItems.THORITE_SHARD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_THORITE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.THORITE_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.THORITE_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.THORITE_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.THORITE_BOOTS.get(), ModelTemplates.FLAT_ITEM);
    }
}
