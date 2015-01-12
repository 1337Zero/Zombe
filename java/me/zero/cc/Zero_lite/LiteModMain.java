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
import me.zero.cc.Zero_lite.Mods.ModData;
import me.zero.cc.Zero_lite.Mods.OreHighlighterMod;
import me.zero.cc.Zero_lite.Mods.ReciepeMod;
import me.zero.cc.Zero_lite.Mods.SpeedMod;
import me.zero.cc.Zero_lite.Mods.TimeMod;
import me.zero.cc.Zero_lite.utils.InfoLineManager;
import me.zero.cc.Zero_lite.utils.Speicher;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
			speicher.addMod(new MobHighlighterMod(minecraft,speicher));
			speicher.addMod(new OreHighlighterMod(minecraft,speicher));
			speicher.setMinecraft(minecraft);
			ch = new ChatMod(minecraft,speicher);
			speicher.addMod(ch);
			init = true;
		}
	}

	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame,	boolean clock) {	
		initMods(minecraft);
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
	public boolean onChat(IChatComponent chat, String message,ReturnValue<IChatComponent> newMessage) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onPostRenderEntities(float partialTicks) {
		// TODO Auto-generated method stub
		((MobHighlighterMod)speicher.getMod(ModData.MobHighLighter.name())).render(partialTicks);
		((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).render(partialTicks);
	}

	@Override
	public void onPostRender(float partialTicks) {		
	}

}

