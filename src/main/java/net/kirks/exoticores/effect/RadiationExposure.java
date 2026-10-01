package net.kirks.exoticores.effect;

import net.kirks.exoticores.network.payload.GeigerPayload;
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

public class RadiationExposure {
    public static final int INITIAL_DURATION = 10;

    private static final Map<LivingEntity, Float> PENDING = new WeakHashMap<>();

    private static void advance(@Nonnull LivingEntity entity, @Nonnull MobEffectInstance currentInstance, float intensity) {
        entity.addEffect(new MobEffectInstance(
                currentInstance.getEffect(),
                currentInstance.getDuration()+(int)intensity,
                currentInstance.getAmplifier() + 1,
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
                PacketDistributor.sendToPlayer((ServerPlayer) entity, new GeigerPayload(intensity));
        }
    }

    public static void contribute(@Nonnull LivingEntity entity, float intensity) {
        if (entity.level().isClientSide()) return;
        if (Float.isInfinite(intensity) || intensity <= 0) return;

        PENDING.merge(entity, intensity, Math::max);
    }

    @SubscribeEvent
    public static void beforeServerTick(ServerTickEvent.Pre event) {
        PENDING.clear();
    }

    @SubscribeEvent
    public static void afterServerTick(ServerTickEvent.Post event) {
        var contributions = Map.copyOf(PENDING);
        PENDING.clear();

        contributions.forEach((entity, intensity) -> {
            if (entity.isRemoved() || !entity.isAlive()) return;

            if (!entity.hasEffect(ModEffects.RADIATION)) {
                entity.addEffect(new MobEffectInstance(ModEffects.RADIATION, INITIAL_DURATION, 0));
            }

            attemptToAdvance(entity, intensity);
        });
    }
}
