package me.zero.cc.Zero_lite.Gui;

import net.minecraft.client.Minecraft;
import me.zero.cc.Zero_lite.utils.Speicher;

public class Zombe_Menu{

	private boolean shown = false;
	private Speicher speicher;
	
	public Zombe_Menu(Speicher speicher){
		this.speicher = speicher;	
	}	
	
	public void draw(Minecraft minecraft){	
		if(!shown){
			ConfigMainFrame gui = new ConfigMainFrame(speicher);
			minecraft.displayGuiScreen(gui);
			shown = true;
		}
	}	
	public void setShown(boolean shown){
		this.shown = shown;
	}
}
