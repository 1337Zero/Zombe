package me.zero.cc.Zero_lite.utils;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipException;


import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.utils.worldedit.ByteArrayTag;
import me.zero.cc.Zero_lite.utils.worldedit.CompoundTag;
import me.zero.cc.Zero_lite.utils.worldedit.IntTag;
import me.zero.cc.Zero_lite.utils.worldedit.ListTag;
import me.zero.cc.Zero_lite.utils.worldedit.NBTOutputStream;
import me.zero.cc.Zero_lite.utils.worldedit.ShortTag;
import me.zero.cc.Zero_lite.utils.worldedit.StringTag;
import me.zero.cc.Zero_lite.utils.worldedit.Tag;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

public class Schematic{

	short width,height,length;
	byte[] blocks,data;
	private WorldClient world;
	private double playerX,playerY,playerZ;
	public NBTTagList entities,tileentity;
	private CompoundTag Cschematic;
	
	public Schematic(WorldClient world,File f) throws IOException{
		this.world = world;
		load(f);
	}
	/**
	 * Creates the Schematic out of the selection
	 * @param selection
	 * @throws IOException
	 */
	public Schematic(ArrayList<BlockMark> selection) throws IOException{
		loadFromSelection(selection);
	}
	
	 public void pasteSchematic(double px,double py,double pz){ 
			 try{
			   		for(int x = 0; x < width; ++x){
			   			for(int y = 0; y < height; ++y){
			   				for(int z = 0; z < length; ++z){
			   					int index = y * width * length + z * width + x;	   					
			   					double xpos = x + px;
			   					double ypos = y + py;
			   					double zpos = z + pz;
			   					int b = blocks[index] & 0xFF;
			   					BlockUtils.getBlockByID(b);
								Minecraft.getInstance().getIntegratedServer().getPlayerList().getPlayerByUUID(Minecraft.getInstance().player.getUniqueID()).getEntityWorld().setBlockState(new BlockPos(xpos, ypos, zpos), Block.getStateById(data[index]));
			   				}
			   			}
			   		}
			   		/*if(tileentity != null){
			   			for(int i = 0; i < tileentity.tagCount();i++){
				   			TileEntity enti = TileEntity.createAndLoadEntity(tileentity.getCompoundTagAt(i));
				   			enti.setPos(new BlockPos(enti.getPos().getX() + Minecraft.getInstance().thePlayer.posX,enti.getPos().getY() + Minecraft.getInstance().thePlayer.posY, enti.getPos().getZ() + Minecraft.getInstance().thePlayer.posZ));
				   			Minecraft.getInstance().getIntegratedServer().getEntityWorld().setTileEntity(enti.getPos(), enti);
				   		}
			   		}*/
			   		if(entities != null){
			   			for(int i = 0; i < entities.size();i++){				   				
				   			Entity ent = createEntityFromNBT(entities.getCompound(i));
				   			if(ent != null){
				   				Minecraft.getInstance().getIntegratedServer().getPlayerList().getPlayerByUUID(Minecraft.getInstance().player.getUniqueID()).addEntity(ent);
				   			}	   			
				   		}
			   		}
			   		//Minecraft.getInstance().getIntegratedServer().getEntityWorld().updateEntities();
			   		//Minecraft.getInstance().getIntegratedServer().getPlayerList().getPlayerByUUID(Minecraft.getInstance().player.getUniqueID()).getEntityWorld().
			   }catch(Exception e){
				   e.printStackTrace();
			   } 
		 }
	 private void load(File f) throws IOException{
		 FileInputStream fis = new FileInputStream(f);
		 NBTTagCompound nbt = null;
		 try{
			 nbt = CompressedStreamTools.readCompressed(fis);
		 }catch(ZipException e){
			 nbt = CompressedStreamTools.read(new DataInputStream(fis));
		 }
		 if(nbt != null){
			 width = nbt.getShort("Width");
			 height = nbt.getShort("Height");
			 length = nbt.getShort("Length");		
			 if(nbt.getList("Entities", 9).size() == 0){
				 entities = (NBTTagList)nbt.get("Entities");	
			 }else{
				 entities = nbt.getList("Entities", 9);	
			 }
			 if(nbt.getList("TileEntities", 9).size() == 0){
				 tileentity = (NBTTagList)nbt.get("TileEntities");	
			 }else{
				 tileentity = nbt.getList("TileEntities", 9);	
			 }	 
			 blocks = nbt.getByteArray("Blocks");
			 data = nbt.getByteArray("Data");
		 }else{
			 Minecraft.getInstance().player.sendChatMessage(LiteModMain.formateTextColor(LiteModMain.prefix + "&4Could not load the file!"));
		 }
		 fis.close();
	 }
	 private Entity createEntityFromNBT(NBTTagCompound nbt){
		 //return EntityList.createEntityFromNBT(nbt, Minecraft.getInstance().world);
		 return null;
	 }

	 public void loadFromSelection(ArrayList<BlockMark> selection){
		 blocks = new byte[selection.size()];
		 data = new byte[selection.size()];
		 HashMap<String, Tag> schematic = new HashMap<String, Tag>();
		 
		 BlockMark firstMark = selection.get(0);
		 BlockMark secondMark = selection.get(selection.size()-1);
		 
		 double minX = Math.min(firstMark.getX(), secondMark.getX());
		 double minY = Math.min(firstMark.getY(), secondMark.getY());
		 double minZ = Math.min(firstMark.getZ(), secondMark.getZ());
		 
		 double maxX = Math.max(firstMark.getX(), secondMark.getX());
		 double maxY = Math.max(firstMark.getY(), secondMark.getY());
		 double maxZ = Math.max(firstMark.getZ(), secondMark.getZ());
		 
		 width = SelectionHelper.calcWidth(selection.get(0), selection.get(selection.size()-1));
		 height = SelectionHelper.calcHeight(selection.get(0), selection.get(selection.size()-1));
		 length = SelectionHelper.calcLength(selection.get(0), selection.get(selection.size()-1));
		 
		 schematic.put("Width", new ShortTag(width));
	     schematic.put("Length", new ShortTag(length));
	     schematic.put("Height", new ShortTag(height));
	     
	     schematic.put("Materials", new StringTag("Alpha"));
	     schematic.put("WEOriginX", new IntTag((int)minX));
	     schematic.put("WEOriginY", new IntTag((int)minY));
	     schematic.put("WEOriginZ", new IntTag((int)minZ));
	     schematic.put("WEOffsetX", new IntTag((int)(Minecraft.getInstance().player.posX - minX)));
	     schematic.put("WEOffsetY", new IntTag((int)(Minecraft.getInstance().player.posY - minY)));
	     schematic.put("WEOffsetZ", new IntTag((int)(Minecraft.getInstance().player.posZ - minZ)));
	     
	     List<Tag> tileEntities = new ArrayList<Tag>();
	     List<Tag> entities = new ArrayList<Tag>();
	     
	     schematic.put("TileEntities", new ListTag(CompoundTag.class, tileEntities));
	     schematic.put("Entities", new ListTag(CompoundTag.class, entities));
	     
		 //int i = 0;		 
		 for(double y = minY; y <= maxY;y++){
			 for(double z = minZ; z <= maxZ;z++){
				 for(double x = minX; x <= maxX; x++){						
					 /*BlockPos mypos = new BlockPos(x,y,z);
					 Block.geti
					 blocks[i] = (byte) Block.getIdFromBlock(Minecraft.getInstance().world.getBlockState(mypos).getBlock());
					 data[i] = (byte) Minecraft.getInstance().world.getBlockState(mypos).getBlock().getMetaFromState(Minecraft.getInstance().world.getBlockState(mypos));
					 i++;*/
					}	
				}	
			}
		 schematic.put("Blocks", new ByteArrayTag(blocks));
	     schematic.put("Data", new ByteArrayTag(data));
	     
	     Cschematic = new CompoundTag(schematic);
	     
	 }
	 public void writeToFile(String filename) throws IOException{
		 System.out.println("[Schematic] writing to file...");
		 if(Cschematic != null){
			 String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "mods" + System.getProperty("file.separator") + "Lite_Zombe" + System.getProperty("file.separator") + "schematics" + System.getProperty("file.separator");
			 File Dirpath = new File(filename);
			 File file = new File(path + filename + ".schematic");
			 if(!Dirpath.exists()) Dirpath.createNewFile();
			 if(!file.exists()) file.createNewFile();		  
			 DataOutputStream var2 = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(file))));
			 NBTOutputStream out = new NBTOutputStream(var2);
			 
			 out.writeNamedTag("Schematic", Cschematic);
			 
			 out.close();
		 }else{
			 System.out.println("[Schematic] CSchematic is null");
		 }
		 System.out.println("[Schematic] done writing to  File");
	 }

	@Override
	public String toString() {
		return "Schematic [width=" + width + ", height=" + height + ", length="
				+ length + ", blocks=" + Arrays.toString(blocks) + ", data="
				+ Arrays.toString(data) + ", world=" + world + ", playerX="
				+ playerX + ", playerY=" + playerY + ", playerZ=" + playerZ
				+ ", entities=" + entities + ", tileentity=" + tileentity + "]";
	}
	}