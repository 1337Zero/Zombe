package me.zero.cc.Zero_lite.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;


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
		BufferBuilder worldRenderer = tessellator.getBuffer();
	    	    
	    BlockPos blockpos = new BlockPos(x,y,z);
		//AxisAlignedBB axis = iblockstate.getSelectedBoundingBox(minecraft.world, blockpos);
		AxisAlignedBB axis = new AxisAlignedBB(blockpos);
		
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.minX, axis.minY, axis.minZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.maxX, axis.minY, axis.minZ).color(r, g, b, alpha).endVertex();	
	    tessellator.draw();
	
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.minX, axis.minY, axis.minZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.minX, axis.minY, axis.maxZ).color(r, g, b, alpha).endVertex();	
	    tessellator.draw();
	    
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.maxX, axis.minY, axis.maxZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.maxX, axis.minY, axis.minZ).color(r, g, b, alpha).endVertex();	
	    tessellator.draw();
	    
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.maxX, axis.minY, axis.maxZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.minX, axis.minY, axis.maxZ).color(r, g, b, alpha).endVertex();	
	    tessellator.draw();
	    	    
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.minX, axis.maxY, axis.minZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.maxX, axis.maxY, axis.minZ).color(r, g, b, alpha).endVertex();	
	    tessellator.draw();
	    
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.minX, axis.maxY, axis.minZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.minX, axis.maxY, axis.maxZ).color(r, g, b, alpha).endVertex();	
	    tessellator.draw();
	    
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.maxX, axis.maxY, axis.maxZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.maxX, axis.maxY, axis.minZ).color(r, g, b, alpha).endVertex();	
	    tessellator.draw();
	    
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.maxX, axis.maxY, axis.maxZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.minX, axis.maxY, axis.maxZ).color(r, g, b, alpha).endVertex();	
	    tessellator.draw();
	    	    
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.minX, axis.maxY, axis.minZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.minX, axis.minY, axis.minZ).color(r, g, b, alpha).endVertex();
	    tessellator.draw();
	    
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.maxX, axis.maxY, axis.maxZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.maxX, axis.minY, axis.maxZ).color(r, g, b, alpha).endVertex();
	    tessellator.draw();	
	    
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.maxX, axis.maxY, axis.minZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.maxX, axis.minY, axis.minZ).color(r, g, b, alpha).endVertex();
	    tessellator.draw();	
	    
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.minX, axis.maxY, axis.maxZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.minX, axis.minY, axis.maxZ).color(r, g, b, alpha).endVertex();
	    tessellator.draw();	
	    
	    normalizeRenderer();
	}
	
	private void setUpRenderer(float partialTicks){
		EntityPlayerSP player = minecraft.player;
		double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
		double y = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
		double z = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
		//GL.glPushMatrix();
	    GlStateManager.pushMatrix();
		//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
	    OpenGlHelper.glMultiTexCoord2f(OpenGlHelper.GL_TEXTURE1, 240F, 240F);
	    // GL.glEnableBlend();
	    GlStateManager.enableBlend();
		GL11.glEnable(2848);
	    //GL.glBlendFunc(770, 771);
	    GlStateManager.blendFunc(770, 771);
		//GL.glDisableTexture2D();
	    GlStateManager.disableTexture2D();
	    //GL.glDisableLighting();
	    GlStateManager.disableLighting();
	    //GL.glDisableDepthTest();
	    GlStateManager.disableDepthTest();
	    
	    //GL.glDepthFunc(515);
	    GlStateManager.depthFunc(515);
	    GL11.glLineWidth(1.0F);
	    //GL.glTranslated(-x, -y, -z);
	    GlStateManager.translated(-x, -y, -z);
	}	
	private void normalizeRenderer(){
	    //GL.glEnableTexture2D();
	    GlStateManager.enableTexture2D();
		//GL.glDisableBlend();
	    GlStateManager.disableBlend();
	    //GL.glPopMatrix();
	    GlStateManager.popMatrix();
	    //GL.glEnableDepthTest();
	    GlStateManager.enableDepthTest();
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
