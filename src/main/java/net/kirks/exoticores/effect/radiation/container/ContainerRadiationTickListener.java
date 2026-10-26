package net.kirks.exoticores.effect.radiation.container;

import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

public class ContainerRadiationTickListener {

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel level) {
            ContainerRadiationTicker.tick(level);
        }
    }
}
