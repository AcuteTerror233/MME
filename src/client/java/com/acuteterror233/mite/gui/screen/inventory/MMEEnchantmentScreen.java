package com.acuteterror233.mite.gui.screen.inventory;

import com.acuteterror233.mite.inventory.MMEEnchantmentMenu;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.book.BookModel;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;
import java.util.Optional;

/**
 * MME 附魔 GUI 界面。
 * 渲染附魔台的客户端界面。
 */
@Environment(EnvType.CLIENT)
public class MMEEnchantmentScreen extends AbstractContainerScreen<MMEEnchantmentMenu> {
    private static final Identifier[] ENABLED_LEVEL_SPRITES = new Identifier[]{
            Identifier.withDefaultNamespace("container/enchanting_table/level_1"),
            Identifier.withDefaultNamespace("container/enchanting_table/level_2"),
            Identifier.withDefaultNamespace("container/enchanting_table/level_3")
    };
    private static final Identifier[] DISABLED_LEVEL_SPRITES = new Identifier[]{
            Identifier.withDefaultNamespace("container/enchanting_table/level_1_disabled"),
            Identifier.withDefaultNamespace("container/enchanting_table/level_2_disabled"),
            Identifier.withDefaultNamespace("container/enchanting_table/level_3_disabled")
    };
    private static final Identifier ENCHANTMENT_SLOT_DISABLED_SPRITE = Identifier.withDefaultNamespace(
            "container/enchanting_table/enchantment_slot_disabled"
    );
    private static final Identifier ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE = Identifier.withDefaultNamespace(
            "container/enchanting_table/enchantment_slot_highlighted"
    );
    private static final Identifier ENCHANTMENT_SLOT_SPRITE = Identifier.withDefaultNamespace("container/enchanting_table/enchantment_slot");
    private static final Identifier ENCHANTING_TABLE_LOCATION = Identifier.withDefaultNamespace("textures/gui/container/enchanting_table.png");
    private static final Identifier ENCHANTING_BOOK_LOCATION = Identifier.withDefaultNamespace("textures/entity/enchantment/enchanting_table_book.png");
    private final RandomSource random = RandomSource.create();
    private BookModel bookModel;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    private ItemStack last = ItemStack.EMPTY;

    public MMEEnchantmentScreen(MMEEnchantmentMenu enchantmentMenu, Inventory inventory, Component component) {
        super(enchantmentMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        this.bookModel = new BookModel(this.minecraft.getEntityModels().bakeLayer(ModelLayers.BOOK));
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.minecraft.player.experienceDisplayStartTick = this.minecraft.player.tickCount;
        this.tickBook();
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        for (int k = 0; k < 3; k++) {
            double d = mouseButtonEvent.x() - (i + 60);
            double e = mouseButtonEvent.y() - (j + 14 + 19 * k);
            if (d >= 0.0 && e >= 0.0 && d < 108.0 && e < 19.0 && this.menu.clickMenuButton(this.minecraft.player, k)) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, k);
                return true;
            }
        }

        return super.mouseClicked(mouseButtonEvent, bl);
    }

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
                String costText = cost + "";
                int textWidth = 86 - this.font.width(costText);
                FormattedText message = EnchantmentNames.getInstance().getRandomName(this.font, textWidth);
                int col = -9937334;
                if ((goldCount < i + 1 || this.minecraft.player.experienceLevel < cost) && !this.minecraft.player.hasInfiniteMaterials()) {
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

    private void extractBook(final GuiGraphicsExtractor graphics, final int left, final int top) {
        float a = this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        float open = Mth.lerp(a, this.oOpen, this.open);
        float flip = Mth.lerp(a, this.oFlip, this.flip);
        int x0 = left + 14;
        int y0 = top + 14;
        int x1 = x0 + 38;
        int y1 = y0 + 31;
        graphics.book(this.bookModel, ENCHANTING_BOOK_LOCATION, 40.0F, open, flip, x0, y0, x1, y1);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int i, int j, float f) {
        float g = this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        super.extractRenderState(guiGraphics, i, j, g);
        this.extractTooltip(guiGraphics, i, j);
        boolean bl = this.minecraft.player.hasInfiniteMaterials();
        int k = this.menu.getGoldCount();

        for (int l = 0; l < 3; l++) {
            int m = this.menu.costs[l];
            Optional<Holder.Reference<Enchantment>> optional = this.minecraft.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(this.menu.enchantClue[l]);
                    if (!optional.isEmpty()) {
                int n = this.menu.levelClue[l];
                int o = l + 1;
                if (this.isHovering(60, 14 + 19 * l, 108, 17, i, j) && m > 0 && n >= 0 && optional != null) {
                    List<Component> list = Lists.<Component>newArrayList();
                    list.add(
                            Component.translatable("container.enchant.clue", Enchantment.getFullname(optional.get(), n)).withStyle(ChatFormatting.WHITE)
                    );
                    if (!bl) {
                        list.add(CommonComponents.EMPTY);
                        if (this.minecraft.player.experienceLevel < m) {
                            list.add(Component.translatable("container.enchant.level.requirement", new Object[]{this.menu.costs[l]}).withStyle(ChatFormatting.RED));
                        } else {
                            MutableComponent mutableComponent;
                            if (o == 1) {
                                mutableComponent = Component.translatable("container.enchant.lapis.one");
                            } else {
                                mutableComponent = Component.translatable("container.enchant.lapis.many", new Object[]{o});
                            }

                            list.add(mutableComponent.withStyle(k >= o ? ChatFormatting.GRAY : ChatFormatting.RED));
                            MutableComponent mutableComponent2;
                            if (o == 1) {
                                mutableComponent2 = Component.translatable("container.enchant.level.one");
                            } else {
                                mutableComponent2 = Component.translatable("container.enchant.level.many", new Object[]{o});
                            }

                            list.add(mutableComponent2.withStyle(ChatFormatting.GRAY));
                        }
                    }

                    guiGraphics.setComponentTooltipForNextFrame(this.font, list, i, j);
                    break;
                }
            }
        }
    }

    public void tickBook() {
        ItemStack itemStack = this.menu.getSlot(0).getItem();
        if (!ItemStack.matches(itemStack, this.last)) {
            this.last = itemStack;

            do {
                this.flipT = this.flipT + (this.random.nextInt(4) - this.random.nextInt(4));
            } while (this.flip <= this.flipT + 1.0F && this.flip >= this.flipT - 1.0F);
        }

        this.oFlip = this.flip;
        this.oOpen = this.open;
        boolean bl = false;

        for (int i = 0; i < 3; i++) {
            if (this.menu.costs[i] != 0) {
                bl = true;
            }
        }

        if (bl) {
            this.open += 0.2F;
        } else {
            this.open -= 0.2F;
        }

        this.open = Mth.clamp(this.open, 0.0F, 1.0F);
        float f = (this.flipT - this.flip) * 0.4F;
        float g = 0.2F;
        f = Mth.clamp(f, -0.2F, 0.2F);
        this.flipA = this.flipA + (f - this.flipA) * 0.9F;
        this.flip = this.flip + this.flipA;
    }
}