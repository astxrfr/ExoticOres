package net.kirks.exoticores.datagen;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.worldgen.ModRegistrySetBuilder;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = ExoticOres.MODID, value = Dist.CLIENT)
public class ModDataGenerationEvents {
    @SubscribeEvent
    public static void onClientDataGather(GatherDataEvent.Client event) {
        event.createProvider(output -> new ModModelProvider(output, ExoticOres.MODID));

        event.createProvider(output -> new DatapackBuiltinEntriesProvider(
                output,
                event.getLookupProvider(),
                ModRegistrySetBuilder.BUILDER,
                Set.of(ExoticOres.MODID)
        ));

        event.createProvider(((output, registries) ->
            new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(
                    ModBlockLootTablesProvider::new,
                    LootContextParamSets.BLOCK
                    )
            ), registries)
        ));

        event.createProvider(ModBlockTagsProvider::new);

        event.createProvider(ModRecipeProvider.Runner::new);

        event.createProvider(ModEquipmentAssetProvider::new);
    }
}
