package net.kirks.exoticores.datagen;

import net.kirks.exoticores.block.CatalyzerTableBlock;
import net.kirks.exoticores.registry.ModBlocks;
import net.kirks.exoticores.registry.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Override
    protected void registerModels(
            @NonNull BlockModelGenerators blockModels,
            @NonNull ItemModelGenerators itemModels
    ) {
        registerBlocks(blockModels);
        registerItems(itemModels);
    }

    private void registerBlocks(BlockModelGenerators blockModels) {
        // TRIVIAL CUBES
        blockModels.createTrivialCube(ModBlocks.THORITE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.THORITE_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.LEAD_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_LEAD_ORE.get());

        createCatalyzerModels(blockModels);
    }

    private void createCatalyzerModels(BlockModelGenerators blockModels) {
        var block = ModBlocks.CATALYZER_TABLE_BLOCK.get();

        var off = ModelTemplates.CUBE.create(
                block,
                catalyzerTextures(false),
                blockModels.modelOutput
        );

        var on = ModelTemplates.CUBE.createWithSuffix(
                block,
                "_on",
                catalyzerTextures(true),
                blockModels.modelOutput
        );

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(
                                PropertyDispatch.initial(CatalyzerTableBlock.LIT)
                                        .select(
                                                false,
                                                BlockModelGenerators.plainVariant(off)
                                        )
                                        .select(
                                                true,
                                                BlockModelGenerators.plainVariant(on)
                                        )
                        )
                        .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
        );

        blockModels.registerSimpleItemModel(block, off);
    }

    private TextureMapping catalyzerTextures(boolean encendido) {
        var block = ModBlocks.CATALYZER_TABLE_BLOCK.get();

        Material front = new Material(
                Identifier.fromNamespaceAndPath(
                        "exoticores",
                        encendido
                                ? "block/catalyzer_animation_on"
                                : "block/catalyzer_animation_off"
                )
        );

        return new TextureMapping()
                .put(
                        TextureSlot.UP,
                        TextureMapping.getBlockTexture(block, "_up")
                )
                .put(
                        TextureSlot.DOWN,
                        TextureMapping.getBlockTexture(block, "_down")
                )
                .put(TextureSlot.NORTH, front)
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
                .put(TextureSlot.PARTICLE, front);
    }

    private void registerItems(ItemModelGenerators itemModels) {
        // Raw materials
        itemModels.generateFlatItem(ModItems.THORITE_SHARD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_THORITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_LEAD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LEAD_INGOT.get(), ModelTemplates.FLAT_ITEM);

        // Thorite tools
        itemModels.generateFlatItem(ModItems.THORITE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.THORITE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.THORITE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.THORITE_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.THORITE_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateSpear(ModItems.THORITE_SPEAR.get());

        // Lead tools
        itemModels.generateFlatItem(ModItems.LEAD_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.LEAD_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.LEAD_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.LEAD_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.LEAD_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateSpear(ModItems.LEAD_SPEAR.get());

        // thorite armor
        itemModels.generateFlatItem(ModItems.THORITE_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.THORITE_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.THORITE_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.THORITE_BOOTS.get(), ModelTemplates.FLAT_ITEM);

        // rad suit
        itemModels.generateFlatItem(ModItems.RADIATION_SUIT_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RADIATION_SUIT_CHESPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RADIATION_SUIT_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RADIATION_SUIT_BOOTS.get(), ModelTemplates.FLAT_ITEM);
    }
}