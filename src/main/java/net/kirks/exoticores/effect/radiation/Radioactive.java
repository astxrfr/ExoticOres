package net.kirks.exoticores.effect.radiation;

import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.Map;

public interface Radioactive<C extends RadiationContext> {
    default void emit(C context) {
        var c = radiate(context);
        if (c == null) return;

        c.forEach(RadiationExposure::contribute);
    }

    @Nullable
    Map<? extends LivingEntity, Float> radiate(C context);
}
