package net.kirks.exoticores.item;

import net.kirks.exoticores.effect.radiation.ItemRadiationContext;
import net.kirks.exoticores.effect.radiation.RadiationExposure;
import net.kirks.exoticores.effect.radiation.Radioactive;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Defines an item emitter that emits radiation to its owner and nearby living entities.
 */
public interface RadiationItemEmitter extends Radioactive<ItemRadiationContext> {
    /**
     * Applies the radiation effect described by the provided context.
     * <p>
     * If the entity is an item dropped into the world, nearby living entities are affected based on distance.
     * If the entity is a player, the player receives exposure at the configured effect range.
     *
     * @param context the context that defines the entity, world, and radiation
     *                source being evaluated
     * @return a map of affected living entities to their radiation intensity, or
     *         {@code null} when the effect should not be applied
     */
    @Override
    default @Nullable Map<? extends LivingEntity, Float> radiate(ItemRadiationContext context) {
        if (context.level().isClientSide()) return null;

        if (context.entity() instanceof ItemEntity) {
            return affectNearby(context);
        }

        if (context.entity() instanceof Player player) {
            return Map.of(player, (float) RadiationExposure.EFFECT_RANGE.getAsDouble());
        }

        return null;
    }

    /**
     * Finds living entities within the active radiation radius and computes how
     * strongly each one is affected by the dropped item.
     * <p>
     * Closer targets receive greater intensity, while entities outside the range
     * are omitted from the result.
     *
     * @param context the context containing the dropped item and world state
     * @return a map of nearby living entities to their computed radiation level
     */
    default Map<LivingEntity, Float> affectNearby(ItemRadiationContext context) {
        var dropped = context.entity();
        double radius = RadiationExposure.EFFECT_RANGE.getAsDouble();
        var affected = new WeakHashMap<LivingEntity, Float>();

        for (LivingEntity target : context.level().getEntitiesOfClass(LivingEntity.class, dropped.getBoundingBox().inflate(radius))) {
            double distance = dropped.position().distanceTo(target.position());
            float intensity = (float) Math.max(0, (radius - distance));

            if (intensity > 0)
                affected.put(target, intensity);
        }

        return affected;
    }
}
