package net.kirks.exoticores.datagen;
import net.kirks.exoticores.ExoticOres;
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
    protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        var thoriteTexture = Identifier.fromNamespaceAndPath(ExoticOres.MODID, "thorite");
        var radSuitTexture = Identifier.fromNamespaceAndPath(ExoticOres.MODID, "radiation_suit");

        output.accept(
                ModArmorMaterial.THORITE_ARMOR_MATERIAL.assetId(),
                EquipmentClientInfo.builder()
                        .addLayers(
                                EquipmentClientInfo.LayerType.HUMANOID,
                                new EquipmentClientInfo.Layer(thoriteTexture, Optional.empty(), false)
                        )
                        .addLayers(
                                EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS,
                                new EquipmentClientInfo.Layer(thoriteTexture, Optional.empty(), false)
                        )
                        .build()
        );

        output.accept(
                ModArmorMaterial.RADIATION_SUIT_MATERIAL.assetId(),
                EquipmentClientInfo.builder()
                        .addLayers(
                                EquipmentClientInfo.LayerType.HUMANOID,
                                new EquipmentClientInfo.Layer(radSuitTexture, Optional.empty(), false)
                        )
                        .addLayers(
                                EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS,
                                new EquipmentClientInfo.Layer(radSuitTexture, Optional.empty(), false)
                        )
                        .build()
        );
    }
}
