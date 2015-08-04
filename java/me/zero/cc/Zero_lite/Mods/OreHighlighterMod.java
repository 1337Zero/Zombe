package me.zero.cc.Zero_lite.Mods;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;



import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Config.OreHighLighterModConfig;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseStringButton;
import me.zero.cc.Zero_lite.Gui.Buttons.SimpleSlider;
import me.zero.cc.Zero_lite.utils.GL;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import me.zero.cc.Zero_lite.utils.Mark;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockLadder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class OreHighlighterMod implements Mod {

	private LiteModMain speicher;
	private double lastpressed = 0;
	private double lastaktu = 0;
	
	private Minecraft minecraft;
	private OreHighLighterModConfig config;
	
	private int onkey = 27;
	private boolean enabled = false;
	
	private int infoID;
	private GuiPositions pos = GuiPositions.DOWN_RIGHT;
	
	private List<Mark> blocks = new ArrayList<Mark>();
	
	private boolean showinfo = false;
	
	private int lastPlayerChunkX = 0;
	private int lastPlayerChunkZ = 0;
	
	private int radius = 2;
	
	public OreHighlighterMod(Minecraft minecraft,LiteModMain speicher) {
		this.minecraft = minecraft;
		this.speicher = speicher;
		config = new OreHighLighterModConfig();
		lastpressed = System.currentTimeMillis();
		lastaktu = System.currentTimeMillis();
		
		onkey = Integer.parseInt(speicher.getConfig().getData("OreHighlighter.Toggle-OreHighlighter"));
		enabled = Boolean.valueOf(speicher.getConfig().getData("OreHighlighter.enabled"));
		
		pos = GuiPositions.valueOf(speicher.getConfig().getData("OreHighlighter.info-Pos"));
		infoID = this.speicher.getInfoLineManager().getInfoLine(pos).addInfo("");		

		showinfo = Boolean.valueOf(speicher.getConfig().getData("OreHighlighter.showinfo"));	
		
		radius = Integer.parseInt(speicher.getConfig().getData("OreHighlighter.radius"));
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void use() {
		
		if(Keyboard.isKeyDown(onkey) && (minecraft.currentScreen == null)){
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
	    
	    Tessellator worldRenderer = Tessellator.instance;
	    //WorldRenderer worldRenderer = tessellator.getWorldRenderer();
	    

	    
	    GL.glColor4f(block.getR(), block.getG(),block.getB(),block.getAlpha());
	    //AxisAlignedBB axis = minecraft.theWorld.getBlockState(new BlockPos(block.getX(),block.getY(), block.getZ())).getBlock().getSelectedBoundingBox(minecraft.theWorld, new BlockPos(block.getX(), block.getY(), block.getZ()));
	    AxisAlignedBB axis = minecraft.theWorld.getBlock((int)block.getX(),(int)block.getY(),(int)block.getZ()).getSelectedBoundingBoxFromPool(minecraft.theWorld, (int)block.getX(), (int)block.getY(), (int)block.getZ());
	    		
	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.minZ);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.maxZ);		
		worldRenderer.draw();

	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.maxZ);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.minZ);
		worldRenderer.draw();

	    worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.minY, axis.maxZ);
		worldRenderer.addVertex(axis.maxX, axis.maxY, axis.minZ);
		worldRenderer.draw();
	    
		worldRenderer.startDrawing(2);
		worldRenderer.addVertex(axis.minX, axis.maxY, axis.minZ);
		worldRenderer.addVertex(axis.maxX, axis.minY, axis.maxZ);		
		worldRenderer.draw();
	    	    
	}
	
	private void updateRenderPos() throws Exception{	
		blocks.clear();
				
		double posx = minecraft.thePlayer.posX - radius*10;
		double posy = minecraft.thePlayer.posY - radius*10;
		double posz = minecraft.thePlayer.posZ - radius*10;
						
		for(double x = posx; x < (posx + (radius *10)*2);x++){
			
			for(double y = posy; y < (posy + (radius *10)*2);y++){				
				for(double z = posz; z < (posz + (radius *10)*2);z++){
					//Block block = minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();	
					Block block = minecraft.theWorld.getBlock((int)x, (int)y, (int)z);
					if(isInConfig("" + Block.getIdFromBlock(block))){
						String color = config.getData("Color."+ Block.getIdFromBlock(block));
						if(color != null){	
							blocks.add(new Mark(Float.parseFloat(color.split(",")[0]) , Float.parseFloat(color.split(",")[1]), Float.parseFloat(color.split(",")[2]), Float.parseFloat(color.split(",")[3]), x, y, z));									
						}else{
							System.out.println("[Zombe-Lite] An Error was detected: " + Block.getIdFromBlock(block) + " was found in your config but no color was found");
						}
					}
				}
			}
		}
	}	

	private boolean isInConfig(String key){
		String[] founds = config.getData("Blocklist").split(",");
		for(int i = 0; i < founds.length;i++){
			if(founds[i].equals(key)){
				return true;
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
			onkey = (int)value;
			speicher.getConfig().replaceData("OreHighlighter.Toggle-OreHighlighter", onkey + "");
		}else if(ValueToManupulate.equalsIgnoreCase("Radius")){
			radius = (int)value/10;
			speicher.getConfig().replaceData("OreHighlighter.radius", radius + "");
		}else{
			System.out.println("Unknown value " + ValueToManupulate);
		}		
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		if(valueToManupulate.equalsIgnoreCase("togglehighlighter")){
			enabled = b;
			speicher.getConfig().replaceData("OreHighlighter.enabled", "" + b);
		}else if(valueToManupulate.equalsIgnoreCase("showinfo")){
			showinfo = b;
			speicher.getConfig().replaceData("OreHighlighter.showinfo", "" + b);
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
		return radius;
	}
	/**
	 * Sets the Radius of the search-area
	 * @param radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
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
		return onkey;
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
		GuiBooleanButton togglespeed = new GuiBooleanButton(2, width/2-170, height/4+20, 150, 20, "Toggle Highlighter", ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).isEnabled(), "togglehighlighter", ModData.OreHighLighter, speicher);
		SimpleSlider slider = new SimpleSlider(0, width/2, height/4-10, "Radius", (int) ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).getRadius() , 150, 20, ModData.OreHighLighter, "Radius", speicher,"Das ist ein Test;Das ist ein Test2".split(";"));
		
		chooseOn = new GuiChooseKeyButton(2, width/2, height/4+20, 150, 20, "Enable-Key", ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).getOn());
		GuiChooseStringButton choosepos = new GuiChooseStringButton(7, width/2-170, height/4+70, 150, 20, "Info-Pos", GuiPositions.getPosList(), "infopos", ModData.OreHighLighter, speicher, GuiPositions.getPos(((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).getPos()));
		GuiBooleanButton showInfo = new GuiBooleanButton(8, width/2, height/4+70, 150, 20, "Show-Info", ((OreHighlighterMod)speicher.getMod(ModData.OreHighLighter.name())).isShowInfo(), "showinfo", ModData.OreHighLighter, speicher);
			
		GuiButton back = new GuiButton(9, width/2-100,height-50 , "back to game");

		buttonList.add(slider);
		buttonList.add(showInfo);
		buttonList.add(chooseOn);
		buttonList.add(choosepos);
		buttonList.add(togglespeed);
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

