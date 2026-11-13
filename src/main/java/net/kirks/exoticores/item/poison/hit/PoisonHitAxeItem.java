package net.kirks.exoticores.item.poison.hit;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import org.jspecify.annotations.NonNull;


/**
 * An axe that applies poison damage effects after striking an enemy.
 * This item extends the base axe behavior and emits a poison hit effect
 * when the player successfully damages a living target.
 */
public class PoisonHitAxeItem extends AxeItem implements PoisonHitItemEmitter {
  /**
   * Creates a poison-inflicting axe with the given material and combat stats.
   */
  public PoisonHitAxeItem(ToolMaterial material, float attackDamageBaseline, float attackSpeedBaseline, Properties properties) {
    super(material, attackDamageBaseline, attackSpeedBaseline, properties);
  }

  @Override
  public void postHurtEnemy(@NonNull ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
    applyPoison(mob, attacker);
  }
}