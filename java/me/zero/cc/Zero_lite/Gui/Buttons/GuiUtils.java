package me.zero.cc.Zero_lite.Gui.Buttons;

import me.zero.cc.Zero_lite.LiteModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class GuiUtils {
	/**
	 * Gives an Integer back from the longest String of an Array
	 * @param textarray
	 * @return integer
	 */
	public static int maxStringLength(String[] textarray){
		int maxlength = 0;
		
		 for(String text: textarray){    
			 if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) > maxlength){
				 maxlength = Minecraft.getMinecraft().fontRendererObj.getStringWidth(LiteModMain.formateTextColor(text)) + 10;
			 }
		 }		
		return maxlength;
	}
	/**
	 * Looks if the given interger value is over the screen
	 * @param minecraft
	 * @param uiX
	 * @return boolean
	 */
	public static boolean isOverScreenWidth(Minecraft minecraft,int uiX){
		ScaledResolution reso = new ScaledResolution(minecraft);
		if(uiX > reso.getScaledWidth()){
			return true;
		}
		return false;
	}
	/**
	 * Looks if the given interger value is over the screen
	 * @param minecraft
	 * @param uiZ
	 * @return boolean
	 */
	public static boolean isOverScreenHeight(Minecraft minecraft,int uiZ){
		ScaledResolution reso = new ScaledResolution(minecraft);
		if(uiZ > reso.getScaledHeight()){
			return true;
		}
		return false;
	}
	
}
