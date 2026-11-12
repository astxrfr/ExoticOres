package net.kirks.exoticores.block;

import net.kirks.exoticores.block.entity.RadioactiveBlockEntity;
import net.kirks.exoticores.effect.radiation.BlockRadiationContext;
import net.kirks.exoticores.effect.radiation.Radioactive;
import net.kirks.exoticores.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class RadioactiveBlock extends Block implements EntityBlock {
    public RadioactiveBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RadioactiveBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        if (type != ModBlockEntities.RADIOACTIVE_BLOCK_ENTITY.get()) return null;

        return level.isClientSide()
                ? null
                : (lvl, pos, _, block) -> ((RadioactiveBlockEntity)block).emit(new BlockRadiationContext(lvl, pos));
    }
}