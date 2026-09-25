package net.kirks.exoticores.datagen;

import net.kirks.exoticores.registry.ModBlocks;
import net.kirks.exoticores.registry.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.data.PackOutput;
import org.jspecify.annotations.NonNull;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
        registerBlocks(blockModels);

        registerItems(itemModels);
    }

    private void registerBlocks(BlockModelGenerators blockModels) {
        // TRIVIAL CUBES
        blockModels.createTrivialCube(ModBlocks.THORITE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.THORITE_BLOCK.get());

        // TRIVIAL BLOCKS
        blockModels.createHorizontallyRotatedBlock(
                ModBlocks.CATALYZER_TABLE_BLOCK.get(),
                TexturedModel.createDefault(block -> new TextureMapping()
                    .put(TextureSlot.UP, TextureMapping.getBlockTexture(block, "_up"))
                    .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(block, "_down"))
                    .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(block, "_north"))
                    .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(block, "_south"))
                    .put(TextureSlot.EAST, TextureMapping.getBlockTexture(block, "_east"))
                    .put(TextureSlot.WEST, TextureMapping.getBlockTexture(block, "_west"))
                    .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_north")),
                ModelTemplates.CUBE
            ));
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
