package net.kirks.exoticores.effect;

import net.kirks.exoticores.registry.ModEffects;
import net.kirks.exoticores.registry.ModDataAttachments;
import net.kirks.exoticores.effect.radiation.RadiationExposure;
import net.kirks.exoticores.tags.ModTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import java.util.Set;

public class ModEffectEvents {
    private static final Set<EntityType<?>> UNAFFECTED_ENTITIES_RADIATION = Set.of(
            EntityTypes.ENDERMITE,
            EntityTypes.ENDERMAN,
            EntityTypes.ENDER_DRAGON,
            EntityTypes.SHULKER
    );

    @SubscribeEvent
    public static void onEffectApplicable(MobEffectEvent.Applicable event) {
        if (!event.getEffectInstance().getEffect().is(ModEffects.RADIATION.getKey())) return;

        if (RadiationExposure.isHealingReplacement(event.getEffectInstance())) {
            event.setResult(MobEffectEvent.Applicable.Result.APPLY);
            return;
        }

        boolean inmuneEntity = UNAFFECTED_ENTITIES_RADIATION.contains(event.getEntity().getType());

        boolean wearingProtection =
                event.getEntity().getItemBySlot(EquipmentSlot.HEAD).is(ModTags.Items.RADIATION_PROTECTIVE)
                && event.getEntity().getItemBySlot(EquipmentSlot.CHEST).is(ModTags.Items.RADIATION_PROTECTIVE)
                && event.getEntity().getItemBySlot(EquipmentSlot.LEGS).is(ModTags.Items.RADIATION_PROTECTIVE)
                && event.getEntity().getItemBySlot(EquipmentSlot.FEET).is(ModTags.Items.RADIATION_PROTECTIVE);

        if (inmuneEntity || wearingProtection)
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
    }

    @SubscribeEvent
    public static void onRadiationExpired(MobEffectEvent.Expired event) {
        var instance = event.getEffectInstance();
        if (instance != null && instance.getEffect().is(ModEffects.RADIATION.getKey()))
            event.getEntity().setData(ModDataAttachments.RADIATION_COOLDOWN, 0);
    }
}
