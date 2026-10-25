package net.kirks.exoticores.effect.radiation;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public record ItemRadiationContext(ServerLevel level, ItemStack stack, Entity owner) implements RadiationContext {
    @Override
    public Level getLevel() {
        return level;
    }
}
