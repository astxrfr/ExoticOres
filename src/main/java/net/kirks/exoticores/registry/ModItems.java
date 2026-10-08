package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.item.RadioactiveItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(ExoticOres.MODID);

    // MATERIALES

    public static final DeferredItem<RadioactiveItem> RAW_THORITE =
            ITEMS.registerItem(
                    "raw_thorite",
                    RadioactiveItem::new,
                    () -> new Item.Properties().rarity(Rarity.RARE)
            );

    public static final DeferredItem<Item> THORITE_SHARD =
            ITEMS.registerItem(
                    "thorite_shard",
                    Item::new,
                    () -> new Item.Properties().rarity(Rarity.RARE)
            );

    public static final DeferredItem<Item> RAW_LEAD =
            ITEMS.registerItem(
                    "raw_lead",
                    Item::new,
                    Item.Properties::new
            );

    public static final DeferredItem<Item> LEAD_INGOT =
            ITEMS.registerItem(
                    "lead_ingot",
                    Item::new,
                    Item.Properties::new
            );

    // HERRAMIENTAS DE THORITE

    public static final DeferredItem<Item> THORITE_PICKAXE =
            ITEMS.registerItem("thorite_pickaxe", properties ->
                    new Item(
                            toolAppearance(properties, "thorite_pickaxe")
                                    .pickaxe(
                                            ModToolMaterial.THORITE,
                                            1.0F,
                                            -2.8F
                                    )
                    ));

    // Usamos AxeItem para conservar el clic derecho al pelar troncos.
    public static final DeferredItem<AxeItem> THORITE_AXE =
            ITEMS.registerItem("thorite_axe", properties ->
                    new AxeItem(
                            ModToolMaterial.THORITE,
                            5.0F,
                            -3.0F,
                            toolAppearance(properties, "thorite_axe")
                    ));

    // La pala conserva la funcion de hacer caminos.
    public static final DeferredItem<ShovelItem> THORITE_SHOVEL =
            ITEMS.registerItem("thorite_shovel", properties ->
                    new ShovelItem(
                            ModToolMaterial.THORITE,
                            1.5F,
                            -3.0F,
                            toolAppearance(properties, "thorite_shovel")
                    ));

    // La azada conserva la funcion de arar la tierra.
    public static final DeferredItem<HoeItem> THORITE_HOE =
            ITEMS.registerItem("thorite_hoe", properties ->
                    new HoeItem(
                            ModToolMaterial.THORITE,
                            -4.0F,
                            0.0F,
                            toolAppearance(properties, "thorite_hoe")
                    ));

    public static final DeferredItem<Item> THORITE_SWORD =
            ITEMS.registerItem("thorite_sword", properties ->
                    new Item(
                            toolAppearance(properties, "thorite_sword")
                                    .sword(
                                            ModToolMaterial.THORITE,
                                            3.0F,
                                            -2.4F
                                    )
                    ));

    // La lanza usa estocadas y carga; no se arroja como un tridente.
    public static final DeferredItem<Item> THORITE_SPEAR =
            ITEMS.registerItem("thorite_spear", properties ->
                    new Item(
                            toolAppearance(properties, "thorite_spear")
                                    .spear(
                                            ModToolMaterial.THORITE,
                                            0.8F,       // Tiempo entre estocadas.
                                            1.0F,       // Multiplicador de daño de carga.
                                            0.6F,       // Espera para activar la carga.
                                            4.0F, 9.0F, // Ventana y velocidad para desmontar.
                                            8.0F, 5.0F, // Ventana y velocidad para empujar.
                                            12.0F, 4.5F // Ventana y velocidad para hacer daño.
                                    )
                    ));

    // Aqui elegimos la definicion visual del item, no el PNG directamente.
    // Para la lanza apunta a assets/exoticores/items/thorite_spear.json.
    private static Item.Properties toolAppearance(
            Item.Properties properties, String modelName) {

        return properties.rarity(Rarity.RARE)
                .component(
                        DataComponents.ITEM_MODEL,
                        Identifier.fromNamespaceAndPath(
                                ExoticOres.MODID,
                                modelName
                        )
                );
    }

    // BLOQUES COMO ITEMS

    public static final DeferredItem<BlockItem> THORITE_ORE_ITEM =
            ITEMS.registerSimpleBlockItem(
                    "thorite_ore",
                    ModBlocks.THORITE_ORE
            );

    public static final DeferredItem<BlockItem> THORITE_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(
                    "thorite_block",
                    ModBlocks.THORITE_BLOCK
            );

    public static final DeferredItem<BlockItem> CATALYZER_TABLE_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(
                    "catalyzer_table",
                    ModBlocks.CATALYZER_TABLE_BLOCK
            );

    public static final DeferredItem<BlockItem> LEAD_ORE_ITEM =
            ITEMS.registerSimpleBlockItem(
                    "lead_ore",
                    ModBlocks.LEAD_ORE
            );

    public static final DeferredItem<BlockItem> DEEPSLATE_LEAD_ORE_ITEM =
            ITEMS.registerSimpleBlockItem(
                    "deepslate_lead_ore",
                    ModBlocks.DEEPSLATE_LEAD_ORE
            );

    // ARMADURA DE THORITE

    public static final DeferredItem<Item> THORITE_HELMET =
            ITEMS.registerItem("thorite_helmet", properties ->
                    new Item(properties.humanoidArmor(
                            ModArmorMaterial.THORITE_ARMOR_MATERIAL,
                            ArmorType.HELMET
                    )));

    public static final DeferredItem<Item> THORITE_CHESTPLATE =
            ITEMS.registerItem("thorite_chestplate", properties ->
                    new Item(properties.humanoidArmor(
                            ModArmorMaterial.THORITE_ARMOR_MATERIAL,
                            ArmorType.CHESTPLATE
                    )));

    public static final DeferredItem<Item> THORITE_LEGGINGS =
            ITEMS.registerItem("thorite_leggings", properties ->
                    new Item(properties.humanoidArmor(
                            ModArmorMaterial.THORITE_ARMOR_MATERIAL,
                            ArmorType.LEGGINGS
                    )));

    public static final DeferredItem<Item> THORITE_BOOTS =
            ITEMS.registerItem("thorite_boots", properties ->
                    new Item(properties.humanoidArmor(
                            ModArmorMaterial.THORITE_ARMOR_MATERIAL,
                            ArmorType.BOOTS
                    )));

    // EQUIPMENT

    public static final DeferredItem<Item> RADIATION_SUIT_HELMET =
            ITEMS.registerItem("radiation_suit_helmet", properties ->
                    new Item(properties.humanoidArmor(
                            ModArmorMaterial.RADIATION_SUIT_MATERIAL,
                            ArmorType.HELMET
                    )));

    // Conservamos CHESPLATE porque otros archivos ya usan este nombre.
    public static final DeferredItem<Item> RADIATION_SUIT_CHESPLATE =
            ITEMS.registerItem("radiation_suit_chestplate", properties ->
                    new Item(properties.humanoidArmor(
                            ModArmorMaterial.RADIATION_SUIT_MATERIAL,
                            ArmorType.CHESTPLATE
                    )));

    public static final DeferredItem<Item> RADIATION_SUIT_LEGGINGS =
            ITEMS.registerItem("radiation_suit_leggings", properties ->
                    new Item(properties.humanoidArmor(
                            ModArmorMaterial.RADIATION_SUIT_MATERIAL,
                            ArmorType.LEGGINGS
                    )));

    public static final DeferredItem<Item> RADIATION_SUIT_BOOTS =
            ITEMS.registerItem("radiation_suit_boots", properties ->
                    new Item(properties.humanoidArmor(
                            ModArmorMaterial.RADIATION_SUIT_MATERIAL,
                            ArmorType.BOOTS
                    )));
}