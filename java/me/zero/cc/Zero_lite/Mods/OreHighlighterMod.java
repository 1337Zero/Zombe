package me.zero.cc.Zero_lite.Mods;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mumfrey.liteloader.gl.GL;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Config.OreHighLighterModConfig;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseStringButton;
import me.zero.cc.Zero_lite.Gui.Buttons.SimpleSlider;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import me.zero.cc.Zero_lite.utils.KeySetting;
import me.zero.cc.Zero_lite.utils.Mark;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockLadder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EnumFaceing;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class OreHighlighterMod implements Mod {

	private LiteModMain speicher;
	private double lastpressed = 0;
	private double lastaktu = 0;
	
	private Minecraft minecraft;
	private OreHighLighterModConfig config;
	
	private KeySetting onkey = new KeySetting("OreHighlighter.Toggle-OreHighlighter");
	private boolean enabled = false;
	
	private int infoID;
	private GuiPositions pos = GuiPositions.DOWN_RIGHT;
	
	protected List<Mark> blocks = new ArrayList<Mark>();
	
	private boolean showinfo = false;
	
	private int lastPlayerChunkX = 0;
	private int lastPlayerChunkZ = 0;
	
	private OreSearchThread orsearh;
	
	private KeySetting radius = new KeySetting(2,"OreHighlighter.radius");
	
	public OreHighlighterMod(Minecraft minecraft,LiteModMain speicher) {
		this.minecraft = minecraft;
		this.speicher = speicher;
		config = new OreHighLighterModConfig();
		lastpressed = System.currentTimeMillis();
		lastaktu = System.currentTimeMillis();
		
		//onkey = Integer.parseInt(speicher.getConfig().getData("OreHighlighter.Toggle-OreHighlighter"));
		enabled = Boolean.valueOf(speicher.getConfig().getData("OreHighlighter.enabled"));
		
		pos = GuiPositions.valueOf(speicher.getConfig().getData("OreHighlighter.info-Pos"));
		infoID = this.speicher.getInfoLineManager().getInfoLine(pos).addInfo("");		

		showinfo = Boolean.valueOf(speicher.getConfig().getData("OreHighlighter.showinfo"));	
		
		//radius = Integer.parseInt(speicher.getConfig().getData("OreHighlighter.radius"));
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void use() {
		
		if(onkey.isKeyDown() && (minecraft.currentScreen == null)){
			if((System.currentTimeMillis() - lastpressed) >=100){
				if(enabled){
					enabled = false;
					showinfo = false;
					speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
				}else{
					showinfo = true;
					enabled = true;
				}	
				speicher.getConfig().replaceData("OreHighlighter.enabled", "" + enabled);
			}	
			lastpressed = System.currentTimeMillis();
		}
		try {
			if(enabled){
				if((System.currentTimeMillis() - lastaktu) >= 2500){
					updateRenderPos();						
					lastaktu = System.currentTimeMillis();
				}
			}
			if(showinfo){
				speicher.getInfoLineManager().getInfoLine(pos).setInfo(infoID, blocks.size() + " Block/s");
			}else{
				speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);	
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Called by every render tick... has to be registered in LiteModMain
	 * @param float
	 */
	public void render(float partialTicks){
		if(blocks.size() > 0 && enabled){			
			EntityPlayerSP player = minecraft.thePlayer;
			double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
			double y = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
			double z = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
			
			setUpRenderer(x, y, z);
			for(int i = 0; i < blocks.size();i++){
				renderBlock(blocks.get(i));
			}
			normalizeRenderer();
		}
	}
	
	private void setUpRenderer(double x,double y,double z){
		GL.glPushMatrix();
	    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
	    GL.glEnableBlend();
	    GL11.glEnable(2848);
	    GL.glBlendFunc(770, 771);
	    GL.glDisableTexture2D();
	    GL.glDisableLighting();
	    GL.glDisableDepthTest();

	    GL.glDepthFunc(515);
	    GL11.glLineWidth(1.0F);
	    GL.glTranslated(-x, -y, -z);
	}	
	private void normalizeRenderer(){
	    GL.glEnableTexture2D();
	    GL.glDisableBlend();
	    GL.glPopMatrix();
	    GL.glEnableDepthTest();
	}
	/**
	 * Marks a Block with lines
	 * @param MarkedBlock
	 */
	public void renderBlock(Mark block){
	    
	    Tessellator tessellator = Tessellator.getInstance();
	    WorldRenderer worldRenderer = tessellator.getWorldRenderer();
	    

	    
	    GL.glColor4f(block.getR(), block.getG(),block.getB(),block.getAlpha());
	    AxisAlignedBB axis = minecraft.theWorld.getBlockState(new BlockPos(block.getX(),block.getY(), block.getZ())).getBlock().getSelectedBoundingBox(minecraft.theWorld, new BlockPos(block.getX(), block.getY(), block.getZ()));

	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.minZ);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.maxZ);		
	    tessellator.draw();

	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.maxZ);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.minZ);
	    tessellator.draw();

	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.maxZ);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.minZ);
	    tessellator.draw();
	    
		worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.minZ);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.maxZ);		
	    tessellator.draw();
	    	    
	}
	
	private void updateRenderPos(){	
		
		if(orsearh == null){
			orsearh = new OreSearchThread(this,config);
			orsearh.start();			
		}
	}	

	protected boolean isInConfig(String key){
		String[] founds = config.getData("Blocklist").split(",");
		for(int i = 0; i < founds.length;i++){			
			if(founds[i].contains("-")){
				if(founds[i].split("-")[0].equals(key.split(":")[0])){
					if(founds[i].split("-")[1].equals(key.split(":")[1])){
						return true;
					}	
				}
			}else{
				if(founds[i].equals(key.split(":")[0])){
					return true;
				}
			}			
		}
		return false;
	}
	
	@Override
	public String getName() {
		return ModData.OreHighLighter.name();
	}

	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public GuiScreen drawGui() {
		return new OreHighLighterGui(speicher);
	}

	@Override
	public void manupulateValue(String ValueToManupulate, double value) {
		if(ValueToManupulate.equalsIgnoreCase("Enable-Key")){
			onkey.setKey((int)value);
		}else if(ValueToManupulate.equalsIgnoreCase("Radius")){
			radius.setKey((int)value/10);
		}else{
			System.out.println("Unknown value " + ValueToManupulate);
		}		
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		if(valueToManupulate.equalsIgnoreCase("togglehighlighter")){
			enabled = b;
			speicher.getConfig().replaceData("OreHighlighter.enabled", "" + b);
			if(orsearh != null){
				orsearh.enabled = false;
				orsearh = null;
			}
			blocks.clear();
		}else if(valueToManupulate.equalsIgnoreCase("showinfo")){
			showinfo = b;
			speicher.getConfig().replaceData("OreHighlighter.showinfo", "" + b);
		}else if(valueToManupulate.equalsIgnoreCase("dselection")){
			speicher.getConfig().replaceData("OreHighlighter.selectivesearch", "" + b);	
			if(orsearh != null){
				orsearh.selectivesearch = b;
			}
		}else{
			System.out.println("Unknown value1 " + valueToManupulate);
		}			
	}

	@Override
	public void manupulateValue(String valueToManupulate, String value) {
		if(valueToManupulate.equalsIgnoreCase("infopos")){
			speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
			pos = GuiPositions.valueOf(value);
			infoID = speicher.getInfoLineManager().getInfoLine(pos).addInfo("");
			speicher.getConfig().replaceData("OreHighlighter.info-Pos", pos.toString());
		}else{
			System.out.println("Fehler: " + valueToManupulate + " is not a known Value in " + this.getName());
		}
	}
	/**
	 * Returns the Radius of the search-area
	 * @return Integer
	 */
	public int getRadius() {
		return radius.getKey();
	}
	/**
	 * Sets the Radius of the search-area
	 * @param radius
	 */
	public void setRadius(int radius) {
		this.radius.setKey(radius);
	}
	/**
	 * Get if the info is shown
	 * @return Boolean
	 */
	public boolean isShowInfo() {
		return showinfo;
	}
	/**
	 * Get the Position of the info
	 * @return GuiPositions
	 */
	public GuiPositions getPos() {
		return pos;
	}
	/**
	 * Get the key to enable
	 * @return Integer
	 */
	public int getOn() {
		return onkey.getKey();
	}
	public boolean isSelectiveSearch(){
		if(orsearh != null){
			return orsearh.selectivesearch;
		}else{
			return false;
		}
	}
}
class OreHighLighterGui extends GuiScreen{
	
	private LiteModMain speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	private GuiChooseKeyButton chooseOn;
	
	public OreHighLighterGui(LiteModMain speicher){
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
		GuiBooleanButton togglehighlighter = new GuiBooleanButton(2, width/2-170, height/4-10, 150, 20, "Toggle Highlighter", ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).isEnabled(), "togglehighlighter", ModData.OreHighLighter, speicher,LiteModMain.lconfig.getData("OreHighLighter.toggle").split(";"));
		SimpleSlider slider = new SimpleSlider(0, width/2, height/4-10, "Radius", (int) ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).getRadius() , 150, 20, ModData.OreHighLighter, "Radius", speicher,LiteModMain.lconfig.getData("OreHighLighter.radius").split(";"));
		
		chooseOn = new GuiChooseKeyButton(2, width/2, height/4+20, 150, 20, "Enable-Key", ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).getOn(),LiteModMain.lconfig.getData("OreHighLighter.chooseonkey").split(";"));
		GuiChooseStringButton choosepos = new GuiChooseStringButton(7, width/2-170, height/4+70, 150, 20, "Info-Pos", GuiPositions.getPosList(), "infopos", ModData.OreHighLighter, speicher, GuiPositions.getPos(((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).getPos()),LiteModMain.lconfig.getData("Main.choosepos").split(";"));
		GuiBooleanButton showInfo = new GuiBooleanButton(8, width/2, height/4+70, 150, 20, "Show-Info", ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).isShowInfo(), "showinfo", ModData.OreHighLighter, speicher,LiteModMain.lconfig.getData("OreHighLighter.showinfo").split(";"));
		GuiBooleanButton useselective = new GuiBooleanButton(9, width/2-170, height/4+20, 150, 20, "dynamic selection", ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).isSelectiveSearch(), "dselection", ModData.OreHighLighter, speicher,LiteModMain.lconfig.getData("OreHighLighter.dynamicselection").split(";"));
			
		GuiButton back = new GuiButton(buttonList.size() + 1, width/2-100,height-50 , "back to game");

		buttonList.add(slider);
		buttonList.add(showInfo);
		buttonList.add(chooseOn);
		buttonList.add(choosepos);
		buttonList.add(togglehighlighter);
		buttonList.add(useselective);
		buttonList.add(back);
	}
	protected void keyTyped(char c,int key){
		if(GivingKey){
			if(key != 65 && key != 1){
				//speicher.getMinecraft().thePlayer.playSound("mob.ghast.scream", 1.0F, 1.0F);	
				valueToManupulate = valueToManupulate.replace(" ", "");
				((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).manupulateValue(valueToManupulate, key);
				
				if(valueToManupulate.equalsIgnoreCase("Enable-Key")){
					chooseOn.setButtonkey(key);
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
class OreSearchThread extends Thread{

	private OreHighlighterMod omod;
	private OreHighLighterModConfig config;
	public boolean selectivesearch;
	public boolean enabled = true;
	
	public OreSearchThread(OreHighlighterMod omod,OreHighLighterModConfig config){
		this.omod = omod;
		this.config = config;
		selectivesearch = Boolean.valueOf(LiteModMain.config.getData("OreHighlighter.selectivesearch"));
	}
	
	@Override
	public void run() {

		while(enabled){
			updateRenderPos();
			try {
				this.sleep(Long.valueOf(LiteModMain.config.getData("OreHighlighter.threadsleep")));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	private void updateRenderPos(){	
		
		ArrayList<Mark> tempblocks = new ArrayList<Mark>();
			
		double posx = Minecraft.getMinecraft().thePlayer.posX - omod.getRadius()*10;
		double posy = Minecraft.getMinecraft().thePlayer.posY - omod.getRadius()*10;
		double posz = Minecraft.getMinecraft().thePlayer.posZ - omod.getRadius()*10;
		
		if(selectivesearch){
			String facing = EnumFacing.fromAngle(Minecraft.getMinecraft().thePlayer.rotationYawHead).getName().toUpperCase();
			
			if(facing.equalsIgnoreCase("north")){
				//updaten -
				posz = (Minecraft.getMinecraft().thePlayer.posZ +1) - (omod.getRadius()*10)*2;
				posx = Minecraft.getMinecraft().thePlayer.posX - omod.getRadius()*10;
			}else if(facing.equalsIgnoreCase("south")){
				posz = Minecraft.getMinecraft().thePlayer.posZ;
			}else if(facing.equalsIgnoreCase("west")){
				//updaten -
				posx = (Minecraft.getMinecraft().thePlayer.posX +1) - (omod.getRadius()*10)*2;
				posz = Minecraft.getMinecraft().thePlayer.posZ - omod.getRadius()*10;
			}else if(facing.equalsIgnoreCase("east")){
				posx = Minecraft.getMinecraft().thePlayer.posX;
			}
			
		}
		for(double x = posx; x < (posx + (omod.getRadius() *10)*2);x++){			
			for(double y = posy; y < (posy + (omod.getRadius() *10)*2);y++){				
				for(double z = posz; z < (posz + (omod.getRadius() *10)*2);z++){
					Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();	
					if(Block.getIdFromBlock(block) != 0){
						if(omod.isInConfig("" + Block.getIdFromBlock(block) + ":" + Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y,z)).getBlock().getDamageValue(Minecraft.getMinecraft().theWorld, new BlockPos(x, y,z)))){
							String color = config.getData("Color."+ Block.getIdFromBlock(block));
							if(color == null){ 
								color = config.getData("Color."+ Block.getIdFromBlock(block) + "-" + Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y,z)).getBlock().getDamageValue(Minecraft.getMinecraft().theWorld, new BlockPos(x, y,z)));
								if(color != null){ 
									tempblocks.add(new Mark(Float.parseFloat(color.split(",")[0]) , Float.parseFloat(color.split(",")[1]), Float.parseFloat(color.split(",")[2]), Float.parseFloat(color.split(",")[3]), x, y, z));									
								}else{
									System.out.println("[Zombe-Lite] An Error was detected: " + Block.getIdFromBlock(block) + " was found in your config but no color was found");
								}
							}else{
								tempblocks.add(new Mark(Float.parseFloat(color.split(",")[0]) , Float.parseFloat(color.split(",")[1]), Float.parseFloat(color.split(",")[2]), Float.parseFloat(color.split(",")[3]), x, y, z));									
							}
						}
					}
				}					
			}
		}		
		omod.blocks = tempblocks;
	}	
}

