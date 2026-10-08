package net.kirks.exoticores.block.entity;

import net.kirks.exoticores.Config;
import net.kirks.exoticores.effect.RadiationExposure;
import net.kirks.exoticores.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class RadioactiveBlockEntity extends BlockEntity {
    private static final double EFFECT_RANGE = 10 * Config.RADIATION_RADIUS_MULT.get();

    public RadioactiveBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.RADIOACTIVE_BLOCK_ENTITY.get(), worldPosition, blockState);
    }

    public static void tick(Level level, BlockPos pos) {
        if (level.isClientSide()) return;
        AABB range = new AABB(pos).inflate(EFFECT_RANGE);

        for (LivingEntity mob : level.getEntitiesOfClass(LivingEntity.class, range)) {
            var distance = Math.sqrt(pos.distToCenterSqr(mob.position()));
            float intensity = (float) Math.max(1, (10 - distance));
            RadiationExposure.contribute(mob, intensity);
        }
    }
}
