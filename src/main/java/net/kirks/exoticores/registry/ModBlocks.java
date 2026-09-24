package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.block.RadioactiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ExoticOres.MODID);

    private static final BlockBehaviour.Properties thorite_ore_properties = BlockBehaviour.Properties.of()
            .requiresCorrectToolForDrops()
            .strength(60.0F, 1500.0F)
            .sound(SoundType.AMETHYST)
            .mapColor(MapColor.COLOR_PURPLE);
    private static final BlockBehaviour.Properties thorite_block_properties = BlockBehaviour.Properties.of()
            .lightLevel((_) -> 5)
            .requiresCorrectToolForDrops()
            .strength(30.0F, 1500.0F)
            .sound(SoundType.AMETHYST)
            .mapColor(MapColor.COLOR_PURPLE);

    public static final DeferredBlock<RadioactiveBlock> THORITE_ORE =
            BLOCKS.registerBlock("thorite_ore", RadioactiveBlock::new, () -> thorite_ore_properties);
    public static final DeferredBlock<Block> THORITE_BLOCK =
            BLOCKS.registerBlock("thorite_block", Block::new, () -> thorite_block_properties);
}
