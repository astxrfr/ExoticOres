package net.kirks.exoticores.item;

import net.kirks.exoticores.ExoticOres;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.neoforged.neoforge.transfer.resource.Resource;

public class ModArmorMaterial {
  public static final ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));
    public static final ResourceKey<EquipmentAsset> THORITE_KEY = ResourceKey.create(ROOT_ID, Identifier.fromNamespaceAndPath(ExoticOres.MODID, "Thorite"));
}
