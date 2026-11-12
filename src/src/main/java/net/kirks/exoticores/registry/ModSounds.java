package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, ExoticOres.MODID);

    public static final Holder<SoundEvent> GEIGER_CLICK =
            SOUND_EVENTS.register("geiger_click", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(ExoticOres.MODID, "geiger_click")));
}
