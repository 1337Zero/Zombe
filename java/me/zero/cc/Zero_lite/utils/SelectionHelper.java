package me.zero.cc.Zero_lite.utils;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;

public class SelectionHelper {

	public static ArrayList<BlockMark> calcSelectedBlocks(Minecraft minecraft,BlockMark firstMark,BlockMark secondMark,float r,float g,float b,float alpha){
		
		ArrayList<BlockMark> marks = new ArrayList<BlockMark>();
		
		int movex = (int) (secondMark.getX() - firstMark.getX());
		int movey = (int) (secondMark.getY() - firstMark.getY());
		int movez = (int) (secondMark.getZ() - firstMark.getZ());
		
		if(movey > 0) movey++;
		
		if(movex > 0 & movez > 0){
			if(movey > 0){
				for(int y = 0; y < movey;y++){
					for(int x = 0; x <= movex;x++){
						for(int z = 0; z <= movez;z++){marks.add(new BlockMark((int)(firstMark.getX() + x), (int)(firstMark.getY() + y), (int)(firstMark.getZ() + z), minecraft, r,g, b, alpha));
						}
					}
				}
			}else{
				for(int y = firstMark.getY(); y >= secondMark.getY();y--){
					for(int x = 0; x <= movex;x++){
						for(int z = 0; z <= movez;z++){marks.add(new BlockMark((int)(firstMark.getX() + x), (int)(y), (int)(firstMark.getZ() + z), minecraft, r,g, b, alpha));
						}
					}
				}
			}			
		}else if(movex > 0 & movez < 0){			
			if(movey > 0){
				for(int y = firstMark.getY(); y <= secondMark.getY();y++){
					for(int x = 0; x <= movex;x++){
						for(int z = firstMark.getZ(); z >= secondMark.getZ();z--){
							marks.add(new BlockMark((int)(firstMark.getX() + x), (int)(y), (int)(z), minecraft, r,g, b, alpha));
						}
					}
				}
			}else{
				for(int y = firstMark.getY(); y >= secondMark.getY();y--){
					for(int x = 0; x <= movex;x++){
						for(int z = firstMark.getZ(); z >= secondMark.getZ();z--){
							marks.add(new BlockMark((int)(firstMark.getX() + x), (int)(y), (int)(z), minecraft, r,g, b, alpha));
						}
					}
				}
			}
		}else if(movex < 0 & movez > 0){
			if(movey > 0){
				for(int y = firstMark.getY(); y <= secondMark.getY();y++){
					for(int x = firstMark.getX(); x >= secondMark.getX();x--){
						for(int z = 0; z <= movez;z++){
							marks.add(new BlockMark((int)(x), (int)(y), (int)(firstMark.getZ() + z), minecraft, r,g, b, alpha));
						}
					}
				}
			}else{
				for(int y = firstMark.getY(); y >= secondMark.getY();y--){
					for(int x = firstMark.getX(); x >= secondMark.getX();x--){
						for(int z = 0; z <= movez;z++){
							marks.add(new BlockMark((int)(x), (int)(y), (int)(firstMark.getZ() + z), minecraft, r,g, b, alpha));
						}
					}
				}
			}
		}else if(movex < 0 & movez < 0){
			if(movey > 0){
				for(int y = firstMark.getY(); y <= secondMark.getY();y++){
					for(int x = firstMark.getX(); x >= secondMark.getX();x--){
						for(int z = firstMark.getZ(); z >= secondMark.getZ();z--){
							marks.add(new BlockMark((int)(x), (int)(y), (int)(z), minecraft, r,g, b, alpha));
						}
					}
				}	
			}else{
				for(int y = firstMark.getY(); y >= secondMark.getY();y--){
					for(int x = firstMark.getX(); x >= secondMark.getX();x--){
						for(int z = firstMark.getZ(); z >= secondMark.getZ();z--){
							marks.add(new BlockMark((int)(x), (int)(y), (int)(z), minecraft, r,g, b, alpha));
						}
					}
				}	
			}					
		}else{
			if(movex == 0){
				if(movey > 0){
					if(movez > 0){
						for(int y = firstMark.getY(); y <= secondMark.getY();y++){
							for(int x = 0; x < 1;x++){
								for(int z = firstMark.getZ(); z <= secondMark.getZ();z++){
									marks.add(new BlockMark((int)(firstMark.getX() + x), (int)(y), (int)(z), minecraft, r,g, b, alpha));
								}
							}
						}
					}else{
						for(int y = firstMark.getY(); y <= secondMark.getY();y++){
							for(int x = 0; x < 1;x++){
								for(int z = firstMark.getZ(); z >= secondMark.getZ();z--){
									marks.add(new BlockMark((int)(firstMark.getX() + x), (int)(y), (int)(z), minecraft, r,g, b, alpha));
								}
							}
						}	
					}						
				}else{
					if(movez > 0){
						for(int y = firstMark.getY(); y >= secondMark.getY();y--){
							for(int x = 0; x < 1;x++){
								for(int z = firstMark.getZ(); z <= secondMark.getZ();z++){
									marks.add(new BlockMark((int)(firstMark.getX() + x), (int)(y), (int)(z), minecraft, r,g, b, alpha));
								}
							}
						}
					}else{
						for(int y = firstMark.getY(); y >= secondMark.getY();y--){
							for(int x = 0; x < 1;x++){
								for(int z = firstMark.getZ(); z >= secondMark.getZ();z--){
									marks.add(new BlockMark((int)(firstMark.getX() + x), (int)(y), (int)(z), minecraft, r,g, b, alpha));
								}
							}
						}	
					}
				}
			}else if(movez == 0){
				if(movey > 0){
					if(movex > 0){
						for(int y = firstMark.getY(); y <= secondMark.getY();y++){
							for(int x = firstMark.getX(); x <= secondMark.getX();x++){
								for(int z = 0; z < 1;z++){
									marks.add(new BlockMark((int)(x), (int)(y), (int)(firstMark.getZ() + z), minecraft, r,g, b, alpha));
								}
							}
						}
					}else{
						for(int y = firstMark.getY(); y <= secondMark.getY();y++){
							for(int x = firstMark.getX(); x >= secondMark.getX();x--){
								for(int z = 0; z < 1;z++){
									marks.add(new BlockMark((int)(x), (int)(y), (int)(firstMark.getZ() + z), minecraft, r,g, b, alpha));
								}
							}
						}
					}
				}else{
					if(movex > 0){
						for(int y = firstMark.getY(); y >= secondMark.getY();y--){
							for(int x = firstMark.getX(); x <= secondMark.getX();x++){
								for(int z = 0; z < 1;z++){	
									marks.add(new BlockMark((int)(x), (int)(y), (int)(firstMark.getZ() + z), minecraft, r,g, b, alpha));
								}
							}
						}
					}else{
						for(int y = firstMark.getY(); y >= secondMark.getY();y--){
							for(int x = firstMark.getX(); x >= secondMark.getX();x--){
								for(int z = 0; z < 1;z++){
									marks.add(new BlockMark((int)(x), (int)(y), (int)(firstMark.getZ() + z), minecraft, r,g, b, alpha));
								}
							}
						}
					}
				}
			}else{
				System.out.println("dafuq is this?");
			}
		}
		return marks;		
	}
}
