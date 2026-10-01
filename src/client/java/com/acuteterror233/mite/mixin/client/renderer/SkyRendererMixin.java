package com.acuteterror233.mite.mixin.client.renderer;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.renderer.state.MMERenderStateDataKeys;
import com.acuteterror233.mite.world.attribute.MMEEnvironmentAttributes;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.mojang.renderpearl.api.buffers.GpuBuffer;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import com.mojang.renderpearl.api.vertex.VertexFormat;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.state.level.SkyRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.minecraft.resources.Identifier;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.Supplier;

@Mixin(SkyRenderer.class)
public abstract class SkyRendererMixin implements AutoCloseable {
    @Final
    @Shadow
    private TextureAtlas celestialsAtlas;
    @Unique
    private GpuBuffer specialMoonBuffer;
    @Final
    @Shadow
    private RenderSystem.AutoStorageIndexBuffer quadIndices;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(TextureManager textureManager, AtlasManager atlasManager, RenderTarget renderTarget, CallbackInfo ci){
        this.specialMoonBuffer = buildSpecialMoonPhases(this.celestialsAtlas);
    }

    @Shadow
    private Matrix4f applyCelestialBodyTransform(final PoseStack poseStack, final float height, final float scale) {
        return null;
    }
    @Shadow
    private void drawCelestialBody(
            final Supplier<String> label,
            final RenderPass renderPass,
            final GpuBufferSlice dynamicTransforms,
            final GpuBuffer indexBuffer,
            final GpuBuffer vertexBuffer,
            final int baseVertex
    ){}

    @Unique
    private static GpuBuffer buildSpecialMoonPhases(final TextureAtlas atlas) {
        SpecialMoonPhase[] phases = SpecialMoonPhase.values();
        VertexFormat format = DefaultVertexFormat.POSITION_TEX;

        try (ByteBufferBuilder byteBufferBuilder = ByteBufferBuilder.exactlySized(phases.length * 4 * format.getVertexSize())) {
            BufferBuilder bufferBuilder = new BufferBuilder(byteBufferBuilder, PrimitiveTopology.QUADS, format);

            for (SpecialMoonPhase phase : phases) {
                TextureAtlasSprite sprite = atlas.getSprite(Identifier.fromNamespaceAndPath(MME.MOD_ID, "special_moon/" + phase.getSerializedName()));
                bufferBuilder.addVertex(-1.0F, 0.0F, -1.0F).setUv(sprite.getU1(), sprite.getV1());
                bufferBuilder.addVertex(1.0F, 0.0F, -1.0F).setUv(sprite.getU0(), sprite.getV1());
                bufferBuilder.addVertex(1.0F, 0.0F, 1.0F).setUv(sprite.getU0(), sprite.getV0());
                bufferBuilder.addVertex(-1.0F, 0.0F, 1.0F).setUv(sprite.getU1(), sprite.getV0());
            }

            try (MeshData mesh = bufferBuilder.buildOrThrow()) {
                return RenderSystem.getDevice().createBuffer(() -> "Special Moon phases", 36, mesh.vertexBuffer());
            }
        }
    }

    @Unique
    private void renderSpecialMoon(final RenderPass renderPass, final SpecialMoonPhase specialMoonPhase, final float rainBrightness, final PoseStack poseStack) {
        int baseVertex = specialMoonPhase.index() * 4;
        Matrix4f modelViewMatrix = this.applyCelestialBodyTransform(poseStack, 100.0F, 20.0F);
        GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms().writeTransform(modelViewMatrix, new Vector4f(1.0F, 1.0F, 1.0F, rainBrightness));
        this.drawCelestialBody(() -> "Special Moon", renderPass, dynamicTransforms, this.quadIndices.getBuffer(6), this.specialMoonBuffer, baseVertex);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;renderSunMoonAndStars(Lcom/mojang/renderpearl/api/commands/RenderPass;Lcom/mojang/blaze3d/vertex/PoseStack;FFFLnet/minecraft/world/level/MoonPhase;FF)V"))
    private void render(GpuBufferSlice skyFog, SkyRenderState state, CallbackInfo ci, @Local RenderPass renderPass, @Local PoseStack poseStack){
        poseStack.pushPose();
        poseStack.rotateDegrees(Axis.YP, -90.0F);
        poseStack.pushPose();
        poseStack.rotate(Axis.XP, state.moonAngle);
        renderSpecialMoon(renderPass, Objects.requireNonNull(state.getData(MMERenderStateDataKeys.SPECIAL_MOON)), state.rainBrightness, poseStack);
        poseStack.popPose();
        poseStack.popPose();
    }

    @Inject(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;shouldRenderDarkDisc(FLnet/minecraft/client/multiplayer/ClientLevel;)Z"))
    private void extractRenderState(ClientLevel level, float partialTicks, Camera camera, SkyRenderState state, CallbackInfo ci){
        state.setData(MMERenderStateDataKeys.SPECIAL_MOON, camera.attributeProbe().getValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE, partialTicks));
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void onClose(CallbackInfo ci) {
        this.specialMoonBuffer.close();
    }
}
