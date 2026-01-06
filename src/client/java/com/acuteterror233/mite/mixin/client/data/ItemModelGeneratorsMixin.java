package com.acuteterror233.mite.mixin.client.data;

import com.acuteterror233.mite.atinterface.ItemModelGeneratorsExtension;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.renderer.item.properties.conditional.FishingRodCast;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.BiConsumer;

@Mixin(ItemModelGenerators.class)
public abstract class ItemModelGeneratorsMixin implements ItemModelGeneratorsExtension {

    @Shadow @Final public ItemModelOutput itemModelOutput;
    @Shadow @Final public abstract ResourceLocation generateLayeredItem(Item item, ResourceLocation layer0, ResourceLocation layer1);
    @Shadow public abstract ResourceLocation createFlatItemModel(Item item, ModelTemplate model);
    @Shadow	@Final public BiConsumer<ResourceLocation, ModelInstance> modelOutput;
    @Shadow @Final public abstract void generateBooleanDispatch(Item item, ConditionalItemModelProperty property, ItemModel.Unbaked onTrue, ItemModel.Unbaked onFalse);
    @Shadow @Final public abstract ResourceLocation createFlatItemModel(Item item, String suffix, ModelTemplate model);

    @Unique
    public ResourceLocation uploadLayers(Item item, ModelTemplate model) {
        return model.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/buckets/")), this.modelOutput);
    }

    @Unique
    @Override
    public void MME$registerBucket(Item item, ResourceLocation identifier, Item item1) {
        if (identifier != null){
            this.itemModelOutput.accept(item, ItemModelUtils.plainModel(this.generateLayeredItem(item, BuiltInRegistries.ITEM.getKey(item1).withPrefix("item/buckets/"), identifier)));
        }else {
            this.itemModelOutput.accept(item, ItemModelUtils.plainModel(this.uploadLayers(item,ModelTemplates.FLAT_ITEM)));
        }
    }


    @Unique
    public final void MME$registerFishingRod(Item item, ResourceLocation cast) {
        ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(this.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ROD_ITEM));
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(cast);
        this.generateBooleanDispatch(item, new FishingRodCast(), unbaked2, unbaked);
    }
}
