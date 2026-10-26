package net.kirks.exoticores.effect;

import net.kirks.exoticores.effect.radiation.RadiationExposure;
import net.kirks.exoticores.registry.ModEffects;
import net.kirks.exoticores.registry.ModItems;
import net.kirks.exoticores.tags.ModTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import java.util.Set;

public class ModEffectEvents {
    private static final float TOOL_RADIATION_INTENSITY = 10000f;

    private static final Set<EntityType<?>> UNAFFECTED_ENTITIES_RADIATION =
            Set.of(
                    EntityTypes.ENDERMITE,
                    EntityTypes.ENDERMAN,
                    EntityTypes.ENDER_DRAGON,
                    EntityTypes.SHULKER
            );

    @SubscribeEvent
    public static void onToolHit(LivingDamageEvent.Post event) {
        LivingEntity target = event.getEntity();
        if (target.level().isClientSide() || !target.isAlive()) return;

        DamageSource source = event.getSource();
        if (source.getWeaponItem() == null || !isThoriteTool(source.getWeaponItem())) return;
        if (!(source.getEntity() instanceof LivingEntity)) return;

        if (source.getDirectEntity() != source.getEntity()) return;

        boolean meleeHit = source.is(DamageTypes.PLAYER_ATTACK)
                        || source.is(DamageTypes.MOB_ATTACK)
                        || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO)
                        || source.is(DamageTypes.SPEAR);

        if (!meleeHit) return;

        // TODO: improper implementation, switch to provided interfaces
        RadiationExposure.contribute(target, TOOL_RADIATION_INTENSITY);
    }

    private static boolean isThoriteTool(ItemStack stack) {
        // TODO: SWITCH TO TAG FOR FUCKS SAKE
        return stack.is(ModItems.THORITE_AXE.get())
                || stack.is(ModItems.THORITE_SHOVEL.get())
                || stack.is(ModItems.THORITE_SWORD.get())
                || stack.is(ModItems.THORITE_PICKAXE.get())
                || stack.is(ModItems.THORITE_HOE.get())
                || stack.is(ModItems.THORITE_SPEAR.get());
    }

    @SubscribeEvent
    public static void onEffectApplicable(MobEffectEvent.Applicable event) {
        if (!event.getEffectInstance().getEffect().is(ModEffects.RADIATION.getKey())) return;

        boolean inmuneEntity = UNAFFECTED_ENTITIES_RADIATION.contains(event.getEntity().getType());

        boolean wearingProtection =
                event.getEntity().getItemBySlot(EquipmentSlot.HEAD).is(ModTags.Items.RADIATION_PROTECTIVE)
                && event.getEntity().getItemBySlot(EquipmentSlot.CHEST).is(ModTags.Items.RADIATION_PROTECTIVE)
                && event.getEntity().getItemBySlot(EquipmentSlot.LEGS).is(ModTags.Items.RADIATION_PROTECTIVE)
                && event.getEntity().getItemBySlot(EquipmentSlot.FEET).is(ModTags.Items.RADIATION_PROTECTIVE);

        if (inmuneEntity || wearingProtection)
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
    }
}
