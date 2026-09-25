package net.kirks.exoticores.block;

import net.kirks.exoticores.block.entity.WaterFurnaceBlockEntity;
import net.kirks.exoticores.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class WaterFurnaceBlock extends Block implements EntityBlock {

    public WaterFurnaceBlock(Properties properties) {
        super(properties);
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
                instanceof WaterFurnaceBlockEntity furnace) {

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
        return new WaterFurnaceBlockEntity(pos, state);
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
                ModBlockEntities.WATER_FURNACE.get(),
                WaterFurnaceBlockEntity::serverTick
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