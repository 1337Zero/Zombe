package me.zero.cc.Zero_lite.Gui.Buttons;

import org.lwjgl.opengl.GL11;

import me.zero.cc.Zero_lite.Mods.FlyMod;
import me.zero.cc.Zero_lite.Mods.ModData;
import me.zero.cc.Zero_lite.Mods.OreHighlighterMod;
import me.zero.cc.Zero_lite.Mods.SpeedMod;
import me.zero.cc.Zero_lite.Mods.TimeMod;
import me.zero.cc.Zero_lite.utils.Speicher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

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
      private Speicher speicher;
      
      
	public SimpleSlider(int id, int x, int y, String label, int startingValue,int width,int height,ModData modname,String valueNameToManupulate,Speicher speicher) {
		super(id, x, y, width, height, label);		
		xstart = x;
		xPosition = (int) ((((double)width/10.0)*(double)startingValue) + (double)xstart);
		yPosition = y;
		this.height = height;
		this.width = width;
		this.sliderValue = 0.0F;
		int value = 0 ;
		if(modname.name().equalsIgnoreCase(ModData.FlyMod.name())){
			value =  (int)((FlyMod)speicher.getMod(modname.name())).getFlyValue();
		}else if(modname.name().equalsIgnoreCase(ModData.SpeedMod.name())){
			value =  (int)((SpeedMod)speicher.getMod(modname.name())).getSpeedValue();
		}else if(modname.name().equalsIgnoreCase(ModData.TimeMod.name())){
			 value = ((TimeMod)speicher.getMod(ModData.TimeMod.name())).getMultipl();
		}else if(modname.name().equalsIgnoreCase(ModData.OreHighLighter.name())){
			 value = ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).getRadius()*10;
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
        	 //System.out.println(xPosition);
             this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 66, 4, 20);
             this.drawTexturedModalRect(this.xPosition + 4, this.yPosition, 196, 66, 4, 20);
         }
	}
	public void setText(String text){
		txt = text;
	}
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
		 int value = 0;
		 if(modname.name().equalsIgnoreCase(ModData.FlyMod.name())){
			 value =  (int)((FlyMod)speicher.getMod(modname.name())).getFlyValue();
		 }else if(modname.name().equalsIgnoreCase(ModData.SpeedMod.name())){
			 value =  (int)((SpeedMod)speicher.getMod(modname.name())).getSpeedValue();
		 }else if(modname.name().equalsIgnoreCase(ModData.TimeMod.name())){
			 value = ((TimeMod)speicher.getMod(ModData.TimeMod.name())).getMultipl();
		 }else if(modname.name().equalsIgnoreCase(ModData.OreHighLighter.name())){
			 value = ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).getRadius()*10;
		 }	
		 
		 this.setText(valueToManupulate + ": " + value);
	 }	 
	 /**
	  * 
	  * @return the value in percent where the sliders is at the moment,not 100% accurate
	  */
	 public int getPercent(){		
		int xvalue = xPosition - xstart;
		if(xvalue == 1){
			xvalue = 0;
		}
		if(xvalue == 141){
			xvalue = width;
		}
		 return (int) (xvalue / (width / 100.0));
	 }
}
