package net.kirks.exoticores.network;

import net.kirks.exoticores.client.screen.RadiationScreenOverlay;
import net.kirks.exoticores.client.sound.GeigerTickClient;
import net.kirks.exoticores.network.payload.RadiationIntensityPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModNetworkEvents {
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(RadiationIntensityPayload.TYPE, RadiationIntensityPayload.STREAM_CODEC);
    }

    @EventBusSubscriber(value = Dist.CLIENT)
    public static class Client {
        public static void registerClientHandlers(RegisterClientPayloadHandlersEvent event) {
            event.register(RadiationIntensityPayload.TYPE, Client::onRadiationIntensityUpdate);
        }

        private static void onRadiationIntensityUpdate(RadiationIntensityPayload payload, IPayloadContext context) {
            RadiationScreenOverlay.recieve(payload);
            GeigerTickClient.receive(payload);
        }
    }
}
