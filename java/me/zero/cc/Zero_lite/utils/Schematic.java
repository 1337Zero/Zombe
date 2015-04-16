package me.zero.cc.Zero_lite.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipException;

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
	
	public Schematic(WorldClient world,double playerX,double playerY,double playerZ,File f) throws IOException{
		this.world = world;
		this.playerX = playerX;
		this.playerY = playerY;
		this.playerZ = playerZ;		
		load(f);
	}
	
	 public void pasteSchematic(){ 
		 try{
	   		for(int x = 0; x < width; ++x){
	   			for(int y = 0; y < height; ++y){
	   				for(int z = 0; z < length; ++z){
	   					int index = y * width * length + z * width + x;	   					
	   					double xpos = x + playerX;
	   					double ypos = y + playerY;
	   					double zpos = z + playerZ;
	   					int b = blocks[index] & 0xFF;
	   					Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(xpos, ypos, zpos), Block.getBlockById(b).getStateFromMeta(data[index]));
	   				}
	   			}
	   		}
	   		for(int i = 0; i < tileentity.tagCount();i++){
	   			TileEntity enti = TileEntity.createAndLoadEntity(tileentity.getCompoundTagAt(i));
	   			enti.setPos(new BlockPos(enti.getPos().getX() + Minecraft.getMinecraft().thePlayer.posX,enti.getPos().getY() + Minecraft.getMinecraft().thePlayer.posY, enti.getPos().getZ() + Minecraft.getMinecraft().thePlayer.posZ));
	   			Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setTileEntity(enti.getPos(), enti);
	   		}
	   		for(int i = 0; i < entities.tagCount();i++){	   			
	   			Entity ent = createEntityFromNBT(entities.getCompoundTagAt(i));
	   			if(ent != null){
	   				Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().spawnEntityInWorld(ent);
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

	@Override
	public String toString() {
		return "Schematic [width=" + width + ", height=" + height + ", length="
				+ length + ", blocks=" + Arrays.toString(blocks) + ", data="
				+ Arrays.toString(data) + ", world=" + world + ", playerX="
				+ playerX + ", playerY=" + playerY + ", playerZ=" + playerZ
				+ ", entities=" + entities + ", tileentity=" + tileentity + "]";
	}

	}