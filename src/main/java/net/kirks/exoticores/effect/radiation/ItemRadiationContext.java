package net.kirks.exoticores.effect.radiation;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Context carrying the server level, item stack, and entity associated with a radiation event.
 * This record is used to pass the world state and target entity to radiation-related logic without exposing mutable state.
 *
 * @param level the {@link ServerLevel} in which the radiation effect occurs
 * @param stack the {@link ItemStack} associated with the effect
 * @param entity the {@link Entity} associated with the effect
 */
public record ItemRadiationContext(ServerLevel level, ItemStack stack, Entity entity) implements RadiationContext {
    /**
     * Returns the level containing this radiation context.
     *
     * @return this context's server level
     */
    @Override
    public Level getLevel() {
        return level;
    }
}
