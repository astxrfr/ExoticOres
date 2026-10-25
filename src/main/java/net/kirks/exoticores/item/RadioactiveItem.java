package net.kirks.exoticores.item;

import net.kirks.exoticores.effect.radiation.ItemRadiationContext;
import net.kirks.exoticores.effect.radiation.RadiationExposure;
import net.kirks.exoticores.effect.radiation.Radioactive;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.WeakHashMap;

public class RadioactiveItem extends Item implements Radioactive<ItemRadiationContext>{
    public RadioactiveItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@NonNull ItemStack itemStack, ServerLevel level, @NonNull Entity owner, @Nullable EquipmentSlot slot) {
        emit(new ItemRadiationContext(level, itemStack, owner));
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (entity.level() instanceof ServerLevel level)
            emit(new ItemRadiationContext(level, stack, entity));

        return false;
    }

    @Override
    public @Nullable Map<? extends LivingEntity, Float> radiate(ItemRadiationContext context) {
        if (context.level().isClientSide()) return null;

        if (context.owner() instanceof ItemEntity) {
            return affectNearby(context);
        }

        if (context.owner() instanceof Container) {
            return affectNearby(context);
        }

        if (context.owner() instanceof Player player) {
            return Map.of(player, 10f);
        }

        return null;
    }
}
