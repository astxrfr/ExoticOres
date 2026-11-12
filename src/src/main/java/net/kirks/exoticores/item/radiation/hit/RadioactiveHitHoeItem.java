package net.kirks.exoticores.item.radiation.hit;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.effect.radiation.ItemRadiationContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import org.jspecify.annotations.NonNull;

public class RadioactiveHitHoeItem extends HoeItem implements RadioactiveHitItemEmitter {
    public RadioactiveHitHoeItem(ToolMaterial material, float attackDamageBaseline, float attackSpeedBaseline, Properties properties) {
        super(material, attackDamageBaseline, attackSpeedBaseline, properties);
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
