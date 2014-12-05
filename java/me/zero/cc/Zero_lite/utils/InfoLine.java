package me.zero.cc.Zero_lite.utils;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class InfoLine {

	private ArrayList<String> infos = new ArrayList<String>();
	private boolean first = true;
	private int posx = 0;
	private int posy = 0;
	private boolean visible = false;
	private GuiPositions pos;
	
	public InfoLine(int posx, int posy,GuiPositions pos){
		this.posx = posx;
		this.posy = posy;
		this.pos = pos;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public int addInfo(String info){
		infos.add(info);
		return infos.size() -1;		
	}
	public void removeInfo(int id){
		infos.remove(id);
	}
	public void resetInfo(int id){
		infos.set(id, "");
	}
	public void setInfo(int id, String txt){
		infos.set(id, txt);
	}
	public void UpdateLine(Minecraft minecraft){
		ScaledResolution reso = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
		int posx = reso.getScaledWidth();
		int posy = reso.getScaledHeight();
		
		if(pos == GuiPositions.UP_LEFT){
			posx = 5;
			posy = 5;
		}	
		if(pos == GuiPositions.UP_CENTER){
			posx = posx/2;
			posy = 5;
		}		
		if(pos == GuiPositions.UP_RIGHT){
			posy = 5;
		}
		if(pos == GuiPositions.MID_LEFT){
			posx = 5;
			posy = posy/2;
		}
		if(pos == GuiPositions.MID_CENTER){
			posx = posx/2;
			posy = posy/2;
		}
		if(pos == GuiPositions.MID_RIGHT){
			posy = posy/2;
		}
		if(pos == GuiPositions.DOWN_LEFT){
			posx = 5;
			posy = posy-10;
		}		
		if(pos == GuiPositions.DOWN_CENTER){
			posx = posx/2;
			posy = posy-10;
		}
		if(pos == GuiPositions.DOWN_RIGHT){
			posy = posy-10;
		}
		
		if(pos != GuiPositions.UP_LEFT && pos != GuiPositions.MID_LEFT && pos != GuiPositions.DOWN_LEFT){
			String info = createInfoLine();
			minecraft.fontRendererObj.drawString(info, (posx-minecraft.fontRendererObj.getStringWidth(info)), posy, 0xffff0000);	
		}else{
			minecraft.fontRendererObj.drawString(createInfoLine(), posx, posy, 0xffff0000);
		}			
	}
	private String createInfoLine(){
		ArrayList<String> newinfos = new ArrayList<String>();
		for(int i = 0; i < infos.size();i++){
			if(!infos.get(i).equalsIgnoreCase("")){
				newinfos.add(infos.get(i));
			}			
		}
		return newinfos.toString().replace("[", "").replace("]", "");
	}
	
}
