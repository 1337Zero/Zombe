package me.zero.cc.Zero_lite.Mods;

import net.minecraft.client.gui.GuiScreen;

public interface Mod {
	
	public boolean isEnabled();
	public void use();
	public String getName();
	public String getVersion();
	public GuiScreen drawGui();
	public void manupulateValue(String ValueToManupulate,int value);
	public void manupulateValue(String valueToManupulate, boolean b);
	public void manupulateValue(String valueToManupulate, String value);
}
