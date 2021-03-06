package me.zero.cc.Zero_lite.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import com.mumfrey.liteloader.core.LiteLoader;

public class MobHighLighterConfig {

	private HashMap<String, String> data = new HashMap<String, String>();
	private String path = "";	
	private List<String> defaultconfig = Arrays.asList(
			"Moblist:Polar_Bear,Stray,Llama,Evoker,Vindicator,Vex,Chicken,Cow,Horse,Ocelot,Pig,Sheep,Bat,Squid,Villager,Rabbit,Creeper,Skeleton,Zombie,Spider,Enderman,PigZombie,Cavespider,Silverfish,Lohe,Magmacube,Witch,Endermite,Guardian,Wolf,Item,Player,Mushroomcow,Slime,Ghast,Snowman,Giant,Irongolem,Enderdragon,Boat,Minecart,Spawnerminecart,Chestminecart,Tntminecart,Furnaceminecart,Xp,Wither",
								
			"Color.Chicken:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Cow:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Horse:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Ocelot:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Pig:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Sheep:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Bat:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Mushroomcow:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Squid:0.0F, 1.0F, 0.0F, 1.0F",			
			"Color.Villager:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Rabbit:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Creeper:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Skeleton:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Zombie:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Spider:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Enderman:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.PigZombie:1.0F, 0.4F, 0.1F, 1.0F",
			"Color.Cavespider:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Silverfish:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Blaze:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Magmacube:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Witch:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Endermite:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Guardian:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Wither:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Wolf:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Item:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Xp:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Player:1.0F, 1.0F, 1.0F, 1.0F",
			"Color.Slime:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Ghast:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Giant:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Snowman:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Boat:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Minecart:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Spawnerminecart:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Tntminecart:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Hopperminecart:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Furnaceminecart:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Chestminecart:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Irongolem:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Enderdragon:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Stray:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Llama:0.0F, 1.0F, 0.0F, 1.0F",
			"Color.Vex:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Vindicator:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Evoker:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.Polar_Bear:0.0F, 1.0F, 0.0F, 1.0F"
			);
	
	public MobHighLighterConfig(){
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
		File config_File = new File(path + System.getProperty("file.separator") + "Mobhighlighter.cfg");
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
	 * @param list ,the default Config
	 * @throws Exception
	 */
	private void createConfigFile(List<String> list,String valuetochange) throws Exception{
		File config_File = new File(path + System.getProperty("file.separator") + "Mobhighlighter.cfg");
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
	 * @throws Exception 
	 */
	private void saveConfig(String changedpart) throws Exception{
		if(!LiteLoader.isDevelopmentEnvironment()){
			File config_File = new File(path + System.getProperty("file.separator") + "Mobhighlighter.cfg");		
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
	 * @param key ,the identifier for that data
	 * @return String data loaded from File
	 */
	public String getData(String key){
		String back = "";
		if(data.get(key) == null){
			System.out.println(key + " was not found in your config,i will try update it for you ;D");
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
	 * @param key ,the identifier for that data
	 * @param newdata , the new value of that data
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
