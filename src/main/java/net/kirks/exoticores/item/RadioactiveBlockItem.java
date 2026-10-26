package net.kirks.exoticores.item;

import net.kirks.exoticores.effect.radiation.ItemRadiationContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class RadioactiveBlockItem extends BlockItem implements BaseRadioactiveItem {
    public RadioactiveBlockItem(Block block, Properties properties) {
        super(block, properties);
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
