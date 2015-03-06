package me.zero.cc.Zero_lite.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.zero.cc.Zero_lite.LiteModMain;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class CommandListener {

	private Minecraft minecraft;
	private LiteModMain main;
	
	public CommandListener(Minecraft minecraft,LiteModMain main){
		this.minecraft = minecraft;
		this.main = main;
	}
	
	public boolean onCommand(String cmd,String[] para){
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
			}else if(cmd.contains("set")){
				if(para.length == 2){		
					try{
						return onSetCommand(para[1],0);
					}catch(NumberFormatException e){
						sendMessage("&4 (" + para[1] + ") is not an  number");
					}					
				}if(para.length == 3){	
					try{
						return onSetCommand(para[1],Integer.valueOf(para[2]));
					}catch(NumberFormatException e){
						sendMessage("&4 (" + para[2] + ") is not an  number");
					}
				}else{
					sendMessage("&4Not enough Parameters try " + main.getConfig().getData("Main.cmdControlCharacter") + "set <id> <meta>");
				}
				return true;
			}else if(cmd.equalsIgnoreCase("killall")){
				return onKillAll();
			}else if(cmd.equals("help")){
				return onHelpCommand();
			}else if(cmd.equals("sel")){
				return onSellCommand();
			}else if(cmd.contains("replace")){
				if(para.length == 3){
					try{
						return onReplaceCommand(para[1], 0, para[2], 0);
					}catch(NumberFormatException e){
						sendMessage("&4 (" + para[1] + ") is not an  number");
					}					
				}if(para.length == 5){	
					try{
						return onReplaceCommand(para[1], Integer.valueOf(para[2]), para[3], Integer.valueOf(para[4]));
					}catch(NumberFormatException e){
						sendMessage("&4 (" + para[3] + ") is not an  number");
					}
				}else{
					sendMessage("&4Not enough Parameters try " + main.getConfig().getData("Main.cmdControlCharacter") + "replace <id> <meta> <id2> <meta2>");
				}
				return true;
			}else if(cmd.contains("shift")){
				if(para.length == 2){	
					try{
						return onShiftCommand(Integer.valueOf(para[1]));
					}catch(NumberFormatException e){
						sendMessage("&4 (" + para[1] + ") is not an  number");
					}	
				}
			}else if(cmd.contains("expand")){
				if(para.length == 2){	
					try{
						return onExpandCommand(Integer.valueOf(para[1]));
					}catch(NumberFormatException e){
						sendMessage("&4 (" + para[1] + ") is not an  number");
					}	
				}
			}else if(cmd.contains("move")){
				if(para.length == 2){	
					try{
						return onMoveCommand(Integer.valueOf(para[1]));
					}catch(NumberFormatException e){
						sendMessage("&4 (" + para[1] + ") is not an  number");
					}	
				}
			}
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
	private boolean onSetCommand(String id,int meta){
		if(main.getSelection().size() > 0){
			if(id.equals("0")){
				for(BlockMark pos : main.getSelection()){					
					minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), Block.getBlockById(Integer.valueOf(id)).getDefaultState());
				}
			}else{
				for(BlockMark pos : main.getSelection()){					
					ItemStack stack = new ItemStack(Item.getByNameOrId(id), 1, meta);
					minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), Block.getBlockFromItem(stack.getItem()).getStateFromMeta(meta));
				}
			}
		}else{
			sendMessage("&4Please select some Blocks first!");
		}
		return true;
	}
	private boolean onReplaceCommand(String idToReplace,int metaToReplace, String idToSet,int metaToSet){
		if(main.getSelection().size() > 0){
				for(BlockMark pos : main.getSelection()){
					if(Block.getIdFromBlock(minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock()) == Integer.parseInt(idToReplace)){
						if(minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getDamageValue(minecraft.theWorld, new BlockPos(pos.getX(), pos.getY(),pos.getZ())) == metaToReplace){
							ItemStack stack = new ItemStack(Item.getByNameOrId(idToSet), 1, metaToSet);
							minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), Block.getBlockFromItem(stack.getItem()).getStateFromMeta(metaToSet));	
						}
					}
				}
		}else{
			sendMessage("&4Please select some Blocks first!");
		}
		return true;
	}
	private boolean onCopyCommand(){
		sendMessage("Coding hard on this, try again later...:)");
		return true;
	}
	private boolean onPasteCommand(){
		sendMessage("Coding hard on this, try again later...:)");
		return true;
	}
	private boolean onSaveCommand(){
		sendMessage("Coding hard on this, try again later...:)");
		return true;
	}
	private boolean onLoadCommand(){
		sendMessage("Coding hard on this, try again later...:)");
		return true;
	}
	private boolean onKillAll(){
		List<Entity> mobs = minecraft.getIntegratedServer().getServer().getEntityWorld().loadedEntityList;
		List<Entity> clientmobs = minecraft.theWorld.loadedEntityList;
		int count = 0;
		//Deleting Mobs from the local Server...
		for(Entity mob : mobs){
			if(!(mob instanceof EntityPlayerMP) & !(mob instanceof EntityPlayerSP) & !(mob instanceof EntityPlayer)){
				minecraft.getIntegratedServer().getServer().getEntityWorld().unloadEntities(mobs);
				count++;
			}
		}
		//Deleting Mobs from the Renderer
		for(Entity mob : clientmobs){
			if(!(mob instanceof EntityPlayerMP) & !(mob instanceof EntityPlayerSP) & !(mob instanceof EntityPlayer)){
				mob.setDead();
			}
		}
		sendMessage("&6removed &4" + count + "&6 Entities");
		return true;
	}
	private boolean onHelpCommand(){
		List<String> cmdhelp = Arrays.asList(
				"&6near --> &f Display the loaded Entites",
				"&6helps --> &f Display the help",
				"&6killall --> &f Kills all loaded Entites",
				"&6set <id> {meta}--> &f Sets the Selecion to the given id",
				"&6copy --> &f Copies the Selection",
				"&6paste --> &f Paste the Selectuion",
				"&6sel --> &f Clears the Selection",
				"&6save --> &f Saves from a Schematic",
				"&6load --> &f Loads from a Schematic",
				"&6replace <id> {meta} <id2> {meta2} --> &f Replaces a give Block with the given id");
		
		for(String s : cmdhelp){
			sendMessage(s);
		}		
		return true;
	}
	private boolean onShiftCommand(int value){
		EnumFacing facing = EnumFacing.fromAngle(minecraft.thePlayer.rotationYawHead);
		if(facing.equals(EnumFacing.NORTH)){
			//z--
			main.getFirstmark().setZ(main.getFirstmark().getZ() - value);
			main.getSecondmark().setZ(main.getSecondmark().getZ() - value);
		}else if(facing.equals(EnumFacing.SOUTH)){
			//z++
			main.getFirstmark().setZ(main.getFirstmark().getZ() + value);
			main.getSecondmark().setZ(main.getSecondmark().getZ() + value);
		}else if(facing.equals(EnumFacing.EAST)){
			//x++
			main.getFirstmark().setX(main.getFirstmark().getX() + value);
			main.getSecondmark().setX(main.getSecondmark().getX() + value);
		}else if(facing.equals(EnumFacing.WEST)){
			//x--
			main.getFirstmark().setX(main.getFirstmark().getX() - value);
			main.getSecondmark().setX(main.getSecondmark().getX() - value);
		}else if(facing.equals(EnumFacing.UP)){
			//y++
			main.getFirstmark().setY(main.getFirstmark().getY() + value);
			main.getSecondmark().setY(main.getSecondmark().getY() + value);
		}else if(facing.equals(EnumFacing.DOWN)){
			//y--
			main.getFirstmark().setY(main.getFirstmark().getY() - value);
			main.getSecondmark().setY(main.getSecondmark().getY() - value);
		}
		main.setSelection(SelectionHelper.calcSelectedBlocks(minecraft, main.getFirstmark(), main.getSecondmark(),Float.valueOf(main.getConfig().getData("Main.selectionR")),Float.valueOf(main.getConfig().getData("Main.selectionG")),Float.valueOf(main.getConfig().getData("Main.selectionB")),Float.valueOf(main.getConfig().getData("Main.selectionAlpha"))));
		return true;
	}
	private boolean onExpandCommand(int value){
		MovingObjectPosition objpos = minecraft.thePlayer.rayTrace(5, 1.0F);
		if((objpos.getBlockPos().getY() - minecraft.thePlayer.posY) > 2){
			if(main.getFirstmark().getY() < main.getSecondmark().getY()){
				main.getFirstmark().setY(main.getFirstmark().getY() + value);
			}else{
				main.getSecondmark().setY(main.getSecondmark().getY() + value);
			}		
		}else if((objpos.getBlockPos().getY() - minecraft.thePlayer.posY) < -2){
			if(main.getFirstmark().getY() < main.getSecondmark().getY()){
				main.getFirstmark().setY(main.getFirstmark().getY() - value);
			}else{
				main.getSecondmark().setY(main.getSecondmark().getY() - value);
			}		
		}else{			
			EnumFacing facing = EnumFacing.fromAngle(minecraft.thePlayer.rotationYawHead);			
			if(facing.equals(EnumFacing.NORTH)){
				//z--			
				if(main.getFirstmark().getZ() < main.getSecondmark().getZ()){
					main.getFirstmark().setZ(main.getFirstmark().getZ() - value);
				}else{
					main.getSecondmark().setZ(main.getSecondmark().getZ() - value);
				}				
			}else if(facing.equals(EnumFacing.SOUTH)){
				//z++
				if(main.getFirstmark().getZ() > main.getSecondmark().getZ()){
					main.getFirstmark().setZ(main.getFirstmark().getZ() + value);
				}else{
					main.getSecondmark().setZ(main.getSecondmark().getZ() + value);
				}
			}else if(facing.equals(EnumFacing.EAST)){
				//x++
				if(main.getFirstmark().getX() > main.getSecondmark().getX()){
					main.getFirstmark().setX(main.getFirstmark().getX() + value);
				}else{
					main.getSecondmark().setX(main.getSecondmark().getX() + value);
				}
			}else if(facing.equals(EnumFacing.WEST)){
				//x--
				if(main.getFirstmark().getX() < main.getSecondmark().getX()){
					main.getFirstmark().setX(main.getFirstmark().getX() - value);
				}else{
					main.getSecondmark().setX(main.getSecondmark().getX() - value);
				}
			}
		}
		main.setSelection(SelectionHelper.calcSelectedBlocks(minecraft, main.getFirstmark(), main.getSecondmark(),Float.valueOf(main.getConfig().getData("Main.selectionR")),Float.valueOf(main.getConfig().getData("Main.selectionG")),Float.valueOf(main.getConfig().getData("Main.selectionB")),Float.valueOf(main.getConfig().getData("Main.selectionAlpha"))));	
		sendMessage("Selected " + main.getSelection().size() + " Blocks");
		return true;
	}
	private boolean onMoveCommand(int value){
		MovingObjectPosition objpos = minecraft.thePlayer.rayTrace(5, 1.0F);
		if((objpos.getBlockPos().getY() - minecraft.thePlayer.posY) > 2){
			//Up Code here		
			for(BlockMark pos : main.getSelection()){
				minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY() + value, pos.getZ()), minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getStateFromMeta(minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getDamageValue(minecraft.theWorld, new BlockPos(pos.getX(), pos.getY(),pos.getZ()))));	
				minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), Block.getBlockById(0).getDefaultState());
			}			
		}else if((objpos.getBlockPos().getY() - minecraft.thePlayer.posY) < -2){
			//Down Code here
			for(BlockMark pos : main.getSelection()){
				minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY() - value, pos.getZ()), minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getStateFromMeta(minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getDamageValue(minecraft.theWorld, new BlockPos(pos.getX(), pos.getY(),pos.getZ()))));	
				minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), Block.getBlockById(0).getDefaultState());
			}
		}else{			
			EnumFacing facing = EnumFacing.fromAngle(minecraft.thePlayer.rotationYawHead);			
			if(facing.equals(EnumFacing.NORTH)){
				//z--			
				for(BlockMark pos : main.getSelection()){
					minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - value), minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getStateFromMeta(minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getDamageValue(minecraft.theWorld, new BlockPos(pos.getX(), pos.getY(),pos.getZ()))));	
					minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), Block.getBlockById(0).getDefaultState());
				}
			}else if(facing.equals(EnumFacing.SOUTH)){
				//z++
				for(BlockMark pos : main.getSelection()){
					minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + value), minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getStateFromMeta(minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getDamageValue(minecraft.theWorld, new BlockPos(pos.getX(), pos.getY(),pos.getZ()))));	
					minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), Block.getBlockById(0).getDefaultState());
				}				
			}else if(facing.equals(EnumFacing.EAST)){
				//x++
				for(BlockMark pos : main.getSelection()){
					minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX() + value, pos.getY(), pos.getZ()), minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getStateFromMeta(minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getDamageValue(minecraft.theWorld, new BlockPos(pos.getX(), pos.getY(),pos.getZ()))));	
					minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), Block.getBlockById(0).getDefaultState());
				}				
			}else if(facing.equals(EnumFacing.WEST)){
				//x--
				for(BlockMark pos : main.getSelection()){
					minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX() - value, pos.getY(), pos.getZ()), minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getStateFromMeta(minecraft.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(),pos.getZ())).getBlock().getDamageValue(minecraft.theWorld, new BlockPos(pos.getX(), pos.getY(),pos.getZ()))));	
					minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), Block.getBlockById(0).getDefaultState());
				}
			}
		}
		main.setSelection(SelectionHelper.calcSelectedBlocks(minecraft, main.getFirstmark(), main.getSecondmark(),Float.valueOf(main.getConfig().getData("Main.selectionR")),Float.valueOf(main.getConfig().getData("Main.selectionG")),Float.valueOf(main.getConfig().getData("Main.selectionB")),Float.valueOf(main.getConfig().getData("Main.selectionAlpha"))));	
		sendMessage("Moved " + main.getSelection().size() + " Blocks");	
		return true;
	}
	private boolean onSellCommand(){
		main.getSelection().clear();
		main.setFirstmark(null);
		main.setSecondmark(null);
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
