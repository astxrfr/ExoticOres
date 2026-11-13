package net.kirks.exoticores.effect.radiation.container;

import net.kirks.exoticores.block.entity.RadiationBlockEmitter;

/**
 * Represents a container capable of interacting with radioactive state updates.
 * Implementors are expected to participate in the radiation tick lifecycle and
 * expose whether they currently hold a radioactive item. This interface is
 * intended for mixin implementation on container block entities.
 */
public interface RadiationContainer extends RadiationBlockEmitter {
    /**
     * Executes a single tick of radiation logic for this container.
     */
    void exoticOres$tick();

    /**
     * Checks whether the container currently contains a radioactive item.
     *
     * @return {@code true} if a radioactive item is present; {@code false} otherwise
     */
    boolean exoticOres$hasRadioactiveItem();
}
