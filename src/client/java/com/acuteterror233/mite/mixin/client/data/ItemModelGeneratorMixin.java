package com.acuteterror233.mite.mixin.client.data;

import com.acuteterror233.mite.atinterface.ItemModelGeneratorExtension;
import net.minecraft.client.data.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.BiConsumer;

@Mixin(ItemModelGenerator.class)
public abstract class ItemModelGeneratorMixin implements ItemModelGeneratorExtension {

    @Shadow @Final public ItemModelOutput output;
    @Shadow @Final public abstract Identifier uploadTwoLayers(Item item, Identifier layer0, Identifier layer1);
    @Shadow public abstract Identifier upload(Item item, Model model);
    @Shadow	@Final public BiConsumer<Identifier, ModelSupplier> modelCollector;

    @Unique
    public Identifier uploadLayers(Item item, Model model) {
        return model.upload(ModelIds.getItemModelId(item), TextureMap.layer0(Registries.ITEM.getId(item).withPrefixedPath("item/buckets/")), this.modelCollector);
    }

    @Unique
    @Override
    public void registerbucket(Item item, Identifier identifier,Item item1) {
        if (identifier != null){
            this.output.accept(item, ItemModels.basic(this.uploadTwoLayers(item, Registries.ITEM.getId(item1).withPrefixedPath("item/buckets/"), identifier)));
        }else {
            this.output.accept(item, ItemModels.basic(this.uploadLayers(item,Models.GENERATED)));
        }
    }
}
