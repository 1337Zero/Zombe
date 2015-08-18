package me.zero.cc.Zero_lite.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.*;

import com.mumfrey.liteloader.core.LiteLoader;

public class LanguageConfig {

	private HashMap<String, String> data = new HashMap<String, String>();
	private String path = "";	
	private List<String> defaultconfig = Arrays.asList(
	"Main.updateMSG:&4A new Lite-Zombe Version is avaible: <urlversion>","Main.choosepos:Choose the Position; of the Info-Textline",
	"Selection.firstmark:&6Set Firstmark to ( + <x>:<y>:<z>)","Selection.secondmark:&6Set Secondmark to ( + <x>:<y>:<z>)","Selection.selected:Selected <size> Blocks",
	"FlyMod.changeflyvalue:Change the Speed for flying;&6Up and Down","FlyMod.togglefly:Enable the Toggle-Flymod;Fly only works if both Buttons;Enable and Flydown/up are pressed","FlyMod.enablefly:Enable the Flymod","FlyMod.ignoreshift:If enabled, this will slow you down;if you press shift and Fly-up/down","FlyMod.nerfswim:Disables the 'Swim' Effect; for creative Fly","FlyMod.chooseflyup:Choose the key for Flyup","FlyMod.chooseflydown:Choose the key for Flydown","FlyMod.enable:Choose the key for enable/disable Flymod","FlyMod.Flyinfo:Shows the Fly-Speed",
	"InfoMod.showdir:Shows you your looking direction","InfoMod.showfps:Displays a fps counter","InfoMod.showcoor:Displays your x,y,z Coordinates","InfoMod.showworldage:Displays the World Age",
	"LightMod.togglelight:Enable the LightMod","LightMod.enable:Choose the key to enable the LightMod",
	"MobHighLighter.toggle:Enable the Mobhighlighter","MobHighLighter.chooseonkey:Choose the key to enable; the Mobhighlighter","MobHighLighter.showinfo:Display the Amount of marked Mobs",
	"OreHighLighter.toggle:Enable the OreHighLighter","OreHighLighter.radius:Set the Radius for OreHighLighter","OreHighLighter.chooseonkey:Choose the the key; to enable the Orehighlighter","OreHighLighter.showinfo:Displays the Radius",
	"PathMod.toggle:Enable the PathMod","PathMod.enablekey:Choose the key to enable the Pathmod","PathMod.showinfo:Displays the Amount of Marks","PathMod.seethroughwall:Enable displaying through walls",
	"RangeMod.togglereachplace:Enable Placing Blocks over distance","RangeMod.togglereachbreak:Enable breaking Blocks over distance","RangeMod.togglereachpick:Enable Picking Blocks; over distance","RangeMod.marker:Marks the Block; you are looking at (with range)","RangeMod.dropblock:Let Blocks drop items","RangeMod.removefrominv:Enable removing Items ;from Inventory","RangeMod.addtoinv:Get blocks you break; with range","RangeMod.range:Set the Range Value","RangeMod.chooseplacekey:Choose the key to enable Reachplace","RangeMod.choosepickkey:Choose the key to enable; Reachpick","RangeMod.choosebreakkey:Choose the key to Reachbreak","RangeMod.showinfo:Displays the Range Value",
	"RecipeMod.enable:Enable the RecipeMod","RecipeMod.loadcustomrecipes:Enable loading CustomRecipes; from your config","RecipeMod.showcraftinpattern:Displays possible Pattern for crafting Recipes;if you overlay possible Craftingoutputs",
	"SpeedMod.choosespeed:Choose your horizontal moving Speed","SpeedMod.togglespeed:Enable Toggle Speed;Speed only works if both Buttons;Enable and move are pressed","SpeedMod.enablespeed:Enable Speedmod","SpeedMod.inteligendmode:Slows you down if you are looking up or down","SpeedMod.showinfo:Displays the Speed Value","SpeedMod.choosespeedkey:Choose the key to enable SpeedMod",
	"TimeMod.freezetime:Freezes the time","TimeMod.addtime:Choose the Button to add 240","TimeMod.removetime:Choose the key to add 240 to the time ","TimeMod.multiplier:How fast should the time pass by ?","TimeMod.enable:Enable Timemod","TimeMod.showinfo:Displays the added time",
	""
	);
	
	/**
	 * Constructor for this class, loads the config from the Given path
	 * 
	 */
	
	public LanguageConfig(){
		path = System.getProperty("user.dir");
		path = path + System.getProperty("file.separator") + "mods" + System.getProperty("file.separator") + "Lite_Zombe";
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
		File config_File = new File(path + System.getProperty("file.separator") + "language.cfg");
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
		File config_File = new File(path + "\\language.cfg");
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
			File config_File = new File(path + "\\language.cfg");		
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
