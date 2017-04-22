package me.zero.cc.Zero_lite.Mods;

import java.util.ArrayList;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseStringButton;
import me.zero.cc.Zero_lite.utils.GroundMark;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import me.zero.cc.Zero_lite.utils.Markables;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class InfoMod implements Mod {

	private Minecraft minecraft;
	private double percent = 0;
	private int rotation = 0;
	private String Facing = "";
	//unused
	private int onkey = 0;
	private LiteModMain speicher;
	
	private int fps = 0;
	private int newstfps = 0;
	private long lastaktu = 0;
	private String version = "0.1";
	
	private int infoidFPS = 0;
	private int infoidDir = 0;
	private int infoidCoor = 0;
	private int infoidWorldAge = 0;
	
	private GuiPositions posFPS = GuiPositions.UP_CENTER;
	private GuiPositions posDir = GuiPositions.UP_RIGHT;
	private GuiPositions posCoor = GuiPositions.UP_RIGHT;
	private GuiPositions posWorldAge = GuiPositions.UP_LEFT;
		
	private boolean enabled = true;
	private boolean showfps = false;
	private boolean showdir = false;
	private boolean showcoor = false;
	private boolean showWorldAge = false;
	
	private boolean showfriendly = false;
	private boolean showaggressiv = false;
	private boolean dynamicselection = false;
	
	public ArrayList<Markables> blocks = new ArrayList<Markables>();
	private LightSearchThread lst;
	
	private long lastdiraktu = 0;
	
	public InfoMod(Minecraft minecraft,LiteModMain speicher){		
		this.minecraft = minecraft;
		this.speicher = speicher;	
		
		posFPS = GuiPositions.valueOf(speicher.getConfig().getData("Info-Mod.FPS-Pos"));
		posDir = GuiPositions.valueOf(speicher.getConfig().getData("Info-Mod.Dir-POS"));
		posCoor= GuiPositions.valueOf(speicher.getConfig().getData("Info-Mod.Coor-Pos"));
		posWorldAge = GuiPositions.valueOf(speicher.getConfig().getData("Info-Mod.Worldage-Pos"));		
		showfps = Boolean.valueOf(speicher.getConfig().getData("Info-Mod.showFPS"));
		showdir = Boolean.valueOf(speicher.getConfig().getData("Info-Mod.showdir"));
		showcoor = Boolean.valueOf(speicher.getConfig().getData("Info-Mod.showcoor"));
		showWorldAge = Boolean.valueOf(speicher.getConfig().getData("Info-Mod.showworldage"));		
		
		infoidFPS = speicher.getInfoLineManager().getInfoLine(posFPS).addInfo("");
		infoidDir = speicher.getInfoLineManager().getInfoLine(posDir).addInfo("");
		infoidCoor = speicher.getInfoLineManager().getInfoLine(posCoor).addInfo("");
		infoidWorldAge = speicher.getInfoLineManager().getInfoLine(posWorldAge).addInfo("");
		
		showfriendly = Boolean.valueOf(speicher.getConfig().getData("Info-Mod.showfriendlyspawns"));
		showaggressiv = Boolean.valueOf(speicher.getConfig().getData("Info-Mod.showaggressivspawns"));
		dynamicselection = Boolean.valueOf(speicher.getConfig().getData("Info-Mod.dynamicselection"));

		lastaktu = System.currentTimeMillis();
		lastdiraktu = System.currentTimeMillis();
	}	
	@Override
	public boolean isEnabled() {		
		return enabled;
	}
	@Override
	public void use() {
		if(showfps){
			UpdateFPS();
		}
		if(lst == null) {
			if(showaggressiv || showfriendly){
				lst = new LightSearchThread(this);
				lst.start();
			}
		}
		if(showdir){
			if(System.currentTimeMillis() - lastdiraktu >= 60){
				//Old but Gold -> if Enumfacing will be removed this should also calc the facing...
				//rotationyam = minecraft.thePlayer.rotationYawHead;			
				//UpdateDirection(getPercent());
				//UpdateRotation(minecraft);
				Facing = EnumFacing.fromAngle(minecraft.player.rotationYawHead).getName().toUpperCase();
				lastdiraktu = System.currentTimeMillis();
			}			
		}	
		drawInfo();
	}
	private void UpdateFPS(){
		if(System.currentTimeMillis() - lastaktu <= 1000){
			fps++;
		}else{
			newstfps = fps;
			fps = 0;
			lastaktu = System.currentTimeMillis();
		}
	}
	/**
	 * Calculates the the world Age in human-readable way
	 * @return String
	 */
	private String getWorldAge(){
		long worldage = minecraft.world.getTotalWorldTime();
		int sek = (int) (worldage/20);
		
		int basesec = 1;
		int basemin = (60*basesec);
		int basehour = (60*basemin);
		int baseday = (24*basehour);
		int baseyear = (365*baseday);
		
		int years = sek/(baseday)/365;
		int days = (sek%baseyear)/(baseday);
		int hours = (sek%baseday)/(basehour);
		int mins = (sek%basehour)/(basemin);
		int seconds = (sek%basemin*basesec);
				
		ArrayList<String> back = new ArrayList<String>();
		
		if(years > 0){
			back.add( years + " Y");
		}
		if(days > 0){
			back.add(days + " D");
		}
		if(hours > 0){
			back.add(hours + " H");
		}
		if(mins > 0){
			back.add(mins + " M");
		}
		if(seconds > 0){
			back.add(seconds + " S");
		}			
		return back.toString().replace("[", "").replace("]", "");
	}
	@Override
	public String getName() {		
		return ModData.InfoMod.name();
	}
	@Override
	public String getVersion() {
		return version;
	}
	private void drawInfo() {
		if(showfps){
			speicher.getInfoLineManager().getInfoLine(posFPS).setInfo(infoidFPS, "FPS(" + newstfps + ")");
		}else{
			speicher.getInfoLineManager().getInfoLine(posFPS).setInfo(infoidFPS, "");
		}
		if(showdir){
			speicher.getInfoLineManager().getInfoLine(posDir).setInfo(infoidDir, Facing);
		}else{
			speicher.getInfoLineManager().getInfoLine(posDir).setInfo(infoidDir, "");
		}
		if(showcoor){
			speicher.getInfoLineManager().getInfoLine(posCoor).setInfo(infoidCoor, "(" + (int)minecraft.player.posX + "," + (int)minecraft.player.posY + "," + (int)minecraft.player.posZ + ")");
		}else{
			speicher.getInfoLineManager().getInfoLine(posCoor).setInfo(infoidCoor, "");
		}
		if(showWorldAge){
			speicher.getInfoLineManager().getInfoLine(posWorldAge).setInfo(infoidWorldAge, getWorldAge());
		}else{
			speicher.getInfoLineManager().getInfoLine(posWorldAge).setInfo(infoidWorldAge, "");
		}
	}
	/**
	 * Gets the key for enable
	 * @return Integer
	 */
	public int getOn() {
		return onkey;
	}
	/**
	 * Sets the key for enable
	 * @param Integer
	 */
	public void setOn(int on) {
		onkey = on;
	}
	/**
	 * Returns how often the Player has turned
	 * @return Integer
	 */
	public int getRotation() {
		return rotation;
	}
	/**
	 * Sets how often the Player has turned
	 * @return Integer
	 */
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	/**
	 * Gets the Percent where the player is looking
	 * @return Double
	 */
	public double getPercent() {
		return percent;
	}
	/**
	 * Sets the Percent where the player is looking
	 * @param percent
	 */
	public void setPercent(double percent) {
		this.percent = percent;
	}
	/**
	 * Gets the Facing of the Player
	 * @return String
	 */
	public String getFacing() {
		return Facing;
	}
	/**
	 * Sets the facing
	 * @param String
	 */
	public void setFacing(String facing) {
		Facing = facing;
	}

	@Override
	public GuiScreen drawGui() {
		return new InfoModGui(speicher);
	}

	@Override
	public void manupulateValue(String ValueToManupulate, double percent) {		
		System.out.println("Don't know that " + ValueToManupulate);
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {		
		if(valueToManupulate.equalsIgnoreCase("showcoor")){
			showcoor = b;
			speicher.getConfig().replaceData("Info-Mod.showcoor", showcoor + "");
		}else if(valueToManupulate.equalsIgnoreCase("showfps")){
			showfps = b;
			speicher.getConfig().replaceData("Info-Mod.showFPS", showfps + "");
		}else if(valueToManupulate.equalsIgnoreCase("showdir")){
			showdir = b;
			speicher.getConfig().replaceData("Info-Mod.showdir", showdir + "");
		}else if(valueToManupulate.equalsIgnoreCase("showworldage")){
			showWorldAge = b;
			speicher.getConfig().replaceData("Info-Mod.showworldage", showWorldAge + "");
		}else if(valueToManupulate.equalsIgnoreCase("showfmobspawn")){
			showfriendly = b;
			speicher.getConfig().replaceData("Info-Mod.showfriendlyspawns", showfriendly + "");
			if(showfriendly == true){
				if(lst == null){
					lst = new LightSearchThread(this);
					lst.start();
				}
			}else if(!showaggressiv){
				lst.enabled = false;
				lst = null;
				blocks.clear();
			}			
		}else if(valueToManupulate.equalsIgnoreCase("showamobspawn")){
			showaggressiv = b;
			speicher.getConfig().replaceData("Info-Mod.showaggressivspawns", showaggressiv + "");
			if(showaggressiv == true){
				if(lst == null){
					lst = new LightSearchThread(this);
					lst.start();
				}
			}else if(!showfriendly){
				lst.enabled = false;
				lst = null;
				blocks.clear();
			}
		}else if(valueToManupulate.equalsIgnoreCase("dynamichsel")){			
			dynamicselection = b;
			speicher.getConfig().replaceData("Info-Mod.dynamicselection", dynamicselection + "");
		}else{
			System.out.println("Don't know that " + valueToManupulate);
		}	
	}
	@Override
	public void manupulateValue(String valueToManupulate, String value) {
		if(valueToManupulate.equalsIgnoreCase("posdir")){
			speicher.getInfoLineManager().getInfoLine(posDir).resetInfo(infoidDir);
			posDir = GuiPositions.valueOf(value);
			infoidDir = speicher.getInfoLineManager().getInfoLine(posDir).addInfo("");		
			speicher.getConfig().replaceData("Info-Mod.Dir-POS", posDir.toString());
		}else
		if(valueToManupulate.equalsIgnoreCase("posfps")){
			speicher.getInfoLineManager().getInfoLine(posFPS).resetInfo(infoidFPS);
			posFPS = GuiPositions.valueOf(value);
			infoidFPS = speicher.getInfoLineManager().getInfoLine(posFPS).addInfo("");	
			speicher.getConfig().replaceData("Info-Mod.FPS-Pos", posFPS.toString());
		}else
		if(valueToManupulate.equalsIgnoreCase("poscoor")){
			speicher.getInfoLineManager().getInfoLine(posCoor).resetInfo(infoidCoor);
			posCoor = GuiPositions.valueOf(value);
			infoidCoor = speicher.getInfoLineManager().getInfoLine(posCoor).addInfo("");	
			speicher.getConfig().replaceData("Info-Mod.Coor-Pos", posCoor.toString());
		}else
		if(valueToManupulate.equalsIgnoreCase("posworldage")){
			speicher.getInfoLineManager().getInfoLine(posWorldAge).resetInfo(infoidWorldAge);
			posWorldAge = GuiPositions.valueOf(value);
			infoidWorldAge = speicher.getInfoLineManager().getInfoLine(posWorldAge).addInfo("");	
			speicher.getConfig().replaceData("Info-Mod.Worldage-Pos", posWorldAge.toString());
		}else{
			System.out.println("Don't know that " + valueToManupulate);		
		}		
	}
	/**
	 * Get the Position of the FPS Counter
	 * @return GuiPositions
	 */
	public GuiPositions getPosFPS() {
		return posFPS;
	}
	/**
	 * Sets the Position of the FPS Counter
	 * @param GuiPositions
	 */
	public void setPosFPS(GuiPositions posFPS) {
		this.posFPS = posFPS;
	}
	/**
	 * Get the Position for the Direction
	 * @return GuiPositions
	 */
	public GuiPositions getPosDir() {
		return posDir;
	}
	/**
	 * Get the Position for the WorldAge
	 * @return GuiPositions
	 */
	public GuiPositions getPosWorldAge() {
		return posWorldAge;
	}
	/**
	 * Set the Position for the WorldAge
	 * @param GuiPositions
	 */
	public void setPosWorldAge(GuiPositions posWorldAge) {
		this.posWorldAge = posWorldAge;
	}
	/**
	 * Set the Position for the Direction
	 * @param GuiPositions
	 */
	public void setPosDir(GuiPositions posDir) {
		this.posDir = posDir;
	}
	/**
	 * Get the Position for the Coordinates
	 * @return GuiPositions
	 */
	public GuiPositions getPosCoor() {
		return posCoor;
	}
	/**
	 * Set the Position for the Coordinates
	 * @param GuiPositions
	 */
	public void setPosCoor(GuiPositions posCoor) {
		this.posCoor = posCoor;
	}
	/**
	 * Get if the FPS are shown
	 * @return Boolean
	 */
	public boolean isShowfps() {
		return showfps;
	}
	/**
	 * Set if the FPS are shown
	 * @param Boolean
	 */
	public void setShowfps(boolean showfps) {
		this.showfps = showfps;
	}
	/**
	 * Get if the Worldage is shown
	 * @return Boolean
	 */
	public boolean isShowWorldAge() {
		return showWorldAge;
	}
	/**
	 * Set if the Worldage is shown
	 * @param Boolean
	 */
	public void setShowWorldAge(boolean showWorldAge) {
		this.showWorldAge = showWorldAge;
	}
	/**
	 * Get if the Direction is shown
	 * @return Boolean
	 */
	public boolean isShowdir() {
		return showdir;
	}
	/**
	 * Set if the Direction is shown
	 * @param Boolean
	 */
	public void setShowdir(boolean showdir) {
		this.showdir = showdir;
	}
	/**
	 * Get if the Coordinations are shown
	 * @return Boolean
	 */
	public boolean isShowcoor() {
		return showcoor;
	}
	/**
	 * Set if the Coordinations are shown
	 * @param Boolean
	 */
	public void setShowcoor(boolean showcoor) {
		this.showcoor = showcoor;
	}
	public int getRadius() {
		return Integer.parseInt(LiteModMain.config.getData("InfoMod.radius"));
	}
	/**
	 * Called by every render tick... has to be registered in LiteModMain
	 * @param float
	 */
	public void render(float partialTicks){
		if(blocks.size() > 0 && enabled){
			for(int i = 0; i < blocks.size();i++){
				blocks.get(i).draw(partialTicks);
			}			
		}
		
	}
	public boolean isShowingAggressivSpawns() {
		return showaggressiv;
	}
	public boolean isShowingFriendlySpawns() {
		return showfriendly;
	}
	public boolean isDynamic() {
		return dynamicselection;
	}
}
class InfoModGui extends GuiScreen{
	
	private LiteModMain speicher;
	
	public InfoModGui(LiteModMain speicher){
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
		}
	}
	/**
	 * Initialize Buttons and add them to the Button list
	 */
	public void drawButtons(){
		GuiBooleanButton showDirection = new GuiBooleanButton(1, 10, 20, 150, 20, "Show Direction", ((InfoMod)speicher.getMod(ModData.InfoMod.name())).isShowdir(), "showdir", ModData.InfoMod, speicher,LiteModMain.lconfig.getData("InfoMod.showdir").split(";"));
		GuiChooseStringButton dirpos = new GuiChooseStringButton(2, width/2+50, 20, 150, 20, "Dir-Position", GuiPositions.getPosList(), "posdir", ModData.InfoMod, speicher,GuiPositions.getPos(((InfoMod)speicher.getMod(ModData.InfoMod.name())).getPosDir()),LiteModMain.lconfig.getData("Main.choosepos").split(";"));
		
		GuiBooleanButton showFPS= new GuiBooleanButton(3, 10, 45, 150, 20, "Show FPS", ((InfoMod)speicher.getMod(ModData.InfoMod.name())).isShowfps(), "showfps", ModData.InfoMod, speicher,LiteModMain.lconfig.getData("InfoMod.showfps").split(";"));
		GuiChooseStringButton fpspos = new GuiChooseStringButton(4, width/2+50, 45, 150, 20, "FPS-Position", GuiPositions.getPosList(), "posfps", ModData.InfoMod, speicher,GuiPositions.getPos(((InfoMod)speicher.getMod(ModData.InfoMod.name())).getPosFPS()),LiteModMain.lconfig.getData("Main.choosepos").split(";"));
		
		GuiBooleanButton showCoor = new GuiBooleanButton(5, 10, 70, 150, 20, "Show Coor", ((InfoMod)speicher.getMod(ModData.InfoMod.name())).isShowcoor(), "showcoor", ModData.InfoMod, speicher,LiteModMain.lconfig.getData("InfoMod.showcoor").split(";"));
		GuiChooseStringButton coorpos = new GuiChooseStringButton(6, width/2+50, 70, 150, 20, "Coor-Position", GuiPositions.getPosList(), "poscoor", ModData.InfoMod, speicher,GuiPositions.getPos(((InfoMod)speicher.getMod(ModData.InfoMod.name())).getPosCoor()),LiteModMain.lconfig.getData("Main.choosepos").split(";"));
		
		GuiBooleanButton showworldage = new GuiBooleanButton(7, 10, 95, 150, 20, "Show WorldAge", ((InfoMod)speicher.getMod(ModData.InfoMod.name())).isShowWorldAge(), "showworldage", ModData.InfoMod, speicher,LiteModMain.lconfig.getData("InfoMod.showworldage").split(";"));
		GuiChooseStringButton worldagepos = new GuiChooseStringButton(8, width/2+50, 95, 150, 20, "WorldAge-Position", GuiPositions.getPosList(), "posworldage", ModData.InfoMod, speicher,GuiPositions.getPos(((InfoMod)speicher.getMod(ModData.InfoMod.name())).getPosWorldAge()),LiteModMain.lconfig.getData("Main.choosepos").split(";"));
		
		
		GuiBooleanButton showFriendly = new GuiBooleanButton(7, 10, 120, 150, 20, "Mark friendly spawns", ((InfoMod)speicher.getMod(ModData.InfoMod.name())).isShowingFriendlySpawns(), "showfmobspawn", ModData.InfoMod, speicher,LiteModMain.lconfig.getData("InfoMod.showfriendlymobspawn").split(";"));
		GuiBooleanButton showAggressiv = new GuiBooleanButton(7, 10, 145, 150, 20, "Mark aggressiv spawns", ((InfoMod)speicher.getMod(ModData.InfoMod.name())).isShowingAggressivSpawns(), "showamobspawn", ModData.InfoMod, speicher,LiteModMain.lconfig.getData("InfoMod.showaggressivmobspawn").split(";"));
		
		GuiBooleanButton dynamic = new GuiBooleanButton(7, width/2+50, 120, 150, 20, "dynamic selection", ((InfoMod)speicher.getMod(ModData.InfoMod.name())).isDynamic(), "dynamichsel", ModData.InfoMod, speicher,LiteModMain.lconfig.getData("InfoMod.dynamicselection").split(";"));
		
		
		
		GuiButton back = new GuiButton(9, width/2-100,height-50 , "back to game");
		
		buttonList.add(showworldage);
		buttonList.add(worldagepos);
		buttonList.add(dirpos);
		buttonList.add(fpspos);
		buttonList.add(coorpos);
		buttonList.add(showCoor);
		buttonList.add(showFPS);
		buttonList.add(showDirection);
		
		buttonList.add(showFriendly);
		buttonList.add(showAggressiv);
		buttonList.add(dynamic);
		
		buttonList.add(back);
	}
	protected void keyTyped(char c,int key){			
			if(key == 65 || key == 1){
				speicher.getMinecraft().displayGuiScreen(null);		
		}		
	}
}
class LightSearchThread extends Thread{

	private InfoMod omod;
	public boolean selectivesearch;
	public boolean enabled = true;
	
	public LightSearchThread(InfoMod omod){
		this.omod = omod;
		selectivesearch = Boolean.valueOf(LiteModMain.config.getData("InfoMod.dynamicselection"));
	}
	
	@Override
	public void run() {
		while(enabled){
			updateRenderPos();
			try {
				sleep(Long.valueOf(LiteModMain.config.getData("InfoMod.threadsleep")));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}	
	private void updateRenderPos(){	
		ArrayList<Markables> tempblocks = new ArrayList<Markables>();		
		
		double posx = Minecraft.getMinecraft().player.posX - omod.getRadius()*10;
		double posz = Minecraft.getMinecraft().player.posZ - omod.getRadius()*10;
			
		if(selectivesearch){
			String facing = EnumFacing.fromAngle(Minecraft.getMinecraft().player.rotationYawHead).getName().toUpperCase();				
			if(facing.equalsIgnoreCase("north")){
				posz = (Minecraft.getMinecraft().player.posZ +1) - (omod.getRadius()*10)*2;
				posx = Minecraft.getMinecraft().player.posX - omod.getRadius()*10;
			}else if(facing.equalsIgnoreCase("south")){
				posz = Minecraft.getMinecraft().player.posZ;
			}else if(facing.equalsIgnoreCase("west")){
				//updaten -
				posx = (Minecraft.getMinecraft().player.posX +1) - (omod.getRadius()*10)*2;
				posz = Minecraft.getMinecraft().player.posZ - omod.getRadius()*10;
			}else if(facing.equalsIgnoreCase("east")){
				posx = Minecraft.getMinecraft().player.posX;
			}				
		}
		int radius = omod.getRadius()*10;
		for(double x = posx; x < (posx + (radius)*2);x++){
			for(double y = Minecraft.getMinecraft().player.posY-(radius); y < Minecraft.getMinecraft().player.posY+(radius);y++){				
				for(double z = posz; z < (posz + (radius)*2);z++){
					if(Minecraft.getMinecraft() != null){
						if(Minecraft.getMinecraft().world != null){
							if(Minecraft.getMinecraft().world.getBlockState(new BlockPos(x, y, z)) != null){
								Block block = Minecraft.getMinecraft().world.getBlockState(new BlockPos(x, y, z)).getBlock();
								if(Block.getIdFromBlock(block) == 0 ){
									Block blockbelow = Minecraft.getMinecraft().world.getBlockState(new BlockPos(x, y-1, z)).getBlock();
									if(Block.getIdFromBlock(blockbelow) != 0 && Block.getIdFromBlock(blockbelow) != 31 && Block.getIdFromBlock(blockbelow) != 175 && Block.getIdFromBlock(blockbelow) != 8 && Block.getIdFromBlock(blockbelow) != 9&& Block.getIdFromBlock(blockbelow) != 10&& Block.getIdFromBlock(blockbelow) != 11){
										if(block != null){
											int blightlevel = Minecraft.getMinecraft().world.getLight(new BlockPos(x, y, z));
											if(blightlevel >= 9){
												if(omod.isShowingFriendlySpawns()){
													//green: 0.0F,1.0F,0.0F,1.0F													
													tempblocks.add(new GroundMark(x,y,z,Minecraft.getMinecraft(),0.0F,1.0F,0.0F,1.0F));
												}												
											}else if(blightlevel <= 7){
												if(omod.isShowingAggressivSpawns()){
													//red: 1.0F,0.0F,0.0F,1.0F
													tempblocks.add(new GroundMark(x,y,z,Minecraft.getMinecraft(),1.0F,0.0F,0.0F,1.0F));		
												}																	
											}else{
												/*Not used atm maybe later for other settings*/
											}									
										}
									}									
								}								
							}							
						}						
					}					
				}
			}
			omod.blocks = tempblocks;		
		}		
	}
}
