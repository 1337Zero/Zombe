package me.zero.cc.Zero_lite;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.zero.cc.Zero_lite.Config.Config;
import me.zero.cc.Zero_lite.Mods.ChatMod;
import me.zero.cc.Zero_lite.Mods.FlyMod;
import me.zero.cc.Zero_lite.Mods.InfoMod;
import me.zero.cc.Zero_lite.Mods.LightMod;
import me.zero.cc.Zero_lite.Mods.MobHighlighterMod;
import me.zero.cc.Zero_lite.Mods.Mod;
import me.zero.cc.Zero_lite.Mods.ModData;
import me.zero.cc.Zero_lite.Mods.OreHighlighterMod;
import me.zero.cc.Zero_lite.Mods.PathMod;
import me.zero.cc.Zero_lite.Mods.ReciepeMod;
import me.zero.cc.Zero_lite.Mods.SpeedMod;
import me.zero.cc.Zero_lite.Mods.TimeMod;
import me.zero.cc.Zero_lite.utils.InfoLineManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IBlockAccess;

import com.mumfrey.liteloader.ChatFilter;
import com.mumfrey.liteloader.PostRenderListener;
import com.mumfrey.liteloader.RenderListener;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.core.LiteLoaderEventBroker.ReturnValue;
import com.mumfrey.liteloader.transformers.event.ReturnEventInfo;

public class LiteModMain implements Tickable, ChatFilter,PostRenderListener{

	private ArrayList<Mod> mods = new ArrayList<Mod>();
	private static KeyBinding Zombe_config = new KeyBinding("Zombe-Config", Keyboard.KEY_F7, "Zombe-Config");
	private boolean init = false;
	private ChatMod ch;
	private Config config;
	private InfoLineManager ilm;
	private Minecraft minecraft;
	
	public String getName() {
		return "Zero-Lite";
	}
	public String getVersion() {
		return "0.1";
	}

	public void init(File configPath) {	
		LiteLoader.getInput().registerKeyBinding(Zombe_config);
		config = new Config();
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
			this.addMod(new ReciepeMod(minecraft,this));
			this.addMod(new TimeMod(minecraft, this));
			this.addMod(new MobHighlighterMod(minecraft,this));
			this.addMod(new OreHighlighterMod(minecraft,this));
			this.addMod(new PathMod(minecraft,this));
			ch = new ChatMod(minecraft,this);
			this.addMod(ch);
			init = true;
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

	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame,	boolean clock) {	
		initMods(minecraft);
		if(minecraft.thePlayer != null){
			ilm.use(minecraft);
			if(Zombe_config.isKeyDown()){
				minecraft.displayGuiScreen(new ConfigMainFrame(this));
			}
			
			for(int i = 0; i < this.getMods().size();i++){
				this.getMods().get(i).use();
			}
		}
	}
	@Override
	public boolean onChat(IChatComponent chat, String message,ReturnValue<IChatComponent> newMessage) {
		//return false = no chat
		return true;
	}

	@Override
	public void onPostRenderEntities(float partialTicks) {
		((MobHighlighterMod)this.getMod(ModData.MobHighLighter.name())).render(partialTicks);
		((OreHighlighterMod)this.getMod(ModData.OreHighLighter.name())).render(partialTicks);
		((PathMod)this.getMod(ModData.PathMod.name())).render(partialTicks);
	}

	@Override
	public void onPostRender(float partialTicks) {		
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
	public InfoLineManager getInfoLineManager() {
		return ilm;
	}
	public void setInfoLineManager(InfoLineManager ilm) {
		this.ilm = ilm;
	}
	public void setConfig(Config config) {
		this.config = config;
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
		for(int i = 0; i < lmm.getMods().size();i++){
			buttonList.add(new GuiButton(i, width/2-100, height/lmm.getMods().size() -10 + (i*20), 200, 20, lmm.getMods().get(i).getName()));
		}	
		buttonList.add(new GuiButton(lmm.getMods().size(), width/2-100, height/(lmm.getMods().size()+1) -10 + ((lmm.getMods().size()+1) *20), 200, 20,"back to game..."));
	}
	protected void keyTyped(char c,int key){
		//F7 equals Button 65, so close Gui on key 65
		if(key == 65 || key == 1){
			lmm.getMinecraft().displayGuiScreen(null);
		}
		
	}
}

