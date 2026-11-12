package net.kirks.exoticores.effect;

import com.mojang.serialization.MapCodec;
import net.kirks.exoticores.registry.ModEffects;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.List;

public record ClearExceptProtectedConsumeEffect() implements ConsumeEffect {
    public static final ClearExceptProtectedConsumeEffect INSTANCE = new ClearExceptProtectedConsumeEffect();
    public static final MapCodec<ClearExceptProtectedConsumeEffect> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, ClearExceptProtectedConsumeEffect> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final Type<ClearExceptProtectedConsumeEffect> TYPE = new Type<>(CODEC, STREAM_CODEC);

    @Override
    public @NonNull Type<? extends ConsumeEffect> getType() {
        return TYPE;
    }

    @Override
    public boolean apply(@NonNull Level level, @NonNull ItemStack stack, LivingEntity user) {
        boolean any = false;
        for (MobEffectInstance instance : List.copyOf(user.getActiveEffects())) {
            if (instance.getEffect().value() != ModEffects.RADIATION.get())
                any |= user.removeEffect(instance.getEffect());
            else
                if (user instanceof ServerPlayer player) player.sendOverlayMessage(Component.translatable("message.exoticores.effect_not_cleared"));
        }

        return any;
    }
}
