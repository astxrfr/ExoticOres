package net.kirks.exoticores.item;

import net.kirks.exoticores.effect.RadiationEffect;
import net.kirks.exoticores.registry.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class RadioactiveItem extends Item {
    public RadioactiveItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@NonNull ItemStack itemStack, ServerLevel level, @NonNull Entity owner, @Nullable EquipmentSlot slot) {
        if (level.isClientSide()) return;

        if (owner instanceof Player player) {
            if (player.hasEffect(ModEffects.RADIATION)) {
                MobEffectInstance effectInstance = Objects.requireNonNull(player.getEffect(ModEffects.RADIATION));
                RadiationEffect effect = (RadiationEffect) effectInstance.getEffect().value();
                effect.attemptToAdvanceStage(player);
                return;
            }
            player.addEffect(new MobEffectInstance(ModEffects.RADIATION, RadiationEffect.PER_STAGE_DURATION, 0));
        }
    }
}
