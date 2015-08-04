package me.zero.cc.Zero_lite.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.AxisAlignedBB;

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
		
		Tessellator worldRenderer = Tessellator.instance;
	    //WorldRenderer worldRenderer = tessellator.getWorldRenderer();
	    	    
	    GL.glColor4f(r,g,b,alpha);
	   // AxisAlignedBB axis = minecraft.theWorld.getBlock(x,y, z).getSelectedBoundingBox(minecraft.theWorld,x, y, z);
	    AxisAlignedBB axis = minecraft.theWorld.getBlock(x,y, z).getSelectedBoundingBoxFromPool(minecraft.theWorld, x, y, z);
	    
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.minZ);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.minZ);		
		worldRenderer.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.minZ);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.maxZ);		
		worldRenderer.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.maxZ);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.minZ);		
		worldRenderer.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.maxZ);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.maxZ);		
		worldRenderer.draw();
	    	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.minZ);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.minZ);		
		worldRenderer.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.minZ);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.maxZ);		
		worldRenderer.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.maxZ);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.minZ);		
		worldRenderer.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.maxZ);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.maxZ);		
		worldRenderer.draw();
	    	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.minZ);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.minZ);	
		worldRenderer.draw();
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.maxZ);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.maxZ);	
		worldRenderer.draw();	
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.minZ);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.minZ);	
		worldRenderer.draw();	
	    
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.maxZ);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.maxZ);	
		worldRenderer.draw();	
	    
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

}
