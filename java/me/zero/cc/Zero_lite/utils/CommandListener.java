package me.zero.cc.Zero_lite.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.zero.cc.Zero_lite.LiteModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class CommandListener {

	private Minecraft minecraft;
	private LiteModMain main;
	
	public CommandListener(Minecraft minecraft,LiteModMain main){
		this.minecraft = minecraft;
		this.main = main;
	}
	
	public boolean onCommand(String cmd){
		if(cmd.startsWith(main.getConfig().getData("Main.cmdControlCharacter"))){
			cmd = cmd.replace(main.getConfig().getData("Main.cmdControlCharacter"), "");
			
			if(cmd.equalsIgnoreCase("near")){
				return onNearCommand();
			}else if(cmd.equalsIgnoreCase("copy")){
				return onCopyCommand();
			}else if(cmd.equalsIgnoreCase("paste")){
				return onPasteCommand();
			}else if(cmd.equalsIgnoreCase("save")){
				return onSaveCommand();
			}else if(cmd.equalsIgnoreCase("set")){
				return onSetCommand(cmd.split("set")[1]);
			}else if(cmd.equalsIgnoreCase("killall")){
				return onKillAll();
			}
		}else{
			System.out.println("no command");
		}
		return false;
	}
	
	private boolean onNearCommand(){
		List<Entity> mobs = minecraft.theWorld.loadedEntityList;
		HashMap<String, Integer> moblist = new HashMap<String, Integer>();
		ArrayList<String> founds = new ArrayList<String>();
		
		for(Entity e : mobs){
			if(e instanceof EntityPlayerSP | e instanceof EntityPlayerMP | e instanceof EntityPlayer){
				if(!founds.contains("Player")){
					moblist.put("Player", 1);
					founds.add("Player");
				}else{
					moblist.put("Player" , moblist.get("Player") + 1);
				}						
			}else{					
				if(!founds.contains(e.getName())){
					founds.add(e.getName());
					moblist.put(e.getName(), 1);
				}else{
					moblist.put(e.getName(), moblist.get(e.getName()) + 1 );
				}						
			}					
		}
		sendMessage(main.formateTextColor("&6found &4" + founds.size() + "&6 diffrent Entities"));
		for(int i = 0; i < founds.size();i++){
			sendMessage("&f" + founds.get(i) + ": " + moblist.get(founds.get(i)));
		}
		moblist.clear();
		founds.clear();
					
		return true;
	}
	private boolean onSetCommand(String para){
		System.out.println(para);
		if(main.getSelection().size() > 0){
			System.out.println(para);

		}else{
			sendMessage("&4Please select some Blocks first!");
		}
		return true;
	}
	private boolean onCopyCommand(){
		return true;
	}
	private boolean onPasteCommand(){
		return true;
	}
	private boolean onSaveCommand(){
		return true;
	}
	private boolean onLoadCommand(){
		return true;
	}
	private boolean onKillAll(){
		List<Entity> mobs = minecraft.getIntegratedServer().getServer().getEntityWorld().loadedEntityList;
		List<Entity> clientmobs = minecraft.theWorld.loadedEntityList;
		int count = 0;
		for(Entity mob : mobs){
			if(!(mob instanceof EntityPlayerMP) & !(mob instanceof EntityPlayerSP) & !(mob instanceof EntityPlayer)){
				minecraft.getIntegratedServer().getServer().getEntityWorld().unloadEntities(mobs);
				count++;
			}
		}
		for(Entity mob : clientmobs){
			if(!(mob instanceof EntityPlayerMP) & !(mob instanceof EntityPlayerSP) & !(mob instanceof EntityPlayer)){
				mob.setDead();
			}
		}
		sendMessage("&6removed &4" + count + "&6 Entities");
		return true;
	}
	/**
	 * Sends a Message to the Player, use Server Color codes to Color this
	 * @param msg Message send to the Player
	 */
	private void sendMessage(String msg){
		minecraft.thePlayer.addChatMessage(new ChatComponentText(main.formateTextColor(main.prefix + msg)));
	}
	
	
}
