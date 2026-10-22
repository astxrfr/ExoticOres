package net.kirks.exoticores.client.screen;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.effect.radiation.RadiationExposureManager;
import net.kirks.exoticores.network.payload.RadiationIntensityPayload;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import java.util.Random;

@EventBusSubscriber(modid = ExoticOres.MODID, value = Dist.CLIENT)
public class RadiationScreenOverlay {
    private static final int TILE_SIZE = 64;
    private static final float UPPER_THRESHOLD = 0.12f;

    private static float target = 0f;
    private static float exposure = 0f;
    private static long lastPacketTick = -1000;

    private static final Identifier VIGNETTE =
            Identifier.fromNamespaceAndPath(ExoticOres.MODID, "screen/radiation/vignette");

    private static final Identifier LAYER =
            Identifier.fromNamespaceAndPath(ExoticOres.MODID, "radiation_grain");

    private static final Identifier GRAIN =
            Identifier.fromNamespaceAndPath(ExoticOres.MODID, "screen/radiation/grain");

    private RadiationScreenOverlay() {}

    public static void recieve(RadiationIntensityPayload payload) {
        target = (float) (payload.intensity() / RadiationExposureManager.EFFECT_RANGE);
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) lastPacketTick = mc.level.getGameTime();
    }

    @SubscribeEvent
    public static void registerLayers(RegisterGuiLayersEvent event) {
        event.registerBelowAll(LAYER, RadiationScreenOverlay::render);
    }

    private static void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.level == null) return;
        if (mc.level.getGameTime() - lastPacketTick > 12) target = 0f;

        exposure += (target - exposure) * 0.08f;

        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();

        drawGrain(graphics, width, height);
        drawVignette(graphics, width, height);
        drawTextIndicator(graphics, width, height, mc);
    }

    private static void drawGrain(GuiGraphicsExtractor graphics, int width, int height) {
        float opacity = exposure * UPPER_THRESHOLD;
        int alpha = Math.round(Math.clamp(opacity, 0.0f, 1.0f) * 255.0f);
        int color = (alpha << 24) | 0x00FFFFFF;

        if (alpha == 0) return;

        graphics.enableScissor(0, 0, width, height);

        try {
            for (int y = 0; y < height; y += TILE_SIZE) {
                for (int x = 0; x < width; x += TILE_SIZE) {
                    int column = x / TILE_SIZE;
                    int row = y / TILE_SIZE;

                    int hash = column * 374761393 + row * 668265263;
                    hash = (hash ^ (hash >>> 13)) * 1274126177;
                    hash ^= hash >>> 16;

                    int quarterTurns = hash & 3;
                    float angle = quarterTurns * ((float) Math.PI / 2.0f);

                    var pose = graphics.pose();
                    pose.pushMatrix();

                    try {
                        pose.translate(x + TILE_SIZE / 2.0f, y + TILE_SIZE / 2.0f);
                        pose.rotate(angle);

                        graphics.blitSprite(
                                RenderPipelines.GUI_TEXTURED,
                                GRAIN,
                                -TILE_SIZE / 2,
                                -TILE_SIZE / 2,
                                TILE_SIZE,
                                TILE_SIZE,
                                color
                        );
                    } finally {
                        pose.popMatrix();
                    }
                }
            }
        } finally {
            graphics.disableScissor();
        }
    }

    private static void drawVignette(GuiGraphicsExtractor graphics, int width, int height) {
        float vignetteOpacity = Math.clamp(exposure * 0.35F, 0.0F, 1.0F);
        int vignetteAlpha = Math.round(vignetteOpacity * 255);

        if (vignetteAlpha > 0) {
            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    VIGNETTE,
                    0,
                    0,
                    width,
                    height,
                    (vignetteAlpha << 24) | 0x00FFFFFF
            );
        }
    }

    //TODO: make this effect stand out better
    private static void drawTextIndicator(GuiGraphicsExtractor graphics, int width, int height, Minecraft mc) {
        var dose = exposure*10;
        var format = String.format("%.2f mSv/h", dose);
        MutableComponent label = Component.literal(format).withStyle(ChatFormatting.BOLD);

        var rdm = new Random();

        int xPos = width / 2;
        int yPos = height / 16;
        int alphaB = dose > 0.01 ? 160 : 0;
        int alphaF = dose > 0.01 ? 200 : 0;

        var displacement = Math.max(0, exposure);
        float xOffset = (rdm.nextFloat() * 2 - 1) * displacement;
        float yOffset = (rdm.nextFloat() * 2 - 1) * displacement;

        drawOffsetText(
                graphics,
                mc,
                label,
                xPos,
                yPos,
                -xOffset,
                -yOffset,
                (alphaB << 24) | 0x005cc8e2
        );
        drawOffsetText(
                graphics,
                mc,
                label,
                xPos,
                yPos,
                xOffset,
                yOffset,
                (alphaF << 24) | 0x0089249c
        );
    }

    private static void drawOffsetText(
            GuiGraphicsExtractor graphics,
            Minecraft mc,
            Component label,
            int x,
            int y,
            float offsetX,
            float offsetY,
            int color
    ) {
        float scale = 1.25f;

        var pose = graphics.pose();
        pose.pushMatrix();

        try {
            pose.translate(x+offsetX, y+offsetY);
            pose.scale(scale);
            graphics.text(mc.font, label, -mc.font.width(label)/2, 0, color, true);
        } finally {
            pose.popMatrix();
        }
    }
}
