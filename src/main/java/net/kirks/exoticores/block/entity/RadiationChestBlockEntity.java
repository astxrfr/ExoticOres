package net.kirks.exoticores.block.entity;

import net.kirks.exoticores.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class RadiationChestBlockEntity extends BlockEntity implements Container, MenuProvider {

  public static final int CONTAINER_SIZE = 27;

  private NonNullList<ItemStack> items =
          NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);

  public RadiationChestBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.RADIATION_CHEST_BLOCK_ENTITY.get(), pos, state);
  }

  @Override
  public int getContainerSize() {
    return CONTAINER_SIZE;
  }

  @Override
  public boolean isEmpty() {
    for (ItemStack stack : items) {
      if (!stack.isEmpty()) return false;
    }
    return true;
  }

  @Override
  public ItemStack getItem(int slot) {
    return items.get(slot);
  }

  @Override
  public ItemStack removeItem(int slot, int amount) {
    ItemStack removed = ContainerHelper.removeItem(items, slot, amount);
    if (!removed.isEmpty()) setChanged();
    return removed;
  }

  @Override
  public ItemStack removeItemNoUpdate(int slot) {
    return ContainerHelper.takeItem(items, slot);
  }

  @Override
  public void setItem(int slot, ItemStack stack) {
    items.set(slot, stack);
    if (stack.getCount() > getMaxStackSize()) stack.setCount(getMaxStackSize());
    setChanged();
  }

  @Override
  public boolean stillValid(Player player) {
    if (level == null || level.getBlockEntity(worldPosition) != this) return false;
    return player.distanceToSqr(
            worldPosition.getX() + 0.5,
            worldPosition.getY() + 0.5,
            worldPosition.getZ() + 0.5
    ) <= 64.0;
  }

  @Override
  public void clearContent() {
    items.clear();
    setChanged();
  }

  @Override
  public Component getDisplayName() {
    return Component.translatable("container.exoticores.radiation_chest");
  }

  @Override
  public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
    return ChestMenu.threeRows(containerId, playerInventory, this);
  }

  @Override
  public void loadAdditional(ValueInput input) {
    super.loadAdditional(input);
    items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    ContainerHelper.loadAllItems(input, items);
  }

  @Override
  public void saveAdditional(ValueOutput output) {
    super.saveAdditional(output);
    ContainerHelper.saveAllItems(output, items);
  }
}