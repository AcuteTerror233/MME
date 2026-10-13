package com.acuteterror233.mite.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * Ancient Skeleton Lord renderer: vanilla skeleton model and skin, told apart by the ancient
 * metal gear it spawns in and the glowing red-eyes overlay layer inherited from
 * {@link SkeletonLordRenderer}.
 */
public class AncientSkeletonLordRenderer extends SkeletonLordRenderer {
    public AncientSkeletonLordRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
}
