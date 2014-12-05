package me.zero.cc.Zero_lite.Gui;

import java.util.ArrayList;
import me.zero.cc.Zero_lite.utils.Speicher;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class ModGui extends GuiScreen{

	private Speicher speicher;
	private ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();
	
	public ModGui(Speicher speicher){
		this.speicher = speicher;
	}
	
	public void setButtonList(ArrayList<GuiButton> button){
		this.buttons = button;
	}
	
	public void initGui(){
		drawButtons();
	}
	
	public void actionPerformed(GuiButton b){	
		if(b.displayString.contains("back to game")){
			speicher.getMinecraft().displayGuiScreen(null);
			speicher.getZm().setShown(false);
		}
	}
	
	public void drawButtons(){
		
		for(int i = 0; i < buttons.size();i++){
			buttonList.add(buttons.get(i)); 
		}
	}
	protected void keyTyped(char c,int key){
		if(key == 65){
			speicher.getMinecraft().displayGuiScreen(null);
			speicher.getZm().setShown(false);
		}
	}
	
}
