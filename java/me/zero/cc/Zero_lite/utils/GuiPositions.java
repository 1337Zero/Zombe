package me.zero.cc.Zero_lite.utils;

import java.util.ArrayList;

public enum GuiPositions {

	UP_LEFT,UP_CENTER,UP_RIGHT,
	MID_LEFT,MID_CENTER,MID_RIGHT,
	DOWN_LEFT,DOWN_CENTER,DOWN_RIGHT;
	
	/**
	 * Returns a Integer Value which represends the Position
	 * @param GuiPositions
	 * @return Integer
	 */
	public static int getPos(GuiPositions pos){		
		if(pos.name().equals(GuiPositions.UP_LEFT.name())) return 0;
		if(pos.name().equals(GuiPositions.UP_CENTER.name())) return 1;
		if(pos.name().equals(GuiPositions.UP_RIGHT.name())) return 2;	
		if(pos.name().equals(GuiPositions.MID_LEFT.name())) return 3;	
		if(pos.name().equals(GuiPositions.MID_CENTER.name())) return 4;	
		if(pos.name().equals(GuiPositions.MID_RIGHT.name())) return 5;	
		if(pos.name().equals(GuiPositions.DOWN_LEFT.name())) return 6;	
		if(pos.name().equals(GuiPositions.DOWN_CENTER.name())) return 7;	
		if(pos.name().equals(GuiPositions.DOWN_RIGHT.name())) return 8;	
		
		throw new IllegalArgumentException(pos.name() + " is not Supported!");
	}
	
	public int size(){
		return 9;
	}
	/**
	 * Returns a List with the names of Guipositions
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> getPosList(){
		ArrayList<String> list = new ArrayList<String>();		
		list.add(GuiPositions.UP_LEFT.name());
		list.add(GuiPositions.UP_CENTER.name());
		list.add(GuiPositions.UP_RIGHT.name());	
		list.add(GuiPositions.MID_LEFT.name());
		list.add(GuiPositions.MID_CENTER.name());
		list.add(GuiPositions.MID_RIGHT.name());
		list.add(GuiPositions.DOWN_LEFT.name());
		list.add(GuiPositions.DOWN_CENTER.name());
		list.add(GuiPositions.DOWN_RIGHT.name());
		return list;
	}
	
	
}
