package net.kirks.exoticores.item.poison.hit;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

/**
 * Represents a poison-inflicting item that applies its effect when it harms an enemy.
 */
public class PoisonHitItem extends Item implements PoisonHitItemEmitter {
  /**
   * Creates a new poison hit item with the specified item properties.
   */
  public PoisonHitItem(Properties properties) {
    super(properties);
  }

  @Override
  public void postHurtEnemy(@NonNull ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
    applyPoison(mob, attacker);
  }
}