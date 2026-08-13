package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.item.RadioactiveItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExoticOres.MODID);

    public static final DeferredItem<RadioactiveItem> THORITE_SHARD =
            ITEMS.registerItem("thorite_shard", RadioactiveItem::new, () -> new Item.Properties().rarity(Rarity.RARE));

    // BLOCK ITEMS
    public static final DeferredItem<BlockItem> THORITE_ORE_ITEM =
            ITEMS.registerSimpleBlockItem("thorite_ore", ModBlocks.THORITE_ORE);
}
