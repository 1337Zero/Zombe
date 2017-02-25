package me.zero.cc.Zero_lite.Mods;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseStringButton;
import me.zero.cc.Zero_lite.Gui.Buttons.SimpleSlider;
import me.zero.cc.Zero_lite.utils.BlockMark;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import me.zero.cc.Zero_lite.utils.KeySetting;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class RangeMod implements Mod {

	private boolean isenabled = false;
	private Minecraft minecraft;
	private LiteModMain main;
	private int range = 16;
	private boolean dropblock = false;
	private boolean markblock = true;
	private boolean removeFromInventory = false;
	private boolean addToInventory = false;
	private boolean reachbreak = false;
	private boolean reachplace = false;
	private boolean showinfo = false;
	private boolean reachPick = false;
	
	private double lastdestroyed = 0;
	private double lastplaced = 0;
	private double lastpressed = 0;
	private double lastpicked = 0;
	
	private int placedelay = 250;
	private int destroydelay = 125;
	private int pickdelay = 250;
	
	KeySetting reachplaceonkey = new KeySetting("RangeMod.reachbreakonkey");
	KeySetting reachbreakonkey = new KeySetting("RangeMod.reachplaceonkey");
	KeySetting reachPickonkey = new KeySetting("RangeMod.reachpickonkey");
	
	public boolean isReachPick() {
		return reachPick;
	}

	public void setReachPick(boolean reachPick) {
		this.reachPick = reachPick;
	}
	
	private int infoID = 0;
	
	private GuiPositions pos = GuiPositions.UP_LEFT;
	
	public RangeMod(Minecraft minecraft,LiteModMain main) {
		this.minecraft = minecraft;
		this.main = main;
		lastdestroyed = System.currentTimeMillis();
		lastplaced = System.currentTimeMillis();
		lastpicked = System.currentTimeMillis();
		//Load config
		range = Integer.parseInt(main.getConfig().getData("RangeMod.Range"));
		dropblock = Boolean.valueOf(main.getConfig().getData("RangeMod.DropBlock"));
		markblock = Boolean.valueOf(main.getConfig().getData("RangeMod.MarkBlock"));
		removeFromInventory = Boolean.valueOf(main.getConfig().getData("RangeMod.removefrominventory"));
		addToInventory = Boolean.valueOf(main.getConfig().getData("RangeMod.addtoinventory"));
		
		placedelay = Integer.valueOf(main.getConfig().getData("RangeMod.placedelay"));
		destroydelay = Integer.valueOf(main.getConfig().getData("RangeMod.destroydelay"));
		pickdelay = Integer.valueOf(main.getConfig().getData("RangeMod.pickdelay"));
		
		reachbreak = Boolean.valueOf(main.getConfig().getData("RangeMod.reachbreak"));
		reachplace = Boolean.valueOf(main.getConfig().getData("RangeMod.reachplace"));
		reachPick = Boolean.valueOf(main.getConfig().getData("RangeMod.reachpick")); 

		showinfo = Boolean.valueOf(main.getConfig().getData("RangeMod.showinfo"));
		pos = GuiPositions.valueOf(main.getConfig().getData("RangeMod.info-Pos"));
		
		infoID = main.getInfoLineManager().getInfoLine(pos).addInfo("");
	}
	
	public int getReachPickonkey() {
		return reachPickonkey.getKey();
	}

	public void setReachPickonkey(int reachPickonkey) {
		this.reachPickonkey.setKey(reachPickonkey);
	}

	@Override
	public boolean isEnabled() {
		return isenabled;
	}
	/**
	 * Methode to fix some id bugs from Minecraft...maybe there is a better way to get the id of the blocks from items
	 * @return Block
	 */
	private Block getBlockinPlayerHand(){
		
		Block b = Block.getBlockFromItem(minecraft.player.inventory.getStackInSlot(minecraft.player.inventory.currentItem).getItem()); 
		
		if(b == null){
			int id = 0;
			
			switch(Item.getIdFromItem(minecraft.player.inventory.getStackInSlot(minecraft.player.inventory.currentItem).getItem())){
			
			case 331: id = 55; break;
			case 356: id = 93; break;
			case 404: id = 149;break;
			
			}
			
			b = Block.getBlockById(id);
			//b.modifyAcceleration(minecraft.theWorld, pos.getBlockPos(), entityIn, minecraft.thePlayer.getLookVec());
		}		
		return b;
	}

	@Override
	public void use() {
		
		//if(Keyboard.isKeyDown(reachbreakonkey) && (minecraft.currentScreen == null)){
		if(reachbreakonkey.isPressed() && (minecraft.currentScreen == null)){
			if((System.currentTimeMillis() - lastpressed) >=100){
				if(reachbreak){
					reachbreak = false;
					main.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
				}else{
					reachbreak = true;
				}	
				main.getConfig().replaceData("RangeMod.reachbreak", "" + reachbreak);
			}	
			lastpressed = System.currentTimeMillis();
		}
		
		//if(Keyboard.isKeyDown(reachplaceonkey) && (minecraft.currentScreen == null)){
		if(reachplaceonkey.isPressed() && (minecraft.currentScreen == null)){
			if((System.currentTimeMillis() - lastpressed) >=100){
				if(reachplace){
					reachplace = false;
					main.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
				}else{
					reachplace = true;
				}	
				main.getConfig().replaceData("RangeMod.reachplace", "" + reachplace);
			}	
			lastpressed = System.currentTimeMillis();
		}
		
		//if(Keyboard.isKeyDown(reachPickonkey) && (minecraft.currentScreen == null)){
		if(reachPickonkey.isPressed() && (minecraft.currentScreen == null)){	
			if((System.currentTimeMillis() - lastpressed) >=100){
				if(reachPick){
					reachPick = false;
					main.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
				}else{
					reachPick = true;
				}	
				main.getConfig().replaceData("RangeMod.reachpick", "" + reachPick);
			}	
			lastpressed = System.currentTimeMillis();
		}
		
		if(showinfo){
			main.getInfoLineManager().getInfoLine(pos).setInfo(infoID, "Reach: " + range);
		}else{
			main.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);	
		}
		//Marks the Block you are looking at
		if(markblock){
			RayTraceResult objpos = minecraft.player.rayTrace(range,1.0F);
			if(objpos != null){
				if(objpos.getBlockPos() != null){
					markBlock(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ());
				}	
			}		
		}
		//Destroys the Block where you are looking at
		if(minecraft.gameSettings.keyBindAttack.isKeyDown()){
			if(reachbreak){
				if(minecraft.world.getBlockState(minecraft.player.rayTrace(6,1.0F).getBlockPos()).getBlock().getMaterial(null).equals(Material.AIR)){
					RayTraceResult objpos = minecraft.player.rayTrace(range,1.0F);
					if(objpos.getBlockPos() != null){
						//If to fast Blockbreaking minecraft crashes...idk why
						if((System.currentTimeMillis() - lastdestroyed) >= destroydelay){	
							if(minecraft.isSingleplayer()){	
								minecraft.player.swingArm(EnumHand.MAIN_HAND);	
								if(addToInventory){
									int subid = Minecraft.getMinecraft().world.getBlockState(new BlockPos(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ())).getBlock().getMetaFromState(Minecraft.getMinecraft().world.getBlockState(new BlockPos(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ())));
									
									ItemStack newitem = new ItemStack(minecraft.world.getBlockState(new BlockPos(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ())).getBlock(),1,subid);
									minecraft.getIntegratedServer().worlds[0].getPlayerEntityByUUID(minecraft.player.getUniqueID()).inventory.addItemStackToInventory(newitem);	
									minecraft.getIntegratedServer().getEntityWorld().destroyBlock(new BlockPos(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ()), false);
								}else{
									minecraft.getIntegratedServer().getEntityWorld().destroyBlock(new BlockPos(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ()), dropblock);
								}
								
							}else{		
								//Don't know how to make this in MP
								//Maybe sending a packet but idk
							}
							lastdestroyed = System.currentTimeMillis();
						}
					}
				}				
			}			
			//Sets the Block where you are looking
		}else if(minecraft.gameSettings.keyBindUseItem.isKeyDown()){
			if(reachplace){
				if(minecraft.world.getBlockState(minecraft.player.rayTrace(6,1.0F).getBlockPos()).getBlock().getMaterial(minecraft.world.getBlockState(minecraft.player.rayTrace(6,1.0F).getBlockPos())).equals(Material.AIR)){
					RayTraceResult objpos = minecraft.player.rayTrace(range,1.0F);
					if(objpos.getBlockPos() != null){	
						if(minecraft.isSingleplayer()){							
							if(minecraft.player.inventory.getStackInSlot(minecraft.player.inventory.currentItem) != null){
								if(System.currentTimeMillis() - lastplaced >= placedelay){
									if(getBlockinPlayerHand() != null){
										minecraft.player.swingArm(EnumHand.MAIN_HAND);
										int x = objpos.getBlockPos().getX();
										int y = objpos.getBlockPos().getY();
										int z = objpos.getBlockPos().getZ();
										
										if(objpos.sideHit.equals(EnumFacing.UP)){
											y = y + 1;
										}else if(objpos.sideHit.equals(EnumFacing.DOWN)){
											y = y - 1;
										}else if(objpos.sideHit.equals(EnumFacing.NORTH)){
											z = z -1;
										}else if(objpos.sideHit.equals(EnumFacing.SOUTH)){
											z = z + 1;
										}else if(objpos.sideHit.equals(EnumFacing.WEST)){
												x = x -1;
										}else if(objpos.sideHit.equals(EnumFacing.EAST)){
												x = x+1;
										}
										if(Block.getIdFromBlock(getBlockinPlayerHand()) == 93 | Block.getIdFromBlock(getBlockinPlayerHand()) == 149 | Block.getIdFromBlock(getBlockinPlayerHand()) == 55){

											int looksite = 1;
																						
											if(EnumFacing.fromAngle(minecraft.player.rotationYawHead).getName().equalsIgnoreCase("NORTH")){
												looksite = 4;
											}else if(EnumFacing.fromAngle(minecraft.player.rotationYawHead).getName().equalsIgnoreCase("SOUTH")){
												looksite = 2;
											}else if(EnumFacing.fromAngle(minecraft.player.rotationYawHead).getName().equalsIgnoreCase("EAST")){
												looksite = 5;
											}else if(EnumFacing.fromAngle(minecraft.player.rotationYawHead).getName().equalsIgnoreCase("WEST")){
												looksite = 3;
											}else if(EnumFacing.fromAngle(minecraft.player.rotationYawHead).getName().equalsIgnoreCase("UP")){
												looksite = 1;
											}else if(EnumFacing.fromAngle(minecraft.player.rotationYawHead).getName().equalsIgnoreCase("DOWN")){
												looksite = 6;
											}
											if(minecraft.world.getBlockState(new BlockPos(x, y, z)).getBlock().getMaterial(minecraft.world.getBlockState(new BlockPos(x, y, z))).equals(Material.AIR)){
												minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(x, y, z), getBlockinPlayerHand().getBlockState().getBlock().getStateFromMeta(looksite));
											}											
										}else{
											if(minecraft.world.getBlockState(new BlockPos(x, y, z)).getBlock().getMaterial(minecraft.world.getBlockState(new BlockPos(x, y, z))).equals(Material.AIR)){
												minecraft.getIntegratedServer().getEntityWorld().setBlockState(new BlockPos(x, y, z), getBlockinPlayerHand().getStateFromMeta(minecraft.player.inventory.getStackInSlot(minecraft.player.inventory.currentItem).getMetadata()));
											}
										}
										
										if(removeFromInventory){
											if (minecraft.player.inventory.getStackInSlot(minecraft.player.inventory.currentItem).getCount() == 1){
												//minecraft.player.inventory.setInventorySlotContents(minecraft.player.inventory.currentItem, null);
												minecraft.player.inventory.removeStackFromSlot(minecraft.player.inventory.currentItem);
												//minecraft.getIntegratedServer().worlds[0].getPlayerEntityByUUID(minecraft.player.getUniqueID()).inventory.mainInventory[minecraft.player.inventory.currentItem] = null;
											}else{
												minecraft.player.inventory.setInventorySlotContents(minecraft.player.inventory.currentItem, new ItemStack(minecraft.getIntegratedServer().worlds[0].getPlayerEntityByUUID(minecraft.player.getUniqueID()).inventory.getCurrentItem().getItem(), minecraft.getIntegratedServer().worlds[0].getPlayerEntityByUUID(minecraft.player.getUniqueID()).inventory.getCurrentItem().getCount()-1));
												//minecraft.getIntegratedServer().worlds[0].getPlayerEntityByUUID(minecraft.player.getUniqueID()).inventory.mainInventory[minecraft.player.inventory.currentItem] = new ItemStack(minecraft.getIntegratedServer().worlds[0].getPlayerEntityByUUID(minecraft.player.getUniqueID()).inventory.getCurrentItem().getItem(), minecraft.getIntegratedServer().worlds[0].getPlayerEntityByUUID(minecraft.player.getUniqueID()).inventory.getCurrentItem().stackSize-1);
											}									
										}
									}
									lastplaced = System.currentTimeMillis();
								}	
							}				
						}else{		
							//Don't know how to make this in MP
							//Maybe sending a packet but idk
						}
						
					}
				}				
			}				
			//Picks the Block you are looking at
		}else if(minecraft.gameSettings.keyBindPickBlock.isKeyDown()){
			if(reachPick){
				RayTraceResult objpos = minecraft.player.rayTrace(range,1.0F);
				if(objpos != null){
					if(objpos.getBlockPos() != null){
						if(minecraft.isSingleplayer()){
							if(System.currentTimeMillis() - lastpicked >= pickdelay){
								Block block = minecraft.world.getBlockState(new BlockPos(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ())).getBlock();	
								if(!block.getMaterial( minecraft.world.getBlockState(new BlockPos(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ()))).equals(Material.AIR)){
									ItemStack newitem;
									if(block.getMaterial( minecraft.world.getBlockState(new BlockPos(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ()))).equals(Material.LAVA)){
										newitem = new ItemStack(Item.getByNameOrId("minecraft:lava_bucket"));
									}else if(block.getMaterial( minecraft.world.getBlockState(new BlockPos(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ()))).equals(Material.WATER)){
										newitem = new ItemStack(Item.getByNameOrId("minecraft:water_bucket"));
									}else{
										int subid = Minecraft.getMinecraft().world.getBlockState(new BlockPos(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ())).getBlock().getMetaFromState(Minecraft.getMinecraft().world.getBlockState(new BlockPos(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ())));
										newitem = new ItemStack(minecraft.world.getBlockState(new BlockPos(objpos.getBlockPos().getX(), objpos.getBlockPos().getY(), objpos.getBlockPos().getZ())).getBlock(),1,subid);
									}
									if(newitem != null){		
										minecraft.player.inventory.setInventorySlotContents(minecraft.player.inventory.currentItem, newitem);
										//minecraft.getIntegratedServer().worlds[0].getPlayerEntityByUUID(minecraft.player.getUniqueID()).inventory.mainInventory[minecraft.player.inventory.currentItem] = newitem;
									}
								}
								lastpicked = System.currentTimeMillis();
							}
						}
					}
				}				
			}
		}
	}
	private void markBlock(int x, int y, int z){
		main.addMarkables(new BlockMark(x,y,z, minecraft, 1.0F, 0.0F, 1.0F, 1.0F));
	}

	@Override
	public String getName() {
		return ModData.RangeMod.name();
	}

	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public GuiScreen drawGui() {
		return new RangeModGui(main);
	}
	@Override
	public int getOn() {
		return 0;
	}

	public boolean isDropblock() {
		return dropblock;
	}

	public void setDropblock(boolean dropblock) {
		this.dropblock = dropblock;
	}

	public boolean isMarkblock() {
		return markblock;
	}

	public void setMarkblock(boolean markblock) {
		this.markblock = markblock;
	}

	public boolean isRemoveFromInventory() {
		return removeFromInventory;
	}

	public void setRemoveFromInventory(boolean removeFromInventory) {
		this.removeFromInventory = removeFromInventory;
	}

	public boolean isAddToInventory() {
		return addToInventory;
	}

	public void setAddToInventory(boolean addToInventory) {
		this.addToInventory = addToInventory;
	}

	public int getReachplaceonkey() {
		return reachplaceonkey.getKey();
	}

	public void setReachplaceonkey(int reachplaceonkey) {
		this.reachplaceonkey.setKey(reachplaceonkey);;
	}

	public int getReachbreakonkey() {
		return reachbreakonkey.getKey();
	}

	public void setReachbreakonkey(int reachbreakonkey) {
		this.reachbreakonkey.setKey(reachbreakonkey);
	}
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public boolean isShowinfo() {
		return showinfo;
	}

	public void setShowinfo(boolean showinfo) {
		this.showinfo = showinfo;
	}

	public GuiPositions getPos() {
		return pos;
	}

	public void setPos(GuiPositions pos) {
		this.pos = pos;
	}

	public boolean isReachbreak() {
		return reachbreak;
	}

	public void setReachbreak(boolean reachbreak) {
		this.reachbreak = reachbreak;
	}

	public boolean isReachplace() {
		return reachplace;
	}

	public void setReachplace(boolean reachplace) {
		this.reachplace = reachplace;
	}

	@Override
	public void manupulateValue(String ValueToManupulate, double value) {
		if(ValueToManupulate.equalsIgnoreCase("range")){
			if(value > 0){
				range = (int)value;
			}else{
				range = 2;
			}			
			main.getConfig().replaceData("RangeMod.Range", "" + range);
		}else if(ValueToManupulate.equalsIgnoreCase("EnablePick-Key")){
			reachPickonkey.setKey((int)value);
		}else if(ValueToManupulate.equalsIgnoreCase("Enableplace-Key")){
			reachplaceonkey.setKey((int)value);
		}else if(ValueToManupulate.equalsIgnoreCase("Enablebreak-Key")){
			reachbreakonkey.setKey((int)value);
		}else{
			System.out.println("Unknown Value + " + ValueToManupulate);
		}
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		if(valueToManupulate.equalsIgnoreCase("togglereachplace")){
			reachplace = b;
			main.getConfig().replaceData("RangeMod.reachplace", "" + b);
		}else if(valueToManupulate.equalsIgnoreCase("togglereachbreak")){
			reachbreak = b;
			main.getConfig().replaceData("RangeMod.reachbreak", "" + b);
		}else if(valueToManupulate.equalsIgnoreCase("togglemark")){
			markblock = b;
			main.getConfig().replaceData("RangeMod.MarkBlock", "" + b);
		}else if(valueToManupulate.equalsIgnoreCase("dropblock")){
			dropblock = b;
			main.getConfig().replaceData("RangeMod.DropBlock", "" + b);
		}else if(valueToManupulate.equalsIgnoreCase("removefrominventory")){
			removeFromInventory = b;
			main.getConfig().replaceData("RangeMod.removefrominventory", "" + b);
		}else if(valueToManupulate.equalsIgnoreCase("addtoinventory")){
			addToInventory = b;
			main.getConfig().replaceData("RangeMod.addtoinventory", "" + b);
		}else if(valueToManupulate.equalsIgnoreCase("showinfo")){	
			showinfo = b;
			main.getConfig().replaceData("RangeMod.showinfo", "" + b);
		}else if(valueToManupulate.equalsIgnoreCase("togglereachpick")){
			reachPick = b;
			main.getConfig().replaceData("RangeMod.reachpick", reachPick+"");
		}else{
			System.out.println(valueToManupulate + " " + b);
		}
	}

	@Override
	public void manupulateValue(String valueToManupulate, String value) {
		if(valueToManupulate.equalsIgnoreCase("infopos")){
			main.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
			pos = GuiPositions.valueOf(value);
			infoID = main.getInfoLineManager().getInfoLine(pos).addInfo("");
			main.getConfig().replaceData("RangeMod.info-Pos", "" + value);
		}else{
			System.out.println(valueToManupulate + ": " + value);
		}
	}
}
class RangeModGui extends GuiScreen{
	
	private LiteModMain speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	private GuiChooseKeyButton chooseEnableBlockPlace,chooseEnableBlockBreak,chooseEnableBlockPick;
	
	public RangeModGui(LiteModMain speicher){
		this.speicher = speicher;
	}
	
	public void initGui(){
		drawButtons();
	}
	/**
	 * Called if a Button is pressed
	 */
	public void actionPerformed(GuiButton b){	
		if(b.displayString.contains("back to game")){
			speicher.getMinecraft().displayGuiScreen(null);
		}else if(b.displayString.contains("waiting")){
			valueToManupulate = b.displayString.split("waiting")[0];
			GivingKey = true;
		}
	}
	/**
	 * Initialize Buttons and add them to the Button list
	 */
	public void drawButtons(){
		GuiBooleanButton toggleplace = new GuiBooleanButton(2, 80, height/4-40, 150, 20, "Toggle Reachplace", ((RangeMod)speicher.getMod(ModData.RangeMod.name())).isReachplace(), "togglereachplace", ModData.RangeMod, speicher,LiteModMain.lconfig.getData("RangeMod.togglereachplace").split(";"));
		GuiBooleanButton togglebreak = new GuiBooleanButton(2, 80, height/4-20, 150, 20, "Toggle Reachbreak", ((RangeMod)speicher.getMod(ModData.RangeMod.name())).isReachbreak(), "togglereachbreak", ModData.RangeMod, speicher,LiteModMain.lconfig.getData("RangeMod.togglereachbreak").split(";"));
		GuiBooleanButton togglemark = new GuiBooleanButton(2, 80, height/4, 150, 20, "Toggle Marker", ((RangeMod)speicher.getMod(ModData.RangeMod.name())).isMarkblock(), "togglemark", ModData.RangeMod, speicher,LiteModMain.lconfig.getData("RangeMod.marker").split(";"));
		GuiBooleanButton togglerangepick = new GuiBooleanButton(2, 250, height/4+20, 150, 20, "Toggle ReachPick", ((RangeMod)speicher.getMod(ModData.RangeMod.name())).isReachPick(), "togglereachpick", ModData.RangeMod, speicher,LiteModMain.lconfig.getData("RangeMod.togglereachpick").split(";"));
		
		GuiBooleanButton dropblock = new GuiBooleanButton(2, 250, height/4-40, 150, 20, "Drop Blocks", ((RangeMod)speicher.getMod(ModData.RangeMod.name())).isDropblock(), "dropblock", ModData.RangeMod, speicher,LiteModMain.lconfig.getData("RangeMod.dropblock").split(";"));
		GuiBooleanButton removefrominventory = new GuiBooleanButton(2, 250, height/4-20, 150, 20, "Remove From Inv", ((RangeMod)speicher.getMod(ModData.RangeMod.name())).isRemoveFromInventory(), "removefrominventory", ModData.RangeMod, speicher,LiteModMain.lconfig.getData("RangeMod.removefrominv").split(";"));
		GuiBooleanButton addtoinventory = new GuiBooleanButton(2, 250, height/4, 150, 20, "add to Inv", ((RangeMod)speicher.getMod(ModData.RangeMod.name())).isAddToInventory(), "addtoinventory", ModData.RangeMod, speicher,LiteModMain.lconfig.getData("RangeMod.addtoinv").split(";"));
					
		SimpleSlider slider = new SimpleSlider(0,80, height/4+100, "range", (int) ((RangeMod)speicher.getMod(ModData.RangeMod.name())).getRange()/10 , 150, 20, ModData.RangeMod, "Range", speicher,LiteModMain.lconfig.getData("RangeMod.range").split(";"));

		chooseEnableBlockPlace = new GuiChooseKeyButton(2, 80, height/4+60, 150, 20, "Enableplace-Key", ((RangeMod)speicher.getMod(ModData.RangeMod.name())).getReachplaceonkey(),LiteModMain.lconfig.getData("RangeMod.chooseplacekey").split(";"));
		chooseEnableBlockBreak = new GuiChooseKeyButton(2, 80, height/4+80, 150, 20, "Enablebreak-Key", ((RangeMod)speicher.getMod(ModData.RangeMod.name())).getReachbreakonkey(),LiteModMain.lconfig.getData("RangeMod.choosebreakkey").split(";"));
		chooseEnableBlockPick = new GuiChooseKeyButton(2, 250, height/4+40, 150, 20, "EnablePick-Key", ((RangeMod)speicher.getMod(ModData.RangeMod.name())).getReachPickonkey(),LiteModMain.lconfig.getData("RangeMod.choosepickkey").split(";"));
		
		GuiChooseStringButton choosepos = new GuiChooseStringButton(7, 80, height/4+40, 150, 20, "Info-Pos", GuiPositions.getPosList(), "infopos", ModData.RangeMod, speicher, GuiPositions.getPos(((RangeMod)speicher.getMod(ModData.RangeMod.name())).getPos()),LiteModMain.lconfig.getData("Main.choosepos").split(";"));
		
		GuiBooleanButton showInfo = new GuiBooleanButton(8, 80, height/4+20, 150, 20, "Show-Info", ((RangeMod)speicher.getMod(ModData.RangeMod.name())).isShowinfo(), "showinfo", ModData.RangeMod, speicher,LiteModMain.lconfig.getData("RangeMod.showinfo").split(";"));
				
		
		//Enable key,enable Button,
		
		GuiButton back = new GuiButton(9, 60,height-50 , "back to game");

		buttonList.add(slider);
		buttonList.add(showInfo);
		
		buttonList.add(chooseEnableBlockBreak);
		buttonList.add(chooseEnableBlockPlace);
		buttonList.add(chooseEnableBlockPick);
		
		buttonList.add(choosepos);		
		
		buttonList.add(dropblock);
		buttonList.add(removefrominventory);
		buttonList.add(addtoinventory);
		
		buttonList.add(togglebreak);
		buttonList.add(togglemark);
		buttonList.add(toggleplace);
		buttonList.add(togglerangepick);
		
		buttonList.add(back);
	}
	protected void keyTyped(char c,int key){
		if(GivingKey){
			if(key != 65 && key != 1){
				valueToManupulate = valueToManupulate.replace(" ", "");
				((RangeMod)speicher.getMod(ModData.RangeMod.name())).manupulateValue(valueToManupulate, key);
				
				if(valueToManupulate.equalsIgnoreCase("Enableplace-Key")){
					chooseEnableBlockPlace.setButtonkey(key);
				}else if(valueToManupulate.equalsIgnoreCase("Enablebreak-Key")){
					chooseEnableBlockBreak.setButtonkey(key);
				}else if(valueToManupulate.equalsIgnoreCase("EnablePick-Key")){
					chooseEnableBlockPick.setButtonkey(key);
				}else{
					System.out.println(valueToManupulate);
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
