package com.acuteterror233.mite.gui.screen.inventory;

import com.acuteterror233.mite.inventory.GradeAnvilMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class GradeAnvilScreen extends ItemCombinerScreen<GradeAnvilMenu> {
    private static final ResourceLocation TEXT_FIELD_TEXTURE = ResourceLocation.withDefaultNamespace("container/anvil/text_field");
    private static final ResourceLocation TEXT_FIELD_DISABLED_TEXTURE = ResourceLocation.withDefaultNamespace("container/anvil/text_field_disabled");
    private static final ResourceLocation ERROR_TEXTURE = ResourceLocation.withDefaultNamespace("container/anvil/error");
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/anvil.png");
    private static final Component TOO_EXPENSIVE_TEXT = Component.translatable("container.repair.expensive");
    private EditBox name;
    private final Player player;

    public GradeAnvilScreen(GradeAnvilMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, TEXTURE);
        this.player = inventory.player;
        this.titleLabelX = 60;
    }
    @Override
    protected void subInit() {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.name = new EditBox(this.font, i + 62, j + 24, 103, 12, Component.translatable("container.repair"));
        this.name.setCanLoseFocus(false);
        this.name.setTextColor(-1);
        this.name.setTextColorUneditable(-1);
        this.name.setBordered(false);
        this.name.setMaxLength(50);
        this.name.setResponder(this::onRenamed);
        this.name.setValue("");
        this.addWidget(this.name);
        this.name.setEditable(this.menu.getSlot(0).hasItem());
    }
    @Override
    protected void setInitialFocus() {
        this.setInitialFocus(this.name);
    }
    @Override
    public void resize(Minecraft client, int width, int height) {
        String string = this.name.getValue();
        this.init(client, width, height);
        this.name.setValue(string);
    }
    private void onRenamed(String name) {
        Slot slot = this.menu.getSlot(0);
        if (slot.hasItem()) {
            String string = name;
            if (!slot.getItem().has(DataComponents.CUSTOM_NAME) && name.equals(slot.getItem().getHoverName().getString())) {
                string = "";
            }

            if (this.menu.setNewItemName(string)) {
                this.minecraft.player.connection.send(new ServerboundRenameItemPacket(string));
            }
        }
    }
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.minecraft.player.closeContainer();
        }
        return !this.name.keyPressed(keyCode, scanCode, modifiers) && !this.name.canConsumeInput() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
    }

    @Override
    public void renderFg(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
        this.name.render(context, mouseX, mouseY, deltaTicks);
    }

    @Override
    protected void renderErrorIcon(GuiGraphics context, int x, int y) {
        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(this.menu.getResultSlot()).hasItem()) {
            context.blitSprite(RenderType::guiTextured, ERROR_TEXTURE, x + 99, y + 45, 28, 21);
        }
    }
    @Override
    protected void renderBg(GuiGraphics context, float deltaTicks, int mouseX, int mouseY) {
        super.renderBg(context, deltaTicks, mouseX, mouseY);
        context.blitSprite(
                RenderType::guiTextured, this.menu.getSlot(0).hasItem() ? TEXT_FIELD_TEXTURE : TEXT_FIELD_DISABLED_TEXTURE, this.leftPos + 59, this.topPos + 20, 110, 16
        );
    }
    @Override
    public void slotChanged(AbstractContainerMenu handler, int slotId, ItemStack stack) {
        if (slotId == 0) {
            this.name.setValue(stack.isEmpty() ? "" : stack.getHoverName().getString());
            this.name.setEditable(!stack.isEmpty());
            this.setFocused(this.name);
        }
    }
    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        super.renderLabels(context, mouseX, mouseY);
        int i = this.menu.getLevelCost();
        if (i > 0) {
            int j = 8453920;
            Component text;
            if (i >= 40 && !this.minecraft.player.hasInfiniteMaterials()) {
                text = TOO_EXPENSIVE_TEXT;
                j = 16736352;
            } else if (!this.menu.getSlot(2).hasItem()) {
                text = null;
            } else {
                text = Component.translatable("container.repair.cost", i);
                if (!this.menu.getSlot(2).mayPickup(this.player)) {
                    j = 16736352;
                }
            }

            if (text != null) {
                int k = this.imageWidth - 8 - this.font.width(text) - 2;
                int l = 69;
                context.fill(k - 2, 67, this.imageWidth - 8, 79, 1325400064);
                context.drawString(this.font, text, k, 69, j);
            }
        }
    }
}
