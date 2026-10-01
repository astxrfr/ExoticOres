package net.kirks.exoticores.network;

import net.kirks.exoticores.network.payload.GeigerPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModNetworkEvents {
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(GeigerPayload.TYPE, GeigerPayload.STREAM_CODEC);
    }

    @EventBusSubscriber(value = Dist.CLIENT)
    public static class Client {
        public static void registerClientHandlers(RegisterClientPayloadHandlersEvent event) {
            event.register(GeigerPayload.TYPE, ((payload, context) -> GeigerTickClient.receive(payload.intensity())));
        }
    }
}
