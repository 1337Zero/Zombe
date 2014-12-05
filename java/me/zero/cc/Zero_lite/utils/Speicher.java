package me.zero.cc.Zero_lite.utils;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import me.zero.cc.Zero_lite.Config.Config;
import me.zero.cc.Zero_lite.Gui.Zombe_Menu;
import me.zero.cc.Zero_lite.Mods.Mod;

public class Speicher {

	private Zombe_Menu zm;		
	private ArrayList<Mod> mods = new ArrayList<Mod>();
	private InfoLineManager ilm;
	private Minecraft minecraft;
	private Config config;
	
	public Zombe_Menu getZm() {
		return zm;
	}
	public void setZm(Zombe_Menu zm) {
		this.zm = zm;
	}
	public InfoLineManager getInfoLineManager() {
		return ilm;
	}
	public void setInfoLineManager(InfoLineManager ilm){
		this.ilm = ilm;
	}
	public ArrayList<Mod> getMods() {
		return mods;
	}
	public void addMod(Mod mod) {
		mods.add(mod);
	}
	public Mod getMod(String name){
		for(int i = 0; i < mods.size();i++){
			if(mods.get(i).getName().equals(name)){
				return mods.get(i);
			}
		}
		System.out.println("[ERROR] Mod {" + name + "} not found!");
		return null;
	}
	public Minecraft getMinecraft() {
		return minecraft;
	}
	public void setMinecraft(Minecraft minecraft) {
		this.minecraft = minecraft;
	}
	public Config getConfig() {
		return config;
	}
	public void setConfig(Config config) {
		this.config = config;
	}
	
}
