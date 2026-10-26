package net.kirks.exoticores.item;

import net.kirks.exoticores.effect.radiation.ItemRadiationContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class RadioactiveItem extends Item implements RadiationItemEmitter {
    public RadioactiveItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@NonNull ItemStack itemStack, @NonNull ServerLevel level, @NonNull Entity owner, @Nullable EquipmentSlot slot) {
        emit(new ItemRadiationContext(level, itemStack, owner));
    }

    @Override
    public boolean onEntityItemUpdate(@NonNull ItemStack stack, ItemEntity entity) {
        if (entity.level() instanceof ServerLevel level)
            emit(new ItemRadiationContext(level, stack, entity));

        return false;
    }
}
