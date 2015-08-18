package me.zero.cc.Zero_lite.Gui.Buttons;

import java.awt.Color;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.lwjgl.opengl.GL11;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Mods.RangeMod;
import me.zero.cc.Zero_lite.Mods.FlyMod;
import me.zero.cc.Zero_lite.Mods.ModData;
import me.zero.cc.Zero_lite.Mods.OreHighlighterMod;
import me.zero.cc.Zero_lite.Mods.SpeedMod;
import me.zero.cc.Zero_lite.Mods.TimeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class SimpleSlider extends GuiButton{

	  public float sliderValue = 1.0F;
      public boolean dragging = false;
      private int xstart = 0;
      private int xPosition = 0;
      private int yPosition = 0;
      private int width = 0;
      private int height = 0;
      private String txt = "";
      private ModData modname;
      private String valueToManupulate;
      private LiteModMain speicher;
      private String[] overlayText;
      
	public SimpleSlider(int id, int x, int y, String label, double startingValue,int width,
			int height,ModData modname,String valueNameToManupulate,LiteModMain speicher,String[] overlayText) {
		super(id, x, y, width, height, label);		
		xstart = x;
		xPosition = (int) ((((double)width/10.0)*(double)startingValue) + (double)xstart);
		yPosition = y;
		this.height = height;
		this.width = width;
		this.sliderValue = 0.0F;
		this.overlayText = overlayText;
		double value = 0 ;
		if(modname.name().equalsIgnoreCase(ModData.FlyMod.name())){
			value =  ((FlyMod)speicher.getMod(modname.name())).getFlyValue();
		}else if(modname.name().equalsIgnoreCase(ModData.SpeedMod.name())){
			value =  ((SpeedMod)speicher.getMod(modname.name())).getSpeedValue();
		}else if(modname.name().equalsIgnoreCase(ModData.TimeMod.name())){
			 value = ((TimeMod)speicher.getMod(ModData.TimeMod.name())).getMultipl();
		}else if(modname.name().equalsIgnoreCase(ModData.OreHighLighter.name())){
			 value = ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).getRadius()*10;
		}else if(modname.name().equalsIgnoreCase(ModData.RangeMod.name())){
			 value = ((RangeMod)speicher.getMod(ModData.RangeMod.name())).getRange();
		}		
		txt = valueNameToManupulate + ": " + value;
		this.valueToManupulate = valueNameToManupulate;
		this.modname = modname;
		this.speicher = speicher;
	}
	protected void mouseDragged(Minecraft mc, int x, int y) {
         if (this.enabled){
        	 if (this.dragging){          	
                 if(xstart < x && (xstart + width)-8 > x){
                	 xPosition = x;
                 }
        	 }         
        	 this.displayString = txt;
        	 
        	 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
             this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 66, 4, 20);
             this.drawTexturedModalRect(this.xPosition + 4, this.yPosition, 196, 66, 4, 20);
             
             if(this.isMouseOver()){        
            	 RenderTooltip(x+10, y+10, overlayText);
             }
         }
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
	/**
	 * Set the text of the Slider
	 * @param text, the Label of the Slider
	 */
	public void setText(String text){
		txt = text;
	}
	/**
	 * Get the text of the Slider
	 * @return String, the label of the Slider
	 */
	public String getText(){
		return txt;
	}
	
	 public boolean mousePressed(Minecraft mc, int x, int y) {
		 if(y > yPosition && y < (yPosition + height)){
			 this.dragging = true;
			 return true;
		 }else{
			 return false;
		 }
	 } 
	 public void mouseReleased(int x, int y) {
		 this.dragging = false;
		 if(xstart < x && (xstart + width)-8 > x){
        	xPosition = x;
         }
		 speicher.getMod(modname.name()).manupulateValue(valueToManupulate, getPercent());
		 double value = 0;
		 if(modname.name().equalsIgnoreCase(ModData.FlyMod.name())){
			 value =  ((FlyMod)speicher.getMod(modname.name())).getFlyValue();
		 }else if(modname.name().equalsIgnoreCase(ModData.SpeedMod.name())){
			 value =  ((SpeedMod)speicher.getMod(modname.name())).getSpeedValue();
		 }else if(modname.name().equalsIgnoreCase(ModData.TimeMod.name())){
			 value = ((TimeMod)speicher.getMod(ModData.TimeMod.name())).getMultipl();
		 }else if(modname.name().equalsIgnoreCase(ModData.OreHighLighter.name())){
			 value = ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).getRadius()*10;
		 }else if(modname.name().equalsIgnoreCase(ModData.RangeMod.name())){
			 value = ((RangeMod)speicher.getMod(ModData.RangeMod.name())).getRange();
		 }		
		 NumberFormat df = NumberFormat.getInstance();
		 df.setMaximumFractionDigits(2);
		 this.setText(valueToManupulate + ": " + df.format(value));
	 }	 
	 /**
	  * 
	  * @return the value in percent where the sliders is at the moment,not 100% accurate
	  */
	 public double getPercent(){		
		int xvalue = xPosition - xstart;
		if(xvalue == 1){
			xvalue = 0;
		}
		if(xvalue == 141){
			xvalue = width;
		}
		return (xvalue / (width / 100.0));
	 }
}
