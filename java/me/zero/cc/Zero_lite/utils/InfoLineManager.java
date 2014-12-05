package me.zero.cc.Zero_lite.utils;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class InfoLineManager {

	private ArrayList<InfoLine> infolines = new ArrayList<InfoLine>();
	
	public InfoLineManager(Minecraft minecraft){
		int valuex = minecraft.displayWidth/2;
				
		infolines.add(new InfoLine(5, 5,GuiPositions.UP_LEFT));
		infolines.add(new InfoLine((valuex/2), 5,GuiPositions.UP_CENTER));
		infolines.add(new InfoLine(valuex, 5,GuiPositions.UP_RIGHT));	
		infolines.add(new InfoLine(valuex, 5,GuiPositions.MID_LEFT));	
		infolines.add(new InfoLine(valuex, 5,GuiPositions.MID_CENTER));	
		infolines.add(new InfoLine(valuex, 5,GuiPositions.MID_RIGHT));	
		infolines.add(new InfoLine(valuex, 5,GuiPositions.DOWN_LEFT));	
		infolines.add(new InfoLine(valuex, 5,GuiPositions.DOWN_CENTER));	
		infolines.add(new InfoLine(valuex, 5,GuiPositions.DOWN_RIGHT));	
	}
	
	public void use(Minecraft minecraft){				
		for(int i = 0; i < infolines.size();i++){
			infolines.get(i).UpdateLine(minecraft);
		}
	}
	
	public InfoLine getInfoLine(int x){
		return infolines.get(x);
	}
	public InfoLine getInfoLine(GuiPositions pos){		
		return getInfoLine(pos.getPos(pos));
	}
	
}
