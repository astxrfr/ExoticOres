package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.item.RadioactiveItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExoticOres.MODID);

    // RAW MATERIALS
    public static final DeferredItem<RadioactiveItem> RAW_THORITE =
            ITEMS.registerItem("raw_thorite", RadioactiveItem::new, () -> new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> THORITE_SHARD =
            ITEMS.registerItem("thorite_shard", Item::new, () -> new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> RAW_LEAD =
            ITEMS.registerItem("raw_lead", Item::new, Item.Properties::new);
    public static final DeferredItem<Item> LEAD_INGOT =
            ITEMS.registerItem("lead_ingot", Item::new, Item.Properties::new);

    // BLOCK ITEMS
    public static final DeferredItem<BlockItem> THORITE_ORE_ITEM =
            ITEMS.registerSimpleBlockItem("thorite_ore", ModBlocks.THORITE_ORE);
    public static final DeferredItem<BlockItem> THORITE_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem("thorite_block", ModBlocks.THORITE_BLOCK);
    public static final DeferredItem<BlockItem> CATALYZER_TABLE_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem("catalyzer_table", ModBlocks.CATALYZER_TABLE_BLOCK);
    public static final DeferredItem<BlockItem> LEAD_ORE_ITEM =
            ITEMS.registerSimpleBlockItem("lead_ore", ModBlocks.LEAD_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_LEAD_ORE_ITEM =
            ITEMS.registerSimpleBlockItem("deepslate_lead_ore", ModBlocks.DEEPSLATE_LEAD_ORE);

    // EQUIPMENT
    public static final DeferredItem<Item> THORITE_HELMET = ITEMS.registerItem("thorite_helmet", properties ->
            new Item(properties.humanoidArmor(ModArmorMaterial.THORITE_ARMOR_MATERIAL, ArmorType.HELMET)));

    public static final DeferredItem<Item> THORITE_CHESTPLATE = ITEMS.registerItem("thorite_chestplate", properties ->
            new Item(properties.humanoidArmor(ModArmorMaterial.THORITE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));

    public static final DeferredItem<Item> THORITE_LEGGINGS = ITEMS.registerItem("thorite_leggings", properties ->
            new Item(properties.humanoidArmor(ModArmorMaterial.THORITE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));

    public static final DeferredItem<Item> THORITE_BOOTS = ITEMS.registerItem("thorite_boots", properties ->
            new Item(properties.humanoidArmor(ModArmorMaterial.THORITE_ARMOR_MATERIAL, ArmorType.BOOTS)));
}
