package net.kirks.exoticores.recipe;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

public class ModBrewingEvents {
    @SubscribeEvent
    public static void registerRecipes(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addRecipe(new MyceliumBrewingRecipe());
    }
}
