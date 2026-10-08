package net.kirks.exoticores.effect;

import net.kirks.exoticores.registry.ModDataAttachments;
import net.kirks.exoticores.registry.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;

public class RadiationEffect extends MobEffect {
    private static final int TICK_EFFECT_INTERVAL = 80;

    public RadiationEffect(MobEffectCategory category, int color) {
        super(category, color);
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
