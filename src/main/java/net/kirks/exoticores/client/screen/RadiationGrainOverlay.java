package net.kirks.exoticores.client.screen;

import net.kirks.exoticores.ExoticOres;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = ExoticOres.MODID, value = Dist.CLIENT)
public class RadiationGrainOverlay {
    private static final Identifier LAYER =
            Identifier.fromNamespaceAndPath(ExoticOres.MODID, "radiation_grain");

    private static final Identifier GRAIN =
            Identifier.fromNamespaceAndPath(ExoticOres.MODID, "screen/radiation/grain");

    private RadiationGrainOverlay() {}

    @SubscribeEvent
    public static void registerLayers(RegisterGuiLayersEvent event) {
        event.registerBelowAll(LAYER, RadiationGrainOverlay::new);
    }

    private static void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        
    }
}
