package me.zero.cc.Zero_lite.Gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import me.zero.cc.Zero_lite.Mods.Mod;
import me.zero.cc.Zero_lite.utils.Speicher;

public class ConfigMainFrame extends GuiScreen{

	private Speicher speicher;
	
	public ConfigMainFrame(Speicher speicher){
		this.speicher = speicher;
	}
	
	public void initGui(){
		drawButtons();
	}
	
	public void drawScreen(int i, int j, float f){
		drawDefaultBackground();
		super.drawScreen(i, j, f);
	}
	
	public void actionPerformed(GuiButton b){	
		if(b.displayString.contains("back to game")){
			speicher.getMinecraft().displayGuiScreen(null);
			speicher.getZm().setShown(false);
		}else{
			Mod mod = speicher.getMod(b.displayString);
			speicher.getZm().setShown(false);
			if(mod != null){
				speicher.getMinecraft().displayGuiScreen(mod.drawGui());				
			}else{
				System.out.println(b.displayString + " is not a existing Mod");
			}
		}		
	}
	public void drawButtons(){
		for(int i = 0; i < speicher.getMods().size();i++){
			buttonList.add(new GuiButton(i, width/2-100, height/speicher.getMods().size() -10 + (i*20), 200, 20, speicher.getMods().get(i).getName()));
		}	
		buttonList.add(new GuiButton(speicher.getMods().size(), width/2-100, height/(speicher.getMods().size()+1) -10 + ((speicher.getMods().size()+1) *20), 200, 20,"back to game..."));
	}
	protected void keyTyped(char c,int key){
		//F7 equals Button 65, so close Gui on key 65
		if(key == 65 || key == 1){
			speicher.getMinecraft().displayGuiScreen(null);
			speicher.getZm().setShown(false);
		}
		
	}
	
}
