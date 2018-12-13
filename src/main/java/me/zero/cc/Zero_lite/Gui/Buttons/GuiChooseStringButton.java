package me.zero.cc.Zero_lite.Gui.Buttons;

import java.util.List;


import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.mods.ModData;

public class GuiChooseStringButton extends ZGuiButton{

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
	
	public GuiChooseStringButton(int id, int x, int y, int width, int height,String label, List<String> list,String valueToManupulate,ModData moddata,LiteModMain speicher,int defaultpos,String[] overlayText,ZGuiInterface in) {
		super(id, x, y, width, height, label, overlayText,in);
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
		this.displayString = txt + ": " + list.get(listpos);
	}


	@Override
	public boolean mouseClicked(double x, double y, int p_mouseClicked_5_) {
		if(x > xstart && x < (xstart + width)){			 
			 if(y > ystart && y < (ystart + height)){					 
				 listpos++;				 
				 if(listpos >= list.size()){
					 listpos = 0;
				 }
				 speicher.getMod(moddata.name()).manupulateValue(valueToManupulate, list.get(listpos));		
				 this.displayString = txt + ": " + list.get(listpos);
				 return true;
			 }			 
		 }		
		return super.mouseClicked(x, y, p_mouseClicked_5_);
	}
	
	/*@Override
	protected boolean mousePressed(double x, double y) {	
		 if(x > xstart && x < (xstart + width)){			 
			 if(y > ystart && y < (ystart + height)){					 
				 listpos++;				 
				 if(listpos >= list.size()){
					 listpos = 0;
				 }
				 speicher.getMod(moddata.name()).manupulateValue(valueToManupulate, list.get(listpos));		
				 this.displayString = txt + ": " + list.get(listpos);
				 return true;
			 }			 
		 }		 
		 return false;
	}*/
}
