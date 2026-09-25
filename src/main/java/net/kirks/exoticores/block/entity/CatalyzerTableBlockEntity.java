package net.kirks.exoticores.block.entity;

import net.kirks.exoticores.menu.CatalyzerTableMenu;
import net.kirks.exoticores.registry.ModBlockEntities;
import net.kirks.exoticores.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class CatalyzerTableBlockEntity extends BlockEntity
        implements Container, MenuProvider {

    public static final int INPUT_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;
    public static final int WATER_SLOT = 3;

    private static final int CONTAINER_SIZE = 4;
    private static final int COOKING_TIME = 200;

    private NonNullList<ItemStack> items =
            NonNullList.withSize(
                    CONTAINER_SIZE,
                    ItemStack.EMPTY
            );

    private int burnTime;
    private int burnDuration;
    private int cookingProgress;

    /*
     * Datos que se sincronizan desde el servidor
     * hacia la pantalla del cliente.
     *
     * 0 = combustible restante
     * 1 = duración total del combustible
     * 2 = progreso de cocción
     * 3 = tiempo total de cocción
     */
    private final ContainerData data =
            new ContainerData() {

                @Override
                public int get(int index) {
                    return switch (index) {
                        case 0 -> burnTime;
                        case 1 -> burnDuration;
                        case 2 -> cookingProgress;
                        case 3 -> COOKING_TIME;
                        default -> 0;
                    };
                }

                @Override
                public void set(
                        int index,
                        int value
                ) {
                    switch (index) {
                        case 0 -> burnTime = value;
                        case 1 -> burnDuration = value;
                        case 2 -> cookingProgress = value;
                        default -> {
                        }
                    }
                }

                @Override
                public int getCount() {
                    return 4;
                }
            };

    public CatalyzerTableBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.CATALYZER_TABLE_BLOCK_ENTITY.get(),
                pos,
                state
        );
    }

    public static void serverTick(
            Level level,
            BlockPos pos,
            BlockState state,
            CatalyzerTableBlockEntity blockEntity
    ) {
        boolean changed = false;

        /*
         * Si el horno está encendido,
         * reduce el combustible cada tick.
         */
        if (blockEntity.burnTime > 0) {
            blockEntity.burnTime--;
            changed = true;
        }

        /*
         * Enciende un combustible nuevo solamente
         * cuando el horno realmente puede procesar.
         */
        if (blockEntity.burnTime == 0
                && blockEntity.canProcess()) {

            if (blockEntity.tryConsumeFuel(level)) {
                changed = true;
            }
        }

        /*
         * Procesa el material si el horno está encendido
         * y se cumplen todos los requisitos.
         */
        if (blockEntity.burnTime > 0
                && blockEntity.canProcess()) {

            blockEntity.cookingProgress++;
            changed = true;

            if (blockEntity.cookingProgress
                    >= COOKING_TIME) {

                blockEntity.processItem();
                blockEntity.cookingProgress = 0;
            }
        } else if (blockEntity.cookingProgress != 0) {

            blockEntity.cookingProgress = 0;
            changed = true;
        }

        if (changed) {
            blockEntity.setChanged();
        }
    }

    private boolean hasValidInput() {
        return items.get(INPUT_SLOT)
                .is(ModItems.RAW_THORITE.get());
    }

    private boolean hasWaterBucket() {
        return items.get(WATER_SLOT)
                .is(Items.WATER_BUCKET);
    }

    private boolean isAllowedFuel(
            ItemStack stack
    ) {
        return stack.is(Items.COAL)
                || stack.is(Items.CHARCOAL);
    }

    private static int getFuelDuration(
            Level level,
            ItemStack fuel
    ) {
        return fuel.getBurnTime(
                RecipeType.SMELTING,
                level.fuelValues()
        );
    }

    private boolean canProcess() {
        if (!hasValidInput()
                || !hasWaterBucket()) {

            return false;
        }

        ItemStack output =
                items.get(OUTPUT_SLOT);

        if (output.isEmpty()) {
            return true;
        }

        return output.is(
                ModItems.THORITE_SHARD.get()
        ) && output.getCount()
                < output.getMaxStackSize();
    }

    private boolean tryConsumeFuel(
            Level level
    ) {
        ItemStack fuel =
                items.get(FUEL_SLOT);

        if (!isAllowedFuel(fuel)) {
            return false;
        }

        int duration =
                getFuelDuration(level, fuel);

        if (duration <= 0) {
            return false;
        }

        burnTime = duration;
        burnDuration = duration;

        fuel.shrink(1);

        return true;
    }

    private void processItem() {
        if (!canProcess()) {
            return;
        }

        /*
         * Consume una unidad de raw thorite.
         */
        items.get(INPUT_SLOT).shrink(1);

        ItemStack output =
                items.get(OUTPUT_SLOT);

        /*
         * Produce un thorite shard.
         */
        if (output.isEmpty()) {
            items.set(
                    OUTPUT_SLOT,
                    new ItemStack(
                            ModItems.THORITE_SHARD.get()
                    )
            );
        } else {
            output.grow(1);
        }

        /*
         * El cubo de agua permanece dentro
         * del horno y no se consume.
         */
        setChanged();
    }

    public boolean isBurning() {
        return burnTime > 0;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getBurnDuration() {
        return burnDuration;
    }

    public int getCookingProgress() {
        return cookingProgress;
    }

    public int getCookingTotalTime() {
        return COOKING_TIME;
    }

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(
            int slot,
            int amount
    ) {
        ItemStack removed =
                ContainerHelper.removeItem(
                        items,
                        slot,
                        amount
                );

        if (!removed.isEmpty()) {
            setChanged();
        }

        return removed;
    }

    @Override
    public ItemStack removeItemNoUpdate(
            int slot
    ) {
        return ContainerHelper.takeItem(
                items,
                slot
        );
    }

    @Override
    public void setItem(
            int slot,
            ItemStack stack
    ) {
        items.set(slot, stack);

        if (stack.getCount()
                > getMaxStackSize()) {

            stack.setCount(
                    getMaxStackSize()
            );
        }

        setChanged();
    }

    @Override
    public boolean canPlaceItem(
            int slot,
            ItemStack stack
    ) {
        return switch (slot) {
            case INPUT_SLOT ->
                    stack.is(
                            ModItems.RAW_THORITE.get()
                    );

            case FUEL_SLOT ->
                    isAllowedFuel(stack);

            case WATER_SLOT ->
                    stack.is(Items.WATER_BUCKET);

            case OUTPUT_SLOT ->
                    false;

            default ->
                    false;
        };
    }

    @Override
    public boolean stillValid(
            Player player
    ) {
        if (level == null
                || level.getBlockEntity(
                worldPosition
        ) != this) {

            return false;
        }

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
        return Component.translatable(
                "container.exoticores.water_furnace"
        );
    }

    @Override
    public AbstractContainerMenu createMenu(
            int containerId,
            Inventory playerInventory,
            Player player
    ) {
        return new CatalyzerTableMenu(
                containerId,
                playerInventory,
                this,
                this.data
        );
    }

    @Override
    public void loadAdditional(
            ValueInput input
    ) {
        super.loadAdditional(input);

        items = NonNullList.withSize(
                CONTAINER_SIZE,
                ItemStack.EMPTY
        );

        ContainerHelper.loadAllItems(
                input,
                items
        );

        burnTime =
                input.getIntOr(
                        "BurnTime",
                        0
                );

        burnDuration =
                input.getIntOr(
                        "BurnDuration",
                        0
                );

        cookingProgress =
                input.getIntOr(
                        "CookingProgress",
                        0
                );
    }

    @Override
    public void saveAdditional(
            ValueOutput output
    ) {
        super.saveAdditional(output);

        ContainerHelper.saveAllItems(
                output,
                items
        );

        output.putInt(
                "BurnTime",
                burnTime
        );

        output.putInt(
                "BurnDuration",
                burnDuration
        );

        output.putInt(
                "CookingProgress",
                cookingProgress
        );
    }
}