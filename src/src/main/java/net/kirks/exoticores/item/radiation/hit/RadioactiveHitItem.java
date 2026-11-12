package net.kirks.exoticores.item.radiation.hit;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.effect.radiation.ItemRadiationContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class RadioactiveHitItem extends Item implements RadioactiveHitItemEmitter {
    public RadioactiveHitItem(Properties properties) {
        super(properties);
    }

    @Override
    public void postHurtEnemy(@NonNull ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
        if (attacker.level() != mob.level()) {
            ExoticOres.LOGGER.warn("congratulations! you broke the game! (attacker's level is not the same as the attacked entity, skipping radiation application)", new IllegalStateException());
            return;
        }

        if (attacker.level() instanceof ServerLevel serverLevel)
            emit(new ItemRadiationContext(serverLevel, itemStack, mob));
    }
}
