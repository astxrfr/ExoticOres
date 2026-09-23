package net.kirks.exoticores.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ThoriteArmorMaterial implements ArmorMaterial {
  private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11}
  private static final int[] PROTECTION_PER_SLOT = new int[]{3, 8, 6, 3}

  @Override
  public int getDefenseForSlot(@NotNull EquipmentSlot slot) {
    return PROTECTION_PER_SLOT[slot.getIndex()];
  }

  @Override
  public int getEnchantmentValue() {
    return 15;
  }

  @Override
  public @NotNull net.minecraft.sounds.SoundEvent getEquipSound() {
    return SoundEvents.ARMOR_EQUIP_GENERIC;
  }

  @Override
  public @NotNull Ingredient getRepairIngredient() {
    return Ingredient.of(ModItems.THORITE_SHARD.get());
  }

  @Override
  public @NotNull String getName() {
    return "thorite";
  }

  @Override
  public float getToughness() {
    return 2.0f;
  }

  @Override
  public float getKnockbackResistance() {
    return 0.1f;
  }
}