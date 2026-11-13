package net.kirks.exoticores.datagen;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.block.CatalyzerTableBlock;
import net.kirks.exoticores.block.RadioactiveMushroomBlock;
import net.kirks.exoticores.registry.ModBlocks;
import net.kirks.exoticores.registry.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.item.properties.select.ItemBlockState;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
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
        blockModels.createTrivialCube(ModBlocks.THORITE_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.THORITE_CRYSTAL_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.LEAD_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_LEAD_ORE.get());
        blockModels.createTrivialCube(ModBlocks.LEAD_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.RAW_LEAD_BLOCK.get());

        // TRIVIAL BLOCKS
        createKyaniteModel(blockModels);

        blockModels.createTrivialBlock(
                ModBlocks.THORITE_ORE.get(),
                TexturedModel.createDefault(
                        block -> new TextureMapping()
                                .put(
                                        TextureSlot.SIDE,
                                        new Material(Identifier.fromNamespaceAndPath(
                                                "exoticores",
                                                "block/thorite_ore_side"
                                        ))
                                )
                                .put(
                                        TextureSlot.TOP,
                                        new Material(Identifier.fromNamespaceAndPath(
                                                "exoticores",
                                                "block/thorite_ore_top"
                                        ))
                                )
                                .put(
                                        TextureSlot.BOTTOM,
                                        new Material(Identifier.fromNamespaceAndPath(
                                                "exoticores",
                                                "block/thorite_ore_top"
                                        ))
                                ),
                        ModelTemplates.CUBE_BOTTOM_TOP
                )
        );

        createMushroomModels(blockModels);
        createCatalyzerModels(blockModels);
    }

    private void createKyaniteModel(BlockModelGenerators blockModels) {
        var modelPasive = ModelTemplates.CUBE_BOTTOM_TOP.create(
                ModBlocks.KYANITE_BLOCK.get(),
                new TextureMapping()
                        .put(TextureSlot.TOP, new Material(Identifier.fromNamespaceAndPath(ExoticOres.MODID, "block/kyanite_block_pasive")))
                        .put(TextureSlot.BOTTOM, new Material(Identifier.fromNamespaceAndPath(ExoticOres.MODID, "block/kyanite_block_pasive")))
                        .put(TextureSlot.SIDE, new Material(Identifier.fromNamespaceAndPath(ExoticOres.MODID, "block/kyanite_block_side"))),
                blockModels.modelOutput);

        var modelActive = ModelTemplates.CUBE_BOTTOM_TOP.create(
                this.modLocation("block/kyanite_block_active"),
                new TextureMapping()
                        .put(TextureSlot.TOP, new Material(Identifier.fromNamespaceAndPath(ExoticOres.MODID, "block/kyanite_block_top")))
                        .put(TextureSlot.BOTTOM, new Material(Identifier.fromNamespaceAndPath(ExoticOres.MODID, "block/kyanite_block_down")))
                        .put(TextureSlot.SIDE, new Material(Identifier.fromNamespaceAndPath(ExoticOres.MODID, "block/kyanite_block_side"))),
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(
                ModBlocks.KYANITE_BLOCK.get(),
                new MultiVariant(WeightedList.of(
                        new Weighted<>(new Variant(modelPasive), 8),
                        new Weighted<>(new Variant(modelActive), 2)
                )))
        );
    }

    private void createMushroomModels(BlockModelGenerators blockModels) {
        var block = ModBlocks.RADIOACTIVE_MUSHROOM.get();
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block).with(
                        PropertyDispatch.initial(RadioactiveMushroomBlock.AGE)
                                .select(0, BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath("exoticores", "block/radioactive_mushroom_stage0")))
                                .select(1, BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath("exoticores", "block/radioactive_mushroom_stage1")))
                                .select(2, BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath("exoticores", "block/radioactive_mushroom_stage2")))
                )
        );
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

        // mycelium
        var smallShroom = ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath("exoticores", "item/radioactive_mushroom_stage0"));
        var mediumShroom = ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath("exoticores", "item/radioactive_mushroom_stage1"));
        var matureShroom = ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath("exoticores", "item/radioactive_mushroom_stage2"));
        itemModels.itemModelOutput.accept(ModItems.RADIOACTIVE_MUSHROOM.get(),
                ItemModelUtils.select(new ItemBlockState("age"), smallShroom,
                        ItemModelUtils.when("0", smallShroom), ItemModelUtils.when("1", mediumShroom), ItemModelUtils.when("2", matureShroom)));
        itemModels.itemModelOutput.accept(ModItems.MUSHROOM_PULP.get(),
                ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath("exoticores", "item/mushroom_pulp")));
        itemModels.itemModelOutput.accept(ModItems.MYCELIUM_SERUM.get(),
                ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath("exoticores", "item/mycelium_serum")));
    }
}