package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.item.RadioactiveMushroomBlockItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExoticOres.MODID);

    public static final Supplier<CreativeModeTab> EXOTIC_ORES_TAB = CREATIVE_MODE_TABS.register(
            "exotic_ores_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.exoticores.exotic_ores_tab"))
                    .icon(() -> ModItems.THORITE_SHARD.get().getDefaultInstance())
                    .displayItems((params, output) -> {
                        ModItems.ITEMS.getEntries().forEach(entry -> output.accept(entry.get()));
                        output.accept(RadioactiveMushroomBlockItem.createStack(1));
                        output.accept(RadioactiveMushroomBlockItem.createStack(2));
                    })
                    .build()
    );
}
