package net.kirks.exoticores.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.Set;

public class RadiationEffect extends MobEffect {
    private static final int TICK_EFFECT_INTERVAL = 80;

    private static final Set<EntityType<?>> UNAFFECTED_ENTITIES = Set.of(
            EntityTypes.ENDERMITE,
            EntityTypes.ENDERMAN,
            EntityTypes.ENDER_DRAGON,
            EntityTypes.SHULKER
    );

    public RadiationEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        if (UNAFFECTED_ENTITIES.contains(mob.getType())) return true;
        mob.hurtServer(serverLevel, mob.damageSources().magic(), 0.5f + amplification*amplification/2f);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return tickCount % TICK_EFFECT_INTERVAL == 0;
    }
}
