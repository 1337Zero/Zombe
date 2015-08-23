package me.zero.cc.Zero_lite.utils;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipException;

import com.google.common.collect.Maps;

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
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

public class Schematic{

	short width,height,length;
	byte[] blocks,data;
	private WorldClient world;
	private double playerX,playerY,playerZ;
	public NBTTagList entities,tileentity;
	private NBTTagCompound compound;
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
			   					Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(xpos, ypos, zpos), Block.getBlockById(b).getStateFromMeta(data[index]));
			   				}
			   			}
			   		}
			   		if(tileentity != null){
			   			for(int i = 0; i < tileentity.tagCount();i++){
				   			TileEntity enti = TileEntity.createAndLoadEntity(tileentity.getCompoundTagAt(i));
				   			enti.setPos(new BlockPos(enti.getPos().getX() + Minecraft.getMinecraft().thePlayer.posX,enti.getPos().getY() + Minecraft.getMinecraft().thePlayer.posY, enti.getPos().getZ() + Minecraft.getMinecraft().thePlayer.posZ));
				   			Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setTileEntity(enti.getPos(), enti);
				   		}
			   		}
			   		if(entities != null){
			   			for(int i = 0; i < entities.tagCount();i++){	   			
				   			Entity ent = createEntityFromNBT(entities.getCompoundTagAt(i));
				   			if(ent != null){
				   				Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().spawnEntityInWorld(ent);
				   			}	   			
				   		}
			   		}
			   		
		   			Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().updateEntities();
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
			 if(nbt.getTagList("Entities", 9).tagCount() == 0){
				 entities = (NBTTagList)nbt.getTag("Entities");	
			 }else{
				 entities = nbt.getTagList("Entities", 9);	
			 }
			 if(nbt.getTagList("TileEntities", 9).tagCount() == 0){
				 tileentity = (NBTTagList)nbt.getTag("TileEntities");	
			 }else{
				 tileentity = nbt.getTagList("TileEntities", 9);	
			 }	 
			 blocks = nbt.getByteArray("Blocks");
			 data = nbt.getByteArray("Data");
		 }else{
			 Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(LiteModMain.formateTextColor(LiteModMain.prefix + "&4Could not load the file!")));
		 }
		 fis.close();
	 }
	 private Entity createEntityFromNBT(NBTTagCompound nbt){
		 return EntityList.createEntityFromNBT(nbt, Minecraft.getMinecraft().theWorld);
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
	     schematic.put("WEOffsetX", new IntTag((int)(Minecraft.getMinecraft().thePlayer.posX - minX)));
	     schematic.put("WEOffsetY", new IntTag((int)(Minecraft.getMinecraft().thePlayer.posY - minY)));
	     schematic.put("WEOffsetZ", new IntTag((int)(Minecraft.getMinecraft().thePlayer.posZ - minZ)));
	     
	     List<Tag> tileEntities = new ArrayList<Tag>();
	     List<Tag> entities = new ArrayList<Tag>();
	     
	     schematic.put("TileEntities", new ListTag(CompoundTag.class, tileEntities));
	     schematic.put("Entities", new ListTag(CompoundTag.class, entities));
	     
		 int i = 0;		 
		 for(double y = minY; y <= maxY;y++){
			 for(double z = minZ; z <= maxZ;z++){
				 for(double x = minX; x <= maxX; x++){						
					 BlockPos mypos = new BlockPos(x,y,z);
					 blocks[i] = (byte) Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlockState(mypos).getBlock());
					 data[i] = (byte) Minecraft.getMinecraft().theWorld.getBlockState(mypos).getBlock().getMetaFromState(Minecraft.getMinecraft().theWorld.getBlockState(mypos));
					 i++;
					}	
				}	
			}
		 schematic.put("Blocks", new ByteArrayTag(blocks));
	     schematic.put("Data", new ByteArrayTag(data));
	     
	     Cschematic = new CompoundTag(schematic);
	     
	 }
	 public void writeToFile(String filename) throws IOException{
		 System.out.println("writing to file...");
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
			 System.out.println("NOPE");
		 }
		 System.out.println("...done");
	 }

	@Override
	public String toString() {
		return "Schematic [width=" + width + ", height=" + height + ", length="
				+ length + ", blocks=" + Arrays.toString(blocks) + ", data="
				+ Arrays.toString(data) + ", world=" + world + ", playerX="
				+ playerX + ", playerY=" + playerY + ", playerZ=" + playerZ
				+ ", entities=" + entities + ", tileentity=" + tileentity + "]";
	}
	public NBTTagCompound getNbttag() {
		return compound;
	}

	}