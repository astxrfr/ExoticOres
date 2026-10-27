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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class CatalyzerTableBlock extends Block implements EntityBlock {

    // Hacia donde mira el frente del catalizador.
    public static final EnumProperty<Direction> FACING =
            BlockStateProperties.HORIZONTAL_FACING;

    // Aqui guardamos si esta encendido o apagado.
    public static final BooleanProperty LIT =
            BlockStateProperties.LIT;

    public CatalyzerTableBlock(Properties properties) {
        super(properties);

        // Al colocarlo empieza apagado.
        registerDefaultState(
                defaultBlockState()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(LIT, false)
        );
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hit
    ) {
        // Abrimos el menu desde el servidor.
        if (!level.isClientSide()
                && level.getBlockEntity(pos)
                instanceof CatalyzerTableBlockEntity catalyzer) {

            player.openMenu(catalyzer);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        // Esta entidad guarda los materiales, combustible y progreso.
        return new CatalyzerTableBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity>
    @Nullable BlockEntityTicker<T> getTicker(
            Level level,
            BlockState state,
            BlockEntityType<T> type
    ) {
        // La logica se ejecuta solamente en el servidor.
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
    protected void createBlockStateDefinition(
            StateDefinition.@NonNull Builder<Block, BlockState> builder
    ) {
        super.createBlockStateDefinition(builder);

        // Registramos la orientacion y el estado de encendido.
        builder.add(FACING, LIT);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(
            BlockPlaceContext context
    ) {
        // El frente queda mirando hacia quien coloca el bloque.
        return defaultBlockState()
                .setValue(
                        FACING,
                        context.getHorizontalDirection().getOpposite()
                )
                .setValue(LIT, false);
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
        // Solo usamos el ticker si corresponde a esta entidad.
        if (receivedType != expectedType) {
            return null;
        }

        return (BlockEntityTicker<T>) ticker;
    }
}