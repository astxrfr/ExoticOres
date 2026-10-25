package net.kirks.exoticores.block.entity;

import net.kirks.exoticores.effect.radiation.BlockRadiationContext;
import net.kirks.exoticores.effect.radiation.RadiationExposure;
import net.kirks.exoticores.effect.radiation.Radioactive;
import net.kirks.exoticores.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class RadioactiveBlockEntity extends BlockEntity implements Radioactive<BlockRadiationContext> {
    public RadioactiveBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.RADIOACTIVE_BLOCK_ENTITY.get(), worldPosition, blockState);
    }

    @Override
    public @Nullable Map<LivingEntity, Float> radiate(BlockRadiationContext context) {
        var pos = context.pos();
        var level = context.level();
        double radius = RadiationExposure.EFFECT_RANGE;
        if (level.isClientSide()) return null;
        AABB range = new AABB(pos).inflate(radius);

        var affected = new WeakHashMap<LivingEntity, Float>();
        for (LivingEntity mob : level.getEntitiesOfClass(LivingEntity.class, range)) {
            var distance = Math.sqrt(pos.distToCenterSqr(mob.position()));
            float intensity = (float) Math.max(0, (radius - distance));
            affected.put(mob, intensity);
        }

        return affected;
    }
}
