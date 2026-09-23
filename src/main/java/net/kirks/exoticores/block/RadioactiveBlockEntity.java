package net.kirks.exoticores.block;

import net.kirks.exoticores.effect.RadiationEffect;
import net.kirks.exoticores.registry.ModBlockEntities;
import net.kirks.exoticores.registry.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class RadioactiveBlockEntity extends BlockEntity {
    public RadioactiveBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.RADIOACTIVE_BLOCK_ENTITY.get(), worldPosition, blockState);
    }

    public static void tick(Level level, BlockPos pos) {
        if (level.isClientSide()) return;
        AABB range = new AABB(pos).inflate(10);

        for (LivingEntity mob : level.getEntitiesOfClass(LivingEntity.class, range)) {
            if (mob.hasEffect(ModEffects.RADIATION)) {
                RadiationEffect effect = (RadiationEffect) mob.getEffect(ModEffects.RADIATION).getEffect().value();
                effect.attemptToAdvanceStage(mob);
                continue;
            }
            mob.addEffect(new MobEffectInstance(ModEffects.RADIATION, 2400, 0));
        }
    }
}
