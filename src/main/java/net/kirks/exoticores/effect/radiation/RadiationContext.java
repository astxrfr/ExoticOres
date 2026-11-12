package net.kirks.exoticores.effect.radiation;

import net.minecraft.world.level.Level;

/**
 * Represents the context required for radiation-related behavior.
 * Implementations expose the {@link Level} in which the radiation is evaluated.
 */
public interface RadiationContext {
    /**
     * Returns the level instance associated with this radiation context.
     *
     * @return the Minecraft world level in which the radiation logic operates
     */
    Level getLevel();
}
