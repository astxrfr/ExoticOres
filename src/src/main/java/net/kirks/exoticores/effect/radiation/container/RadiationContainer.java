package net.kirks.exoticores.effect.radiation.container;

import net.kirks.exoticores.block.entity.RadiationBlockEmitter;

public interface RadiationContainer extends RadiationBlockEmitter {
    void exoticOres$tick();
    boolean exoticOres$hasRadioactiveItem();
}
