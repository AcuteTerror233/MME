package com.acuteterror233.mite.renderer.entity;

import com.acuteterror233.mite.renderer.entity.layers.AncientCorpseEyesLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;

/**
 * Skeleton Lord renderer: vanilla skeleton model and skin, told apart by the rusted iron gear
 * it spawns in and the glowing red-eyes overlay layer.
 */
public class SkeletonLordRenderer extends SkeletonRenderer {
    public SkeletonLordRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new AncientCorpseEyesLayer(this));
    }
}
