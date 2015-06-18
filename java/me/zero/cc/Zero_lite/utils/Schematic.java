package me.zero.cc.Zero_lite.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;

import com.google.common.collect.Maps;

import me.zero.cc.Zero_lite.LiteModMain;
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
	 /**
	  * Takes the selected area and puts it into this schematic
	  * @param selection
	  */
	 public void loadFromSelection(ArrayList<BlockMark> selection){
		compound = new NBTTagCompound();
		blocks = new byte[selection.size()];
		data = new byte[selection.size()];
		NBTTagList tileentities = new NBTTagList();
		width = SelectionHelper.calcWidth(selection.get(0), selection.get(selection.size()-1));
		height = SelectionHelper.calcHeight(selection.get(0), selection.get(selection.size()-1));
		length = SelectionHelper.calcLength(selection.get(0), selection.get(selection.size()-1));
		compound.setShort("Width", width);
		compound.setShort("Height", height);
		compound.setShort("Length", length);
		
		 BlockMark firstMark = selection.get(0);
		 BlockMark secondMark = selection.get(selection.size()-1);
		
			double minX = Math.min(firstMark.getX(), secondMark.getX());
			double minY = Math.min(firstMark.getY(), secondMark.getY());
			double minZ = Math.min(firstMark.getZ(), secondMark.getZ());
			
			double maxX = Math.max(firstMark.getX(), secondMark.getX());
			double maxY = Math.max(firstMark.getY(), secondMark.getY());
			double maxZ = Math.max(firstMark.getZ(), secondMark.getZ());
		
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
			System.out.println("loaded " + blocks.length + " blocks");
		
		/* for(int i = 0; i < selection.size();i++){
			 blocks[i] = (byte) Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(selection.get(i).getX(), selection.get(i).getY(), selection.get(i).getZ())).getBlock());
			 data[i] =  (byte) Block.getStateId(Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(selection.get(i).getX(), selection.get(i).getY(), selection.get(i).getZ())));
			// System.out.println("Block @ (" + selection.get(i).getX() + ":" + selection.get(i).getY() + ":" + selection.get(i).getZ() +  ") " +Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(selection.get(i).getX(), selection.get(i).getY(), selection.get(i).getZ())).getBlock()) + ":" +  Block.getStateId(Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(selection.get(i).getX(), selection.get(i).getY(), selection.get(i).getZ()))));
			 if(Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(selection.get(i).getX(), selection.get(i).getY(), selection.get(i).getZ())).getBlock().hasTileEntity()){
			 
				TileEntity enti = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getTileEntity(new BlockPos(selection.get(i).getX(), selection.get(i).getY(), selection.get(i).getZ()));
				
			    Map classToNameMap = null;
				
			    try{
			    	Field f = enti.getClass().getDeclaredField("classToNameMap");
					f.setAccessible(true); 
					classToNameMap = (Map)f.get(enti);
					
			    }catch(Exception e){
			    	e.printStackTrace();
			    }
			   
			    if(classToNameMap != null){
					if((String)classToNameMap.get(this.getClass()) != null){
						 NBTTagCompound compound = new NBTTagCompound();
				         compound.setString("id", (String)classToNameMap.get(this.getClass()));
				         compound.setInteger("x", enti.getPos().getX());
				         compound.setInteger("y", enti.getPos().getY());
				         compound.setInteger("z", enti.getPos().getZ());
				         tileentities.appendTag(compound);
					}else{
						System.out.println("no name found for " + enti);
					}
			    }else{
			    	System.out.println("classtonamemap == null");
			    }
			 }
		 }	*/	
		 compound.setTag("TileEntities", tileentities);
		 compound.setByteArray("Blocks", blocks);
		 compound.setByteArray("Data", data);
		 compound.setTag("Entities", new NBTTagList());
	 }
	 public void writeToFile(String filename,NBTTagCompound nbttag) throws IOException{
		 System.out.println("writing to file...");
		 if(nbttag != null){
			 String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "mods" + System.getProperty("file.separator") + "Lite_Zombe" + System.getProperty("file.separator") + "schematics" + System.getProperty("file.separator");
			 File Dirpath = new File(filename);
			 File file = new File(path + filename + ".schematic");
			 if(!Dirpath.exists()) Dirpath.createNewFile();
			 if(!file.exists()) file.createNewFile();		  
			// CompressedStreamTools.safeWrite(nbttag, file); 
			 CompressedStreamTools.writeCompressed(nbttag, new DataOutputStream(new FileOutputStream(file)));
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