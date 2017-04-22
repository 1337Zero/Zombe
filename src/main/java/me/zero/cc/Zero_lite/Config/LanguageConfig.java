package me.zero.cc.Zero_lite.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import com.mumfrey.liteloader.core.LiteLoader;

public class LanguageConfig {

	private HashMap<String, String> data = new HashMap<String, String>();
	private String path = "";	
	private List<String> defaultconfig = Arrays.asList(
	"FlyMod:This Mod let you &6fly Up &fand &6Down faster &fthan normal;You can &6disable &fcreative &6fly swim;&4If you want to move fast forward, enable SpeedMod","InfoMod:Displays some Information;- &6Looking Direction;- &6Fps;- &6Coordinates;- &6WorldAge","SpeedMod:Let you &6move faster forward","LightMod:Disable &6Shadows and Darkness","RecipeMod:Shows you possible crafting Results;- &6Load your own 3*3 Recipes!;- &6See item crafting- pattern","TimeMod:Controll the &6Time!;&6- Make it Stop;&6- Make it faster/slower;&6- Add and substract Time","MobHighLighter:Marks Entities;&6- Change marks in your config;&4- This only affect the Renderer not the Game!","OreHighLighter:Marks Blocks;&6- Change marks in your config","PathMod:Get your moved way marked;&6- Never get lost in caves anymore!","RangeMod:Manupulate the World with;&6- Range Building;&6- Range Picking;&6- Range Breaking",
	"Main.updateMSG:&4A new Lite-Zombe Version is avaible: <urlversion>","Main.choosepos:Choose the &6Position;of the &6Info-Textline","Main.betaMSG:You are running beta version <urlversion>!;Please report bugs and errors!","Main.updatedMSG:Your are running the newest Version;Zombe-Lite (<urlversion>)",
	"Selection.firstmark:&6Set Firstmark to (<x>;<y>;<z>)","Selection.secondmark:&6Set Secondmark to (<x>;<y>;<z>)","Selection.selected:Selected <size> Blocks",
	"FlyMod.changeflyvalue:Change the Speed for flying;&6Up &fand &6Down","FlyMod.togglefly:Enable the Toggle-Flymod;Fly only works if &4both&f Buttons;&6Enable &fand &6Flydown or Up &fare pressed","FlyMod.enablefly:Enable &6Flymod","FlyMod.ignoreshift:If &6enabled&f, this will slow you down;if you press &6shift&f and &6flyup- &for &6down&f key","FlyMod.nerfswim:Disables the '&6swim&f' effect;for creative Fly;&6Flymod&f can be &4disabled&f to use this","FlyMod.chooseflyup:Choose the &6key&f for flying up","FlyMod.chooseflydown:Choose the &6key&f for flying down","FlyMod.enable:Choose the &6key;for &6enable/disable&f Flymod","FlyMod.Flyinfo:Displays the &6fly-speed-value",
	"InfoMod.showdir:Displays your looking &6direction","InfoMod.showfps:Displays a &6fps &fcounter","InfoMod.showcoor:Displays your &6x,y,z&f Coordinates","InfoMod.showworldage:Displays the &6World Age","InfoMod.showfriendlymobspawn:Marks possible spawns for friendly Entities","InfoMod.showaggressivmobspawn:Marks possible spawns for aggressiv Entities","InfoMod.dynamicselection:Only checks &4blocks&f you can &4see",
	"LightMod.togglelight:Enable the &6LightMod","LightMod.enable:Choose the &6key&f to enable",
	"MobHighLighter.toggle:Enable &6Mobhighlighter","MobHighLighter.chooseonkey:Choose the &6key&f to enable","MobHighLighter.showinfo:Display the &6Amount&f of marked &6Mobs",
	"OreHighLighter.toggle:Enable &6OreHighLighter","OreHighLighter.radius:Set the &6Radius for &6OreHighLighter","OreHighLighter.chooseonkey:Choose the the &6key; to enable","OreHighLighter.choosecavekey:Choose the the &6key; to see through Walls","OreHighLighter.showinfo:Displays the &6Radius","OreHighLighter.dynamicselection:Only checks &4blocks&f you can &4see","OreHighLighter.easymark:Uses &62&f instead of &64&f Marks per Block","OreHighLighter.deepness:Change how &6far&f to look into the ground",
	"PathMod.toggle:Enable &6PathMod","PathMod.enablekey:Choose the &6key;to enable &6Pathmod","PathMod.showinfo:Displays the &6Amount&f of &6Marks","PathMod.seethroughwall:Enable see &6Pathmarks through &6walls",
	"RangeMod.togglereachplace:Enable &6Placing Blocks&f over distance","RangeMod.togglereachbreak:Enable &6breaking Blocks&f over distance","RangeMod.togglereachpick:Enable &6Picking Blocks;over distance","RangeMod.marker:&6Marks&f the &6Block;you are looking at","RangeMod.dropblock:Let &6Blocks drop items","RangeMod.removefrominv:Enable &6removing Items;from &6Inventory","RangeMod.addtoinv:&6Get blocks &fyou break","RangeMod.range:Set the &6Range Value","RangeMod.chooseplacekey:Choose the key to &6enable Reachplace","RangeMod.choosepickkey:Choose the key to;&6enable Reachpick","RangeMod.choosebreakkey:Choose the key to &6enable Reachbreak","RangeMod.showinfo:Displays the &6Range Value",
	"RecipeMod.enable:Enable &6RecipeMod","RecipeMod.loadcustomrecipes:Enable &6loading CustomRecipes;from your &6config","RecipeMod.showcraftinpattern:Displays possible &6Pattern for crafting Recipes;if you &6overlay &fpossible &6Craftingoutputs",
	"SpeedMod.choosespeed:Choose your &6horizontal moving Speed","SpeedMod.togglespeed:Enable &6Toggle Speed;Speed only works if &4both&f Buttons;&6Enable &fand &6move&f are pressed","SpeedMod.enablespeed:Enable &6Speedmod","SpeedMod.inteligendmode:&6Slows&f you &6down &fif you are &6looking up or down","SpeedMod.showinfo:Displays the &6Speed Value","SpeedMod.choosespeedkey:Choose the &6key&f to &6enable SpeedMod",
	"TimeMod.freezetime:Enable &6Time freezing","TimeMod.addtime:Choose the Button to &6add 240","TimeMod.removetime:Choose the key to &6remove 240","TimeMod.multiplier:How fast should the &6time pass by&f ?","TimeMod.enable:Enable &6Timemod","TimeMod.showinfo:Displays the &6summed added time","TimeMod.choosefreeze:Choose the &6key&f to Freeze the Time",
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
		File config_File = new File(path + System.getProperty("file.separator") +  "language.cfg");
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
			File config_File = new File(path + System.getProperty("file.separator") + "language.cfg");		
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
