package net.kirks.exoticores.effect;

import net.kirks.exoticores.registry.ModEffects;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class ModEffectEvents {
    @SubscribeEvent
    public static void onEffectRemove(MobEffectEvent.Remove event) {
        if (event.getEffect() != ModEffects.RADIATION) return;

        if (event.getEntity().getUseItem().is(Items.MILK_BUCKET)) event.setCanceled(true);
    }
}
