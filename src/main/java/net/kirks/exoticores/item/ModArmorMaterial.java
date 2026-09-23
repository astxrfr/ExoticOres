package net.kirks.exoticores.item;

import com.google.common.collect.Maps;
import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.registry.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.neoforged.neoforge.transfer.resource.Resource;

import java.util.Map;

public class ModArmorMaterial {
  public static final ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));
    public static final ResourceKey<EquipmentAsset> THORITE_KEY = ResourceKey.create(ROOT_ID, Identifier.fromNamespaceAndPath(ExoticOres.MODID, "Thorite"));

    public static final ArmorMaterial THORITE_ARMOR_MATERIAL = new ArmorMaterial(1200, MakeDefense(5, 7, 9, 5, 11), 16, SoundEvents.ARMOR_EQUIP_NETHERITE, 2f, ModTags.);


    private static Map<ArmorType, Integer> MakeDefense(int boots, int legs, int chest, int helm, int body) {
      return Maps.newEnumMap(
              Map.of(ArmorType.BOOTS, boots, ArmorType.LEGGINGS, legs, ArmorType.CHESTPLATE, chest, ArmorType.HELMET, helm, ArmorType.BODY, body)
      );
    }
}
