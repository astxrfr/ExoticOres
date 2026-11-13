package net.kirks.exoticores.item.poison.hit;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import org.jspecify.annotations.NonNull;

/**
 * A hoe that applies its poison effect when it hurts an enemy.
 */
public class PoisonHitHoeItem extends HoeItem implements PoisonHitItemEmitter {
  /**
   * Creates a poison-emitting hoe with the specified combat attributes.
   */
  public PoisonHitHoeItem(ToolMaterial material, float attackDamageBaseline, float attackSpeedBaseline, Properties properties) {
    super(material, attackDamageBaseline, attackSpeedBaseline, properties);
  }

  @Override
  public void postHurtEnemy(@NonNull ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
    applyPoison(mob, attacker);
  }
}