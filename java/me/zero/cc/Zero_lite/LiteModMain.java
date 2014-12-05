package me.zero.cc.Zero_lite;

import java.io.File;

import org.lwjgl.input.Keyboard;

import me.zero.cc.Zero_lite.Config.Config;
import me.zero.cc.Zero_lite.Gui.Zombe_Menu;
import me.zero.cc.Zero_lite.Mods.ChatMod;
import me.zero.cc.Zero_lite.Mods.FlyMod;
import me.zero.cc.Zero_lite.Mods.InfoMod;
import me.zero.cc.Zero_lite.Mods.LightMod;
import me.zero.cc.Zero_lite.Mods.MobHighlighterMod;
import me.zero.cc.Zero_lite.Mods.ReciepeMod;
import me.zero.cc.Zero_lite.Mods.SpeedMod;
import me.zero.cc.Zero_lite.Mods.TimeMod;
import me.zero.cc.Zero_lite.utils.InfoLine;
import me.zero.cc.Zero_lite.utils.InfoLineManager;
import me.zero.cc.Zero_lite.utils.Speicher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

import com.mumfrey.liteloader.ChatFilter;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.core.LiteLoaderEventBroker.ReturnValue;

public class LiteModMain implements Tickable, ChatFilter{

	private static KeyBinding Zombe_config = new KeyBinding("Zombe-Config", Keyboard.KEY_F7, "Zombe-Config");
	private Speicher speicher;
	private boolean init = false;
	private ChatMod ch;
	
	public String getName() {
		return "Zero-Lite";
	}

	public String getVersion() {
		return "0.1";
	}

	public void init(File configPath) {	
		LiteLoader.getInput().registerKeyBinding(Zombe_config);
		speicher = new Speicher();
		speicher.setConfig(new Config());
		speicher.setZm(new Zombe_Menu(speicher));		
	}

	public void upgradeSettings(String version, File configPath,File oldConfigPath) {	
		//WTF is this ?
	}
	private void initMods(Minecraft minecraft){
		if(!init){
			speicher.setInfoLineManager(new InfoLineManager(minecraft));
			speicher.addMod(new FlyMod(minecraft,speicher));
			speicher.addMod(new InfoMod(minecraft,speicher));
			speicher.addMod(new SpeedMod(minecraft,speicher));
			speicher.addMod(new LightMod(minecraft, speicher));
			speicher.addMod(new ReciepeMod(minecraft,speicher));
			speicher.addMod(new TimeMod(minecraft, speicher));
			speicher.addMod(new MobHighlighterMod(minecraft));
			speicher.setMinecraft(minecraft);
			ch = new ChatMod(minecraft,speicher);
			speicher.addMod(ch);
			init = true;
		}
	}

	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame,	boolean clock) {	
		initMods(minecraft);
		//System.out.println(Keyboard.isKeyDown(65) || Keyboard.isKeyDown(1));
		if(minecraft.thePlayer != null){
			speicher.getInfoLineManager().use(minecraft);
			if(Zombe_config.isKeyDown()){
				speicher.getZm().draw(minecraft);
			}
			
			for(int i = 0; i < speicher.getMods().size();i++){
				speicher.getMods().get(i).use();
			}
		}
	}
	@Override
	public boolean onChat(IChatComponent chat, String message,
			ReturnValue<IChatComponent> newMessage) {
		// TODO Auto-generated method stub
		return false;
	}
}

