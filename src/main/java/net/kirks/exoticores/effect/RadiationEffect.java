package net.kirks.exoticores.effect;

import net.kirks.exoticores.registry.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.Set;

public class RadiationEffect extends MobEffect {
    private static final int TICK_EFFECT_INTERVAL = 80;
    private static final int MAX_RADIATION_STAGE = 5;

    private static final Set<EntityType<?>> UNAFFECTED_ENTITIES = Set.of(
            EntityTypes.ENDERMITE,
            EntityTypes.ENDERMAN,
            EntityTypes.ENDER_DRAGON,
            EntityTypes.SHULKER
    );

    public RadiationEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    private void regressStage(@Nonnull LivingEntity entity, @Nonnull MobEffectInstance currentInstance) {
        if (currentInstance.getAmplifier() == 0) return;

        entity.addEffect(new MobEffectInstance(
                currentInstance.getEffect(),
                2400,
                currentInstance.getAmplifier() - 1,
                currentInstance.isAmbient(),
                currentInstance.isVisible(),
                currentInstance.showIcon()
        ));
    }

    private void advanceStage(@Nonnull LivingEntity entity, @Nonnull MobEffectInstance currentInstance) {
        if (currentInstance.getAmplifier() < MAX_RADIATION_STAGE) {
            entity.addEffect(new MobEffectInstance(
                    currentInstance.getEffect(),
                    2400,
                    currentInstance.getAmplifier() + 1,
                    currentInstance.isAmbient(),
                    currentInstance.isVisible(),
                    currentInstance.showIcon()
            ));
        } else {
            entity.addEffect(new MobEffectInstance(
                    currentInstance.getEffect(),
                    2400,
                    currentInstance.getAmplifier(),
                    currentInstance.isAmbient(),
                    currentInstance.isVisible(),
                    currentInstance.showIcon()
            ));
        }
    }

    public void attemptToAdvanceStage(LivingEntity entity) {
        if (entity.level().isClientSide()) return;
        if (UNAFFECTED_ENTITIES.contains(entity.getType())) return;

        MobEffectInstance current = entity.getEffect(ModEffects.RADIATION);

        if(current == null) return;

        int remainingTicks = current.getDuration();
        if(remainingTicks < 100) advanceStage(entity, current);
    }

    @Override
    public boolean applyEffectTick(@Nonnull ServerLevel serverLevel, LivingEntity mob, int amplification) {
        if (UNAFFECTED_ENTITIES.contains(mob.getType())) return true;

        mob.hurtServer(
                serverLevel,
                mob.damageSources().magic(),
                0.5f + amplification*amplification/25f
        );

        MobEffectInstance currentEffect = mob.getEffect(ModEffects.RADIATION);

        if(currentEffect != null) {
            int duration = currentEffect.getDuration();
            if (duration >= 100) return true;

            regressStage(mob, currentEffect);
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        int interval = Math.max(
                1,
                (int) (TICK_EFFECT_INTERVAL/(1.0 + amplification * 1.5))
        );

        return tickCount % interval == 0;
    }


}
