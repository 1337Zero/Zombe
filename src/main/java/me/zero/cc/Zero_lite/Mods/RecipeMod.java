package me.zero.cc.Zero_lite.Mods;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;

import com.mumfrey.liteloader.core.LiteLoader;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Config.CustomRecipesConfig;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipesMapExtending;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class RecipeMod implements Mod {

	private boolean enabled = false;
	private Minecraft minecraft;
	private String version = "0.1";
	private ArrayList<ItemStack> olditems = new ArrayList<ItemStack>();
	private ArrayList<ItemStack> founditems = new ArrayList<ItemStack>();
	private ArrayList<ItemStack> renderItems = new ArrayList<ItemStack>();
	private boolean aktu = false;
	private LiteModMain speicher;
	private int olditemcount = 0;
	private boolean loadCustomRecipes = false;
	private boolean showCraftingPattern = false;
	
	public RecipeMod(Minecraft minecraft,LiteModMain speicher) {
		this.minecraft = minecraft;
		this.speicher = speicher;
		enabled = Boolean.valueOf(speicher.getConfig().getData("RecipeMod.Enabled"));
		
		loadCustomRecipes = Boolean.valueOf(speicher.getConfig().getData("RecipeMod.loadCustomRecipes"));
		showCraftingPattern = Boolean.valueOf(speicher.getConfig().getData("RecipeMod.showCraftingPattern"));
		
		if(LiteLoader.isDevelopmentEnvironment() | loadCustomRecipes){
			loadCustomRecipes();
			loadCustomRecipes = true;
		}
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * This Calculates and shows the Crafting Pattern for the craftable Items it found earlier
	 * @param array of the found items
	 */
	private void updateShownItems(ArrayList<ItemStack> array){
		ScaledResolution reso = new ScaledResolution(minecraft);
		int mousex = Mouse.getX() / reso.getScaleFactor();
		int mousey = Mouse.getY() / reso.getScaleFactor();
		int mouseyy = reso.getScaledHeight() - mousey;		
		int posx = reso.getScaledWidth();
		int posy = reso.getScaledHeight();
	
		RenderItem itemRenderer = minecraft.getRenderItem();
		int x = (posx/2)+90;
		int y = 20;	
			for (ItemStack fitem : renderItems) {
				if(x < mousex & (x + 15) > mousex){
					if(y < mouseyy & (y +20) > mouseyy){
						int count = 0;						
						IRecipe r = CraftingManager.getRecipeById(count++);						
						while(r != null){
							if(r instanceof ShapedRecipes){
								ShapedRecipes src = (ShapedRecipes) r;								
								if(Item.getIdFromItem(fitem.getItem()) == Item.getIdFromItem(src.getRecipeOutput().getItem())){
									ArrayList<ItemStack> recipe = formatShapedRecipe(getShapedRecipe(src,true), src.getRecipeOutput());
									if(recipe != null){
										int renderposx =  0;
										int renderposy = posy-(4*15);										
										for(int ix = 0; ix < recipe.size();ix++){
											if((ix % 3) == 0){
												renderposy += 15;
												renderposx = 0;
											}if(recipe.get(ix).getMetadata() == 32767){
												ItemStack tempstack = new ItemStack(recipe.get(ix).getItem(), recipe.get(ix).getCount(), 0);
												if(tempstack.getItem() != null){
													itemRenderer.renderItemIntoGUI(tempstack, renderposx, renderposy);
												}											
											}else{
												if(recipe.get(ix).getItem() != null){
													itemRenderer.renderItemIntoGUI(recipe.get(ix), renderposx, renderposy);
												}
											}
											renderposx += 20;
										}
										minecraft.fontRenderer.drawString("Possible recipe:", 0, (posy-(4*15)), 0xFFFFFF);	
										break;
									}
								}
							}else if(r instanceof ShapelessRecipes){
								ShapelessRecipes src = (ShapelessRecipes) r;	
								if(Item.getIdFromItem(fitem.getItem()) == Item.getIdFromItem(src.getRecipeOutput().getItem())){
									ArrayList<ItemStack> recipe = getShapelessRecipe(src,true);
									if(recipe != null){
										int renderposx =  0;
										int renderposy = posy-(4*15);										
										for(int ix = 0; ix < recipe.size();ix++){
											if((ix % 3) == 0){
												renderposy += 15;
												renderposx = 0;
											}
											if(recipe.get(ix).getMetadata() == 32767){
												ItemStack tempstack = new ItemStack(recipe.get(ix).getItem(), recipe.get(ix).getCount(), 0);
												if(tempstack.getItem() != null){
													itemRenderer.renderItemIntoGUI(tempstack, renderposx, renderposy);	
												}
											}else{
												if(recipe.get(ix).getItem() != null){
													itemRenderer.renderItemIntoGUI(recipe.get(ix), renderposx, renderposy);
												}
											}	
											renderposx += 20;
										}
										minecraft.fontRenderer.drawString("Possible recipe:", 0, (posy-(4*15)), 0xFFFFFF);	
										break;
									}
								}							
							}							
							r = CraftingManager.getRecipeById(count++);
						}
						
					}
				}
				if (x + 15 <= (posx-15)) {
					x = x + 15;
				} else {
					y = y + 20;
					x = (posx / 2)+90;
				}
			}	
		}
	/**
	 * This formates a give recept cause there is no better way i found to get the right shape
	 * @param x : List of Itemstacks
	 * @param itemid : the id of the receptoutput
	 * @return list of Itemstacks with the right shape (if a rule is defined here)
	 */
	private ArrayList<ItemStack> formatShapedRecipe(ArrayList<ItemStack> x,ItemStack goalitem){
		int itemid = Item.getIdFromItem(goalitem.getItem());
		ArrayList<ItemStack> newarray = new ArrayList<ItemStack>();
		
		if(itemid == 64 | itemid == 71 | itemid == 193 | itemid == 194 | itemid == 195 | itemid == 196 | itemid == 197 | itemid == 324 | itemid == 330 | itemid == 428 | itemid == 429 | itemid == 430 | itemid == 431 |itemid == 271 | itemid == 275 | itemid == 258 | itemid == 279 | itemid == 286){
			//modify the door's recept
			newarray.add(x.get(0));
			newarray.add(x.get(1));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(x.get(2));
			newarray.add(x.get(3));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(x.get(4));
			newarray.add(x.get(5));
			newarray.add(new ItemStack(Item.getItemById(0)));
			return newarray;
		}else if(itemid == 58 | itemid == 98 | itemid == 89 | itemid == 167 | itemid == 45 | itemid == 359){
			//modify the workbench's recept
			newarray.add(x.get(0));
			newarray.add(x.get(1));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(x.get(2));
			newarray.add(x.get(3));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(new ItemStack(Item.getItemById(0)));
			return newarray;
		}else if(itemid == 280 | itemid == 50 | itemid == 76){
			//modify the stick's recept
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(x.get(0));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(x.get(1));			
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(new ItemStack(Item.getItemById(0)));
			return newarray;
		}else if(itemid >= 290 & itemid <= 294){
			//modify hoe's recept
			newarray.add(x.get(0));
			newarray.add(x.get(1));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(x.get(2));
			newarray.add(x.get(3));
			newarray.add(x.get(4));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(x.get(5));
			newarray.add(new ItemStack(Item.getItemById(0)));
			return newarray;
		}else if(itemid == 256 | itemid == 269 | itemid == 273 | itemid == 277 | itemid == 284 | itemid == 267 | itemid == 268 | itemid == 272 | itemid == 276 | itemid == 283){
			//modify the shovel's recept
			newarray.add(x.get(0));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(x.get(1));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(x.get(2));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(new ItemStack(Item.getItemById(0)));
			return newarray;
		}else if(itemid == 333){
			//modify boat recipe
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(x.get(1));
			newarray.add(new ItemStack(Item.getItemById(0)));
			newarray.add(x.get(2));
			newarray.add(x.get(2));
			newarray.add(x.get(2));
			newarray.add(x.get(2));
			return newarray;
		}
		return x;
	}
	/**
	 * Returns the Recipe of a given ShapedRecipe
	 * @param src
	 * @param withnull
	 * @return
	 */
	private ArrayList<ItemStack> getShapedRecipe(ShapedRecipes src,boolean withnull){
		try{
			ArrayList<ItemStack> recept = new ArrayList<ItemStack>();			
			for(Ingredient i : src.getIngredients()){
				for(ItemStack s  : i.getMatchingStacks()){
					if(withnull){
						if (s != null) {
							recept.add(s);
						}else{
							recept.add(new ItemStack(Item.getItemById(0)));
						}
					}else{
						if (s != null) {
							recept.add(s);
						}
					}				
				
				}
			}
			return recept;
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}			
		return null;
	}
	/**
	 * Returns the Recipe of a given ShaplessRecipe
	 * @param src
	 * @param withnull
	 * @return
	 */
	private ArrayList<ItemStack> getShapelessRecipe(ShapelessRecipes src,boolean withnull){
		try{		
			
			ArrayList<ItemStack> recept = new ArrayList<ItemStack>();
			
			
			for(Ingredient i : src.getIngredients()){
				for(ItemStack s : i.getMatchingStacks()){
					recept.add(s);
				}
			}
			return recept;
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void use() {
		if (enabled) {
			if (minecraft.currentScreen instanceof GuiCrafting) {
				//Show Possible Pattern ?
				if(showCraftingPattern){
					if(renderItems.size() > 0){						
						updateShownItems(renderItems);									
					}
				}					
				GuiCrafting craft = (GuiCrafting) minecraft.currentScreen;

				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				// Getting Stuff from workbench
				for (int i = 1; i < 10; i++) {
					if (craft.inventorySlots.getInventory().get(i) != null) {
						items.add((ItemStack) craft.inventorySlots.getInventory().get(i));
						ItemStack istack = items.get(items.size()-1);
					}
				}	
				//Should i made an update of the shown items ?
				if(items.size() == 0){
					founditems.clear();
				}else{
					int tempitemcount = 0;
					
					for(int i = 0; i < items.size();i++){
						tempitemcount += items.get(i).getCount();
					}
					if(tempitemcount != olditemcount){
						aktu = true;
						olditemcount = tempitemcount;
					}
					for(int i = 0; i < items.size();i++){
						if(olditems.size() == items.size()){
							if(olditems.get(i) != null){
								if(items.get(i).getMetadata() != olditems.get(i).getMetadata()){
									aktu = true;
								}
							}
						}						
					}
				}
				//Calc what can be craftet
				if (aktu) {
					try {				
						olditems.clear();
						for(int i = 0; i < items.size();i++){
							olditems.add(items.get(i));
						}
						founditems = getOutPuts(items);
						items.clear();
					} catch (Exception e) {
						e.printStackTrace();
					}
					aktu = false;
				}
				//Show output in Gui
				ScaledResolution reso = new ScaledResolution(minecraft);
				int posx = reso.getScaledWidth();
				int posy = reso.getScaledHeight();

				RenderItem itemRenderer = minecraft.getRenderItem();

				int x = (posx / 2)+90;
				int y = 20;
				renderItems.clear();	
				if(founditems.size() > 0){
					//minecraft.fontRendererObj.drawString("you can craft this:", x, 0, 0xFFFFFF);
					itemRenderer.renderItemOverlayIntoGUI(minecraft.fontRenderer, new ItemStack(Item.getItemById(0)), (x + (x/4)), 0, "Possible craft's:");
				}
				for (ItemStack fitem : founditems) {
					if(fitem != null){
						itemRenderer.renderItemIntoGUI(fitem, x, y);
						renderItems.add(fitem);
						if (x + 15 <= (posx-15)) {
							x = x + 15;
						} else {
							y = y + 20;
							x = (posx / 2)+90;
						}
					}					
				}
			}
		}
	}
	/**
	 * Loads the Recipes out of a file
	 */
	private void loadCustomRecipes(){
		System.out.println("Lade Custom Recipes!");
		CustomRecipesConfig cfg = LiteModMain.customconfig;
		
		int count = 0;
				
		while(cfg.exists(count + "")){			
			String pattern = cfg.getData(count++ + "");
			boolean error = false;
			
			String recipeoutput = pattern.split(";")[0];
			String[] recipepattern = pattern.split(";")[1].split(",");
			String[] recipepatternReplacement = pattern.split(";")[2].split(",");
						
			if(recipeoutput.split(":").length != 3){
				System.out.println("[RecipeMod] Wrong Format for ID:SUBID:AMOUNT");
				error = true;
			}
			if(recipepattern.length != 3){
				System.out.println("[RecipeMod] Wrong Format for Crafting Matrix");
				error = true;
			}
			
			String lastpart = pattern.split(";")[1];
			int diffrentChars = 0;
			String foundchars = "";
			for(int i = 0; i < lastpart.length();i++){
				if(!foundchars.contains(lastpart.charAt(i) + "") & lastpart.charAt(i) != ','){
					diffrentChars++;
					foundchars = foundchars + lastpart.charAt(i) + "";
				}
			}
			if(recipepatternReplacement.length != diffrentChars){
				System.out.println("[RecipeMod] Not enough diffrent Chars in the Replacement part!" + diffrentChars + " chars !=" + pattern.split(";")[2].split(",").length + " chars");
				error = true;
			}
			
			if(!error){
				ItemStack b = getBlockFromIdAndMeta(Integer.parseInt(recipeoutput.split(":")[0]), Integer.parseInt(recipeoutput.split(":")[1]));
				b.setCount(Integer.parseInt(recipeoutput.split(":")[2]));
				if(LiteLoader.isDevelopmentEnvironment()){
					System.out.println("Creating a recept with " + diffrentChars + " RecipeItems");
					System.out.println("Target is " + recipepattern[0]);
					System.out.println("Target is " + recipepattern[1]);
					System.out.println("Target is " + recipepattern[2]);
					System.out.println(recipepatternReplacement);
				}			
				ShapelessRecipes sl = new ShapelessRecipes("", b, createIngredientList(recipepatternReplacement, diffrentChars));
				Random r = new Random();
				CraftingManager.REGISTRY.register(r.nextInt(10000 - 1000 + 1) + 1000, new ResourceLocation(b.getItem().getUnlocalizedName()), sl);
				System.out.println("Recipe registered!");
			}	
		}		
	}
	
	public NonNullList<Ingredient> createIngredientList(String[] recipepatternReplacement,int diffrentChars){
		NonNullList<Ingredient> nnl = NonNullList.create();
		
		for(int i = 0; i < diffrentChars;i++){
			Ingredient ig = Ingredient.fromStacks(getBlockFromIdAndMeta(Integer.parseInt(recipepatternReplacement[i].split("=")[1].split(":")[0]), Integer.parseInt(recipepatternReplacement[i].split("=")[1].split(":")[1])));
			nnl.add(ig);
		}		
		return nnl;
	}
	/**
	 * Creates an Itemstack out of an Id can be an Itemstack with Block or Item
	 * @param id
	 * @param meta
	 * @return
	 */
	@SuppressWarnings("unused")
	private ItemStack getBlockFromIdAndMeta(int id,int meta){
		ItemStack temp = new ItemStack(Item.getByNameOrId(id + ""));		
		if(temp != null){
			return temp;
		}else{
			//this is not dead Code but thanks eclipse :)
			temp = new ItemStack(Block.getBlockById(id).getStateFromMeta(meta).getBlock());
			return temp;
		}
	}
	/**
	 * Searches for Outputs 
	 * @param cfl
	 * @param items
	 * @return
	 * @throws IOException
	 */
	private ArrayList<ItemStack> getOutPuts(ArrayList<ItemStack> items) throws IOException {
		ArrayList<ItemStack> recip = new ArrayList<ItemStack>();
		
		int count = 0;
		IRecipe r = CraftingManager.getRecipeById(count++);
		
		while(r != null){
			if (r instanceof ShapelessRecipes) {
				ShapelessRecipes src = (ShapelessRecipes) r;
				
				if (matchItemStackRezept(items,getShapelessRecipe(src,false),true,Item.getIdFromItem(src.getRecipeOutput().getItem()))) 
				{
					recip.add(src.getRecipeOutput());
				}
			} else if (r instanceof ShapedRecipes && !(r instanceof RecipesMapExtending)) {
				ShapedRecipes src = (ShapedRecipes) r;
				if (matchItemStackRezept(items,getShapedRecipe(src,false),false,Item.getIdFromItem(src.getRecipeOutput().getItem()))) {
					recip.add(src.getRecipeOutput());
				}
			}
			r = CraftingManager.getRecipeById(count++);
		}			
		return recip;
	}
	/**
	 * Compares a given List of Itemstacks with another (ignores shape)
	 * @param items
	 * @param recept
	 * @param ignoresubid
	 * @param goalitem
	 * @return
	 */
	private boolean matchItemStackRezept(ArrayList<ItemStack> items,ArrayList<ItemStack> recept,boolean ignoresubid,int goalitem) {		
		
		ArrayList<ItemStack> sortedItems = sortRecept(items);
		ArrayList<ItemStack> sortedRecept = sortRecept(recept);
		
		boolean[] ItemReceptMatches = new boolean[sortedRecept.size()];
		
		if(sortedRecept.size() <= sortedItems.size()){
			for(int i = 0; i < sortedRecept.size();i++){		
				
				boolean[] betweenmatch = new boolean[sortedItems.size()];
				
				for(int x = 0; x < sortedItems.size();x++){
					int receptstacksize = sortedRecept.get(i).getCount();
					if(receptstacksize == 81) receptstacksize /=9;
					if(sortedItems.get(x).getCount() >= receptstacksize){
						if(Item.getIdFromItem(sortedItems.get(x).getItem()) == Item.getIdFromItem(sortedRecept.get(i).getItem())){
							if(sortedRecept.get(i).getMetadata() == 32767){								
								betweenmatch[i] = true;								
							}else{
								int metadata1 = sortedItems.get(x).getMetadata();
								int metadata2 = sortedRecept.get(i).getMetadata();							
								if(metadata1 == metadata2){
									betweenmatch[i] = true;
									break;
								}
							}												
						}		
					}					
				}	
				for(int x = 0; x < betweenmatch.length;x++){
					if(betweenmatch[x]){
						ItemReceptMatches[i] = true;
					}
				}
			}
		}else{
			return false;
		}
		for(int i = 0; i < ItemReceptMatches.length;i++){
			if(!ItemReceptMatches[i]){
				return false;
			}
		}		
		return true;		
	}
	/**
	 * Sort a given Recept and Put stacks of items together
	 * @param receptitems
	 * @return
	 */
	private ArrayList<ItemStack> sortRecept(ArrayList<ItemStack> receptitems) {
		HashMap<String, ItemStack> items = new HashMap<String, ItemStack>();
		
		ArrayList<String> itemnames = new ArrayList<String>();
				
		for (ItemStack item : receptitems) {
			if(item != null){
				if(Item.getIdFromItem(item.getItem()) != 0){
					int metadata = item.getMetadata();
					if(metadata >= 32749) metadata = 0;
					if(!itemnames.contains(item.getDisplayName() + metadata)){
						itemnames.add(item.getDisplayName() + metadata);
					}			
					if(items.containsKey(item.getDisplayName() + metadata)){
						ItemStack x = items.get(item.getDisplayName() + metadata).copy();
						x.setCount(item.getCount()+1);
						items.put(item.getDisplayName() + metadata, x);
					}else{
						items.put(item.getDisplayName() + metadata, item);
					}
				}
			}
		}
		Collections.sort(itemnames);
		
		ArrayList<ItemStack> sorteditems = new ArrayList<ItemStack>();
		
		for(String x : itemnames){
			sorteditems.add(items.get(x));
		}	
		return sorteditems;
	}

	@Override
	public String getName() {
		return ModData.RecipeMod.name();
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
	public void manupulateValue(String ValueToManupulate, double percent) {
		System.out.println("[RecipeMod] Unknown value " + ValueToManupulate + " (" + percent + ")");
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		if(valueToManupulate.equalsIgnoreCase("renabled")){
			enabled = b;
			speicher.getConfig().replaceData("RecipeMod.Enabled", b + "");
		}else if(valueToManupulate.equalsIgnoreCase("loadcustomrecipes")){
			loadCustomRecipes = true;
			speicher.getConfig().replaceData("RecipeMod.loadCustomRecipes", b + "");
			loadCustomRecipes();
		}else if(valueToManupulate.equalsIgnoreCase("showcraftingpattern")){
			showCraftingPattern = b;
			speicher.getConfig().replaceData("RecipeMod.showCraftingPattern", b + "");
		}else{
			System.out.println("[RecipeMod] " + valueToManupulate + " is unknown!");
		}
	}
	@Override
	public void manupulateValue(String valueToManupulate, String value) {
		System.out.println("[RecipeMod] " + value);
	}

	@Override
	public int getOn() {
		return 0;
	}

	public boolean isLoadCustomRecipes() {
		return loadCustomRecipes;
	}

	public void setLoadCustomRecipes(boolean loadCustomRecipes) {
		this.loadCustomRecipes = loadCustomRecipes;
	}

	public boolean isShowCraftingPattern() {
		return showCraftingPattern;
	}

	public void setShowCraftingPattern(boolean showCraftingPattern) {
		this.showCraftingPattern = showCraftingPattern;
	}
}
class ReciepeModGui extends GuiScreen{
	
	private LiteModMain speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	
	public ReciepeModGui(LiteModMain speicher){
		this.speicher = speicher;
	}
	
	public void initGui(){
		drawButtons();
	}
	/**
	 * Called if a Button is pressed
	 */
	public void actionPerformed(GuiButton b){	
		if(b.displayString.contains("back to game")){
			speicher.getMinecraft().displayGuiScreen(null);
		}
	}
	/**
	 * Initialize Buttons and add them to the Button list
	 */
	public void drawButtons(){
		GuiBooleanButton booleanb = new GuiBooleanButton(2, width/2-170, height/4+20, 150, 20, "Enabled", ((RecipeMod)speicher.getMod(ModData.RecipeMod.name())).isEnabled(), "renabled", ModData.RecipeMod, speicher,LiteModMain.lconfig.getData("RecipeMod.enable").split(";"));
		GuiBooleanButton loadCustomRecipes = new GuiBooleanButton(2, width/2, height/4+20, 150, 20, "loadCustomRecipes", ((RecipeMod)speicher.getMod(ModData.RecipeMod.name())).isLoadCustomRecipes(), "loadcustomrecipes", ModData.RecipeMod, speicher,LiteModMain.lconfig.getData("RecipeMod.loadcustomrecipes").split(";"));
		GuiBooleanButton showRecipeItems = new GuiBooleanButton(2, width/2-170, height/4+50, 150, 20, "Show Crafting Pattern", ((RecipeMod)speicher.getMod(ModData.RecipeMod.name())).isShowCraftingPattern(), "showcraftingpattern", ModData.RecipeMod, speicher,LiteModMain.lconfig.getData("RecipeMod.showcraftinpattern").split(";"));
		
		GuiButton back = new GuiButton(6, width/2-100,height-50 , "back to game");

		buttonList.add(showRecipeItems);
		buttonList.add(loadCustomRecipes);
		buttonList.add(booleanb);
		buttonList.add(back);
	}
	protected void keyTyped(char c,int key){
		if(GivingKey){
			if(key != 65 && key != 1){	
				valueToManupulate = valueToManupulate.replace(" ", "");
				((RecipeMod)speicher.getMod(ModData.RecipeMod.name())).manupulateValue(valueToManupulate, key);
			}
		}else{
			if(key == 65 || key == 1){
				speicher.getMinecraft().displayGuiScreen(null);
			}
		}		
	}
}
