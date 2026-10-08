package net.kirks.exoticores.datagen;

import net.kirks.exoticores.registry.ModBlocks;
import net.kirks.exoticores.registry.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.PackOutput;
import org.jspecify.annotations.NonNull;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Override
    protected void registerModels(
            @NonNull BlockModelGenerators blockModels,
            @NonNull ItemModelGenerators itemModels) {

        registerBlocks(blockModels);
        registerItems(itemModels);
    }

    private void registerBlocks(BlockModelGenerators blockModels) {

        // Bloques que usan la misma textura en todas sus caras.
        blockModels.createTrivialCube(ModBlocks.THORITE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.THORITE_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.LEAD_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_LEAD_ORE.get());

        // La mesa tiene una textura diferente para cada cara.
        blockModels.createHorizontallyRotatedBlock(
                ModBlocks.CATALYZER_TABLE_BLOCK.get(),
                TexturedModel.createDefault(
                        block -> new TextureMapping()
                                .put(
                                        TextureSlot.UP,
                                        TextureMapping.getBlockTexture(block, "_up")
                                )
                                .put(
                                        TextureSlot.DOWN,
                                        TextureMapping.getBlockTexture(block, "_down")
                                )
                                .put(
                                        TextureSlot.NORTH,
                                        TextureMapping.getBlockTexture(block, "_north")
                                )
                                .put(
                                        TextureSlot.SOUTH,
                                        TextureMapping.getBlockTexture(block, "_south")
                                )
                                .put(
                                        TextureSlot.EAST,
                                        TextureMapping.getBlockTexture(block, "_east")
                                )
                                .put(
                                        TextureSlot.WEST,
                                        TextureMapping.getBlockTexture(block, "_west")
                                )
                                .put(
                                        TextureSlot.PARTICLE,
                                        TextureMapping.getBlockTexture(block, "_north")
                                ),
                        ModelTemplates.CUBE
                )
        );
    }

    private void registerItems(ItemModelGenerators itemModels) {

        // MATERIALES

        itemModels.generateFlatItem(
                ModItems.THORITE_SHARD.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.RAW_THORITE.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.RAW_LEAD.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.LEAD_INGOT.get(),
                ModelTemplates.FLAT_ITEM
        );

        // HERRAMIENTAS
        // Handheld coloca las herramientas en la mano con la orientacion adecuada.

        itemModels.generateFlatItem(
                ModItems.THORITE_PICKAXE.get(),
                ModelTemplates.FLAT_HANDHELD_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.THORITE_AXE.get(),
                ModelTemplates.FLAT_HANDHELD_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.THORITE_SHOVEL.get(),
                ModelTemplates.FLAT_HANDHELD_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.THORITE_HOE.get(),
                ModelTemplates.FLAT_HANDHELD_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.THORITE_SWORD.get(),
                ModelTemplates.FLAT_HANDHELD_ITEM
        );

        // La lanza usa sus modelos de inventario y de mano.
        itemModels.generateSpear(ModItems.THORITE_SPEAR.get());

        // ARMADURA DE THORITE

        itemModels.generateFlatItem(
                ModItems.THORITE_HELMET.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.THORITE_CHESTPLATE.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.THORITE_LEGGINGS.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.THORITE_BOOTS.get(),
                ModelTemplates.FLAT_ITEM
        );

        // TRAJE CONTRA RADIACION

        itemModels.generateFlatItem(
                ModItems.RADIATION_SUIT_HELMET.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.RADIATION_SUIT_CHESPLATE.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.RADIATION_SUIT_LEGGINGS.get(),
                ModelTemplates.FLAT_ITEM
        );

        itemModels.generateFlatItem(
                ModItems.RADIATION_SUIT_BOOTS.get(),
                ModelTemplates.FLAT_ITEM
        );
    }
}