package net.kirks.exoticores.effect.radiation;

import net.kirks.exoticores.ExoticOresConfig;
import net.kirks.exoticores.network.payload.RadiationIntensityPayload;
import net.kirks.exoticores.registry.ModEffects;
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

    private static void advance(@Nonnull LivingEntity entity, @Nonnull MobEffectInstance currentInstance, float intensity) {
        entity.addEffect(new MobEffectInstance(
                currentInstance.getEffect(),
                currentInstance.getDuration()+(int)intensity,
                currentInstance.getAmplifier(),
                currentInstance.isAmbient(),
                currentInstance.isVisible(),
                currentInstance.showIcon()
        ));
    }

    private static void attemptToAdvance(LivingEntity entity, float intensity) {
        MobEffectInstance current = entity.getEffect(ModEffects.RADIATION);
        if(current == null) return;

        advance(entity, current, intensity);

        if (entity.is(EntityTypes.PLAYER)) {
            if (entity.tickCount % 4 == 0)
                PacketDistributor.sendToPlayer((ServerPlayer) entity, new RadiationIntensityPayload(intensity));
        }
    }

    public static void contribute(@Nonnull LivingEntity entity, float intensity) {
        if (isValid(entity, intensity))
            PENDING.merge(entity, intensity, Float::sum);
    }

    public static void contributeHit(@Nonnull LivingEntity entity, float intensity) {
        if (isValid(entity, intensity))
            attemptToAdvance(entity, intensity);
    }

    private static boolean isValid(@Nonnull LivingEntity entity, float intensity) {
        if (RADIATION_DISABLED.getAsBoolean()) return false;
        if (entity.level().isClientSide()) return false;
        if (Float.isInfinite(intensity) || intensity <= 0) return false;
        return true;
    }

    @SubscribeEvent
    public static void afterServerTick(ServerTickEvent.Post event) {
        var mappedContributions = Map.copyOf(PENDING);
        PENDING.clear();

        mappedContributions.forEach((entity, intensity) -> {
            if (entity.isRemoved() || !entity.isAlive()) return;

            if (!entity.hasEffect(ModEffects.RADIATION)) {
                entity.addEffect(new MobEffectInstance(ModEffects.RADIATION, INITIAL_DURATION, 0));
            }

            intensity = (float) (EFFECT_INTENSITY * Math.log(intensity+1));
            attemptToAdvance(entity, intensity);
        });
    }
}
