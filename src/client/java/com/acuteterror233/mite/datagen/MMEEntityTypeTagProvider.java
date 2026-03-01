package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.registry.tag.MMEEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class MMEEntityTypeTagProvider extends FabricTagProvider<EntityType<?>> {
    public MMEEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ENTITY_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(MMEEntityTypeTags.SENSITIVE_TO_BUTCHERING)
                .add(EntityType.CHICKEN)
                .add(EntityType.COW)
                .add(EntityType.HOGLIN)
                .add(EntityType.MOOSHROOM)
                .add(EntityType.PIG)
                .add(EntityType.SHEEP);
    }
}
