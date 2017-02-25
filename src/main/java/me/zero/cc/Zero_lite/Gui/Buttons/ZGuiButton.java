package me.zero.cc.Zero_lite.Gui.Buttons;


import com.mumfrey.liteloader.gl.GL;

import me.zero.cc.Zero_lite.LiteModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ZGuiButton extends GuiButton{

	private String[] overlayText;
	
	public ZGuiButton(int id, int x, int y, int width, int height,String label,String[] overlayText) {
		super(id, x, y, width, height, label);
		this.overlayText = overlayText;
	}
	protected void mouseDragged(Minecraft mc, int x, int y) {		 
		 if(this.isMouseOver()){            	  
        	 RenderTooltip(x+10, y+10, overlayText);
         }
	}
	/**
	 * This methode was orignally writte by Zyin055
	 * <a href=https://github.com/Zyin055/zyinhud/blob/master/src/main/java/com/zyin/zyinhud/gui/GuiTooltipScreen.java>https://github.com/Zyin055/zyinhud/blob/master/src/main/java/com/zyin/zyinhud/gui/GuiTooltipScreen.java</a>
	 * @param x
	 * @param y
	 * @param text
	 */
	protected void RenderTooltip(int x, int y, String[] text){	
        int tooltipX = x -10;
        int tooltipY = y -10;
        int tooltipWidth = GuiUtils.maxStringLength(text) +10;
        int tooltipHeight = 10 + (text.length * 10);
        
        if(GuiUtils.isOverScreenWidth(Minecraft.getMinecraft(), (tooltipWidth + x-5))){
            tooltipX = x - tooltipWidth -15;
        }
        if(GuiUtils.isOverScreenHeight(Minecraft.getMinecraft(), (tooltipHeight + y-5))){
            tooltipY = y -tooltipHeight -15;
        }
        
        float tempzlevel = this.zLevel;
        this.zLevel = 1;
        
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
     
        this.zLevel = tempzlevel;
        GL.glDisableDepthTest();
        int posy = tooltipY+10;
        for(String localtext: text){ 
        	//this.drawString(Minecraft.getMinecraft().fontRendererObj, LiteModMain.formateTextColor(localtext), x, posy, 0xFFFFFF);
        	Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(LiteModMain.formateTextColor(localtext), tooltipX+10, posy, 0xFFFFFF);
        	//Minecraft.getMinecraft().fontRendererObj.drawString(LiteModMain.formateTextColor(localtext), x, posy, 0xFFFFFF);
   		 	posy += 10;
   	 	}
        
        GL.glEnableDepthTest();
	}
}
