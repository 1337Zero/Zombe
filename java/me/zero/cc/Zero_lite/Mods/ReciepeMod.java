package me.zero.cc.Zero_lite.Mods;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.mumfrey.liteloader.core.LiteLoader;

import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.utils.Speicher;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBookCloning;
import net.minecraft.item.crafting.RecipeFireworks;
import net.minecraft.item.crafting.RecipesArmor;
import net.minecraft.item.crafting.RecipesArmorDyes;
import net.minecraft.item.crafting.RecipesMapCloning;
import net.minecraft.item.crafting.RecipesMapExtending;
import net.minecraft.item.crafting.RecipesTools;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

public class ReciepeMod implements Mod {

	private boolean enabled = false;
	private Minecraft minecraft;
	private String version = "0.1";
	private ArrayList<ItemStack> olditems = new ArrayList<ItemStack>();
	private ArrayList<ItemStack> founditems = new ArrayList<ItemStack>();
	private boolean aktu = false;
	private Speicher speicher;

	public ReciepeMod(Minecraft minecraft,Speicher speicher) {
		this.minecraft = minecraft;
		this.speicher = speicher;
		enabled = Boolean.valueOf(speicher.getConfig().getData("RecipeMod.Enabled"));
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void use() {
		if (enabled) {
			CraftingManager cfl = CraftingManager.getInstance();
			if (minecraft.currentScreen instanceof GuiCrafting) {
				GuiCrafting craft = (GuiCrafting) minecraft.currentScreen;

				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				// Getting Stuff from workbench

				for (int i = 1; i < 10; i++) {
					if (craft.inventorySlots.getInventory().get(i) != null) {
						items.add((ItemStack) craft.inventorySlots.getInventory().get(i));
					}
				}
				// seach for possible outputs				
				if(items.size() >= 1){
					if (!sortRecept(olditems).equalsIgnoreCase(sortRecept(items))) {
						aktu = true;
					}
				}else{
					if(items.size() == 0){
						founditems.clear();
					}
				}
				if (aktu) {
					try {		
						olditems = items;
						founditems = getOutPuts(cfl, items);
					} catch (Exception e) {
						e.printStackTrace();
					}
					aktu = false;
				}

				ScaledResolution reso = new ScaledResolution(minecraft,minecraft.displayWidth, minecraft.displayHeight);
				int posx = reso.getScaledWidth();
				int posy = reso.getScaledHeight();

				RenderItem itemRenderer = minecraft.getRenderItem();

				int x = posx / 2;
				int y = 5;
				for (ItemStack fitem : founditems) {
					itemRenderer.renderItemIntoGUI(fitem, x, y);
					
					if (x + 15 <= (posx-15)) {
						x = x + 15;
					} else {
						y = y + 20;
						x = posx / 2;
					}
				}
			}
		}
	}
	

	private ArrayList<ItemStack> getOutPuts(CraftingManager cfl,ArrayList<ItemStack> items) throws Exception {
		ArrayList<ItemStack> recip = new ArrayList<ItemStack>();
		for (int i = 0; i < cfl.getRecipeList().size(); i++) {
			if (cfl.getRecipeList().get(i) instanceof ShapelessRecipes) {
				ShapelessRecipes src = (ShapelessRecipes) cfl.getRecipeList().get(i);
				Field f = null;
				if(LiteLoader.isDevelopmentEnvironment()){
					f = src.getClass().getDeclaredField("recipeItems");
				}else{
					
					 //* Search for there code hide system to find the right variable name ...
					 //* should be b if they don't update anything
					/*for(Field e : src.getClass().getDeclaredFields()){
						System.out.println(e);
					}
					for(Field e : src.getClass().getFields()){
						System.out.println(e);
					}*/
					f = src.getClass().getDeclaredField("b");
				}
				f.setAccessible(true);
				List l = (List) f.get(src);
				ArrayList<String> recept = new ArrayList<String>();
				for (int x = 0; x < l.size(); x++) {
					recept.add(l.get(x).toString());
				}
				if (matchRezept(items, recept, src.getRecipeOutput().toString())) {
					recip.add(src.getRecipeOutput());
				}
			} else if (cfl.getRecipeList().get(i) instanceof ShapedRecipes && !(cfl.getRecipeList().get(i) instanceof RecipesMapExtending)) {
				ShapedRecipes src = (ShapedRecipes) cfl.getRecipeList().get(i);
				Field f = null;
				if(LiteLoader.isDevelopmentEnvironment()){
					f = src.getClass().getDeclaredField("recipeItems");
				}else{
					/*
					 * Search for there code hide system to find the right variable name ...
					 * should be c if they don't update anything
					for(Field e : src.getClass().getDeclaredFields()){
						System.out.println(e);
					}
					for(Field e : src.getClass().getFields()){
						System.out.println(e);
					}*/
					f = src.getClass().getDeclaredField("c");
				}				
				f.setAccessible(true);
				ItemStack[] l = (ItemStack[]) f.get(src);
				ArrayList<String> recept = new ArrayList<String>();
				for (int x = 0; x < l.length; x++) {
					if (l[x] != null) {
						recept.add(l[x].toString());
					}
				}
				if (matchRezept(items, recept, src.getRecipeOutput().toString())) {
					recip.add(src.getRecipeOutput());
				}							
			}
		}
		return recip;
	}

	private boolean matchRezept(ArrayList<ItemStack> items,ArrayList<String> recept, String output) {
		boolean[] match = new boolean[sortStringRecept(recept).split(";").length];		
		if (recept.size() >= 1) {
			if (sortRecept(items).contains(sortStringRecept(recept))) {
				return true;
			}else{
				String[] sortedrecept = sortStringRecept(recept).split(";");
				String[] sorteditems = sortRecept(items).split(";");
				
				for(int i = 0; i < sortedrecept.length;i++){
					boolean worked = true;
					boolean[] betweenmatch = new boolean[sorteditems.length];
					for(int x = 0; x < sorteditems.length;x++){
						
						if(!sortedrecept[i].split(":")[0].equalsIgnoreCase(sorteditems[x].split(":")[0])){
							betweenmatch[x] = false;
						}else{
							int itemamount = Integer.parseInt(sortedrecept[i].split(":")[1]);
							if(itemamount == 81){
								//Why the fuck is the recept for a Block like Coalblock 81 Items but is real 9 ?
								itemamount = 9;
							}
							if(itemamount <= Integer.parseInt(sorteditems[x].split(":")[1])){
								betweenmatch[x] = true;
							}							
						}
					}
					for(int x = 0; x < betweenmatch.length;x++){
						if(betweenmatch[x]){
							match[i] = true;
						}
					}
				}
			}
		}
		boolean back = true;
		for(int i = 0; i < match.length;i++){
			if(!match[i]){
				back = false;
			}
		}
		return back;
	}

	private String sortRecept(ArrayList<ItemStack> receptitems) {
		String back = "";
		HashMap<String, Integer> counts = new HashMap<String, Integer>();
		ArrayList<String> items = new ArrayList<String>();

		for (ItemStack sitem : receptitems) {
			String item = sitem.toString().split("@")[0];
			if (!items.contains(item)) {
				items.add(item);
			}
			if (counts.containsKey(item)) {
				counts.put(item, (counts.get(item) + sitem.stackSize));
			} else {
				counts.put(item, sitem.stackSize);
			}
		}
		Collections.sort(items,new Comparator<String>() {
			@Override
			public int compare(String fitem, String sitem) {				
				return fitem.compareTo(sitem);
			}			
		});
		for (String item : items) {
			if (item != "") {
				if(back == ""){
					back =  item.split("x")[1] + ":" + counts.get(item);
				}else{
					back = back + ";" + item.split("x")[1] + ":" + counts.get(item);
				}				
			}
		}
		return back;
	}

	private String sortStringRecept(ArrayList<String> receptitems) {
		String back = "";
		HashMap<String, Integer> counts = new HashMap<String, Integer>();
		ArrayList<String> items = new ArrayList<String>();

		for (String sitem : receptitems) {
			String item = sitem.toString().split("@")[0];
			if(item != ""){
				if (!items.contains(item)) {
					items.add(item);
				}
				if (counts.containsKey(item)) {
					counts.put(item, (counts.get(item) + Integer.parseInt(item.split("x")[0])));
				} else {
					counts.put(item, Integer.parseInt(item.split("x")[0]));
				}	
			}			
		}
		Collections.sort(items,new Comparator<String>() {
			@Override
			public int compare(String fitem, String sitem) {				
				return fitem.compareTo(sitem);
			}			
		});
		for (String item : items) {
			if (item != "") {
				if(back == ""){
					back =  item.split("x")[1] + ":" + counts.get(item);
				}else{
					back = back + ";" + item.split("x")[1] + ":" + counts.get(item);
				}				
			}
		}
		return back;
	}

	@Override
	public String getName() {
		return ModData.ReciepeMod.name();
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public GuiScreen drawGui() {		
		return new ReciepeModGui(speicher);
	}

	@Override
	public void manupulateValue(String ValueToManupulate, int percent) {
		System.out.println(percent);
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		if(valueToManupulate.equalsIgnoreCase("renabled")){
			enabled = b;
			speicher.getConfig().replaceData("RecipeMod.Enabled", b + "");
		}else{
			System.out.println(valueToManupulate + " is unknown!");
		}
	}
	@Override
	public void manupulateValue(String valueToManupulate, String value) {
		System.out.println(value);

	}
}
class ReciepeModGui extends GuiScreen{
	
	private Speicher speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	
	public ReciepeModGui(Speicher speicher){
		this.speicher = speicher;
	}
	
	public void initGui(){
		drawButtons();
	}
	
	public void actionPerformed(GuiButton b){	
		if(b.displayString.contains("back to game")){
			speicher.getMinecraft().displayGuiScreen(null);
			speicher.getZm().setShown(false);
		}
	}
	
	public void drawButtons(){
		GuiBooleanButton booleanb = new GuiBooleanButton(2, width/2-170, height/4-10, 150, 20, "Enabled", ((ReciepeMod)speicher.getMod(ModData.ReciepeMod.name())).isEnabled(), "renabled", ModData.ReciepeMod, speicher);

		GuiButton back = new GuiButton(6, width/2-100,height-50 , "back to game");
		
		buttonList.add(booleanb);
		buttonList.add(back);
	}
	protected void keyTyped(char c,int key){
		if(GivingKey){
			if(key != 65 && key != 1){	
				valueToManupulate = valueToManupulate.replace(" ", "");
				((ReciepeMod)speicher.getMod(ModData.ReciepeMod.name())).manupulateValue(valueToManupulate, key);
			}
		}else{
			if(key == 65 || key == 1){
				speicher.getMinecraft().displayGuiScreen(null);
				speicher.getZm().setShown(false);
			}
		}		
	}
}
