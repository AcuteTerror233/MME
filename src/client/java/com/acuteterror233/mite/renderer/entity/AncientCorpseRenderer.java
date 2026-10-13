package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.renderer.entity.layers.AncientCorpseEyesLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;

/**
 * Ancient Corpse renderer: vanilla skeleton model and skin, told apart by the ancient metal
 * chainmail gear it spawns in and the glowing red-eyes overlay layer.
 */
public class AncientCorpseRenderer extends SkeletonRenderer {
    public AncientCorpseRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new AncientCorpseEyesLayer(this));
    }
}
