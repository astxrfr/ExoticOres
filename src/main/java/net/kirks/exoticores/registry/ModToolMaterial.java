package net.kirks.exoticores.registry;

import net.kirks.exoticores.tags.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

public final class ModToolMaterial {

    // Va, aqui cambiamos las estadisticas de todas de una sola vez.
    public static final ToolMaterial THORITE = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            1800,   // Durabilidad.
            9.0F,   // Velocidad de mineria.
            4.0F,   // Daño extra del material.
            16,     // Encantabilidad.
            ModTags.Items.THORITE_REPAIRABLE
    );

    private ModToolMaterial() {}
}