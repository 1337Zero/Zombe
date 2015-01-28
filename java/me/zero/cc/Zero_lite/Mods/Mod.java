package me.zero.cc.Zero_lite.Mods;

import net.minecraft.client.gui.GuiScreen;

public interface Mod {
	/**
	 * Returns if the Mod is enabled
	 * @return Boolean
	 */
	public boolean isEnabled();
	
	/**
	 * Basic methode called every tick
	 */
	public void use();
	
	/**
	 * Returns the name of the Mod
	 * @return String
	 */
	public String getName();
	
	/**
	 * Returns the version of the Mod
	 * @return String
	 */
	public String getVersion();
	
	/**
	 * Returns the Guiscreen of the Mod null equals no interface
	 * @return Guiscreen
	 */
	public GuiScreen drawGui();
	
	/**
	 * Methode called by the Guiobjects to modify values in the Mod
	 * @param ValueToManupulate
	 * @param value
	 */
	public void manupulateValue(String ValueToManupulate,int value);
	/**
	 * Methode called by the Guiobjects to modify values in the Mod
	 * @param valueToManupulate
	 * @param b
	 */
	public void manupulateValue(String valueToManupulate, boolean b);
	/**
	 * Methode called by the Guiobjects to modify values in the Mod
	 * @param valueToManupulate
	 * @param value
	 */
	public void manupulateValue(String valueToManupulate, String value);
	/**
	 * Returns an IntegerValue which represent the Key on the Keyboard
	 * @return Integer
	 */
	public int getOn();
}
