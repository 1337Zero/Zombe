package me.zero.cc.Zero_lite.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import me.zero.cc.Zero_lite.LiteModMain;

public class OreHighLighterModConfig {

	private HashMap<String, String> data = new HashMap<String, String>();
	private String path = "";	
	private List<String> defaultconfig = Arrays.asList(
			"Blocklist:gold_ore,iron_ore,coal_ore,lapis_ore,diamond_ore,redstine_ore,emerald_ore,nether_quartz_ore,light_blue_wool",
								
			"Color.gold_ore:1.0F, 0.84F, 0.0F, 1.0F",
			"Color.iron_ore:0.82F, 0.41F, 0.11F, 1.0F",
			"Color.coal_ore:0.0F, 0.0F, 0.0F, 1.0F",
			"Color.lapis_ore:0.46F, 0.53F, 0.6F, 1.0F",
			"Color.diamond_ore:0.0F, 0.74F, 1.0F, 1.0F",
			"Color.redstine_ore:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.emerald_ore:0.26F, 0.8F, 0.5F, 1.0F",
			"Color.nether_quartz_ore:0.78F, 0.78F, 0.78F, 1.0F",
			"Color.light_blue_wool:1.0F, 0.78F, 0.78F, 0.5F"
			);
	/*private List<String> defaultconfig = Arrays.asList(
			"Blocklist:14,15,16,21,56,73,74,129,153,35-5,54,130,146,61,62,23,158,154,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,117,69,77,70,72,132,147,148,90,119,209,166,137,210,211,255",
								
			"Color.54:0.82F, 0.41F, 0.11F, 1.0F",
			"Color.130:0.78F, 0.0F, 0.78F, 1.0F",
			"Color.146:0.82F, 0.61F, 0.11F, 1.0F",
			"Color.61:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.62:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.23:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.158:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.154:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.219:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.220:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.221:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.222:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.223:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.224:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.225:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.226:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.227:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.228:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.229:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.230:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.231:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.232:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.233:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.234:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.117:0.51F, 0.51F, 0.51F, 1.0F",
			"Color.69:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.77:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.70:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.72:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.132:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.147:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.148:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.90:0.54F, 0.0F, 0.54F, 1.0F",
			"Color.119:0.54F, 0.2F, 0.54, 1.0F",
			"Color.209:0.54F, 0.2F, 0.54, 1.0F",
			"Color.166:0.0F, 0.0F, 0.0F, 1.0F",
			"Color.137:1.0F, 0.5F, 0.0F, 1.0F",
			"Color.210:1.0F, 0.5F, 0.0F, 1.0F",
			"Color.211:1.0F, 0.5F, 0.0F, 1.0F",
			"Color.255:0.0F, 0.0F, 0.0F, 1.0F",
			"Color.14:1.0F, 0.84F, 0.0F, 1.0F",
			"Color.15:0.82F, 0.41F, 0.11F, 1.0F",
			"Color.16:0.0F, 0.0F, 0.0F, 1.0F",
			"Color.21:0.46F, 0.53F, 0.6F, 1.0F",
			"Color.56:0.0F, 0.74F, 1.0F, 1.0F",
			"Color.73:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.74:1.0F, 0.0F, 0.0F, 1.0F",
			"Color.129:0.26F, 0.8F, 0.5F, 1.0F",
			"Color.153:0.78F, 0.78F, 0.78F, 1.0F",
			"Color.35-5:1.0F, 0.78F, 0.78F, 0.5F"
			);*/
	
	public OreHighLighterModConfig(){
		path = System.getProperty("user.dir");
		path = path + System.getProperty("file.separator") + "mods" + System.getProperty("file.separator") + "Lite_Zombe";
		try {
			if(!LiteModMain.isDev()){
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
		File config_File = new File(path + System.getProperty("file.separator") + "Orehighlighter.cfg");
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
	 * @param list ,the default list
	 * @throws Exception
	 */
	private void createConfigFile(List<String> list,String valuetochange) throws Exception{
		File config_File = new File(path + System.getProperty("file.separator") + "Orehighlighter.cfg");
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
		if(!LiteModMain.isDev()){
			File config_File = new File(path + System.getProperty("file.separator") + "Orehighlighter.cfg");		
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
	 * @param key ,the identifier for the data
	 * @return String data loaded from File
	 */
	public String getData(String key){
		String back = "";
		if(data.get(key) == null){
			if(!LiteModMain.isDev()){
				System.out.println(key + " was not found in your config,i will try update it for you ;D");
			}
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
	 * @param key ,the identifier for the data
	 * @param newdata ,the new value for the key
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
