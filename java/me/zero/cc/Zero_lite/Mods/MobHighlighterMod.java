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

import me.zero.cc.Zero_lite.Config.MobHighLighterConfig;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseStringButton;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import me.zero.cc.Zero_lite.utils.MarkedEntity;
import me.zero.cc.Zero_lite.utils.Mobs;
import me.zero.cc.Zero_lite.utils.Speicher;
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
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import static org.lwjgl.opengl.GL11.*;

public class MobHighlighterMod implements Mod {

	private boolean enabled = false;
	private int onkey = 0;
	
	private Minecraft minecraft;
	private MobHighLighterConfig config;
	private List<MarkedEntity> entities = new ArrayList<MarkedEntity>();
	private Speicher speicher;
	private double lastpressed = 0;
	private double lastaktu = 0;
	
	private boolean showinfo = false;
	private GuiPositions pos = GuiPositions.DOWN_LEFT;
	private int infoID = 0;
	
	public MobHighlighterMod(Minecraft minecraft,Speicher speicher) {
		this.minecraft = minecraft;
		this.speicher = speicher;
		config = new MobHighLighterConfig();
		lastpressed = System.currentTimeMillis();
		lastaktu = System.currentTimeMillis();
		
		onkey = Integer.parseInt(speicher.getConfig().getData("MobHighlighter.Toggle-Mobhighlighter"));
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
		
		if(Keyboard.isKeyDown(onkey) && (minecraft.currentScreen == null)){
			if((System.currentTimeMillis() - lastpressed) >=100){
				if(enabled){
					enabled = false;
					speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);	
				}else{
					enabled = true;
				}	
			}	
			lastpressed = System.currentTimeMillis();
		}
		try {
			if(enabled){
				if((System.currentTimeMillis() - lastaktu) >= 5000){
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
						entities.add(new MarkedEntity(mobs.get(i),Float.parseFloat(color.split(",")[0]) , Float.parseFloat(color.split(",")[1]), Float.parseFloat(color.split(",")[2]), Float.parseFloat(color.split(",")[3])));	
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

	public void render(float partialTicks){
		if(enabled){
			EntityPlayerSP player = minecraft.thePlayer;
			double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
			double y = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
			double z = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;	
			
			setUpRenderer(x,y,z);	
			for(int i = 0; i < entities.size();i++){
				renderMob(entities.get(i).getEnt(), entities.get(i).getR(),entities.get(i).getG(),entities.get(i).getB(),entities.get(i).getAlpha());
			}
		    normalizeRenderer();
		}
	}
	
	public void renderMob(Entity ent,float r,float g,float b,float alpha){
		    
	    Tessellator tessellator = Tessellator.getInstance();
	    WorldRenderer worldRenderer = tessellator.getWorldRenderer();
	    GL.glColor4f(r, g,b,alpha);
		worldRenderer.startDrawing(2);
	    
	    worldRenderer.addVertex( ent.posX, ent.posY,  ent.posZ);
	    worldRenderer.addVertex( ent.posX, ent.posY + ent.getEyeHeight(),  ent.posZ);

	    tessellator.draw();
	}
	public int getOn(){
		return onkey;
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
	public GuiPositions getPos(){
		return pos;
	}
	public boolean isShowInfo(){
		return showinfo;
	}
	@Override
	public void manupulateValue(String ValueToManupulate, int value) {
		if(ValueToManupulate.equalsIgnoreCase("Enable-Key")){
			onkey = value;
			speicher.getConfig().replaceData("MobHighlighter.Toggle-Mobhighlighter", onkey + "");
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
	
	private Speicher speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	private GuiChooseKeyButton chooseOn;
	
	public MobHighLighterGui(Speicher speicher){
		this.speicher = speicher;
	}
	
	public void initGui(){
		drawButtons();
	}
	
	public void actionPerformed(GuiButton b){	
		if(b.displayString.contains("back to game")){
			speicher.getMinecraft().displayGuiScreen(null);
			speicher.getZm().setShown(false);
		}else if(b.displayString.contains("waiting")){
			valueToManupulate = b.displayString.split("waiting")[0];
			GivingKey = true;
		}
	}
	
	public void drawButtons(){
		GuiBooleanButton togglespeed = new GuiBooleanButton(2, width/2-170, height/4+20, 150, 20, "Toggle Highlighter", ((MobHighlighterMod)speicher.getMod(ModData.MobHighLighter.name())).isEnabled(), "togglehighlighter", ModData.MobHighLighter, speicher);
		
		chooseOn = new GuiChooseKeyButton(2, width/2, height/4+20, 150, 20, "Enable-Key", ((MobHighlighterMod)speicher.getMod(ModData.MobHighLighter.name())).getOn());
		GuiChooseStringButton choosepos = new GuiChooseStringButton(7, width/2-170, height/4+70, 150, 20, "Info-Pos", GuiPositions.getPosList(), "infopos", ModData.MobHighLighter, speicher, GuiPositions.getPos(((MobHighlighterMod)speicher.getMod(ModData.MobHighLighter.name())).getPos()));
		GuiBooleanButton showInfo = new GuiBooleanButton(8, width/2, height/4+70, 150, 20, "Show-Info", ((MobHighlighterMod)speicher.getMod(ModData.MobHighLighter.name())).isShowInfo(), "showinfo", ModData.MobHighLighter, speicher);
			
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
				speicher.getZm().setShown(false);
			}
		}		
	}
}
