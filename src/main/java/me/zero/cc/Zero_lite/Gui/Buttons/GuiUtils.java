package me.zero.cc.Zero_lite.Gui.Buttons;

import me.zero.cc.Zero_lite.LiteModMain;
import net.minecraft.client.Minecraft;

public class GuiUtils {
	/**
	 * Gives an Integer back from the longest String of an Array
	 * @param textarray
	 * @return integer
	 */
	public static int maxStringLength(String[] textarray){
		int maxlength = 0;
		
		 for(String text: textarray){    
			 if(Minecraft.getInstance().fontRenderer.getStringWidth(text) > maxlength){
				 maxlength = Minecraft.getInstance().fontRenderer.getStringWidth(LiteModMain.formateTextColor(text)) + 10;
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
		int scaledWidth = Minecraft.getInstance().mainWindow.getScaledWidth();
		if(uiX > scaledWidth){
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
		int scaledHeight = Minecraft.getInstance().mainWindow.getScaledHeight();
		if(uiZ > scaledHeight){
			return true;
		}
		return false;
	}
	
}
