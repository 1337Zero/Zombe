package me.zero.cc.Zero_lite.Gui.Buttons;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.gl.GL;

import me.zero.cc.Zero_lite.LiteModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiChooseKeyButton extends GuiButton{
	private int buttonkey = 0;
	private int xstart = 0;
	private int ystart = 0;
	private int width = 0;
	private int height = 0;
	private String txt = "";
	private String buttonname = "";
	private boolean waiting = false;
	private String[] overlayText;
	
	public GuiChooseKeyButton(int id, int x, int y, int width, int height,String label, int buttonkey,String[] overlayText){
		super(id, x, y, width, height, label);		
		this.buttonkey = buttonkey;
		this.xstart = x;
		this.ystart = y;
		this.width = width;
		this.height = height;
		this.txt = label;
		this.buttonname = label;
		setLabel(label + ": " + Keyboard.getKeyName(this.buttonkey));
		this.overlayText = overlayText;
	}
	protected void mouseDragged(Minecraft mc, int x, int y) {
		if(waiting){
			this.displayString = buttonname + " waiting...";
		}else{
			this.displayString = buttonname + ": " + Keyboard.getKeyName(buttonkey);
		}
		if(this.isMouseOver()){            	  
       	 RenderTooltip(x+10, y+10, overlayText);
        }
	}
	public boolean mousePressed(Minecraft mc, int x, int y) {		 
		 if(x > xstart && x < (xstart + width)){			 
			 if(y > ystart && y < (ystart + height)){				 
				 //yeah getroffen		
				 waiting = true;
				 this.displayString = buttonname + " waiting...";
				 return true;
			 }			 
		 }		 
		 return false;
	}
	public void mouseReleased(int x, int y) {
		//Unused
	}
	/**
	 * Set the text of the Button
	 * @param txt ,the new Label of the Button
	 */
	public void setLabel(String txt){
		this.txt = txt;
	}
	/**
	 * Get the text of the Button
	 * @return String ,the Label of the Button
	 */
	public String getLabel(){
		return txt;
	}
	/**
	 * Get the key of the Button as Integer value
	 * @return Integer
	 */
	public int getButtonkey() {
		return buttonkey;
	}
	/**
	 * Sets the key of the Button
	 * @param buttonkey ,the key for that Button
	 */
	public void setButtonkey(int buttonkey) {
		waiting = false;
		this.buttonkey = buttonkey;
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
        int tooltipWidth = GuiUtils.maxStringLength(text) +10;       
        
        int tooltipHeight = 10 + (text.length * 10);
        
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
        
        int posy = y;
        for(String localtext: text){ 
        	this.drawString(Minecraft.getMinecraft().fontRendererObj, LiteModMain.formateTextColor(localtext), x, posy, 0xFFFFFF);
   		 	posy += 10;
   	 	}
        GL.glEnableDepthTest();
	}
}
