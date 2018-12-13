package me.zero.cc.Zero_lite.utils;

import me.zero.cc.Zero_lite.LiteModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class KeySetting {

	private int key;
	private String configname;
	private KeyBinding binding;
		
	public KeySetting(int key,String configname){
		this(configname);
		this.key = key;		
		load();
	}
	
	public KeySetting(String configname){
		this.configname = configname;
		load();
	}	
	private void load(){
		this.key = Integer.parseInt(LiteModMain.config.getData(configname));	
		if(Minecraft.getInstance().gameSettings.keyBindJump.getDefault().getKeyCode() == key) {
			System.out.println("detected the same key as jump ... using jump as key");
			binding = Minecraft.getInstance().gameSettings.keyBindJump;
			System.out.println("binding to key " + key);
		}else {
			binding = new KeyBinding("zombe.mod." + configname ,key , "key.categories.zombe");
			System.out.println("binding to key " + key);
		}		
	}
	public boolean isPressed(){
		/*if(binding.isPressed()) {
			System.out.println("key (" + configname + " is isPressed");
		}*/
		return binding.isPressed();
	}
	public boolean isKeyDown(){
		/*if(binding.isKeyDown()) {
			System.out.println("key (" + configname + " is down");
		}*/
		return binding.isKeyDown();
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
		//binding.func_197984_a(key);
		if(Minecraft.getInstance().gameSettings.keyBindJump.getDefault().getKeyCode() == key) {
			System.out.println("detected the same key as jump ... using jump as key");
			binding = Minecraft.getInstance().gameSettings.keyBindJump;
			System.out.println("binding to new key " + key);
		}else {
			binding = new KeyBinding("zombe.mod." + configname ,key , "key.categories.zombe");
			System.out.println("binding to new key " + key);
		}	
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
