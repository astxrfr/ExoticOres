package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.block.RadioactiveBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ExoticOres.MODID);

    private static final BlockBehaviour.Properties thorite_ore_properties = BlockBehaviour.Properties.of()
            .lightLevel((var) -> 3)
            .mapColor(MapColor.COLOR_PURPLE);

    public static final DeferredBlock<RadioactiveBlock> THORITE_ORE =
            BLOCKS.registerBlock("thorite_ore", RadioactiveBlock::new, () -> thorite_ore_properties);
}
