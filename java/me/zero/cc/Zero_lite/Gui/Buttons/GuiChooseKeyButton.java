package me.zero.cc.Zero_lite.Gui.Buttons;

import org.lwjgl.input.Keyboard;

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
	
	public GuiChooseKeyButton(int id, int x, int y, int width, int height,String label, int buttonkey){
		super(id, x, y, width, height, label);		
		this.buttonkey = buttonkey;
		this.xstart = x;
		this.ystart = y;
		this.width = width;
		this.height = height;
		this.txt = label;
		this.buttonname = label;
		setLabel(label + ": " + Keyboard.getKeyName(this.buttonkey));
	}
	protected void mouseDragged(Minecraft mc, int x, int y) {
		if(waiting){
			this.displayString = buttonname + " waiting...";
		}else{
			this.displayString = buttonname + ": " + Keyboard.getKeyName(buttonkey);
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
	public void setLabel(String txt){
		this.txt = txt;
	}
	public String getLabel(){
		return txt;
	}
	public int getButtonkey() {
		return buttonkey;
	}
	public void setButtonkey(int buttonkey) {
		waiting = false;
		this.buttonkey = buttonkey;
	}
}
