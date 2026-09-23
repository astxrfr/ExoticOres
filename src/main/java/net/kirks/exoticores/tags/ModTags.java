package net.kirks.exoticores.tags;

import net.kirks.exoticores.ExoticOres;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
  public static class Blocks {

    private static TagKey<Block> createTag(String name) {
      return BlockTags.create(Identifier.fromNamespaceAndPath(ExoticOres.MODID, name));
    }

  }


  public static class Items {
    public static final TagKey<Item> THORITE_REPAIRABLE = createTag("thorite");

    private static TagKey<Item> createTag(String name) {
      return ItemTags.create(Identifier.fromNamespaceAndPath(ExoticOres.MODID, name));
    }

  }

}