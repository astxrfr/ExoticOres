package net.kirks.exoticores.block;

import com.mojang.serialization.MapCodec;
import net.kirks.exoticores.block.entity.RadioactiveBlockEntity;
import net.kirks.exoticores.effect.radiation.BlockRadiationContext;
import net.kirks.exoticores.registry.ModBlockEntities;
import net.kirks.exoticores.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class RadioactiveMushroomBlock extends VegetationBlock implements EntityBlock {
    public static final MapCodec<RadioactiveMushroomBlock> CODEC = simpleCodec(RadioactiveMushroomBlock::new);
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 2);
    public static final int MAX_AGE = 2;

    private static final int GROWTH_CHANCE = 5;
    private static final int SPREAD_CHANCE = 25;
    private static final int SPREAD_RADIUS = 4;
    private static final int MAX_NEARBY = 5;
    private static final VoxelShape[] SHAPES = {
            Block.box(5, 0, 5, 11, 5, 11),
            Block.box(4, 0, 4, 12, 9, 12),
            Block.box(2, 0, 2, 14, 13, 14)
    };

    public RadioactiveMushroomBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public MapCodec<RadioactiveMushroomBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.KYANITE.get());
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(AGE)];
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
            return;
        }

        int age = state.getValue(AGE);
        if (age < MAX_AGE) {
            if (random.nextInt(GROWTH_CHANCE) == 0)
                level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
            return;
        }

        if (random.nextInt(SPREAD_CHANCE) != 0) return;

        int nearby = 0;
        for (BlockPos candidate : BlockPos.betweenClosed(
                pos.offset(-SPREAD_RADIUS, -1, -SPREAD_RADIUS),
                pos.offset(SPREAD_RADIUS, 1, SPREAD_RADIUS))) {
            if (level.getBlockState(candidate).is(this) && ++nearby >= MAX_NEARBY) return;
        }

        for (int attempt = 0; attempt < 4; attempt++) {
            BlockPos candidate = pos.offset(random.nextInt(9) - 4, random.nextInt(3) - 1, random.nextInt(9) - 4);
            if (level.isEmptyBlock(candidate) && defaultBlockState().canSurvive(level, candidate)) {
                level.setBlock(candidate, defaultBlockState(), Block.UPDATE_ALL);
                return;
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RadioactiveBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide() || type != ModBlockEntities.RADIOACTIVE_BLOCK_ENTITY.get()) return null;

        return (lvl, pos, current, blockEntity) -> {
            if (current.getValue(AGE) == MAX_AGE && current.canSurvive(lvl, pos))
                ((RadioactiveBlockEntity) blockEntity).emit(new BlockRadiationContext(lvl, pos));
        };
    }
}
