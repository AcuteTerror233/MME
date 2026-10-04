package com.acuteterror233.mite.mixin.client.data;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.interfaces.ItemModelGeneratorsExtension;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.renderer.item.properties.conditional.FishingRodCast;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Client datagen mixin into {@code ItemModelGenerators}, implementing
 * {@link ItemModelGeneratorsExtension} so {@code MMEModelProvider} can generate MME-specific item
 * models (metal buckets, fishing rods, chainmail trim variants) through the vanilla generator
 * state. Runs only during {@code runDatagen}.
 */
@Mixin(ItemModelGenerators.class)
public abstract class ItemModelGeneratorsMixin implements ItemModelGeneratorsExtension {

    @Shadow @Final public ItemModelOutput itemModelOutput;
    @Shadow @Final public static List<ItemModelGenerators.TrimMaterialData> TRIM_MATERIAL_MODELS;
    @Shadow	@Final public BiConsumer<Identifier, ModelInstance> modelOutput;
    @Shadow @Final 	public abstract void generateLayeredItem(
            Identifier resourceLocation, Material resourceLocation2, Material resourceLocation3, Material resourceLocation4
    );
    @Shadow @Final public abstract Identifier generateLayeredItem(Identifier resourceLocation, Material resourceLocation2, Material resourceLocation3);
    @Shadow @Final public abstract Identifier generateLayeredItem(Item item, Material layer0, Material layer1);
    @Shadow public abstract Identifier createFlatItemModel(Item item, ModelTemplate model);
    @Shadow @Final public abstract void generateBooleanDispatch(Item item, ConditionalItemModelProperty property, ItemModel.Unbaked onTrue, ItemModel.Unbaked onFalse);
    @Shadow @Final public abstract Identifier createFlatItemModel(Item item, String suffix, ModelTemplate model);

    /**
     * Creates a flat item model whose layer0 texture is taken from the item's own
     * {@code item/buckets/} texture path.
     *
     * @param item  the bucket item
     * @param model the model template to instantiate
     * @return the generated model id
     */
    @Unique
    public Identifier uploadLayers(Item item, ModelTemplate model) {
        return model.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(new Material(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/buckets/"))), this.modelOutput);
    }

    /**
     * Generates the bucket item model: a layered model (empty-bucket texture from {@code item1}
     * plus the content overlay {@code identifier}) when an overlay is given, otherwise a plain
     * flat model using the bucket's own texture.
     *
     * @param item       the bucket item
     * @param identifier content overlay texture, or {@code null} for an empty bucket
     * @param item1      the corresponding empty-bucket item providing the base texture
     */
    @Unique
    @Override
    public void MME$registerBucket(Item item, Identifier identifier, Item item1) {
        if (identifier != null){
            this.itemModelOutput.accept(item, ItemModelUtils.plainModel(this.generateLayeredItem(item, new Material(BuiltInRegistries.ITEM.getKey(item1).withPrefix("item/buckets/")), new Material(identifier))));
        }else {
            this.itemModelOutput.accept(item, ItemModelUtils.plainModel(this.uploadLayers(item,ModelTemplates.FLAT_ITEM)));
        }
    }

    /**
     * Registers a fishing rod with a boolean dispatch on {@code FishingRodCast}: the {@code cast}
     * model while the line is out, a flat handheld-rod model generated from the item itself otherwise.
     *
     * @param item the fishing rod item
     * @param cast model id used while the rod is cast
     */
    @Unique
    public final void MME$registerFishingRod(Item item, Identifier cast) {
        ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(this.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ROD_ITEM));
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(cast);
        this.generateBooleanDispatch(item, new FishingRodCast(), unbaked2, unbaked);
    }

    /**
     * Same as {@link #MME$registerFishingRod(Item, Identifier)} but the idle model reuses the
     * vanilla fishing rod texture.
     *
     * @param item the fishing rod item
     * @param cast model id used while the rod is cast
     */
    @Unique
    public final void MME$registerIronFishingRod(Item item, Identifier cast) {
        ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(this.createFlatItemModel(Items.FISHING_ROD, ModelTemplates.FLAT_HANDHELD_ROD_ITEM));
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(cast);
        this.generateBooleanDispatch(item, new FishingRodCast(), unbaked2, unbaked);
    }

    /**
     * Generates a trimmable chainmail armor model: for every vanilla trim material a layered
     * model (base plate + chainmail overlay + slot overlay for the remapped palette) is created,
     * then dispatched by {@code TrimMaterialProperty} with the untrimmed model as fallback.
     *
     * @param item                     the chainmail armor item
     * @param basePlateModel           item whose texture is used as the base plate layer
     * @param key                      equipment asset key of the armor material
     * @param slotResourceLocation     base texture id of the armor slot
     * @param slot                     armor slot name used for the chainmail overlay texture lookup
     * @param trimPaletteReplacements  trim palette substitution map (missing entries keep the vanilla palette)
     */
    @Unique
    public final void MME$registerChainmailTrimmableItem(Item item, Item basePlateModel, ResourceKey<EquipmentAsset> key, Identifier slotResourceLocation, String slot, Map<TrimMaterials.Palette, TrimMaterials.Palette> trimPaletteReplacements) {
        Identifier model = ModelLocationUtils.getModelLocation(item);
        Material basePlateTexture = TextureMapping.getItemTexture(basePlateModel);
        Material slotTextureChainmailOverlay = new Material(Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/" + slot + "_chainmail_overlay"));
        List<SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>>> trimMaterialModelList = new ArrayList<>(TRIM_MATERIAL_MODELS.size());

        // One layered model per vanilla trim material, keyed by the (possibly remapped) palette suffix.
        for (ItemModelGenerators.TrimMaterialData trimMaterialData : TRIM_MATERIAL_MODELS) {
            Identifier modelTrim = model.withSuffix("_" + trimMaterialData.palette().suffix() + "_trim");
            TrimMaterials.Palette palette = trimPaletteReplacements.getOrDefault(trimMaterialData.palette(), trimMaterialData.palette());
            Material SlotResourceLocationAsKey = new Material(slotResourceLocation.withSuffix("_" + palette.suffix()));
            ItemModel.Unbaked unbaked;
            this.generateLayeredItem(modelTrim, basePlateTexture, slotTextureChainmailOverlay, SlotResourceLocationAsKey);
            unbaked = ItemModelUtils.plainModel(modelTrim);
            trimMaterialModelList.add(ItemModelUtils.when(trimMaterialData.materialKey(), unbaked));
        }
        // Untrimmed base model, also layered with the chainmail overlay.
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(model);
        this.generateLayeredItem(model, basePlateTexture, slotTextureChainmailOverlay);

        this.itemModelOutput.accept(item, ItemModelUtils.select(new TrimMaterialProperty(), unbaked2, trimMaterialModelList));
    }
}
