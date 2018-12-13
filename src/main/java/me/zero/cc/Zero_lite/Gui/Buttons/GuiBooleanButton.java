package me.zero.cc.Zero_lite.Gui.Buttons;


import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.mods.ModData;

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
	
	public GuiBooleanButton(int id, int x, int y, int width, int height,String label, boolean value,String valueToManupulate,ModData moddata,LiteModMain speicher,String[] overlayText,ZGuiInterface in) {
		super(id, x, y, width, height, label, overlayText,in);
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
	@Override
	public boolean mouseClicked(double x, double y, int p_mouseClicked_5_) {
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
		return super.mouseClicked(x, y, p_mouseClicked_5_);
	}
	/*@Override
	protected boolean mousePressed(double x, double y) {
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
	}*/
}
