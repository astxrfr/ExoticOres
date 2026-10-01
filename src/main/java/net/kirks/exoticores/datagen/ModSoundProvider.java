package net.kirks.exoticores.datagen;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.registry.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundProvider extends SoundDefinitionsProvider {
    protected ModSoundProvider(PackOutput output) {
        super(output, ExoticOres.MODID);
    }

    @Override
    public void registerSounds() {
        add(ModSounds.GEIGER_CLICK, SoundDefinition.definition().with(
                sound(Identifier.fromNamespaceAndPath(ExoticOres.MODID, "geiger_click1")),
                sound(Identifier.fromNamespaceAndPath(ExoticOres.MODID, "geiger_click2")),
                sound(Identifier.fromNamespaceAndPath(ExoticOres.MODID, "geiger_click3")),
                sound(Identifier.fromNamespaceAndPath(ExoticOres.MODID, "geiger_click4")))
            .subtitle("subtitles.exoticores.geiger_click")
        );
    }
}
