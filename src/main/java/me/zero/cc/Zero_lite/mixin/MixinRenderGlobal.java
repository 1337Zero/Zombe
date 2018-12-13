package me.zero.cc.Zero_lite.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.mods.ModData;
import me.zero.cc.Zero_lite.mods.OreHighlighterMod;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;

@Mixin(WorldRenderer.class)
public class MixinRenderGlobal {

	//@Inject(method = "updateCameraAndRender(FJ)V", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", args = "ldc=litParticles"))
	@Inject(method = "setupTerrain", at = @At("HEAD"))
	private void setupTerrain(Entity viewEntity, float partialTicks, ICamera camera, int frameCount, boolean playerSpectator,CallbackInfo info) {
		((OreHighlighterMod)LiteModMain.instance.getMod(ModData.OreHighLighter.name())).onRender();
	}
	
}
