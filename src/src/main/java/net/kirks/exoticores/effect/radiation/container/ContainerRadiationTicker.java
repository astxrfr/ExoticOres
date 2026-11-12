package net.kirks.exoticores.effect.radiation.container;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashSet;
import java.util.Set;

public class ContainerRadiationTicker {
    private static final Set<BlockEntity> REGISTRY = new HashSet<>();

    public static void addListener(BlockEntity block) {
        if (REGISTRY.contains(block)) return;

        REGISTRY.add(block);
    }

    public static void removeListener(BlockEntity block) {
        REGISTRY.remove(block);
    }

    public static void tick(ServerLevel level) {
        REGISTRY.forEach(block -> {
            if (block.getLevel() != level || block.isRemoved()) return;

            var container = (RadiationContainer) block;
            container.exoticOres$tick();
        });
    }
}
