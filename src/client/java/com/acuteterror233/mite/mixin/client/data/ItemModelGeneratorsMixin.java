package com.acuteterror233.mite.mixin.client.data;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.atinterface.ItemModelGeneratorsExtension;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.renderer.item.properties.conditional.FishingRodCast;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Mixin(ItemModelGenerators.class)
/**
 * Mixin for {@code ItemModelGenerators} — 实现物品模型生成扩展接口。
 */
public abstract class ItemModelGeneratorsMixin implements ItemModelGeneratorsExtension {

    @Shadow @Final public ItemModelOutput itemModelOutput;
    @Shadow @Final public static List<ItemModelGenerators.TrimMaterialData> TRIM_MATERIAL_MODELS;
    @Shadow	@Final public BiConsumer<Identifier, ModelInstance> modelOutput;
    @Shadow @Final 	public abstract void generateLayeredItem(
            Identifier resourceLocation, Identifier resourceLocation2, Identifier resourceLocation3, Identifier resourceLocation4
    );
    @Shadow @Final public abstract Identifier generateLayeredItem(Identifier resourceLocation, Identifier resourceLocation2, Identifier resourceLocation3);
    @Shadow @Final public abstract Identifier generateLayeredItem(Item item, Identifier layer0, Identifier layer1);
    @Shadow public abstract Identifier createFlatItemModel(Item item, ModelTemplate model);
    @Shadow @Final public abstract void generateBooleanDispatch(Item item, ConditionalItemModelProperty property, ItemModel.Unbaked onTrue, ItemModel.Unbaked onFalse);
    @Shadow @Final public abstract Identifier createFlatItemModel(Item item, String suffix, ModelTemplate model);

    @Unique
    public Identifier uploadLayers(Item item, ModelTemplate model) {
        return model.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/buckets/")), this.modelOutput);
    }

    @Unique
    @Override
    public void MME$registerBucket(Item item, Identifier identifier, Item item1) {
        if (identifier != null){
            this.itemModelOutput.accept(item, ItemModelUtils.plainModel(this.generateLayeredItem(item, BuiltInRegistries.ITEM.getKey(item1).withPrefix("item/buckets/"), identifier)));
        }else {
            this.itemModelOutput.accept(item, ItemModelUtils.plainModel(this.uploadLayers(item,ModelTemplates.FLAT_ITEM)));
        }
    }

    @Unique
    public final void MME$registerFishingRod(Item item, Identifier cast) {
        ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(this.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ROD_ITEM));
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(cast);
        this.generateBooleanDispatch(item, new FishingRodCast(), unbaked2, unbaked);
    }
    @Unique
    public final void MME$registerIronFishingRod(Item item, Identifier cast) {
        ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(this.createFlatItemModel(Items.FISHING_ROD, ModelTemplates.FLAT_HANDHELD_ROD_ITEM));
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(cast);
        this.generateBooleanDispatch(item, new FishingRodCast(), unbaked2, unbaked);
    }
    @Unique
    public final void MME$registerChainmailTrimmableItem(Item item, Item basePlateModel, ResourceKey<EquipmentAsset> key, Identifier slotResourceLocation, String slot) {
        Identifier model = ModelLocationUtils.getModelLocation(item);
        Identifier basePlateTexture = TextureMapping.getItemTexture(basePlateModel);
        Identifier slotTextureChainmailOverlay = Identifier.fromNamespaceAndPath(MME.MOD_ID, "item/" + slot + "_chainmail_overlay");
        List<SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>>> trimMaterialModelList = new ArrayList<>(TRIM_MATERIAL_MODELS.size());

        for (ItemModelGenerators.TrimMaterialData trimMaterialData : TRIM_MATERIAL_MODELS) {
            Identifier modelTrim = model.withSuffix("_" + trimMaterialData.assets().base().suffix() + "_trim");
            Identifier SlotResourceLocationAsKey = slotResourceLocation.withSuffix("_" + trimMaterialData.assets().assetId(key).suffix());
            ItemModel.Unbaked unbaked;
            this.generateLayeredItem(modelTrim, basePlateTexture, slotTextureChainmailOverlay, SlotResourceLocationAsKey);
            unbaked = ItemModelUtils.plainModel(modelTrim);
            trimMaterialModelList.add(ItemModelUtils.when(trimMaterialData.materialKey(), unbaked));
        }
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(model);
        this.generateLayeredItem(model, basePlateTexture, slotTextureChainmailOverlay);

        this.itemModelOutput.accept(item, ItemModelUtils.select(new TrimMaterialProperty(), unbaked2, trimMaterialModelList));
    }
}
