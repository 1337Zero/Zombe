package me.zero.cc.Zero_lite.utils;

import me.zero.cc.Zero_lite.LiteModMain;

import org.lwjgl.input.Keyboard;

public class KeySetting {

	private int key;
	private String configname;
		
	public KeySetting(int key,String configname){
		this.key = key;
		this.configname = configname;
	}
	
	public KeySetting(String configname){
		this.configname = configname;
		load();
	}	
	private void load(){
		this.key = Integer.parseInt(LiteModMain.config.getData(configname));
	}
	public boolean isPressed(){
		return Keyboard.isKeyDown(key);
	}
	public void replaceKey(String configname,int key){
		LiteModMain.config.replaceData(configname, key + "");
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
		replaceKey(configname, key);
	}

	public String getConfigname() {
		return configname;
	}

	public void setConfigname(String configname) {
		this.configname = configname;
	}

	@Override
	public String toString() {
		return "KeySetting [key=" + key + ", configname=" + configname + "]";
	}
}
