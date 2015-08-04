package me.zero.cc.Zero_lite.utils;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;

public class SelectionHelper {

	public static ArrayList<BlockMark> calcSelectedBlocks(Minecraft minecraft,BlockMark firstMark,BlockMark secondMark,float r,float g,float b,float alpha){
		ArrayList<BlockMark> marks = new ArrayList<BlockMark>();
		
		double minX = Math.min(firstMark.getX(), secondMark.getX());
		double minY = Math.min(firstMark.getY(), secondMark.getY());
		double minZ = Math.min(firstMark.getZ(), secondMark.getZ());
		
		double maxX = Math.max(firstMark.getX(), secondMark.getX());
		double maxY = Math.max(firstMark.getY(), secondMark.getY());
		double maxZ = Math.max(firstMark.getZ(), secondMark.getZ());

		for(double y = minY; y <= maxY;y++){
			for(double x = maxX; x >= minX; x--){			
				for(double z = maxZ; z >= minZ;z--){					
					marks.add(new BlockMark((int)(x), (int)(y), (int)(z), minecraft, r,g, b, 1.0f));
				}
			}
		}
		return marks;
	}
	public static short calcHeight(BlockMark first,BlockMark second){
		return (short) (((Math.max(first.getY(), second.getY())) - (Math.min(first.getY(), second.getY()))) +1);
	}
	public static short calcWidth(BlockMark first,BlockMark second){
		return (short) (((Math.max(first.getX(), second.getX())) - (Math.min(first.getX(), second.getX()))) +1);
	}
	public static short calcLength(BlockMark first,BlockMark second){
		return (short) (((Math.max(first.getZ(), second.getZ())) - (Math.min(first.getZ(), second.getZ()))) +1);
	}
}
