package net.kirks.exoticores.registry;

import net.kirks.exoticores.ExoticOres;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ExoticOres.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> RADIATION_COOLDOWN =
            ATTACHMENTS.register("radiation_cooldown", () -> AttachmentType.builder(() -> 0).build());
}
