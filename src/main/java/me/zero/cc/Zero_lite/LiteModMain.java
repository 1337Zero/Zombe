package me.zero.cc.Zero_lite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.zero.cc.Zero_lite.Config.Config;
import me.zero.cc.Zero_lite.Config.CustomRecipesConfig;
import me.zero.cc.Zero_lite.Config.LanguageConfig;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.ZGuiButton;
import me.zero.cc.Zero_lite.Mods.RangeMod;
import me.zero.cc.Zero_lite.Mods.FlyMod;
import me.zero.cc.Zero_lite.Mods.InfoMod;
import me.zero.cc.Zero_lite.Mods.LightMod;
import me.zero.cc.Zero_lite.Mods.MobHighlighterMod;
import me.zero.cc.Zero_lite.Mods.Mod;
import me.zero.cc.Zero_lite.Mods.ModData;
import me.zero.cc.Zero_lite.Mods.OreHighlighterMod;
import me.zero.cc.Zero_lite.Mods.PathMod;
import me.zero.cc.Zero_lite.Mods.RecipeMod;
import me.zero.cc.Zero_lite.Mods.SpeedMod;
import me.zero.cc.Zero_lite.Mods.TimeMod;
import me.zero.cc.Zero_lite.override.ZLRenderGlobal;
import me.zero.cc.Zero_lite.utils.BlockMark;
import me.zero.cc.Zero_lite.utils.CommandListener;
import me.zero.cc.Zero_lite.utils.InfoLineManager;
import me.zero.cc.Zero_lite.utils.Markables;
import me.zero.cc.Zero_lite.utils.SelectionHelper;
import me.zero.cc.Zero_lite.utils.UpdateChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import com.mumfrey.liteloader.ChatFilter;
import com.mumfrey.liteloader.PostRenderListener;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.core.LiteLoaderEventBroker.ReturnValue;



public class LiteModMain implements Tickable, ChatFilter,PostRenderListener{

	private ArrayList<Mod> mods = new ArrayList<Mod>();
	private static KeyBinding Zombe_config = new KeyBinding("Zombe-Config", Keyboard.KEY_F7, "Zombe-Config");
	private boolean init = false;
	public static Config config;
	public static LanguageConfig lconfig;
	public static CustomRecipesConfig customconfig;
	private InfoLineManager ilm;
	private Minecraft minecraft;
	private UpdateChecker updatecheck;
	private boolean checkedupdate = false;
	private ArrayList<String > messages;
	private int urlVersion = 0;
	private String downloadURL = "";
	public static String prefix = "&6[Lite-Zombe] ";
	private boolean update = true;
	private ArrayList<Markables> marks = new ArrayList<Markables>();
	private CommandListener cmdlist;
	private BlockMark firstmark;
	private BlockMark secondmark;
	private ArrayList<BlockMark> selection = new ArrayList<BlockMark>();
	private double lastFirstMarkPick = 0;
	private double lastSecondMarkPick = 0;
	private boolean enableselection = true;
	public static LiteModMain instance = null;
	
	public String getName() {
		return "Zombe-Lite";
	}
	public String getVersion() {
		return "1058";
	}

	public void init(File configPath) {	
		LiteLoader.getInput().registerKeyBinding(Zombe_config);
		config = new Config();	
		lconfig = new LanguageConfig();
		customconfig = new CustomRecipesConfig();
		update = Boolean.valueOf(config.getData("Main.searchupdates"));
		enableselection = Boolean.valueOf(config.getData("Main.enableSelection"));
		if(LiteLoader.isDevelopmentEnvironment()){
			config.replaceData("Main.debug", true + "");
		}
		LiteModMain.instance = this;	
		
		/*
		 * System.out.println("overriding maybe ?");
		
		try {
			ClassPool cp = ClassPool.getDefault();
			//net.minecraft.client.network.NetHandlerPlayClient
			
			cp.importPackage("me.zero.cc.Zero_lite.LiteModMain");
			cp.importPackage("me.zero.cc.Zero_lite.Mods.ModData");
			cp.importPackage("me.zero.cc.Zero_lite.Mods.FlyMod");
			
			CtClass cc = cp.get("net.minecraft.client.network.NetHandlerPlayClient");
			
			CtMethod m = cc.getDeclaredMethod("handleUpdateHealth");
		    
			
			
			//m.insertBefore("{ System.out.println(\"Updating Health\"); }");
			
			//m.insertBefore("{FlyMod m=(FlyMod)LiteModMain.instance.getMod(ModData.FlyMod.name());if(m.isFallGodmode())System.out.println(\"Ignoring dmg\");return;}");
			
			m.insertBefore("FlyMod m = (FlyMod)LiteModMain.instance.getMod(ModData.FlyMod.name());if(!m.isFallGodmode())System.out.println(\"Ignoring dmg\");if(m.isFallGodmode())System.out.println(\"not Ignoring dmg\");if(!m.isFallGodmode())return;");
			
			cc.writeFile();
			//Whatever but it works
			Class c = cc.toClass();			
			
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 */
	}

	public void upgradeSettings(String version, File configPath,File oldConfigPath) {	
		//WTF is this ?
	}
	/**
	 * Initialize the mods and add them to a Arraylist
	 * 
	 * @param minecraft Minecraft-instance
	 */
	private void initMods(Minecraft minecraft){
		//if a mod is added here, also add them in ModData.enum
		if(!init){
			this.minecraft = minecraft;
			ilm = new InfoLineManager(minecraft);
			this.addMod(new FlyMod(minecraft,this));
			this.addMod(new InfoMod(minecraft,this));
			this.addMod(new SpeedMod(minecraft,this));
			this.addMod(new LightMod(minecraft, this));
			this.addMod(new RecipeMod(minecraft,this));
			this.addMod(new TimeMod(minecraft, this));
			this.addMod(new MobHighlighterMod(minecraft,this));
			this.addMod(new OreHighlighterMod(minecraft,this));
			this.addMod(new PathMod(minecraft,this));
			this.addMod(new RangeMod(minecraft,this));
			//ch = new ChatMod(minecraft,this);
			//this.addMod(ch);
			init = true;
			cmdlist = new CommandListener(minecraft,this);
			lastFirstMarkPick = System.currentTimeMillis();
			lastSecondMarkPick = System.currentTimeMillis();

			Minecraft.getMinecraft().renderGlobal = new ZLRenderGlobal(minecraft);
			
			
		}
	}	
	/**
	 * Addes a Mod to the Modlist
	 * @param Mod
	 */
	public void addMod(Mod mod) {
		mods.add(mod);
	}
	/**
	 * Gets a Mod by the given Name, use ModData.Mod.name
	 * @param String
	 * @return Mod
	 */
	public Mod getMod(String name){
		for(int i = 0; i < mods.size();i++){
			if(mods.get(i).getName().equals(name)){
				return mods.get(i);
			}
		}
		System.out.println("[ERROR] Mod {" + name + "} not found!");
		return null;
	}
	/**
	 * Returns a list with all loaded Mods
	 * @return ArrayList<Mod> 
	 */
	public ArrayList<Mod> getMods() {
		return mods;
	}

	boolean injected = false;	
	
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame,	boolean clock) {	
		initMods(minecraft);
		if(minecraft.player != null){					
			if(!injected){
			/*	AbstractClientPlayer ac = (AbstractClientPlayer)minecraft.player;
				//System.out.println(ac.fallDistance);
				EntityPlayer ep = (EntityPlayer)ac;
				//System.out.println(ep.fallDistance);
				EntityLivingBase elb = (EntityLivingBase)ep;
				
				//System.out.println(elb.fallDistance);
				Entity e = (Entity)elb;
				e.setEntityInvulnerable(true);
							*/
				
				//change value invulnerable
			
				
			}	
			 if(!injected){
				/*  try {			
						 //Load this from reflections 
						//Minecraft mc = Minecraft.getMinecraft();
												
						
						Field f = NetHandlerPlayClient.class.getDeclaredField("netManager");
						Field f1 = NetHandlerPlayClient.class.getDeclaredField("guiScreenServer");
						Field f2 = NetHandlerPlayClient.class.getDeclaredField("profile");
						
						f.setAccessible(true);
						f1.setAccessible(true);
						f2.setAccessible(true);
						
						NetworkManager nin = (NetworkManager)f.get(minecraft.getConnection());
						GuiScreen gsc = (GuiScreen)f1.get(minecraft.getConnection());
						GameProfile pro = (GameProfile)f2.get(minecraft.getConnection());;
						
						//Setzte neuen Networkhandler
						System.out.println("Overriding network mananger");
						Field con = minecraft.player.getClass().getDeclaredField("connection");
						con.setAccessible(true);
						con.set(con, new ZNetHandlerPlayClient(minecraft,gsc,nin,pro));
					} catch (NoSuchFieldException e1) {
						e1.printStackTrace();
					} catch (SecurityException e1) {
						e1.printStackTrace();
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}*/
					 injected = true;
			}
			ilm.use(minecraft);
			if(Zombe_config.isKeyDown()){
				minecraft.displayGuiScreen(new ConfigMainFrame(this));
			}			
			if(!checkedupdate && minecraft.world != null && minecraft.currentScreen == null){
				if(update){
					updatecheck = new UpdateChecker(this);	
					updatecheck.start();				
					checkedupdate = true;
				}
			}
			if(messages != null){
				if(messages.size() > 0 || urlVersion != 0 & downloadURL != ""){					
					if(urlVersion > Integer.valueOf(this.getVersion())){
						sendMessage(prefix + lconfig.getData("Main.updateMSG").replace("<urlversion>", urlVersion + ""));
						minecraft.player.sendMessage(new TextComponentString(downloadURL));
						for(String msg : messages){		
							sendMessage(prefix + msg);
						}
					}else if(urlVersion == Integer.valueOf(this.getVersion())){						
						sendMessage(lconfig.getData("Main.updatedMSG").replace("<urlversion>", urlVersion + ""));	
					}else{
						sendMessage(lconfig.getData("Main.betaMSG").replace("<urlversion>", getVersion()));							
					}
					messages = null;
				}
			}			
			for(int i = 0; i < this.getMods().size();i++){
				this.getMods().get(i).use();
			}
			if(isEnableselection()){
				if(minecraft.gameSettings.keyBindUseItem.isKeyDown()){
					if((System.currentTimeMillis() - lastFirstMarkPick) >=500){
						if(minecraft.player.getActiveItemStack() != null){
							if(minecraft.player.getHeldItem(EnumHand.MAIN_HAND) != null){
								if(minecraft.player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(Item.getByNameOrId("wooden_shovel"))){
									RayTraceResult pos = minecraft.player.rayTrace(5,1.0F);
									firstmark = new BlockMark(pos.getBlockPos().getX(), pos.getBlockPos().getY(), pos.getBlockPos().getZ(), minecraft, Float.valueOf(config.getData("Main.firstMarkR")), Float.valueOf(config.getData("Main.firstMarkG")), Float.valueOf(config.getData("Main.firstMarkB")), Float.valueOf(config.getData("Main.firstMarkAlpha")));
									//firstmark = new BlockMark(0, 0, 0, 0, pos.getBlockPos().getX(), pos.getBlockPos().getY(), pos.getBlockPos().getZ());
									lastFirstMarkPick = System.currentTimeMillis();
									sendMessage(lconfig.getData("Selection.firstmark").replace("<x>",  pos.getBlockPos().getX() + "").replace("<y>", pos.getBlockPos().getY() + "").replace("<z>", pos.getBlockPos().getZ() + ""));
									if(secondmark != null){
										//selection = new Selection((Math.max(firstmark.getY(), secondmark.getY()) - Math.min(firstmark.getY(), secondmark.getY())), (Math.max(firstmark.getZ(), secondmark.getZ()) - Math.min(firstmark.getZ(), secondmark.getZ())), (Math.max(firstmark.getX(), secondmark.getX()) - Math.min(firstmark.getX(), secondmark.getX())), firstmark, secondmark, Float.valueOf(config.getData("Main.selectionR")),Float.valueOf(config.getData("Main.selectionG")),Float.valueOf(config.getData("Main.selectionB")),Float.valueOf(config.getData("Main.selectionAlpha")));
										selection = SelectionHelper.calcSelectedBlocks(minecraft, firstmark, secondmark,Float.valueOf(config.getData("Main.selectionR")),Float.valueOf(config.getData("Main.selectionG")),Float.valueOf(config.getData("Main.selectionB")),Float.valueOf(config.getData("Main.selectionAlpha")));
										sendMessage(lconfig.getData("Selection.selected").replace("<size>", selection.size() + ""));
									}
								}
							}
						}												
					}
				}			
				if((System.currentTimeMillis() - lastSecondMarkPick) >=500){
					if(minecraft.gameSettings.keyBindUseItem.isKeyDown()){
						if(minecraft.player.getActiveItemStack() != null){
							if(minecraft.player.getHeldItem(EnumHand.MAIN_HAND) != null){
								if(minecraft.player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(Item.getByNameOrId("wooden_axe"))){
									RayTraceResult pos = minecraft.player.rayTrace(5,1.0F);
									secondmark = new BlockMark(pos.getBlockPos().getX(), pos.getBlockPos().getY(), pos.getBlockPos().getZ(), minecraft, Float.valueOf(config.getData("Main.secondMarkR")), Float.valueOf(config.getData("Main.secondMarkG")), Float.valueOf(config.getData("Main.secondMarkB")), Float.valueOf(config.getData("Main.secondMarkAlpha")));
									lastSecondMarkPick = System.currentTimeMillis();
									sendMessage(lconfig.getData("Selection.secondmark").replace("<x>",  pos.getBlockPos().getX() + "").replace("<y>", pos.getBlockPos().getY() + "").replace("<z>", pos.getBlockPos().getZ() + ""));
									
									if(firstmark != null){
										selection = SelectionHelper.calcSelectedBlocks(minecraft, firstmark, secondmark,Float.valueOf(config.getData("Main.selectionR")),Float.valueOf(config.getData("Main.selectionG")),Float.valueOf(config.getData("Main.selectionB")),Float.valueOf(config.getData("Main.selectionAlpha")));
										sendMessage(lconfig.getData("Selection.selected").replace("<size>", selection.size() + ""));
									}
								}
							}	
						}											
					}
				}
			}						
		}
	}	
	public static String formateTextColor(String msg){		
		msg = msg.replace("&0", "" + TextFormatting.BLACK);
		msg = msg.replace("&1", "" + TextFormatting.DARK_BLUE);
		msg = msg.replace("&2", "" + TextFormatting.DARK_GREEN);
		msg = msg.replace("&3", "" + TextFormatting.DARK_AQUA);
		msg = msg.replace("&4", "" + TextFormatting.DARK_RED);
		msg = msg.replace("&5", "" + TextFormatting.DARK_PURPLE);
		msg = msg.replace("&6", "" + TextFormatting.GOLD);
		msg = msg.replace("&7", "" + TextFormatting.GRAY);
		msg = msg.replace("&8", "" + TextFormatting.DARK_GRAY);
		msg = msg.replace("&9", "" + TextFormatting.BLUE);
		msg = msg.replace("&a", "" + TextFormatting.GREEN);
		msg = msg.replace("&b", "" + TextFormatting.AQUA);
		msg = msg.replace("&c", "" + TextFormatting.RED);
		msg = msg.replace("&d", "" + TextFormatting.LIGHT_PURPLE);
		msg = msg.replace("&e", "" + TextFormatting.YELLOW);
		msg = msg.replace("&f", "" + TextFormatting.WHITE);
		msg = msg.replace("&k", "" + TextFormatting.OBFUSCATED);
		msg = msg.replace("&l", "" + TextFormatting.BOLD);
		msg = msg.replace("&m", "" + TextFormatting.STRIKETHROUGH);
		msg = msg.replace("&n", "" + TextFormatting.UNDERLINE);
		msg = msg.replace("&o", "" + TextFormatting.ITALIC);
		return msg;
	}
	public static void sendMessage(String msg){
		String[] messages = msg.split(";");
		for(String text : messages){
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString(formateTextColor(prefix + text)));
		}
	}
	@Override
	public boolean onChat(ITextComponent chat, String message, ReturnValue<ITextComponent> newMessage) {
		return !cmdlist.onCommand(chat.getUnformattedText().replace("<" + minecraft.player.getName() + "> ", ""),chat.getUnformattedText().replace("<" + minecraft.player.getName() + "> ", "").split(" "));
	}

	@Override
	public void onPostRenderEntities(float partialTicks) {

		for(Markables m : marks){
			m.draw(partialTicks);
		}
		marks.clear();
		((MobHighlighterMod)this.getMod(ModData.MobHighLighter.name())).render(partialTicks);
		((OreHighlighterMod)this.getMod(ModData.OreHighLighter.name())).render(partialTicks);
		((PathMod)this.getMod(ModData.PathMod.name())).render(partialTicks);
		((InfoMod)this.getMod(ModData.InfoMod.name())).render(partialTicks);
		if(firstmark != null){
			firstmark.draw(partialTicks);
		}
		if(secondmark != null){
			secondmark.draw(partialTicks);
		}			
		if(selection.size() > 0){
			
			 double minX = Math.min(firstmark.getX(), secondmark.getX());
			 double minY = Math.min(firstmark.getY(), secondmark.getY());
			 double minZ = Math.min(firstmark.getZ(), secondmark.getZ());
			 
			 double maxX = Math.max(firstmark.getX(), secondmark.getX());
			 double maxY = Math.max(firstmark.getY(), secondmark.getY());
			 double maxZ = Math.max(firstmark.getZ(), secondmark.getZ());
			
			for(BlockMark mark : selection){
				boolean render = false;
				if(mark.getX() == minX | mark.getX() == maxX){
					render = true;
				}
				if(mark.getY() == minY | mark.getY() == maxY){
					render = true;					
				}
				if(mark.getZ() == minZ | mark.getZ() == maxZ){
					render = true;						
				}	
				if(render){
					mark.draw(partialTicks);	
				}
			}
		}
	}

	@Override
	public void onPostRender(float partialTicks) {
		//unused
		
	}
	/**
	 * Get the Minecraft instance
	 * @return Minecraft instance
	 */
	public Minecraft getMinecraft() {
		return minecraft;
	}
	/**
	 * Loads and returns the Config
	 * @return Config the Config instance
	 */
	public Config getConfig() {
		return config;
	}
	/**
	 * Get the InfoLineManager instance with manages the infolines 
	 * @return InfoLineManager instance
	 */
	public InfoLineManager getInfoLineManager() {
		return ilm;
	}
	/**
	 * Addes Announcements from Update-Url
	 * @param messages
	 */
	public synchronized void setAnnouncement(ArrayList<String> messages){
		this.messages = messages;
	}
	/**
	 * Returns the newest Version of Zombe
	 * @return
	 */
	public int getUrlVersion() {
		return urlVersion;
	} 
	/**
	 * Sets the newest Version of Zombe
	 * @param urlVersion
	 */
	public void setUrlVersion(int urlVersion) {
		this.urlVersion = urlVersion;
	}
	/**
	 * Returns the URL of the newest Zombe version
	 * @return
	 */
	public String getDownloadURL() {
		return downloadURL;
	}
	/**
	 * Sets the Download URL for the newest Mod
	 * @param downloadURL
	 */
	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}
	/**
	 * true if an update was found
	 * @return Boolean
	 */
	public boolean isUpdate() {
		return update;
	}
	/**
	 * Sets if an update was found
	 * @param update
	 */
	public void setUpdate(boolean update) {
		this.update = update;
	}
	/**
	 * Adds markables for the render
	 * @param markable
	 */
	public void addMarkables(Markables markable){
		this.marks.add(markable);
	}
	public ArrayList<BlockMark> getSelection() {
		return selection;
	}
	public void setSelection(ArrayList<BlockMark> selection) {
		this.selection = selection;
	}
	public BlockMark getFirstmark() {
		return firstmark;
	}
	public void setFirstmark(BlockMark firstmark) {
		this.firstmark = firstmark;
	}
	public BlockMark getSecondmark() {
		return secondmark;
	}
	public void setSecondmark(BlockMark secondmark) {
		this.secondmark = secondmark;
	}
	public boolean isEnableselection() {
		return enableselection;
	}
	public void setEnableselection(boolean enableselection) {
		this.enableselection = enableselection;
		if(!this.enableselection){
			selection.clear();
			firstmark = null;
			secondmark = null;
		}
	}
	
}
class ConfigMainFrame extends GuiScreen{
	private LiteModMain lmm;
	
	public ConfigMainFrame(LiteModMain lmm){
		this.lmm = lmm;
	}
	
	public void initGui(){
		drawButtons();
	}
	
	public void drawScreen(int i, int j, float f){
		drawDefaultBackground();
		super.drawScreen(i, j, f);
	}
	/**
	 * Called if a Button is pressed
	 */
	public void actionPerformed(GuiButton b){	
		if(b.displayString.contains("back to game")){
			lmm.getMinecraft().displayGuiScreen(null);
		}else if(b.displayString.contains("Update-Check")){
			lmm.setUpdate(!lmm.isUpdate());
			lmm.getConfig().replaceData("Main.searchupdates", "" + lmm.isUpdate());
		}else if(b.displayString.contains("Enable Selection")){
			lmm.setEnableselection(!lmm.isEnableselection());
			lmm.getConfig().replaceData("Main.enableSelection", "" + lmm.isEnableselection());			
		}else{
			Mod mod = lmm.getMod(b.displayString);
			if(mod != null){
				lmm.getMinecraft().displayGuiScreen(mod.drawGui());				
			}else{
				System.out.println(b.displayString + " is not a existing Mod");
			}
		}		
	}
	/**
	 * Draws for every Mod a Button
	 */
	public void drawButtons(){
		int xvalue = 0;
		for(int i = 0; i < lmm.getMods().size();i++){
			buttonList.add(new ZGuiButton(i, xvalue, height/lmm.getMods().size() -10 + (i*20), 100, 20, lmm.getMods().get(i).getName(), LiteModMain.lconfig.getData(lmm.getMods().get(i).getName()).split(";")));
			if(height/lmm.getMods().size() -10 + (i*20) > height){
				xvalue = 120;
			}
		}	
		buttonList.add(new GuiButton(lmm.getMods().size(), width -100, height-20, 100, 20,"back to game..."));
		
		buttonList.add(new GuiBooleanButton(lmm.getMods().size() + 2, width-150, height-60, 150, 20, "Enable Selection", Boolean.valueOf(lmm.getConfig().getData("Main.enableSelection")), "enableselection", ModData.Nil, lmm,"Enables the Selection Part;Use a &6Wood Shovel;and a &6Wood Axe;to make a Selection".split(";")));
		
		buttonList.add(new GuiBooleanButton(lmm.getMods().size() + 1, width-100, height-40, 100, 20, "Update-Check", Boolean.valueOf(lmm.getConfig().getData("Main.searchupdates")), "check-updates", ModData.Nil, lmm,"Enables the Update Check;".split(";")));
	}
	protected void keyTyped(char c,int key){
		//F7 equals Button 65, so close Gui on key 65
		if(key == 65 || key == 1){
			lmm.getMinecraft().displayGuiScreen(null);
		}
		
	}
}

