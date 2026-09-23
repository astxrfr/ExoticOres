package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.item.RadioactiveItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExoticOres.MODID);
    public static final List<DeferredItem<? extends Item>> REGISTERED_ITEMS = new ArrayList<>();
    public static final List<DeferredItem<? extends BlockItem>> REGISTERED_BLOCK_ITEMS = new ArrayList<>();

    public static final DeferredItem<RadioactiveItem> THORITE_SHARD =
            ITEMS.registerItem("thorite_shard", RadioactiveItem::new, () -> new Item.Properties().rarity(Rarity.RARE));

    // BLOCK ITEMS
    public static final DeferredItem<BlockItem> THORITE_ORE_ITEM =
            ITEMS.registerSimpleBlockItem("thorite_ore", ModBlocks.THORITE_ORE);

    // EQUIPMENT
    public static final DeferredItem<Item> THORITE_HELMET = ITEMS.registerItem("thorite_helmet", properties ->
            new Item(properties.humanoidArmor(ModArmorMaterial.THORITE_ARMOR_MATERIAL, ArmorType.HELMET)));

    public static final DeferredItem<Item> THORITE_CHESTPLATE = ITEMS.registerItem("thorite_chestplate", properties ->
            new Item(properties.humanoidArmor(ModArmorMaterial.THORITE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));

    public static final DeferredItem<Item> THORITE_LEGGINGS = ITEMS.registerItem("thorite_leggings", properties ->
            new Item(properties.humanoidArmor(ModArmorMaterial.THORITE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));

    public static final DeferredItem<Item> THORITE_BOOTS = ITEMS.registerItem("thorite_boots", properties ->
            new Item(properties.humanoidArmor(ModArmorMaterial.THORITE_ARMOR_MATERIAL, ArmorType.BOOTS)));


    static {
        REGISTERED_ITEMS.add(THORITE_SHARD);

        REGISTERED_ITEMS.add(THORITE_HELMET);
        REGISTERED_ITEMS.add(THORITE_CHESTPLATE);
        REGISTERED_ITEMS.add(THORITE_LEGGINGS);
        REGISTERED_ITEMS.add(THORITE_BOOTS);
    }
}
