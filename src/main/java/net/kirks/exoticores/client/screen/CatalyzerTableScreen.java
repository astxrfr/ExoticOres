package net.kirks.exoticores.client.screen;

import net.kirks.exoticores.ExoticOres;
import net.kirks.exoticores.menu.CatalyzerTableMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class CatalyzerTableScreen
        extends AbstractContainerScreen<CatalyzerTableMenu> {

    private static final Identifier BACKGROUND_TEXTURE =
            Identifier.fromNamespaceAndPath(
                    ExoticOres.MODID,
                    "textures/gui/container/catalyzer_table.png"
            );

    private static final Identifier LIT_PROGRESS_TEXTURE =
            Identifier.fromNamespaceAndPath(
                    "minecraft",
                    "textures/gui/sprites/container/furnace/lit_progress.png"
            );

    private static final Identifier ARROW_PROGRESS_TEXTURE =
            Identifier.fromNamespaceAndPath(
                    "minecraft",
                    "textures/gui/sprites/container/furnace/burn_progress.png"
            );

    public CatalyzerTableScreen(
            CatalyzerTableMenu menu,
            Inventory playerInventory,
            Component title
    ) {
        super(menu, playerInventory, title, 176, 166);

        // Oculta el título superior del menú.
        this.titleLabelY = -1000;

        // Posición del texto "Inventario".
        this.inventoryLabelX = 8;
        this.inventoryLabelY = 72;
    }

    @Override
    public void extractBackground(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        super.extractBackground(
                graphics,
                mouseX,
                mouseY,
                partialTick
        );

        // Fondo completo del menú.
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                BACKGROUND_TEXTURE,
                this.leftPos,
                this.topPos,
                0,
                0,
                this.imageWidth,
                this.imageHeight,
                176,
                166
        );

        // Animación del fuego.
        if (this.menu.isBurning()) {
            int flameHeight = this.menu.getLitProgress();

            if (flameHeight > 0) {
                graphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        LIT_PROGRESS_TEXTURE,
                        this.leftPos + 56,
                        this.topPos + 36 + (14 - flameHeight),
                        0,
                        14 - flameHeight,
                        14,
                        flameHeight,
                        14,
                        14
                );
            }
        }

        // Animación de la flecha de cocción.
        int arrowWidth = this.menu.getCookingProgress();

        if (arrowWidth > 0) {
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    ARROW_PROGRESS_TEXTURE,
                    this.leftPos + 79,
                    this.topPos + 35,
                    0,
                    0,
                    arrowWidth,
                    17,
                    24,
                    17
            );
        }
    }
}