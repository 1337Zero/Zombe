package me.zero.cc.Zero_lite.Gui.Buttons;

import java.awt.event.KeyEvent;



public class GuiChooseKeyButton extends ZGuiButton{
	private int buttonkey = 0;
	private int xstart = 0;
	private int ystart = 0;
	private int width = 0;
	private int height = 0;
	private String txt = "";
	private String buttonname = "";
	private boolean waiting = false;
	private ZGuiInterface zi;
	
	
	public GuiChooseKeyButton(int id, int x, int y, int width, int height,String label, int buttonkey,String[] overlayText,ZGuiInterface in){
		super(id, x, y, width, height, label, overlayText,in);		
		this.buttonkey = buttonkey;
		this.xstart = x;
		this.ystart = y;
		this.width = width;
		this.height = height;
		this.txt = label;
		this.buttonname = label;
		this.zi = in;
		setLabel(label + ": " + makeBetterReadable(buttonkey));
	}
	private String makeBetterReadable(int key) {
		String name = KeyEvent.getKeyText(buttonkey);
		if(name.length() > 10) {
			return "" + key;
		}
		return name;
	}

	@Override
	public void render(int x, int y, float partialTicks) {
		super.render(x, y, partialTicks);		
		if(waiting){
			this.displayString = buttonname + " waiting...";
		}else{
			this.displayString = buttonname + ": " + makeBetterReadable(buttonkey);
		}
	}
	@Override
	public boolean mouseClicked(double x, double y, int p_mouseClicked_5_) {
		if(x > xstart && x < (xstart + width)){			 
			 if(y > ystart && y < (ystart + height)){				 
				 waiting = true;
				 this.displayString = buttonname + " waiting...";
				 zi.actionPerformed(this);
				 return true;
			 }			 
		 }		 
		return super.mouseClicked(x, y, p_mouseClicked_5_);
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
	public void pressKey(int key) {
		
	}
}
