package net.kirks.exoticores.effect.radiation;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.ExoticOresConfig;
import net.kirks.exoticores.network.payload.RadiationIntensityPayload;
import net.kirks.exoticores.registry.ModEffects;
import net.kirks.exoticores.registry.ModDataAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class RadiationExposure {
    public static final int INITIAL_DURATION = 10;
    public static final float EFFECT_INTENSITY = 3f;

    private static final BooleanSupplier RADIATION_DISABLED = () -> !ExoticOresConfig.RADIATION_ENABLED.get();
    public static final DoubleSupplier EFFECT_RANGE = () -> 10 * ExoticOresConfig.RADIATION_RADIUS_MULT.get();

    private static final Map<LivingEntity, Float> PENDING = new WeakHashMap<>();
    private static final ThreadLocal<MobEffectInstance> HEALING_REPLACEMENT = new ThreadLocal<>();

    private static void advance(@Nonnull LivingEntity entity, @Nonnull MobEffectInstance currentInstance, float intensity) {
        if (currentInstance.isInfiniteDuration()) return;
        int duration = (int) Math.min(Integer.MAX_VALUE, (long) currentInstance.getDuration() + (int) intensity);
        entity.addEffect(new MobEffectInstance(
                currentInstance.getEffect(),
                duration,
                currentInstance.getAmplifier(),
                currentInstance.isAmbient(),
                currentInstance.isVisible(),
                currentInstance.showIcon()
        ));
    }

    private static void attemptToAdvance(LivingEntity entity, float intensity) {
        if (!entity.hasEffect(ModEffects.RADIATION))
            entity.addEffect(new MobEffectInstance(ModEffects.RADIATION, INITIAL_DURATION, 0));

        MobEffectInstance current = entity.getEffect(ModEffects.RADIATION);
        if (current == null) return;

        advance(entity, current, intensity);

        if (entity.is(EntityTypes.PLAYER)) {
            if (entity.tickCount % 4 == 0)
                PacketDistributor.sendToPlayer((ServerPlayer) entity, new RadiationIntensityPayload(intensity));
        }
    }
    
    /**
     * Queues radiation exposure to be combined with other contributions and applied after the server tick.
     *
     * @param entity the living entity receiving exposure
     * @param intensity the positive amount of exposure to contribute in ticks
     */
    public static void contribute(@Nonnull LivingEntity entity, float intensity) {
        if (isValid(entity, intensity))
            PENDING.merge(entity, intensity, Float::sum);
    }

    /**
     * Immediately increases the entity's radiation effect by the supplied intensity.
     *
     * @param entity the living entity receiving radiation
     * @param intensity the positive amount by which to advance the effect in ticks
     */
    public static void contributeHit(@Nonnull LivingEntity entity, float intensity) {
        if (isValid(entity, intensity))
            attemptToAdvance(entity, intensity);
    }

    public static void contributeHeal(@Nonnull LivingEntity entity, float intensity) {
        if (Float.isFinite(intensity) && intensity > 0)
            contributeHeal(entity, (int) Math.min(intensity, Integer.MAX_VALUE));
    }

    public static boolean contributeHeal(@Nonnull LivingEntity entity, int reductionTicks) {
        if (entity.level().isClientSide() || entity.isRemoved() || !entity.isAlive()) return false;
        if (reductionTicks <= 0) return false;

        MobEffectInstance current = entity.getEffect(ModEffects.RADIATION);
        if (current == null || current.isInfiniteDuration()) return false;

        int remaining = Math.max(0, current.getDuration() - reductionTicks);
        if (remaining == 0) {
            boolean removed = entity.removeEffect(ModEffects.RADIATION);
            if (removed) entity.setData(ModDataAttachments.RADIATION_COOLDOWN, 0);
            return removed;
        }

        MobEffectInstance reduced = new MobEffectInstance(
                current.getEffect(), remaining, current.getAmplifier(),
                current.isAmbient(), current.isVisible(), current.showIcon()
        );
        int cooldown = entity.getData(ModDataAttachments.RADIATION_COOLDOWN);
        HEALING_REPLACEMENT.set(reduced);
        try {
            if (!entity.removeEffect(ModEffects.RADIATION)) return false;
            boolean applied = entity.addEffect(reduced);
            entity.setData(ModDataAttachments.RADIATION_COOLDOWN, applied ? cooldown : 0);
            return applied;
        } finally {
            HEALING_REPLACEMENT.remove();
        }
    }

    public static boolean isHealingReplacement(MobEffectInstance effectInstance) {
        return effectInstance != null && HEALING_REPLACEMENT.get() == effectInstance;
    }

    private static boolean isValid(@Nonnull LivingEntity entity, float intensity) {
        if (RADIATION_DISABLED.getAsBoolean()) return false;
        if (entity.level().isClientSide()) return false;
        if (!Float.isFinite(intensity) || intensity <= 0) return false;
        if (entity.isRemoved() || !entity.isAlive()) return false;
        return true;
    }

    @SubscribeEvent
    public static void afterServerTick(ServerTickEvent.Post event) {
        var mappedContributions = Map.copyOf(PENDING);
        PENDING.clear();

        mappedContributions.forEach((entity, intensity) -> {
            if (entity.isRemoved() || !entity.isAlive()) return;

            intensity = (float) (EFFECT_INTENSITY * Math.log(intensity+1));
            attemptToAdvance(entity, intensity);
        });
    }
}
