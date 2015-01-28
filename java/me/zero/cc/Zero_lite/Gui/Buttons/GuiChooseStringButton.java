package me.zero.cc.Zero_lite.Gui.Buttons;

import java.util.List;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Mods.ModData;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiChooseStringButton extends GuiButton{

	private List<String> list;
	private int listpos = 0;
	private int xstart = 0;
	private int ystart = 0;
	private int width = 0;
	private int height = 0;
	private String txt = "";
	private ModData moddata;
	private String valueToManupulate;
	private LiteModMain speicher;
	
	public GuiChooseStringButton(int id, int x, int y, int width, int height,String label, List<String> list,String valueToManupulate,ModData moddata,LiteModMain speicher,int defaultpos) {
		super(id, x, y, width, height, label);
		this.list = list;
		this.xstart = x;
		this.ystart = y;
		this.width = width;
		this.height = height;
		this.txt = label;
		this.moddata = moddata;
		this.valueToManupulate = valueToManupulate;
		this.speicher = speicher;
		listpos = defaultpos;
	}
	protected void mouseDragged(Minecraft mc, int x, int y) {
		 this.displayString = txt + ": " + list.get(listpos);
	}
	public boolean mousePressed(Minecraft mc, int x, int y) {		 
		 if(x > xstart && x < (xstart + width)){			 
			 if(y > ystart && y < (ystart + height)){					 
				 listpos++;				 
				 if(listpos >= list.size()){
					 listpos = 0;
				 }
				 speicher.getMod(moddata.name()).manupulateValue(valueToManupulate, list.get(listpos));				
				 return true;
			 }			 
		 }		 
		 return false;
	}
	public void mouseReleased(int x, int y) {
		//Unused
	}

}
