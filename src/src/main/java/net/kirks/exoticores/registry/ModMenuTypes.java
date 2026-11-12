package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.menu.CatalyzerTableMenu;
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

    public static final Supplier<MenuType<CatalyzerTableMenu>> CATALYZER_TABLE_MENU =
            MENU_TYPES.register(
                    "catalyzer_table_menu",
                    () -> new MenuType<>(
                            CatalyzerTableMenu::new,
                            FeatureFlags.DEFAULT_FLAGS
                    )
            );

    private ModMenuTypes() {
    }
}