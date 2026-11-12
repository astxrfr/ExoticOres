package net.kirks.exoticores.effect.radiation;

import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Defines behavior for objects that can emit radiation. Classes implementing this interface must implement {@link #radiate(C)}
 * for {@link #emit(C)} to use.
 *
 * @param <C> the type of {@link RadiationContext} that this object will use
 */
public interface Radioactive<C extends RadiationContext> {
    /**
     * Emits radiation via {@link RadiationExposure#contribute(LivingEntity, float)} for each entity provided by {@link #radiate(C)}.
     * Skips emission if {@link #radiate(C)} returns null.
     *
     * @param context the context to use for radiation emission
     */
    default void emit(C context) {
        var c = radiate(context);
        if (c == null) return;

        c.forEach(RadiationExposure::contribute);
    }

    /**
     * Calculates the entities exposed to radiation and the amount of radiation each should receive.
     * Implementations may return {@code null} when no radiation should be emitted for the given context.
     *
     * @param context the radiation context used to determine exposure
     * @return a mapping of affected living entities to radiation values, or {@code null} if no entities are affected
     */
    @Nullable
    Map<? extends LivingEntity, Float> radiate(C context);
}
