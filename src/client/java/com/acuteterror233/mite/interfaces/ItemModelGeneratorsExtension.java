package com.acuteterror233.mite.interfaces;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.trim.TrimMaterials;

import java.util.Map;

/**
 * Extension interface for item model generators.
 * Provides custom generation methods for item models in data generation.
 */
public interface ItemModelGeneratorsExtension {
    /**
     * Generates the model for a metal bucket item: either a layered model (empty-bucket texture
     * from {@code item1} plus the content overlay {@code identifier}) or, when {@code identifier}
     * is {@code null}, a plain flat model using the bucket's own texture.
     *
     * @param item       the bucket item being registered
     * @param identifier texture of the bucket content overlay, or {@code null} for an empty bucket
     * @param item1      the corresponding empty-bucket item providing the base texture
     */
    void MME$registerBucket(Item item, Identifier identifier, Item item1);

    /**
     * Registers a fishing rod item with a boolean dispatch on {@code FishingRodCast}: the cast
     * model while the line is out, the plain handheld-rod model otherwise.
     *
     * @param item the fishing rod item
     * @param cast model id used while the rod is cast
     */
    void MME$registerFishingRod(Item item, Identifier cast);

    /**
     * Same as {@link #MME$registerFishingRod(Item, Identifier)} but the idle model reuses the
     * vanilla fishing rod texture (for rods sharing the vanilla look).
     *
     * @param item the fishing rod item
     * @param cast model id used while the rod is cast
     */
    void MME$registerIronFishingRod(Item item, Identifier cast);

    /**
     * Generates a trimmable chainmail armor model: for every vanilla trim material a layered
     * model (base plate + chainmail overlay + armor-slot overlay) is created, dispatched by the
     * {@code TrimMaterialProperty}; palettes may be remapped via {@code trimPaletteReplacements}.
     *
     * @param item                     the chainmail armor item
     * @param basePlateModel           item whose texture is used as the base plate layer
     * @param resourceKey              equipment asset key of the armor material
     * @param resourceLocation         base texture id of the armor slot (trimmed layer)
     * @param Slot                     armor slot name used for the overlay texture lookup
     * @param trimPaletteReplacements  trim palette substitution map (missing entries keep the vanilla palette)
     */
    void MME$registerChainmailTrimmableItem(Item item, Item basePlateModel, ResourceKey<EquipmentAsset> resourceKey, Identifier resourceLocation, String Slot, Map<TrimMaterials.Palette, TrimMaterials.Palette> trimPaletteReplacements);
}
