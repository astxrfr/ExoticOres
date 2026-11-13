package net.kirks.exoticores.effect.radiation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.lang.annotation.Annotation;

/**
 * Immutable context carrying the level and block position associated with a
 * radiation event.
 *
 * <p>This record is used to pass the world state and target location to
 * radiation-related logic without exposing mutable state.</p>
 *
 * @param level the Minecraft {@link Level} instance where the radiation event should occur
 * @param pos the {@link BlockPos} representing the block position originating the radiation event
 */
public record BlockRadiationContext(Level level, BlockPos pos) implements RadiationContext {
    /**
     * Returns the level in which the radiation context was created.
     *
     * @return the Minecraft {@link Level} instance associated with this context
     */
    @Override
    public Level getLevel() {
        return level;
    }
}
