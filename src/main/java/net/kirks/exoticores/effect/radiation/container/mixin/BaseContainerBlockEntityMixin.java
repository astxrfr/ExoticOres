package net.kirks.exoticores.effect.radiation.container.mixin;

import net.kirks.exoticores.effect.radiation.BlockRadiationContext;
import net.kirks.exoticores.effect.radiation.RadiationExposure;
import net.kirks.exoticores.effect.radiation.Radioactive;
import net.kirks.exoticores.effect.radiation.container.ContainerRadiationTicker;
import net.kirks.exoticores.effect.radiation.container.RadiationContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.WeakHashMap;

@Mixin(BaseContainerBlockEntity.class)
public abstract class BaseContainerBlockEntityMixin extends BlockEntity implements RadiationContainer {

    @Shadow
    protected abstract NonNullList<ItemStack> getItems();

    public BaseContainerBlockEntityMixin(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        if (getLevel() instanceof ServerLevel)
            ContainerRadiationTicker.addListener(this);
    }

    @Override
    public void onChunkUnloaded() {
        if (getLevel() instanceof ServerLevel)
            ContainerRadiationTicker.removeListener(this);

        super.onChunkUnloaded();
    }

    @Override
    public void setRemoved() {
        if (getLevel() instanceof ServerLevel)
            ContainerRadiationTicker.removeListener(this);

        super.setRemoved();
    }

    @Override
    public void exoticOres$tick() {
        if (exoticOres$hasRadioactiveItem())
            emit(new BlockRadiationContext(getLevel(), getBlockPos()));
    }

    @Override
    public boolean exoticOres$hasRadioactiveItem() {
        var items = getItems();
        for (ItemStack item : items) {
            if (item.is(itemHolder -> itemHolder.value() instanceof Radioactive<?>))
                return true;
        }

        return false;
    }
}
