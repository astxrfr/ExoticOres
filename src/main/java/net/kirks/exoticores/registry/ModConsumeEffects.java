package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.effect.ClearExceptProtectedConsumeEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModConsumeEffects {
    public static final DeferredRegister<ConsumeEffect.Type<?>> CONSUME_EFFECTS = DeferredRegister.create(Registries.CONSUME_EFFECT_TYPE, ExoticOres.MODID);

    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<ClearExceptProtectedConsumeEffect>> CLEAR_EXCEPT_PROTECTED_CONSUME_EFFECT =
            CONSUME_EFFECTS.register("clear_except_protected_consume_effect", () -> ClearExceptProtectedConsumeEffect.TYPE);
}
