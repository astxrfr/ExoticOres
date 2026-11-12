package net.kirks.exoticores.item.poison.hit;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import org.jspecify.annotations.NonNull;

public class PoisonHitAxeItem extends AxeItem implements PoisonHitItemEmitter {
  public PoisonHitAxeItem(ToolMaterial material, float attackDamageBaseline, float attackSpeedBaseline, Properties properties) {
    super(material, attackDamageBaseline, attackSpeedBaseline, properties);
  }

  @Override
  public void postHurtEnemy(@NonNull ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
    applyPoison(mob, attacker);
  }
}