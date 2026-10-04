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
import net.minecraft.world.level.MoonPhase;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Client-side mixin into {@link SkyRenderer} (sky rendering only exists on the client, so the
 * dedicated server never loads this class).
 * <p>
 * Mechanism: at {@code <init>} a single GPU quad buffer is built once, holding one flat quad
 * per {@link SpecialMoonPhase} sprite packed sequentially from the celestials atlas. During
 * {@code extractRenderState} the current special-moon environment attribute is copied onto the
 * {@link SkyRenderState}; during {@code render} a non-NORMAL phase draws its quad with the same
 * celestial transform as the vanilla moon, while a redirect on {@code renderSunMoonAndStars}
 * suppresses the vanilla moon so the two overlapping quads do not wash each other out.
 * {@code close} releases the GPU buffer.
 */
@Mixin(SkyRenderer.class)
public abstract class SkyRendererMixin implements AutoCloseable {
    @Final
    @Shadow
    private TextureAtlas celestialsAtlas;
    @Unique
    private GpuBuffer specialMoonBuffer;
    /**
     * Special phase captured during {@code render} (single render thread, written before
     * {@code renderSunMoonAndStars} runs). {@code null} before the first frame — treated as
     * NORMAL so the vanilla moon keeps rendering until a real phase is extracted.
     */
    @Unique
    private SpecialMoonPhase currentPhase;
    @Final
    @Shadow
    private RenderSystem.AutoStorageIndexBuffer quadIndices;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(TextureManager textureManager, AtlasManager atlasManager, RenderTarget renderTarget, CallbackInfo ci){
        // The celestials atlas is loaded by the vanilla constructor, so the quads can be packed once here.
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

        // Pack one flat quad (y = 0 plane) per phase into a single buffer; phase.index() * 4 later
        // serves as the base vertex so all phases can share one draw call.
        try (ByteBufferBuilder byteBufferBuilder = ByteBufferBuilder.exactlySized(phases.length * 4 * format.getVertexSize())) {
            BufferBuilder bufferBuilder = new BufferBuilder(byteBufferBuilder, PrimitiveTopology.QUADS, format);

            for (SpecialMoonPhase phase : phases) {
                TextureAtlasSprite sprite = atlas.getSprite(Identifier.fromNamespaceAndPath(MME.MOD_ID, "special_moon/" + phase.getSerializedName()));
                // UVs map the full sprite region of each phase onto its quad.
                bufferBuilder.addVertex(-1.0F, 0.0F, -1.0F).setUv(sprite.getU1(), sprite.getV1());
                bufferBuilder.addVertex(1.0F, 0.0F, -1.0F).setUv(sprite.getU0(), sprite.getV1());
                bufferBuilder.addVertex(1.0F, 0.0F, 1.0F).setUv(sprite.getU0(), sprite.getV0());
                bufferBuilder.addVertex(-1.0F, 0.0F, 1.0F).setUv(sprite.getU1(), sprite.getV0());
            }

            try (MeshData mesh = bufferBuilder.buildOrThrow()) {
                // Upload the packed mesh as a static GPU vertex buffer owned by this renderer.
                return RenderSystem.getDevice().createBuffer(() -> "Special Moon phases", 36, mesh.vertexBuffer());
            }
        }
    }

    @Unique
    private void renderSpecialMoon(final RenderPass renderPass, final SpecialMoonPhase specialMoonPhase, final float rainBrightness, final PoseStack poseStack) {
        int baseVertex = specialMoonPhase.index() * 4;
        // Same transform as the vanilla moon (height 100, scale 20); the color modulate keeps the
        // vanilla alpha as (1,1,1,rainBrightness) so weather dims the special moon identically.
        Matrix4f modelViewMatrix = this.applyCelestialBodyTransform(poseStack, 100.0F, 20.0F);
        GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms().writeTransform(modelViewMatrix, new Vector4f(1.0F, 1.0F, 1.0F, rainBrightness));
        this.drawCelestialBody(() -> "Special Moon", renderPass, dynamicTransforms, this.quadIndices.getBuffer(6), this.specialMoonBuffer, baseVertex);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;renderSunMoonAndStars(Lcom/mojang/renderpearl/api/commands/RenderPass;Lcom/mojang/blaze3d/vertex/PoseStack;FFFLnet/minecraft/world/level/MoonPhase;FF)V"))
    private void render(GpuBufferSlice skyFog, SkyRenderState state, CallbackInfo ci, @Local RenderPass renderPass, @Local PoseStack poseStack){
        this.currentPhase = Objects.requireNonNullElse(state.getData(MMERenderStateDataKeys.SPECIAL_MOON), SpecialMoonPhase.NORMAL_MOON);
        if (this.currentPhase == SpecialMoonPhase.NORMAL_MOON) {
            return;
        }
        // Mirror the vanilla moon's Y-rotation and moonAngle so the special quad tracks the sky identically.
        poseStack.pushPose();
        poseStack.rotateDegrees(Axis.YP, -90.0F);
        poseStack.pushPose();
        poseStack.rotate(Axis.XP, state.moonAngle);
        renderSpecialMoon(renderPass, this.currentPhase, state.rainBrightness, poseStack);
        poseStack.popPose();
        poseStack.popPose();
    }

    @Shadow
    private void renderMoon(final RenderPass renderPass, final MoonPhase moonPhase, final float f, final PoseStack poseStack) {}

    /**
     * Suppresses the vanilla moon while a special phase is active: vanilla {@code renderMoon} draws at the
     * exact same transform (height 100, scale 20) after our special moon, so without this the two quads
     * overlap and the special moon is washed out by the vanilla one. NORMAL phase (or a missing extraction,
     * {@code mme$currentPhase == null}) keeps the vanilla moon untouched.
     */
    @Redirect(method = "renderSunMoonAndStars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;renderMoon(Lcom/mojang/renderpearl/api/commands/RenderPass;Lnet/minecraft/world/level/MoonPhase;FLcom/mojang/blaze3d/vertex/PoseStack;)V"))
    private void mme$suppressVanillaMoonOnSpecialPhase(SkyRenderer instance, RenderPass renderPass, MoonPhase moonPhase, float rainBrightness, PoseStack poseStack) {
        if (this.currentPhase == null || this.currentPhase == SpecialMoonPhase.NORMAL_MOON) {
            this.renderMoon(renderPass, moonPhase, rainBrightness, poseStack);
        }
    }

    @Inject(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;shouldRenderDarkDisc(FLnet/minecraft/client/multiplayer/ClientLevel;)Z"))
    private void extractRenderState(ClientLevel level, float partialTicks, Camera camera, SkyRenderState state, CallbackInfo ci){
        // Copy the special-moon attribute from the camera's environment probe into the render state
        // (hand-off from the level tick thread to the render thread).
        state.setData(MMERenderStateDataKeys.SPECIAL_MOON, camera.attributeProbe().getValue(MMEEnvironmentAttributes.SPECIAL_MOON_PHASE, partialTicks));
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void onClose(CallbackInfo ci) {
        // Release the GPU buffer allocated in <init>; runs once when the sky renderer is discarded.
        this.specialMoonBuffer.close();
    }
}
