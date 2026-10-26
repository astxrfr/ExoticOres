package net.kirks.exoticores.block.entity;

import net.kirks.exoticores.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RadioactiveBlockEntity extends BlockEntity implements RadiationBlockEmitter {
    public RadioactiveBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.RADIOACTIVE_BLOCK_ENTITY.get(), worldPosition, blockState);
    }
}
