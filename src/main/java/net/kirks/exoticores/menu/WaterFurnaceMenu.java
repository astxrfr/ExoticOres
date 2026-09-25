package net.kirks.exoticores.menu;

import net.kirks.exoticores.registry.ModMenuTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class WaterFurnaceMenu extends AbstractContainerMenu {

    private static final int FURNACE_SLOT_COUNT = 4;
    private static final int DATA_COUNT = 4;

    private final Container container;
    private final ContainerData data;

    public WaterFurnaceMenu(
            int containerId,
            Inventory playerInventory
    ) {
        this(
                containerId,
                playerInventory,
                new SimpleContainer(FURNACE_SLOT_COUNT),
                new SimpleContainerData(DATA_COUNT)
        );
    }

    public WaterFurnaceMenu(
            int containerId,
            Inventory playerInventory,
            Container container,
            ContainerData data
    ) {
        super(ModMenuTypes.WATER_FURNACE_MENU.get(), containerId);

        checkContainerSize(container, FURNACE_SLOT_COUNT);
        checkContainerDataCount(data, DATA_COUNT);

        this.container = container;
        this.data = data;

        // Raw thorite
        this.addSlot(new Slot(container, 0, 56, 17));

        // Combustible
        this.addSlot(new Slot(container, 1, 56, 53));

        // Resultado
        this.addSlot(new Slot(container, 2, 116, 35));

        // Cubo de agua
        this.addSlot(new Slot(container, 3, 21, 35));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addDataSlots(data);
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(
                        inventory,
                        column + row * 9 + 9,
                        8 + column * 18,
                        84 + row * 18
                ));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(
                    inventory,
                    column,
                    8 + column * 18,
                    142
            ));
        }
    }

    public boolean isBurning() {
        return data.get(0) > 0;
    }

    public int getLitProgress() {
        int burnTime = data.get(0);
        int burnDuration = data.get(1);

        if (burnDuration <= 0) {
            burnDuration = 200;
        }

        return Mth.ceil(
                burnTime * 14.0F / burnDuration
        );
    }

    public int getCookingProgress() {
        int currentProgress = this.data.get(2);
        int totalTime = this.data.get(3);

        if (currentProgress <= 0 || totalTime <= 0) {
            return 0;
        }

        return currentProgress * 24 / totalTime;
    }

    @Override
    public ItemStack quickMoveStack(
            Player player,
            int slotIndex
    ) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
}