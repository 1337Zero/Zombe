package me.zero.cc.Zero_lite.Gui.Buttons;

import me.zero.cc.Zero_lite.LiteModMain;
import net.minecraft.client.Minecraft;

public class GuiUtils {

	public static int maxStringLength(String[] textarray){
		int maxlength = 0;
		
		 for(String text: textarray){    
			 if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) > maxlength){
				 maxlength = Minecraft.getMinecraft().fontRendererObj.getStringWidth(LiteModMain.formateTextColor(text)) + 10;
			 }
		 }		
		return maxlength;
	}
	
}
