package me.zero.cc.Zero_lite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.zero.cc.Zero_lite.LiteModMain;
import net.minecraft.client.renderer.GameRenderer;

import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class MixinPostRenderEntities {

	@Inject(method = "updateCameraAndRender(FJ)V", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", args = "ldc=litParticles"))
	//@Inject(method = "updateCameraAndRender", at = @At("RETURN"))
	//@Inject(method = "doRender", at = @At("HEAD"))
	private void onPostRenderEntities(float partialTicks, long timeSlice, CallbackInfo ci) {
		// this.broker.postRenderEntities(partialTicks, timeSlice);
		LiteModMain.instance.onPostRenderEntities(partialTicks);
	}
}
