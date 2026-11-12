package net.kirks.exoticores.item.radiation.hit;

import net.kirks.exoticores.ExoticOresConfig;
import net.kirks.exoticores.effect.radiation.ItemRadiationContext;
import net.kirks.exoticores.effect.radiation.RadiationExposure;
import net.kirks.exoticores.effect.radiation.Radioactive;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public interface RadioactiveHitItemEmitter extends Radioactive<ItemRadiationContext> {
    Supplier<Float> HIT_RADIATION_INTENSITY = () -> (float) (300 * ExoticOresConfig.RADIATION_HIT_MULT.getAsDouble());

    @Override
    default void emit(ItemRadiationContext context) {
        var c = radiate(context);
        if (c == null) return;

        c.forEach(RadiationExposure::contributeHit);
    }

    @Override
    default @Nullable Map<? extends LivingEntity, Float> radiate(ItemRadiationContext context) {
        if (context.level().isClientSide()) return null;

        if (context.entity() instanceof LivingEntity target)
            return Map.of(target, HIT_RADIATION_INTENSITY.get());

        return null;
    }
}
