package net.kirks.exoticores.effect;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.network.payload.GeigerPayload;
import net.kirks.exoticores.registry.ModDataAttachments;
import net.kirks.exoticores.registry.ModEffects;
import net.kirks.exoticores.registry.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.Random;

public class RadiationEffect extends MobEffect {
    private static final int TICK_EFFECT_INTERVAL = 80;

    public static final int INITIAL_DURATION = 10;

    public RadiationEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    private void advance(@Nonnull LivingEntity entity, @Nonnull MobEffectInstance currentInstance, float intensity) {
        entity.addEffect(new MobEffectInstance(
                currentInstance.getEffect(),
                currentInstance.getDuration()+(int)intensity,
                currentInstance.getAmplifier() + 1,
                currentInstance.isAmbient(),
                currentInstance.isVisible(),
                currentInstance.showIcon()
        ));
    }

    public void attemptToAdvance(LivingEntity entity, float intensity) {
        if (entity.level().isClientSide()) return;

        MobEffectInstance current = entity.getEffect(ModEffects.RADIATION);
        if(current == null) return;

        advance(entity, current, intensity);

        if (entity.is(EntityTypes.PLAYER)) {
            if (entity.tickCount % 4 == 0)
                PacketDistributor.sendToPlayer((ServerPlayer) entity, new GeigerPayload(intensity));
        }
    }

    @Override
    public boolean applyEffectTick(@Nonnull ServerLevel serverLevel, LivingEntity mob, int amplification) {
        MobEffectInstance currentEffect = mob.getEffect(ModEffects.RADIATION);
        if (currentEffect == null) return false;

        int cooldown = mob.getData(ModDataAttachments.RADIATION_COOLDOWN.get()) - 1;
        if (cooldown > 0) {
            mob.setData(ModDataAttachments.RADIATION_COOLDOWN, cooldown);
            return true;
        }
        // TODO: add sound and graphic effects
        var duration = currentEffect.getDuration();
        float durationDamageFunction = (float) Math.sqrt(duration)/100.0f;
        float damage = Math.max(0.1f, durationDamageFunction);
        mob.hurtServer(serverLevel, mob.damageSources().magic(), damage);

        float durationIntervalFunction = (float) (1 - Math.pow(Math.E, -(duration*duration/Math.pow(2, 30))))*10;
        int nextInterval = Math.max(10, (int) (TICK_EFFECT_INTERVAL / Math.max(1.0, durationIntervalFunction)));
        mob.setData(ModDataAttachments.RADIATION_COOLDOWN, nextInterval);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int remainingDuration, int amplification) {
        return true;
    }
}
