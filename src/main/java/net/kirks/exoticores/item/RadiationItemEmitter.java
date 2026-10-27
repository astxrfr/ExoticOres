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

public interface RadiationItemEmitter extends Radioactive<ItemRadiationContext> {
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
