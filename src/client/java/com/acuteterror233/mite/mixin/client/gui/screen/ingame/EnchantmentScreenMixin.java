package com.acuteterror233.mite.mixin.client.gui.screen.ingame;

import com.acuteterror233.mite.interfaces.EnchantmentMenuExtension;
import com.acuteterror233.mite.registry.EnchantedUpgradeRegistry;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnchantmentScreen.class)
public abstract class EnchantmentScreenMixin extends AbstractContainerScreen<EnchantmentMenu> {
    public EnchantmentScreenMixin(EnchantmentMenu menu, Inventory inventory, Component title, int imageWidth, int imageHeight) {
        super(menu, inventory, title, imageWidth, imageHeight);
    }
    @Shadow
    @Final
    private static final Identifier[] ENABLED_LEVEL_SPRITES = new Identifier[]{
            Identifier.withDefaultNamespace("container/enchanting_table/level_1"),
            Identifier.withDefaultNamespace("container/enchanting_table/level_2"),
            Identifier.withDefaultNamespace("container/enchanting_table/level_3")
    };
    @Shadow
    @Final
    private static final Identifier[] DISABLED_LEVEL_SPRITES = new Identifier[]{
            Identifier.withDefaultNamespace("container/enchanting_table/level_1_disabled"),
            Identifier.withDefaultNamespace("container/enchanting_table/level_2_disabled"),
            Identifier.withDefaultNamespace("container/enchanting_table/level_3_disabled")
    };
    @Shadow
    @Final
    private static final Identifier ENCHANTMENT_SLOT_DISABLED_SPRITE = Identifier.withDefaultNamespace("container/enchanting_table/enchantment_slot_disabled");
    @Shadow
    @Final
    private static final Identifier ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE = Identifier.withDefaultNamespace("container/enchanting_table/enchantment_slot_highlighted");
    @Shadow
    @Final
    private static final Identifier ENCHANTMENT_SLOT_SPRITE = Identifier.withDefaultNamespace("container/enchanting_table/enchantment_slot");
    @Shadow
    @Final
    private static final Identifier ENCHANTING_TABLE_LOCATION = Identifier.withDefaultNamespace("textures/gui/container/enchanting_table.png");
    @Shadow
    private void extractBook(final GuiGraphicsExtractor graphics, final int left, final int top){};

    @Override
    public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int xo = (this.width - this.imageWidth) / 2;
        int yo = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, ENCHANTING_TABLE_LOCATION, xo, yo, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        this.extractBook(graphics, xo, yo);
        EnchantmentNames.getInstance().initSeed(this.menu.getEnchantmentSeed());
        int goldCount = this.menu.getGoldCount();

        for (int i = 0; i < 3; i++) {
            int leftPos = xo + 60;
            int leftPosText = leftPos + 20;
            int cost = this.menu.costs[i];
            if (cost == 0) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ENCHANTMENT_SLOT_DISABLED_SPRITE, leftPos, yo + 14 + 19 * i, 108, 19);
            } else {
                String costText = EnchantmentMenuExtension.xpCosts[i] + "";
                int textWidth = 86 - this.font.width(costText);
                FormattedText message = EnchantmentNames.getInstance().getRandomName(this.font, textWidth);
                int col = -9937334;
                if ((goldCount < i + 1 || this.minecraft.player.totalExperience < EnchantmentMenuExtension.xpCosts[i]) && !this.minecraft.player.hasInfiniteMaterials() && EnchantedUpgradeRegistry.getUpgrade(this.menu.slots.getFirst().getItem()) == null) {
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ENCHANTMENT_SLOT_DISABLED_SPRITE, leftPos, yo + 14 + 19 * i, 108, 19);
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, DISABLED_LEVEL_SPRITES[i], leftPos + 1, yo + 15 + 19 * i, 16, 16);
                    graphics.textWithWordWrap(this.font, message, leftPosText, yo + 16 + 19 * i, textWidth, ARGB.opaque((col & 16711422) >> 1), false);
                    col = -12550384;
                } else {
                    int xx = mouseX - (xo + 60);
                    int yy = mouseY - (yo + 14 + 19 * i);
                    if (xx >= 0 && yy >= 0 && xx < 108 && yy < 19) {
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE, leftPos, yo + 14 + 19 * i, 108, 19);
                        graphics.requestCursor(CursorTypes.POINTING_HAND);
                        col = -128;
                    } else {
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ENCHANTMENT_SLOT_SPRITE, leftPos, yo + 14 + 19 * i, 108, 19);
                    }

                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ENABLED_LEVEL_SPRITES[i], leftPos + 1, yo + 15 + 19 * i, 16, 16);
                    graphics.textWithWordWrap(this.font, message, leftPosText, yo + 16 + 19 * i, textWidth, col, false);
                    col = -8323296;
                }

                graphics.text(this.font, costText, leftPosText + 86 - this.font.width(costText), yo + 16 + 19 * i + 7, col);
            }
        }
    }
}
