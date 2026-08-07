package com.acuteterror233.mite.world.entity.decoration.painting;

import com.acuteterror233.mite.MME;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;

import java.util.Optional;

public class MMEPaintingVariants {
    public static final ResourceKey<PaintingVariant> ABYSS = create("abyss");
    public static final ResourceKey<PaintingVariant> BARON_ALMRIC = create("baron_almric");
    public static final ResourceKey<PaintingVariant> BOAT = create("boat");
    public static final ResourceKey<PaintingVariant> CASTLE = create("castle");
    public static final ResourceKey<PaintingVariant> CASTLE_BRITANNIA = create("castle_britannia");
    public static final ResourceKey<PaintingVariant> DARKLANDS = create("darklands");
    public static final ResourceKey<PaintingVariant> DEATHTRAP_DUNGEON = create("deathtrap_dungeon");
    public static final ResourceKey<PaintingVariant> DND_BASIC = create("dnd_basic");
    public static final ResourceKey<PaintingVariant> DRARACLE = create("draracle");
    public static final ResourceKey<PaintingVariant> ELDEN_GROVE = create("elden_grove");
    public static final ResourceKey<PaintingVariant> FAIR_DAY = create("fair_day");
    public static final ResourceKey<PaintingVariant> FALLEN_BRIDGE = create("fallen_bridge");
    public static final ResourceKey<PaintingVariant> GATE_CLOSING = create("gate_closing");
    public static final ResourceKey<PaintingVariant> GHOUL = create("ghoul");
    public static final ResourceKey<PaintingVariant> GLADSTONE_KEEP = create("gladstone_keep");
    public static final ResourceKey<PaintingVariant> GRAVES = create("graves");
    public static final ResourceKey<PaintingVariant> KING_RICHARD = create("king_richard");
    public static final ResourceKey<PaintingVariant> MESSENGER = create("messenger");
    public static final ResourceKey<PaintingVariant> MOUNTAINS = create("mountains");
    public static final ResourceKey<PaintingVariant> ROLANDS_MANOR = create("rolands_manor");
    public static final ResourceKey<PaintingVariant> SCOTIA = create("scotia");
    public static final ResourceKey<PaintingVariant> SHIP = create("ship");
    public static final ResourceKey<PaintingVariant> SUNLIGHT = create("sunlight");
    public static final ResourceKey<PaintingVariant> TITAN = create("titan");
    public static final ResourceKey<PaintingVariant> WOLVES = create("wolves");

    public static void bootstrap(BootstrapContext<PaintingVariant> bootstrapContext) {
        register(bootstrapContext, ABYSS, 3, 2);
        register(bootstrapContext, BARON_ALMRIC, 3, 2);
        register(bootstrapContext, BOAT, 3, 2);
        register(bootstrapContext, CASTLE, 2, 2);
        register(bootstrapContext, CASTLE_BRITANNIA, 4, 2);
        register(bootstrapContext, DARKLANDS, 1, 1);
        register(bootstrapContext, DEATHTRAP_DUNGEON, 3, 3);
        register(bootstrapContext, DND_BASIC, 2, 2);
        register(bootstrapContext, DRARACLE, 3, 2);
        register(bootstrapContext, ELDEN_GROVE, 4, 2);
        register(bootstrapContext, FAIR_DAY, 4, 2);
        register(bootstrapContext, FALLEN_BRIDGE, 3, 2);
        register(bootstrapContext, GATE_CLOSING, 4, 2);
        register(bootstrapContext, GHOUL, 2, 3);
        register(bootstrapContext, GLADSTONE_KEEP, 3, 2);
        register(bootstrapContext, GRAVES, 2, 2);
        register(bootstrapContext, KING_RICHARD, 2, 2);
        register(bootstrapContext, MESSENGER, 3, 2);
        register(bootstrapContext, MOUNTAINS, 2, 2);
        register(bootstrapContext, ROLANDS_MANOR, 3, 2);
        register(bootstrapContext, SCOTIA, 2, 2);
        register(bootstrapContext, SHIP, 3, 2);
        register(bootstrapContext, SUNLIGHT, 2, 2);
        register(bootstrapContext, TITAN, 3, 2);
        register(bootstrapContext, WOLVES, 2, 2);
    }

    private static void register(BootstrapContext<PaintingVariant> bootstrapContext, ResourceKey<PaintingVariant> resourceKey, int i, int j) {
        bootstrapContext.register(
                resourceKey,
                new PaintingVariant(
                        i,
                        j,
                        resourceKey.identifier(),
                        Optional.of(Component.translatable(resourceKey.identifier().toLanguageKey("painting", "title")).withStyle(ChatFormatting.YELLOW)),
                        Optional.of(Component.translatable(resourceKey.identifier().toLanguageKey("painting", "author")).withStyle(ChatFormatting.GRAY))
                )
        );
    }

    private static ResourceKey<PaintingVariant> create(String string) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, Identifier.fromNamespaceAndPath(MME.MOD_ID, string));
    }
}
