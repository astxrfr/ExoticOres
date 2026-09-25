package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.menu.WaterFurnaceMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(
                    Registries.MENU,
                    ExoticOres.MODID
            );

    public static final Supplier<MenuType<WaterFurnaceMenu>> WATER_FURNACE_MENU =
            MENU_TYPES.register(
                    "water_furnace",
                    () -> new MenuType<>(
                            WaterFurnaceMenu::new,
                            FeatureFlags.DEFAULT_FLAGS
                    )
            );

    private ModMenuTypes() {
    }
}