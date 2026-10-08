package net.kirks.exoticores.effect;

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

    // Va, 20 ticks son un segundo: aqui dejamos 10 segundos.
    private static final int TOOL_RADIATION_DURATION = 20 * 10;

    // Estas entidades ya eran inmunes a la radiacion en el mod.
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

        // Solo lo procesa el servidor y si el objetivo sigue vivo.
        if (target.level().isClientSide() || !target.isAlive()) {
            return;
        }

        // Si el golpe no le quito vida, no aplicamos radiacion.
        if (event.getHealthDamage() <= 0) {
            return;
        }

        DamageSource source = event.getSource();

        if (!(source.getEntity() instanceof LivingEntity)) {
            return;
        }

        // El atacante debe haber golpeado directamente al objetivo.
        if (source.getDirectEntity() != source.getEntity()) {
            return;
        }

        // Incluimos los golpes normales y los ataques de la lanza.
        // Asi no se activa por flechas, espinas u otros efectos.
        boolean meleeHit =
                source.is(DamageTypes.PLAYER_ATTACK)
                        || source.is(DamageTypes.MOB_ATTACK)
                        || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO)
                        || source.is(DamageTypes.SPEAR);

        if (!meleeHit) {
            return;
        }

        // Revisamos el arma del golpe, no todo el inventario.
        ItemStack weapon = source.getWeaponItem();

        if (weapon == null || !isThoriteTool(weapon)) {
            return;
        }

        MobEffectInstance current =
                target.getEffect(ModEffects.RADIATION);

        // Si ya tiene radiacion mas fuerte, no la modificamos.
        if (current != null && current.getAmplifier() > 0) {
            return;
        }

        // Amplificador 0 significa Radiacion I.
        // Otro golpe renueva la duracion sin acumular niveles.
        target.addEffect(
                new MobEffectInstance(
                        ModEffects.RADIATION,
                        TOOL_RADIATION_DURATION,
                        0
                ),
                source.getEntity()
        );
    }

    private static boolean isThoriteTool(ItemStack stack) {
        // Las seis herramientas pueden aplicar radiacion.
        return stack.is(ModItems.THORITE_AXE.get())
                || stack.is(ModItems.THORITE_SHOVEL.get())
                || stack.is(ModItems.THORITE_SWORD.get())
                || stack.is(ModItems.THORITE_PICKAXE.get())
                || stack.is(ModItems.THORITE_HOE.get())
                || stack.is(ModItems.THORITE_SPEAR.get());
    }

    @SubscribeEvent
    public static void onEffectRemove(MobEffectEvent.Remove event) {
        if (event.getEffect() != ModEffects.RADIATION) {
            return;
        }

        // Conservamos la regla del mod: la leche no cura radiacion.
        if (event.getEntity().getUseItem().is(Items.MILK_BUCKET)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEffectApplicable(MobEffectEvent.Applicable event) {
        if (!event.getEffectInstance()
                .getEffect()
                .is(ModEffects.RADIATION.getKey())) {
            return;
        }

        LivingEntity entity = event.getEntity();

        boolean immuneEntity =
                UNAFFECTED_ENTITIES_RADIATION.contains(entity.getType());

        // Necesita las cuatro piezas marcadas como protectoras.
        boolean wearingProtection =
                entity.getItemBySlot(EquipmentSlot.HEAD)
                        .is(ModTags.Items.RADIATION_PROTECTIVE)
                        && entity.getItemBySlot(EquipmentSlot.CHEST)
                        .is(ModTags.Items.RADIATION_PROTECTIVE)
                        && entity.getItemBySlot(EquipmentSlot.LEGS)
                        .is(ModTags.Items.RADIATION_PROTECTIVE)
                        && entity.getItemBySlot(EquipmentSlot.FEET)
                        .is(ModTags.Items.RADIATION_PROTECTIVE);

        if (immuneEntity || wearingProtection) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }
}