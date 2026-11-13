package net.kirks.exoticores.item;

import net.kirks.exoticores.registry.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class RadioactiveMushroomBlockItem extends RadioactiveBlockItem {
    public RadioactiveMushroomBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    public static int getAge(ItemStack stack) {
        String age = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY)
                .properties().getOrDefault("age", "0");
        return switch (age) {
            case "1" -> 1;
            case "2" -> 2;
            default -> 0;
        };
    }

    public static ItemStack createStack(int age) {
        if (age < 0 || age > 2) throw new IllegalArgumentException("La etapa debe ser 0, 1 o 2");
        ItemStack stack = new ItemStack(ModItems.RADIOACTIVE_MUSHROOM.get());
        stack.set(DataComponents.BLOCK_STATE, new BlockItemStateProperties(Map.of("age", Integer.toString(age))));
        return stack;
    }
}
