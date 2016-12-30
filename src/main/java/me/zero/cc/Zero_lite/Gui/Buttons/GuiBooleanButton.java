package me.zero.cc.Zero_lite.Gui.Buttons;

import org.lwjgl.opengl.GL11;

import com.mumfrey.liteloader.gl.GL;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Mods.ModData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;

public class GuiBooleanButton extends ZGuiButton{

	private boolean value = false;
	private int xstart = 0;
	private int ystart = 0;
	private int width = 0;
	private int height = 0;
	private String txt = "";
	private ModData moddata;
	private String valueToManupulate;
	private LiteModMain speicher;
	
	public GuiBooleanButton(int id, int x, int y, int width, int height,String label, boolean value,String valueToManupulate,ModData moddata,LiteModMain speicher,String[] overlayText) {
		super(id, x, y, width, height, label, overlayText);
		this.value = value;
		this.xstart = x;
		this.ystart = y;
		this.width = width;
		this.height = height;
		this.txt = label;
		this.moddata = moddata;
		this.valueToManupulate = valueToManupulate;
		this.speicher = speicher;
		this.displayString = txt + ": " + value;
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
				 this.displayString = txt + ": " + value;
				 return true;
			 }			 
		 }		 
		 return false;
	}
}
