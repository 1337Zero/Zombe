package me.zero.cc.Zero_lite.Gui.Buttons;

import org.lwjgl.opengl.GL11;

import com.mumfrey.liteloader.gl.GL;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Mods.ModData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;

public class GuiBooleanButton extends GuiButton{

	private boolean value = false;
	private int xstart = 0;
	private int ystart = 0;
	private int width = 0;
	private int height = 0;
	private String txt = "";
	private ModData moddata;
	private String valueToManupulate;
	private LiteModMain speicher;
	private String[] overlayText;
	
	public GuiBooleanButton(int id, int x, int y, int width, int height,String label, boolean value,String valueToManupulate,ModData moddata,LiteModMain speicher,String[] overlayText) {
		super(id, x, y, width, height, label);
		this.value = value;
		this.xstart = x;
		this.ystart = y;
		this.width = width;
		this.height = height;
		this.txt = label;
		this.moddata = moddata;
		this.valueToManupulate = valueToManupulate;
		this.speicher = speicher;
		this.overlayText = overlayText;
	}
	protected void mouseDragged(Minecraft mc, int x, int y) {
		 this.displayString = txt + ": " + value;
		 
		 if(this.isMouseOver()){            	  
        	 RenderTooltip(x+10, y+10, overlayText);
         }
	}
	public boolean mousePressed(Minecraft mc, int x, int y) {		 
		 if(x > xstart && x < (xstart + width)){			 
			 if(y > ystart && y < (ystart + height)){
				 if(value){
					 value = false;
				 }else{
					value = true;
				 }
				 if(!moddata.equals(ModData.Nil)){
					 speicher.getMod(moddata.name()).manupulateValue(valueToManupulate, value);						 
				 }			
				 return true;
			 }			 
		 }		 
		 return false;
	}
	public void mouseReleased(int x, int y) {
		//Unused
	}
	/**
	 * This methode was orignally writte by Zyin055
	 * <a href=https://github.com/Zyin055/zyinhud/blob/master/src/main/java/com/zyin/zyinhud/gui/GuiTooltipScreen.java>https://github.com/Zyin055/zyinhud/blob/master/src/main/java/com/zyin/zyinhud/gui/GuiTooltipScreen.java</a>
	 * @param x
	 * @param y
	 * @param text
	 */
	private void RenderTooltip(int x, int y, String[] text){	
        int tooltipX = x -10;
        int tooltipY = y -10;
        int tooltipWidth = maxStringLength(text) +10;       
        
        int tooltipHeight = 10 + (text.length * 10);
        
        //render the background inside box
        int innerAlpha = -0xFEFFFF0;
        drawGradientRect(tooltipX, tooltipY - 1, tooltipX + tooltipWidth + 6, tooltipY, innerAlpha, innerAlpha);
        drawGradientRect(tooltipX, tooltipY + tooltipHeight + 6, tooltipX + tooltipWidth + 6, tooltipY + tooltipHeight + 7, innerAlpha, innerAlpha);
        drawGradientRect(tooltipX, tooltipY, tooltipX + tooltipWidth + 6, tooltipY + tooltipHeight + 6, innerAlpha, innerAlpha);
        drawGradientRect(tooltipX - 1, tooltipY, tooltipX, tooltipY + tooltipHeight + 6, innerAlpha, innerAlpha);
        drawGradientRect(tooltipX + tooltipWidth + 6, tooltipY, tooltipX + tooltipWidth + 7, tooltipY + tooltipHeight + 6, innerAlpha, innerAlpha);
               
        //render the background outside box
        int outerAlpha1 = 0x505000FF;
        int outerAlpha2 = (outerAlpha1 & 0xFEFEFE) >> 1 | outerAlpha1 & -0x1000000;
        drawGradientRect(tooltipX, tooltipY + 1, tooltipX + 1, tooltipY + tooltipHeight + 6 - 1, outerAlpha1, outerAlpha2);
        drawGradientRect(tooltipX + tooltipWidth + 5, tooltipY + 1, tooltipX + tooltipWidth + 7, tooltipY + tooltipHeight + 6 - 1, outerAlpha1, outerAlpha2);
        drawGradientRect(tooltipX, tooltipY, tooltipX + tooltipWidth + 3, tooltipY + 1, outerAlpha1, outerAlpha1);
        drawGradientRect(tooltipX, tooltipY + tooltipHeight + 5, tooltipX + tooltipWidth + 7, tooltipY + tooltipHeight + 6, outerAlpha2, outerAlpha2);
     
        float tempzlevel = this.zLevel;
        this.zLevel = 10;
        int posy = y;
        for(String localtext: text){ 
        	this.drawString(Minecraft.getMinecraft().fontRendererObj, LiteModMain.formateTextColor(localtext), x, posy, 0xFFFFFF);
   		 	posy += 10;
   	 	}
        this.zLevel = tempzlevel;
	}
	private int maxStringLength(String[] textarray){
		int maxlength = 0;
		
		 for(String text: textarray){    
			 if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) > maxlength){
				 maxlength = Minecraft.getMinecraft().fontRendererObj.getStringWidth(LiteModMain.formateTextColor(text));
			 }
		 }		
		return maxlength;
	}

}
