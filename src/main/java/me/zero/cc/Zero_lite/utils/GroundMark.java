package me.zero.cc.Zero_lite.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import com.mumfrey.liteloader.gl.GL;

public class GroundMark implements Markables {

	private double x,y,z;
	private Minecraft minecraft;
	private float r,g,b,alpha;
	
	public GroundMark(double x,double y , double z,Minecraft minecraft,float r,float g,float b,float alpha) {
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
		IBlockState iblockstate = minecraft.world.getBlockState(blockpos);
		AxisAlignedBB axis = iblockstate.getSelectedBoundingBox(minecraft.world, blockpos);
	   
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.minX, axis.minY, axis.minZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.maxX, axis.minY, axis.maxZ).color(r, g, b, alpha).endVertex();	
	    tessellator.draw();
	    
	    worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldRenderer.pos(axis.minX, axis.minY, axis.maxZ).color(r, g, b, alpha).endVertex();
		worldRenderer.pos(axis.maxX, axis.minY, axis.minZ).color(r, g, b, alpha).endVertex();	
	    tessellator.draw();	    
	    
	    normalizeRenderer();
	}
	
	private void setUpRenderer(float partialTicks){
		EntityPlayerSP player = minecraft.player;
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

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

}
