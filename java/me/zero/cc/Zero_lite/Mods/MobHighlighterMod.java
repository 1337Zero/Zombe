package me.zero.cc.Zero_lite.Mods;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.SheetCollate;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.gl.GL;
import com.mumfrey.liteloader.modconfig.Exposable;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Config.MobHighLighterConfig;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseStringButton;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import me.zero.cc.Zero_lite.utils.KeySetting;
import me.zero.cc.Zero_lite.utils.Mark;
import me.zero.cc.Zero_lite.utils.Mobs;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Timer;
import net.minecraft.world.World;
import static org.lwjgl.opengl.GL11.*;

public class MobHighlighterMod implements Mod {

	private boolean enabled = false;
	private KeySetting onkey = new KeySetting("MobHighlighter.Toggle-Mobhighlighter");
	
	private Minecraft minecraft;
	private MobHighLighterConfig config;
	private List<Mark> entities = new ArrayList<Mark>();
	
	private LiteModMain speicher;
	private double lastpressed = 0;
	private double lastaktu = 0;
	
	private boolean showinfo = false;
	private GuiPositions pos = GuiPositions.DOWN_LEFT;
	private int infoID = 0;
	
	public MobHighlighterMod(Minecraft minecraft,LiteModMain speicher) {
		this.minecraft = minecraft;
		this.speicher = speicher;
		config = new MobHighLighterConfig();
		lastpressed = System.currentTimeMillis();
		lastaktu = System.currentTimeMillis();
		
		//onkey = Integer.parseInt(speicher.getConfig().getData("MobHighlighter.Toggle-Mobhighlighter"));
		enabled = Boolean.valueOf(speicher.getConfig().getData("MobHighlighter.enabled"));
		
		pos = GuiPositions.valueOf(speicher.getConfig().getData("MobHighlighter.info-Pos"));
		infoID = this.speicher.getInfoLineManager().getInfoLine(pos).addInfo("");
		

		showinfo = Boolean.valueOf(speicher.getConfig().getData("MobHighlighter.showinfo"));	
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void use() {
		
		if(onkey.isKeyDown() && (minecraft.currentScreen == null)){
			if((System.currentTimeMillis() - lastpressed) >= 100){
				if(enabled){
					enabled = false;
					speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);	
				}else{
					enabled = true;
				}	
				speicher.getConfig().replaceData("MobHighlighter.enabled", "" + enabled);
			}	
			lastpressed = System.currentTimeMillis();
		}
		try {
			if(enabled){
				if((System.currentTimeMillis() - lastaktu) >= 500){
					updateRenderPos();						
					lastaktu = System.currentTimeMillis();
				}
			}
			if(showinfo){
				speicher.getInfoLineManager().getInfoLine(pos).setInfo(infoID, entities.size() + " Mob/s");
			}else{
				speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);	
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateRenderPos() throws Exception{			
		List<Entity> mobs = minecraft.theWorld.loadedEntityList;
		entities.clear();
		
		for(int i = 0; i < mobs.size();i++){
			String mob = Mobs.getClassNameFromString(mobs.get(i).getClass().getSimpleName());
			if(mob != null && isInConfig(mob)){		
				String color = config.getData("Color."+ mob);
				if(color != null){
					if(!mobs.get(i).equals(minecraft.thePlayer)){
						entities.add(new Mark(Float.parseFloat(color.split(",")[0]) , Float.parseFloat(color.split(",")[1]), Float.parseFloat(color.split(",")[2]), Float.parseFloat(color.split(",")[3]), mobs.get(i).posX, mobs.get(i).posY, mobs.get(i).posZ,mobs.get(i).getEyeHeight(), mobs.get(i)));	
					}
				}else{
					System.out.println("[Zombe-Lite] An Error was detected: " + mobs.get(i).getClass().getSimpleName() + " was found in your config but no color was found");
				}
			}else{
				if(LiteLoader.isDevelopmentEnvironment()){
					System.out.println(mobs.get(i).toString());
					System.out.println(mobs.get(i).getClass().getSimpleName());
				}
			}
		}
	}
	private boolean isInConfig(String key){
		String[] founds = config.getData("Moblist").split(",");
		//String found = Mobs.getClassNameFromString(key);
		for(int i = 0; i < founds.length;i++){
			if(founds[i].equalsIgnoreCase(key)){
				return true;
			}
		}
		return false;
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
	 * Called by every render tick... has to be registered in LiteModMain
	 * @param float
	 */
	public void render(float partialTicks){
		if(enabled){
			EntityPlayerSP player = minecraft.thePlayer;
			double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
			double y = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
			double z = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;	
			
			setUpRenderer(x,y,z);	
			for(int i = 0; i < entities.size();i++){
				renderMob(entities.get(i));
			}
		    normalizeRenderer();
		}
	}
	/**
	 * Markes a Mob with a line  {args: Entity ent,float Red,float Green,float Blue,float alpha}
	 * @param  ent
	 * @param  r
	 * @param  g
	 * @param  b
	 * @param  alpha
	 */
	public void renderMob(Mark m){
		    //float r,float g,float b,float alpha,double x,double y,double z,double maxy
	    Tessellator tessellator = Tessellator.getInstance();
	    VertexBuffer worldRenderer = tessellator.getBuffer();
	    GL.glColor4f(m.getR(), m.getG(),m.getB(),m.getAlpha());
		worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
	    worldRenderer.pos( m.getEntity().posX, m.getEntity().posY,  m.getEntity().posZ);
		worldRenderer.pos( m.getEntity().posX, m.getEntity().posY + m.getEntity().getEyeHeight(), m.getEntity().posZ);

	    tessellator.draw();
	}
	/**
	 * Get the key to enable
	 * @return Integer
	 */
	public int getOn(){
		return onkey.getKey();
	}
	
	@Override
	public String getName() {
		return ModData.MobHighLighter.name();
	}

	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public GuiScreen drawGui() {
		return new MobHighLighterGui(speicher);
	}
	/**
	 * Return the Position of the Info
	 * @return GuiPositions
	 */
	public GuiPositions getPos(){
		return pos;
	}
	/**
	 * Returns if the info is shown
	 * @return Boolean
	 */
	public boolean isShowInfo(){
		return showinfo;
	}
	@Override
	public void manupulateValue(String ValueToManupulate, double value) {
		if(ValueToManupulate.equalsIgnoreCase("Enable-Key")){
			onkey.setKey((int)value);
		}else{
			System.out.println("Unknown value " + ValueToManupulate);
		}		
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		if(valueToManupulate.equalsIgnoreCase("togglehighlighter")){
			enabled = b;
			speicher.getConfig().replaceData("MobHighlighter.enabled", "" + b);
		}else if(valueToManupulate.equalsIgnoreCase("showinfo")){
			showinfo = b;
			speicher.getConfig().replaceData("MobHighlighter.showinfo", "" + b);
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
			speicher.getConfig().replaceData("MobHighlighter.info-Pos", pos.toString());
		}else{
			System.out.println("Fehler: " + valueToManupulate + " is not a known Value in " + this.getName());
		}
	}
}
class MobHighLighterGui extends GuiScreen{
	
	private LiteModMain speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	private GuiChooseKeyButton chooseOn;
	
	public MobHighLighterGui(LiteModMain speicher){
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
		GuiBooleanButton togglespeed = new GuiBooleanButton(2, width/2-170, height/4+20, 150, 20, "Toggle Highlighter", ((MobHighlighterMod)speicher.getMod(ModData.MobHighLighter.name())).isEnabled(), "togglehighlighter", ModData.MobHighLighter, speicher,LiteModMain.lconfig.getData("MobHighLighter.toggle").split(";"));
		
		chooseOn = new GuiChooseKeyButton(2, width/2, height/4+20, 150, 20, "Enable-Key", ((MobHighlighterMod)speicher.getMod(ModData.MobHighLighter.name())).getOn(),LiteModMain.lconfig.getData("MobHighLighter.chooseonkey").split(";"));
		GuiChooseStringButton choosepos = new GuiChooseStringButton(7, width/2-170, height/4+70, 150, 20, "Info-Pos", GuiPositions.getPosList(), "infopos", ModData.MobHighLighter, speicher, GuiPositions.getPos(((MobHighlighterMod)speicher.getMod(ModData.MobHighLighter.name())).getPos()),LiteModMain.lconfig.getData("Main.choosepos").split(";"));
		GuiBooleanButton showInfo = new GuiBooleanButton(8, width/2, height/4+70, 150, 20, "Show-Info", ((MobHighlighterMod)speicher.getMod(ModData.MobHighLighter.name())).isShowInfo(), "showinfo", ModData.MobHighLighter, speicher,LiteModMain.lconfig.getData("MobHighLighter.showinfo").split(";"));
			
		GuiButton back = new GuiButton(9, width/2-100,height-50 , "back to game");

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
				((MobHighlighterMod)speicher.getMod(ModData.MobHighLighter.name())).manupulateValue(valueToManupulate, key);
				
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
