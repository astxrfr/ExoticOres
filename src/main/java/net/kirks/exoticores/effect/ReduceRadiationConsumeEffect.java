package net.kirks.exoticores.effect;

import com.mojang.serialization.MapCodec;
import net.kirks.exoticores.effect.radiation.RadiationExposure;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public record ReduceRadiationConsumeEffect() implements ConsumeEffect {
    public static final int REDUCTION_TICKS = 2 * 60 * 20;
    public static final ReduceRadiationConsumeEffect INSTANCE = new ReduceRadiationConsumeEffect();
    public static final MapCodec<ReduceRadiationConsumeEffect> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, ReduceRadiationConsumeEffect> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final Type<ReduceRadiationConsumeEffect> TYPE = new Type<>(CODEC, STREAM_CODEC);

    @Override
    public @NonNull Type<? extends ConsumeEffect> getType() {
        return TYPE;
    }

    @Override
    public boolean apply(@NonNull Level level, @NonNull ItemStack stack, LivingEntity user) {
        return !level.isClientSide() && RadiationExposure.contributeHeal(user, REDUCTION_TICKS);
    }
}
