package net.kirks.exoticores.item.poison.hit;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class PoisonHitItem extends Item implements PoisonHitItemEmitter {
  public PoisonHitItem(Properties properties) {
    super(properties);
  }

  @Override
  public void postHurtEnemy(@NonNull ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
    applyPoison(mob, attacker);
  }
}