package net.kirks.exoticores.block;

import net.kirks.exoticores.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

public class CatalyzerTableBlockEntity extends BlockEntity {
    public CatalyzerTableBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.CATALYZER_TABLE_BLOCK_ENTITY.get(), worldPosition, blockState);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
    }
}
