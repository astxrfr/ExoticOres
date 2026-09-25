package net.kirks.exoticores.block;

import net.kirks.exoticores.block.entity.CatalyzerTableBlockEntity;
import net.kirks.exoticores.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class CatalyzerTableBlock extends Block implements EntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CatalyzerTableBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    /*
     * Abre el menú cuando el jugador hace clic derecho
     * con la mano vacía.
     */
    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hit
    ) {
        if (!level.isClientSide()
                && level.getBlockEntity(pos)
                instanceof CatalyzerTableBlockEntity furnace) {

            player.openMenu(furnace);
        }

        return InteractionResult.SUCCESS;
    }

    /*
     * Crea el BlockEntity asociado al horno.
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        return new CatalyzerTableBlockEntity(pos, state);
    }

    /*
     * Ejecuta la lógica del horno solamente en el servidor.
     */
    @Override
    public <T extends BlockEntity>
    @Nullable BlockEntityTicker<T> getTicker(
            Level level,
            BlockState state,
            BlockEntityType<T> type
    ) {
        if (level.isClientSide()) {
            return null;
        }

        return createTickerHelper(
                type,
                ModBlockEntities.CATALYZER_TABLE_BLOCK_ENTITY.get(),
                CatalyzerTableBlockEntity::serverTick
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(
                FACING,
                context.getHorizontalDirection().getOpposite()
        );
    }

    @SuppressWarnings("unchecked")
    private static <
            T extends BlockEntity,
            E extends BlockEntity
            > @Nullable BlockEntityTicker<T> createTickerHelper(
            BlockEntityType<T> receivedType,
            BlockEntityType<E> expectedType,
            BlockEntityTicker<? super E> ticker
    ) {
        if (receivedType != expectedType) {
            return null;
        }

        return (BlockEntityTicker<T>) ticker;
    }
}