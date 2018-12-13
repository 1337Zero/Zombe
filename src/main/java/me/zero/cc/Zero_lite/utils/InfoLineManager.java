package me.zero.cc.Zero_lite.utils;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class InfoLineManager {

	private ArrayList<InfoLine> infolines = new ArrayList<InfoLine>();
	
	public InfoLineManager(Minecraft minecraft){
				
		infolines.add(new InfoLine(GuiPositions.UP_LEFT));
		infolines.add(new InfoLine(GuiPositions.UP_CENTER));
		infolines.add(new InfoLine(GuiPositions.UP_RIGHT));	
		infolines.add(new InfoLine(GuiPositions.MID_LEFT));	
		infolines.add(new InfoLine(GuiPositions.MID_CENTER));	
		infolines.add(new InfoLine(GuiPositions.MID_RIGHT));	
		infolines.add(new InfoLine(GuiPositions.DOWN_LEFT));	
		infolines.add(new InfoLine(GuiPositions.DOWN_CENTER));	
		infolines.add(new InfoLine(GuiPositions.DOWN_RIGHT));	
	}
	/**
	 * Called every tick to update lines
	 * @param Minecraft
	 */
	public void use(Minecraft minecraft){				
		for(int i = 0; i < infolines.size();i++){
			infolines.get(i).UpdateLine(minecraft);
		}
	}
	/**
	 * Get the Infoline with the given id
	 * @param Integer
	 * @return InfoLine
	 */
	public InfoLine getInfoLine(int x){
		return infolines.get(x);
	}
	/**
	 * Get the Infoline from the given GuiPosition
	 * @param GuiPositions
	 * @return InfoLine
	 */
	public InfoLine getInfoLine(GuiPositions pos){		
		return getInfoLine(GuiPositions.getPos(pos));
	}
	
}
