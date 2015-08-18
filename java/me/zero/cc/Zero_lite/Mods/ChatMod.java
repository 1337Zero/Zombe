package me.zero.cc.Zero_lite.Mods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.client.gui.GuiPanel;
import com.mumfrey.liteloader.client.gui.ScrollPanelContent;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenServerList;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.ResourcePackListEntry;

public class ChatMod implements Mod {

	private Minecraft minecraft;
	private boolean enabled = true;
	private String version = "0.1";
	private String lastMessage = "";
	private LiteModMain speicher;
	private boolean enablekick = false;
	
	public ChatMod(Minecraft minecraft,LiteModMain speicher){
		this.minecraft = minecraft;
		this.speicher = speicher;
	}
	
	@Override	
	public boolean isEnabled() {		
		return enabled;
	}

	@Override
	public String getName() {
		return ModData.ChatMod.name();
	}

	@Override
	public void use() {			
		if(searchForIp(lastMessage)){
			try{
				String sender = lastMessage.split(":")[0];
				String message = lastMessage.split(":")[1];
				minecraft.thePlayer.sendChatMessage("/kick " + sender + " Auto-Kick (IP)");	
			}catch(Exception e){
				e.printStackTrace();
			}		
		}	
		lastMessage = "";
	}
	
	private boolean searchForIp(String msg){
		if(enablekick){			
			msg = msg.replace(" ", "");
			if(msg.contains(".de") || msg.contains(".com") || msg.contains(".org") || msg.contains(".me") || msg.contains(".net") || msg.contains(".to")){			
				if(!msg.contains("cultcraft.de")){
					if(!msg.contains("facebook.com")){
						return true;
					}					
				}				
			}
			String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
			
			Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
			Matcher matcher = pattern.matcher(msg);
			
			if(matcher.find()){
				return true;
			}
		}	
		return false;
	}
	@Override
	public String getVersion(){
		return version;
	}

	@Override
	public GuiScreen drawGui() {
		return new ChatModGui(speicher);	
	}

	@Override
	public void manupulateValue(String ValueToManupulate, double percent) {
		
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		if(valueToManupulate.equalsIgnoreCase("enablekick")){
			enablekick = b;
		}else{
			System.out.println("Fehler: " + valueToManupulate + " is not a known Value in " + this.getName());
		}		
	}

	public String getLastMessage() {
		return lastMessage;
	}
	private String replaceSpezialChars(String msg){
		msg = msg.replace("�1", "");
		msg = msg.replace("�2", "");
		msg = msg.replace("�3", "");
		msg = msg.replace("�4", "");
		msg = msg.replace("�5", "");
		msg = msg.replace("�6", "");
		msg = msg.replace("�7", "");
		msg = msg.replace("�8", "");
		msg = msg.replace("�9", "");
		msg = msg.replace("�a", "");
		msg = msg.replace("�b", "");
		msg = msg.replace("�c", "");
		msg = msg.replace("�d", "");
		msg = msg.replace("�e", "");
		msg = msg.replace("�f", "");	
		msg = msg.replace("�r", "");	
		msg = msg.replace("<", "");
		msg = msg.replace(">", ":");
		msg = msg.toLowerCase();		
		return msg;
	}

	public boolean isEnablekick() {
		return enablekick;
	}

	public void setEnablekick(boolean enablekick) {
		this.enablekick = enablekick;
	}

	public void setLastMessage(String lastMessage) {
		lastMessage = replaceSpezialChars(lastMessage);
		this.lastMessage = lastMessage;
	}

	@Override
	public void manupulateValue(String valueToManupulate, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getOn() {
		// TODO Auto-generated method stub
		return 0;
	}
}
class ChatModGui extends GuiScreen{
	
	private LiteModMain speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	private GuiChooseKeyButton chooseOn;
	
	public ChatModGui(LiteModMain speicher){
		this.speicher = speicher;
	}
	
	public void initGui(){
		drawButtons();
	}
	
	public void actionPerformed(GuiButton b){	
		if(b.displayString.contains("back to game")){
			speicher.getMinecraft().displayGuiScreen(null);
		}else if(b.displayString.contains("waiting")){
			valueToManupulate = b.displayString.split("waiting")[0];
			GivingKey = true;
		}
	}
	
	public void drawButtons(){
		GuiBooleanButton enableKick = new GuiBooleanButton(2, width/2-170, height/4+20, 150, 20, "Kick on Ip", ((ChatMod)speicher.getMod(ModData.ChatMod.name())).isEnablekick(), "enablekick", ModData.ChatMod, speicher,"A player will be kicked;if he writes an IP".split(";"));
		GuiButton back = new GuiButton(6, width/2-100,height-50 , "back to game");
	
		buttonList.add(back);
	}
	protected void keyTyped(char c,int key){
		if(GivingKey){
			if(key != 65 && key != 1){
				//speicher.getMinecraft().thePlayer.playSound("mob.ghast.scream", 1.0F, 1.0F);	
				valueToManupulate = valueToManupulate.replace(" ", "");
				((ChatMod)speicher.getMod(ModData.ChatMod.name())).manupulateValue(valueToManupulate, key);
				
				if(valueToManupulate.equalsIgnoreCase("enablekick")){
					chooseOn.setButtonkey(key);
				}
				GivingKey = false;
			}
		}else{
			if(key == 65 || key == 1){
				speicher.getMinecraft().displayGuiScreen(null);
			}
		}		
	}
}