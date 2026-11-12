package net.kirks.exoticores.item.poison.hit;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.ExoticOresConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public interface PoisonHitItemEmitter {

  default void applyPoison(LivingEntity target, LivingEntity attacker) {
    if (!ExoticOresConfig.POISON_HIT_ENABLED.get()) return;

    if (attacker.level() != target.level()) {
      ExoticOres.LOGGER.warn("congratulations! you broke the game! (attacker's level is not the same as the attacked entity, skipping poison application)", new IllegalStateException());
      return;
    }

    if (!(target.level() instanceof ServerLevel)) return;

    int durationTicks = ExoticOresConfig.POISON_HIT_DURATION_SECONDS.get() * 20;
    int amplifier = ExoticOresConfig.POISON_HIT_AMPLIFIER.get();

    target.addEffect(new MobEffectInstance(MobEffects.POISON, durationTicks, amplifier), attacker);
  }
}