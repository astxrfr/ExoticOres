package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.block.RadioactiveBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.kirks.exoticores.block.CatalyzerTableBlock;


public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ExoticOres.MODID);

    // PROPERTIES
    private static final BlockBehaviour.Properties THORITE_ORE_PROPERTIES = BlockBehaviour.Properties.of()
            .requiresCorrectToolForDrops()
            .strength(25.0F, 1500.0F)
            .sound(SoundType.AMETHYST)
            .mapColor(MapColor.COLOR_PURPLE);

    private static final BlockBehaviour.Properties THORITE_BLOCK_PROPERTIES = BlockBehaviour.Properties.of()
            .lightLevel((_) -> 5)
            .requiresCorrectToolForDrops()
            .strength(20.0F, 1500.0F)
            .sound(SoundType.AMETHYST)
            .mapColor(MapColor.COLOR_PURPLE);

    private static final BlockBehaviour.Properties CATALYZER_TABLE_BLOCK_PROPERTIES = BlockBehaviour.Properties.of()
            .strength(3.5F)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .mapColor(MapColor.COLOR_PURPLE);

    private static final BlockBehaviour.Properties LEAD_ORE_PROPERTIES = BlockBehaviour.Properties.of()
            .requiresCorrectToolForDrops()
            .strength(7.0F)
            .sound(SoundType.STONE)
            .mapColor(DyeColor.GRAY);

    private static final BlockBehaviour.Properties DEEPSLATE_LEAD_ORE_PROPERTIES = BlockBehaviour.Properties.of()
            .requiresCorrectToolForDrops()
            .strength(9.0F)
            .sound(SoundType.DEEPSLATE)
            .mapColor(DyeColor.GRAY);

    // BLOCKS
    public static final DeferredBlock<RadioactiveBlock> THORITE_ORE =
            BLOCKS.registerBlock("thorite_ore", RadioactiveBlock::new, () -> THORITE_ORE_PROPERTIES);

    public static final DeferredBlock<Block> THORITE_BLOCK =
            BLOCKS.registerBlock("thorite_block", Block::new, () -> THORITE_BLOCK_PROPERTIES);

    public static final DeferredBlock<CatalyzerTableBlock> CATALYZER_TABLE_BLOCK =
            BLOCKS.registerBlock("catalyzer_table", CatalyzerTableBlock::new, () -> CATALYZER_TABLE_BLOCK_PROPERTIES);

    public static final DeferredBlock<Block> LEAD_ORE =
            BLOCKS.registerBlock("lead_ore", Block::new, () -> LEAD_ORE_PROPERTIES);

    public static final DeferredBlock<Block> DEEPSLATE_LEAD_ORE =
            BLOCKS.registerBlock("deepslate_lead_ore", Block::new, () -> DEEPSLATE_LEAD_ORE_PROPERTIES);
}

