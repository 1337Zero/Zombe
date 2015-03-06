package me.zero.cc.Zero_lite.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.*;

import com.mumfrey.liteloader.core.LiteLoader;

public class Config {

	private HashMap<String, String> data = new HashMap<String, String>();
	private String path = "";	
	private List<String> defaultconfig = Arrays.asList(
			"Main.searchupdates:true","Main.debug:false","Main.cmdControlCharacter:#","Main.selectionR:1.0F","Main.selectionG:1.0F","Main.selectionB:1.0F","Main.selectionAlpha:0.5F","Main.enableSelection:true","Main.firstMarkR:1.0F","Main.firstMarkG:0.0F","Main.firstMarkB:1.0F","Main.firstMarkAlpha:1.0F","Main.secondMarkR:0.0F","Main.secondMarkG:1.0F","Main.secondMarkB:1.0F","Main.secondMarkAlpha:1.0F",
			"Fly-Mod.Key-Down:16","Fly-Mod.ignoreshift:false","Fly-Mod.Key-Up:57","Fly-Mod.key-Toggle-fly:46","Fly-Mod.flyspeed:1","Fly-Mod.maxflyspeed:10","Fly-Mod.showfly:true","Fly-Mod.fly-Pos:UP_LEFT","Fly-Mod.fly-enabled:false","Fly-Mod.toggle-fly:false","Fly-Mod.nerfcreaetivefly:true",
			"Speed-Mod.Toggle-speed:21","Speed-Mod.maxspeed:10","Speed-Mod.speed:1","Speed-Mod.showspeed:true","Speed-Mod.speed-Pos:UP_LEFT","Speed-Mod.togglespeed:false","Speed-Mod.speed-enabled:false","Speed-Mod.enabled:false",
			"Info-Mod.FPS-Pos:UP_CENTER","Info-Mod.showFPS:false","Info-Mod.Dir-POS:UP_RIGHT","Info-Mod.showdir:false","Info-Mod.Coor-Pos:UP_RIGHT","Info-Mod.showcoor:false","Info-Mod.Worldage-Pos:UP_LEFT","Info-Mod.showworldage:false",
			"Light-Mod.Toggle-Light:38","Light-Mod.lightmod-enabled:false",
			"Time-Mod.Key-addtime:78","Time-Mod.Key-subtime:74","Time-Mod.Key-freezetime:55","Time-Mod.timetoadd:0","Time-Mod.Time-Mod-enabled:false","Time-Mod.time-freezed:false","Time-Mod.time-multiplier:0","Time-Mod.showTimeinfo:false","Time-Mod.showtimepos:UP_CENTER",
			"RecipeMod.Enabled:false",
			"MobHighlighter.enabled:false","MobHighlighter.Toggle-Mobhighlighter:55","MobHighlighter.info-Pos:UP_LEFT","MobHighlighter.showinfo:false",
			"OreHighlighter.enabled:false","OreHighlighter.Toggle-OreHighlighter:27","OreHighlighter.info-Pos:UP_LEFT","OreHighlighter.showinfo:false","OreHighlighter.radius:2",
			"PathMod.info-Pos:UP_LEFT","PathMod.showinfo:false","PathMod.enabled:false","PathMod.Toggle-PathMod:11","PathMod.seethroughwall:true","PathMod.mark.r:1.0F","PathMod.mark.g:0.0F","PathMod.mark.b:1.0F","PathMod.mark.alpha:1.0F",
			"RangeMod.Range:16","RangeMod.DropBlock:false","RangeMod.MarkBlock:false","RangeMod.removefrominventory:true","RangeMod.addtoinventory:true","RangeMod.placedelay:125","RangeMod.destroydelay:125","RangeMod.reachbreak:false","RangeMod.reachplace:false","RangeMod.reachbreakonkey:12","RangeMod.reachplaceonkey:52","RangeMod.showinfo:false","RangeMod.info-Pos:UP_LEFT","RangeMod.reachpick:false","RangeMod.pickdelay:250","RangeMod.reachpickonkey:55");
	
	/**
	 * Constructor for this class, loads the config from the Given path
	 * 
	 */
	
	public Config(){
		path = System.getProperty("user.dir");
		path = path + System.getProperty("file.separator") + "Mods" + System.getProperty("file.separator") + "Lite_Zombe";
		try {
			if(!LiteLoader.isDevelopmentEnvironment()){
				loadConfig();
			}
			
		} catch (Exception e) {
			System.out.println("---- Oops >.< ----");
			e.printStackTrace();
		}
	}
	/**
	 * Load all stuff from HDD
	 */
	private void loadConfig() throws Exception{
		data.clear();
		File folder_Lite_Zombe = new File(path);		
		
		if(!folder_Lite_Zombe.exists()){
			folder_Lite_Zombe.mkdir();
		}
		File config_File = new File(path + "\\config.cfg");
		if(!config_File.exists()){
			config_File.createNewFile();
			createConfigFile(defaultconfig, "");
		}
		BufferedReader br = new BufferedReader (new FileReader(config_File));
		String loaded = br.readLine();
		while(loaded != null){
			if(!loaded.contains("#")){	
				if(loaded.length() > 1){
					data.put(loaded.split(":")[0], loaded.split(":")[1]);
				}				
			}			
			loaded = br.readLine();
		}
		br.close();
	}
	/**
	 * Creates the Config File and writes data from a list into it.
	 * @param list The default Config
	 * @param valuetochange the value which should be changed
	 * @throws Exception
	 */
	private void createConfigFile(List<String> list,String valuetochange) throws Exception{
		File config_File = new File(path + "\\config.cfg");
		config_File.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(config_File));
		for(int i = 0; i < list.size();i++){			
			if(list.get(i).split(":")[0].equalsIgnoreCase(valuetochange.split(":")[0])){
				bw.write(valuetochange + "\n");
			}else{
				bw.write(list.get(i) + "\n");
			}		
		}
		bw.flush();
		bw.close();
	}
	/**
	 * Save Stuff to HDD
	 * @param changedpart The Part of the config that was modifyed
	 * @throws Exception 
	 */
	private void saveConfig(String changedpart) throws Exception{
		if(!LiteLoader.isDevelopmentEnvironment()){
			File config_File = new File(path + "\\config.cfg");		
			List<String> back = new ArrayList<String>();		
			for(String key : data.keySet()){
		      back.add(key + ":" + data.get(key));
		    }
			config_File.delete();
			createConfigFile(back,changedpart);
			loadConfig();
		}		
	}
	/**
	 * Returns Data stored in a HashMap, loded from the HDD.
	 * @param key ,an identifier for the Hashmap
	 * @return data loaded from File as String
	 */
	public String getData(String key){
		String back = "";
		if(data.get(key) == null){
			System.out.println("Your config-File is old, i will try update it for you ;D");
			for(int i = 0; i < defaultconfig.size();i++){
				if(defaultconfig.get(i).split(":")[0].equalsIgnoreCase(key)){
					data.put(key, defaultconfig.get(i).split(":")[1]);
					System.out.println("adding " + defaultconfig.get(i));
					back = defaultconfig.get(i).split(":")[1];
					try {
						saveConfig("");
					} catch (Exception e) {
						e.printStackTrace();
					}
					return back;
				}
			}
		}		
		return data.get(key);
	}
	/**
	 * Replaces data in the hashmap, then writes the hashmap to the HDD
	 * 
	 * @param key ,value that should be changed
	 * @param newdata , value that should be stored
	 */
	public void replaceData(String key,String newdata){
		data.put(key, newdata);
		try{
			saveConfig(key + ":" +newdata);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}	
}
