package net.kirks.exoticores.registry;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (DeferredBlock<? extends Block> deferredBlock : ModBlocks.REGISTERED_BLOCKS) {
            blockModels.createTrivialCube(deferredBlock.get());
        }

        for (DeferredItem<? extends BlockItem> deferredItem : ModItems.REGISTERED_ITEMS) {
            itemModels.generateFlatItem(deferredItem.get(), ModelTemplates.FLAT_ITEM);
        }
    }
}
