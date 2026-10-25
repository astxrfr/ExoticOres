package net.kirks.exoticores.item;

import net.kirks.exoticores.effect.radiation.ItemRadiationContext;
import net.kirks.exoticores.effect.radiation.Radioactive;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class RadioactiveBlockItem extends BlockItem implements Radioactive<ItemRadiationContext> {
    public RadioactiveBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        emit(new ItemRadiationContext(level, itemStack, owner));
    }

    @Override
    public @Nullable Map<? extends LivingEntity, Float> radiate(ItemRadiationContext context) {
        if (context.level().isClientSide()) return null;

        if (context.owner() instanceof Player player) {
            return Map.of(player, 10f);
        }

        return null;
    }
}
