package me.zero.cc.Zero_lite.utils;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
public class InfoLine {

	private ArrayList<String> infos = new ArrayList<String>();

	private boolean visible = false;
	private GuiPositions pos;
	
	public InfoLine(GuiPositions pos){
		this.pos = pos;
	}
	/**
	 * Return true if visible
	 * @return Boolean
	 */
	public boolean isVisible() {
		return visible;
	}
	/**
	 * Set the Visible value
	 * @param Boolean
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	/**
	 * Addes a Text to the Info Arraylist
	 * returns the position in the arraylist
	 * @param String
	 * @return Integer
	 */
	public int addInfo(String info){
		infos.add(info);
		return infos.size() -1;		
	}
	/**
	 * Removes text from the Arraylist with the given id
	 * @param Integer
	 */
	public void removeInfo(int id){
		infos.remove(id);
	}
	/**
	 * Resets the text with the given id
	 * @param Integer
	 */
	public void resetInfo(int id){
		infos.set(id, "");
	}
	/**
	 * Sets the text with the given id
	 * @param Integer
	 * @param String
	 */
	public void setInfo(int id, String txt){
		infos.set(id, txt);
	}
	/**
	 * Updates and draws the Infoline
	 * @param Minecraft
	 */
	public void UpdateLine(Minecraft minecraft){
		if(minecraft != null && minecraft.mainWindow != null) {
			//ScaledResolution reso = new ScaledResolution(minecraft);
			int posx = minecraft.mainWindow.getScaledWidth();
			int posy = minecraft.mainWindow.getScaledHeight();
									
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
				minecraft.fontRenderer.drawStringWithShadow(info, (posx-minecraft.fontRenderer.getStringWidth(info)), posy, 0xffff0000);	
				//minecraft.fontRenderer.drawStringWithShadow(info, posy, posy, posx)
			}else{
				minecraft.fontRenderer.drawStringWithShadow(createInfoLine(), posx, posy, 0xffff0000);
			}	
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
