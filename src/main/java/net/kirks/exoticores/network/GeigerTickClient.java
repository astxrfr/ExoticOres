package net.kirks.exoticores.network;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.registry.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class GeigerTickClient {
    private static float target = 0f;
    private static float smoothed = 0f;
    private static long lastPacketTick = -1000;

    public static void receive(float intensity) {
        target = intensity;
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) lastPacketTick = mc.level.getGameTime();
    }

    @SubscribeEvent
    public static void onFrame(RenderFrameEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.isPaused()) return;

        if (mc.level.getGameTime() - lastPacketTick > 12) target = 0f;

        smoothed += (target - smoothed) * 0.08f;
        if (smoothed < 0.01f) return;

        RandomSource rand = mc.player.getRandom();
        if (rand.nextInt(40) > smoothed) return;

        mc.getSoundManager().play(SimpleSoundInstance.forUI(
                ModSounds.GEIGER_CLICK.value(),
                1.0f,
                rand.nextFloat() * 0.5f
        ));
    }
}
