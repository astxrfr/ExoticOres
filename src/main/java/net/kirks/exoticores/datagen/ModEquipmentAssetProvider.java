package net.kirks.exoticores.datagen;
import net.kirks.exoticores.registry.ModArmorMaterial;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;


import java.util.Optional;
import java.util.function.BiConsumer;

public class ModEquipmentAssetProvider extends EquipmentAssetProvider{

    public ModEquipmentAssetProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void registerModels(
            BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output
    ) {
        var texture = Identifier.fromNamespaceAndPath(
                "exoticores", "thorite"
        );

        output.accept(
                ModArmorMaterial.THORITE_ARMOR_MATERIAL.assetId(),
                EquipmentClientInfo.builder()
                        .addLayers(
                                EquipmentClientInfo.LayerType.HUMANOID,
                                new EquipmentClientInfo.Layer(
                                        texture, Optional.empty(), false
                                )
                        )
                        .addLayers(
                                EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS,
                                new EquipmentClientInfo.Layer(
                                        texture, Optional.empty(), false
                                )
                        )
                        .build()
        );
    }
}
