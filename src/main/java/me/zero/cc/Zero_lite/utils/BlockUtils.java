package me.zero.cc.Zero_lite.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;

public class BlockUtils {

	public static String getBlockName(BlockPos pos) {
		String name = IRegistry.BLOCK.getKey(Minecraft.getInstance().world.getBlockState(pos).getBlock()).toString();
		//1.13.1 ?
		//System.out.println(IRegistry.BLOCK.getKey(iblockstate.getBlock()));
		return name.replace("minecraft:", "");
	}
	public static ResourceLocation getResourceLocationByBlock(Block b) {
		return IRegistry.BLOCK.getKey(b);
	}
	public static Block getBlockByRessource(String name) {	
		if(name.contains("minecraft:")) {
			return IRegistry.BLOCK.get(new ResourceLocation(name));		
		}else {
			return IRegistry.BLOCK.get(new ResourceLocation("minecraft:" + name));		
		}
		
	}
	public static Block getBlockByRessource(ResourceLocation loc) {		
		return IRegistry.BLOCK.get(loc);		
	}
	
	public static Block getBlockByID(int id) {
		return IRegistry.BLOCK.get(id);	
	}
}
