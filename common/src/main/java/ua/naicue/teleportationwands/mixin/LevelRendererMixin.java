package ua.naicue.teleportationwands.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ua.naicue.teleportationwands.client.Handler;

@Mixin(value = LevelRenderer.class, priority = 900)
public class LevelRendererMixin {
    @SuppressWarnings("mapping")
    @Inject(
            method = "renderLevel",
            at = @At(
                    shift = At.Shift.AFTER,
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;applyModelViewMatrix()V",
                    ordinal = 0
            )
    )
    private void renderOverlays(DeltaTracker deltaTracker, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        Handler.renderOutline(camera);
    }
}