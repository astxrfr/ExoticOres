package net.kirks.exoticores.effect.radiation;

import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.WeakHashMap;

public interface Radioactive<C extends RadiationContext> {
    default void emit(C context) {
        var c = radiate(context);
        if (c == null) return;

        c.forEach(RadiationExposure::contribute);
    }

    @Nullable
    Map<? extends LivingEntity, Float> radiate(C context);

    default <CI extends ItemRadiationContext> Map<LivingEntity, Float> affectNearby(CI context) {
        var dropped = context.owner();
        double radius = RadiationExposure.EFFECT_RANGE;
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
