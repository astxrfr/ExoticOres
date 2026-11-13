package net.kirks.exoticores.recipe;

import net.kirks.exoticores.registry.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;

/** Pocion de regeneracion normal + pulpa. No admite agua, pociones arrojadizas ni persistentes. */
public class MyceliumBrewingRecipe implements IBrewingRecipe {
    @Override
    public boolean isInput(ItemStack input) {
        return input.is(Items.POTION)
                && input.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).is(Potions.REGENERATION);
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.is(ModItems.MUSHROOM_PULP.get());
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        return isInput(input) && isIngredient(ingredient)
                ? new ItemStack(ModItems.MYCELIUM_SERUM.get()) : ItemStack.EMPTY;
    }
}
