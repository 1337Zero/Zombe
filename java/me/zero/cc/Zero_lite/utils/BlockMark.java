package me.zero.cc.Zero_lite.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import com.mumfrey.liteloader.gl.GL;

public class BlockMark implements Markables {

	private int x,y,z;
	private Minecraft minecraft;
	private float r,g,b,alpha;
	
	public BlockMark(int x,int y , int z,Minecraft minecraft,float r,float g,float b,float alpha) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.minecraft = minecraft;
		this.r =r;
		this.g = g;
		this.b = b;
		this.alpha = alpha;
	}
	
	@Override
	public void draw(float partialTicks) {
		setUpRenderer(partialTicks);
		
		Tessellator tessellator = Tessellator.getInstance();
	    WorldRenderer worldRenderer = tessellator.getWorldRenderer();
	    	    
	    GL.glColor4f(r,g,b,alpha);
	    AxisAlignedBB axis = minecraft.theWorld.getBlockState(new BlockPos(x,y, z)).getBlock().getSelectedBoundingBox(minecraft.theWorld, new BlockPos(x, y, z));
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.minZ);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.minZ);		
	    tessellator.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.minZ);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.maxZ);		
	    tessellator.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.maxZ);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.minZ);		
	    tessellator.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.maxZ);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.maxZ);		
	    tessellator.draw();
	    	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.minZ);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.minZ);		
	    tessellator.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.minZ);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.maxZ);		
	    tessellator.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.maxZ);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.minZ);		
	    tessellator.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.maxZ);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.maxZ);		
	    tessellator.draw();
	    	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.minZ);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.minZ);	
	    tessellator.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.maxZ);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.maxZ);	
	    tessellator.draw();	
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.minZ);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.minZ);	
	    tessellator.draw();	
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.maxZ);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.maxZ);	
	    tessellator.draw();	
	    
	    normalizeRenderer();
	}
	
	private void setUpRenderer(float partialTicks){
		EntityPlayerSP player = minecraft.thePlayer;
		double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
		double y = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
		double z = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
		GL.glPushMatrix();
	    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
	    GL.glEnableBlend();
	    GL11.glEnable(2848);
	    GL.glBlendFunc(770, 771);
	    GL.glDisableTexture2D();
	    GL.glDisableLighting();
	    GL.glDisableDepthTest();

	    GL.glDepthFunc(515);
	    GL11.glLineWidth(1.0F);
	    GL.glTranslated(-x, -y, -z);
	}	
	private void normalizeRenderer(){
	    GL.glEnableTexture2D();
	    GL.glDisableBlend();
	    GL.glPopMatrix();
	    GL.glEnableDepthTest();
	}

}
