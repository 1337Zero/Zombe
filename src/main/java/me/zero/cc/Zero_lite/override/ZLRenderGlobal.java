package me.zero.cc.Zero_lite.override;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Mods.ModData;
import me.zero.cc.Zero_lite.Mods.OreHighlighterMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;

public class ZLRenderGlobal extends RenderGlobal{

	public ZLRenderGlobal(Minecraft mcIn) {
		super(mcIn);
	}
	 @Override
	    public void setupTerrain(Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
		 if(LiteModMain.instance.getMod(ModData.OreHighLighter.name()) != null){
			 ((OreHighlighterMod)LiteModMain.instance.getMod(ModData.OreHighLighter.name())).onRender();
		 }		 
	        super.setupTerrain(viewEntity, partialTicks, camera, frameCount, playerSpectator);
	    }
}
