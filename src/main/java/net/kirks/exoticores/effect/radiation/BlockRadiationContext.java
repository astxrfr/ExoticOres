package net.kirks.exoticores.effect.radiation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.lang.annotation.Annotation;

public record BlockRadiationContext(Level level, BlockPos pos) implements RadiationContext {
    @Override
    public Level getLevel() {
        return level;
    }
}
