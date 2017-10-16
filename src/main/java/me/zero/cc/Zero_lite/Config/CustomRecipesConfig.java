package me.zero.cc.Zero_lite.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.mumfrey.liteloader.core.LiteLoader;

public class CustomRecipesConfig {

	private HashMap<String, String> data = new HashMap<String, String>();
	private String path = "";	
	private List<String> defaultconfig = Arrays.asList("");
	
	/**
	 * Constructor for this class, loads the CustomRecipes from the Given path
	 * 
	 */
	
	public CustomRecipesConfig(){
		path = System.getProperty("user.dir");
		path = path + System.getProperty("file.separator") + "mods" + System.getProperty("file.separator") + "Lite_Zombe";
		try {
			loadConfig();				
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
		File config_File = new File(path + System.getProperty("file.separator") + "CustomRecipes.cfg");
		if(!config_File.exists()){
			config_File.createNewFile();
			createConfigFile(defaultconfig, "");
		}
		BufferedReader br = new BufferedReader (new FileReader(config_File));
		String loaded = br.readLine();
		while(loaded != null){
			//if(!loaded.contains("#")){
			if(!loaded.startsWith("#")){
				if(loaded.length() > 1){
					data.put(loaded.split("~")[0], loaded.split("~")[1]);
				}
			}
			loaded = br.readLine();
		}
		br.close();
	}
	public boolean exists(String key){
		return data.containsKey(key);
	}
	/**
	 * Addes the Header for the CustomRecipesfile
	 * @param bw
	 * @throws IOException
	 */
	private void addHeader(BufferedWriter bw) throws IOException{
		bw.write("#This is the File for Custom Items...\n");
		bw.write("#Please use this Format to create Custom-Items\n");
		bw.write("##########################################\n#");
		bw.write("#Example1:\n");
		bw.write("#0~46:0:1;XXX,XXX,X#X;X=3:0,#=85:0");
		bw.write("#This creates a new TNT Recept, for crafting you need: XXX in the first line,XXX in the second line and X#X in the last line of the workbench\n");
		bw.write("#X will be replaced with 3:0,# will be replaced with 85:0\n");
		bw.write("#Example2:\n");
		bw.write("1~264:0:64;X,X,#;X=3:0,#=1:0");
		bw.write("#This creates a new Diamond Recept, for crafting you need: X in the first line,X in the second line and # in the last line of the workbench\n");
		bw.write("#X will be replaced with 3:0,# will be replaced with 1:0\n");
		bw.write("###########################################\n");
		bw.write("#So basicly it is:");
		bw.write("#ID~ITEMID:ITEMSUBID:AMOUNT;PATTERN FOR THE FIRST LINE,PATTERN FOR THE SECOND LINE,PATTERN FOR THE LAST LINE,ITEMS YOU NEED TO CRAFT THAT RECIPE WITH PATTERNIDENTIFIER");
	}
	/**
	 * Creates the CustomRecipes File and writes data from a list into it.
	 * @param list The default CustomRecipes
	 * @param valuetochange the value which should be changed
	 * @throws Exception
	 */
	private void createConfigFile(List<String> list,String valuetochange) throws Exception{
		File config_File = new File(path + System.getProperty("file.separator") + "CustomRecipes.cfg");
		config_File.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(config_File));
		addHeader(bw);
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
	 * @param changedpart The Part of the CustomRecipes that was modifyed
	 * @throws Exception 
	 */
	private void saveConfig(String changedpart) throws Exception{
		if(!LiteLoader.isDevelopmentEnvironment()){
			File config_File = new File(path + System.getProperty("file.separator") + "CustomRecipes.cfg");	
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
			System.out.println("Your CustomRecipes-File is old, i will try update it for you ;D");
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
