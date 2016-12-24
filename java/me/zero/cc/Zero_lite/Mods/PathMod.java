package me.zero.cc.Zero_lite.Mods;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mumfrey.liteloader.gl.GL;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseStringButton;
import me.zero.cc.Zero_lite.Gui.Buttons.SimpleSlider;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import me.zero.cc.Zero_lite.utils.KeySetting;
import me.zero.cc.Zero_lite.utils.Mark;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class PathMod implements Mod{

	private boolean enabled = false;
	private ArrayList<Mark> marks = new ArrayList<Mark>();
	private int maxmarks = 501;
	private double lastmarked = 0;
	private double lastpremarked = 0;
	private Minecraft minecraft;
	private double lastx,lasty,lastz;
	private GuiPositions pos = GuiPositions.DOWN_CENTER;
	private KeySetting onkey = new KeySetting(11,"PathMod.Toggle-PathMod");
	private int infoID = 0;
	private boolean showinfo;
	private LiteModMain speicher;
	private double lastpressed = 0;
	private boolean isMarkFarAway = true;
	private boolean seethroughwall = true;
	private float r,g,b,alpha;
	
	public PathMod(Minecraft minecraft,LiteModMain speicher) {
		lastmarked = System.currentTimeMillis();
		lastpressed = System.currentTimeMillis();
		this.minecraft = minecraft;
		this.speicher = speicher;
		
		//onkey = Integer.parseInt(speicher.getConfig().getData("PathMod.Toggle-PathMod"));
		enabled = Boolean.valueOf(speicher.getConfig().getData("PathMod.enabled"));
		
		pos = GuiPositions.valueOf(speicher.getConfig().getData("PathMod.info-Pos"));
		infoID = this.speicher.getInfoLineManager().getInfoLine(pos).addInfo("");		

		showinfo = Boolean.valueOf(speicher.getConfig().getData("PathMod.showinfo"));	
		
		seethroughwall = Boolean.valueOf(speicher.getConfig().getData("PathMod.seethroughwall"));	
		
		r = Float.valueOf(speicher.getConfig().getData("PathMod.mark.r"));
		g = Float.valueOf(speicher.getConfig().getData("PathMod.mark.g"));
		b = Float.valueOf(speicher.getConfig().getData("PathMod.mark.b"));
		alpha = Float.valueOf(speicher.getConfig().getData("PathMod.mark.alpha"));
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
					speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);				
				}else{
					enabled = true;
				}	
			}	
			lastpressed = System.currentTimeMillis();
		}
		
		if(enabled){
			if(lastx == 0){ lastx = minecraft.thePlayer.posX;}
			if(lasty == 0){ lasty = minecraft.thePlayer.posY;}
			if(lastz == 0){ lastz = minecraft.thePlayer.posZ;}
			

			if(showinfo){
				if(marks.size()-2 < 0){
					speicher.getInfoLineManager().getInfoLine(pos).setInfo(infoID, (0) + " Mark/s");
				}else{
				speicher.getInfoLineManager().getInfoLine(pos).setInfo(infoID, (marks.size()-1) + " Mark/s");
				}
			}else{
				speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);	
			}

			if((System.currentTimeMillis() - lastmarked) >=5000){
				lastmarked = System.currentTimeMillis();
				if(isMarkFarAway | marks.size() == 0){
					addMark();
				}else{
					System.out.println("Don't add a mark cause you are to near");
				}
			}
			
			if((System.currentTimeMillis() - lastpremarked) >=2500){
				lastpremarked = System.currentTimeMillis();
				//Only mark is Mark is 'far away'
				isMarkFarAway = false;
				if(marks.size() >= 1){
					Mark lastmark = marks.get(marks.size()-1);
					if(isFarAway(lastmark.getX(), lastmark.getY(), lastmark.getZ(), lastx, lasty, lastz)){
						isMarkFarAway = true;
					}
				}				
				
				lastx = minecraft.thePlayer.posX;
				lasty = minecraft.thePlayer.posY;
				lastz = minecraft.thePlayer.posZ;
			}
		}				
	}
	
	private boolean isFarAway(double x,double y,double z,double x1,double y1,double z1){
		return (x*y*z)-(x1*y1*z1) > 10 || (x*y*z)-(x1*y1*z1) < -10;
	}
	
	private void addMark(){
		if(marks.size() < maxmarks){
			marks.add(new Mark(r, g, b, alpha, minecraft.thePlayer.posX, minecraft.thePlayer.posY, minecraft.thePlayer.posZ,lastx,lasty,lastz));
			System.out.println("adding marks...");
		}else{
			marks.remove(1);	
			marks.add(new Mark(r, g, b, alpha, minecraft.thePlayer.posX, minecraft.thePlayer.posY, minecraft.thePlayer.posZ,lastx,lasty,lastz));
		}
	}
	public void render(float partialTicks){
		if(enabled){
			EntityPlayerSP player = minecraft.thePlayer;
			double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
			double y = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
			double z = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;	
			
			setUpRenderer(x,y,z);	
			for(Mark m : marks){
				renderMark(m);
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
	    if(seethroughwall){
		    GL.glDisableDepthTest();
	    }
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
	
	public void renderMark(Mark m){
	    
	    Tessellator tessellator = Tessellator.getInstance();
	    VertexBuffer worldRenderer = tessellator.getBuffer();
	    GL.glColor4f(m.getR(), m.getG(),m.getB(),m.getAlpha());
	    worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
	    
	    worldRenderer.pos( m.getX(), m.getY(),  m.getZ());
	    worldRenderer.pos( m.getLastx(), m.getLasty(), m.getLastz());

	    tessellator.draw();
	}
	
	@Override
	public String getName() {
		return ModData.PathMod.name();
	}

	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public GuiScreen drawGui() {
		return new PathModGui(speicher);
	}

	@Override
	public void manupulateValue(String ValueToManupulate, double value) {
		if(ValueToManupulate.equalsIgnoreCase("Enable-Key")){
			onkey.setKey((int)value);
			//speicher.getConfig().replaceData("PathMod.Toggle-PathMod", onkey + "");
		}else{
			System.out.println("Unknown value " + ValueToManupulate);
		}		
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		if(valueToManupulate.equalsIgnoreCase("togglehighlighter")){
			enabled = b;
			speicher.getConfig().replaceData("PathMod.enabled", "" + b);
		}else if(valueToManupulate.equalsIgnoreCase("showinfo")){
			showinfo = b;
			speicher.getConfig().replaceData("PathMod.showinfo", "" + b);
		}else if(valueToManupulate.equalsIgnoreCase("seethroughwall")){
			seethroughwall = b;
			speicher.getConfig().replaceData("PathMod.seethroughwall", "" + b);
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
			speicher.getConfig().replaceData("PathMod.info-Pos", pos.toString());
		}else{
			System.out.println("Fehler: " + valueToManupulate + " is not a known Value in " + this.getName());
		}
	}

	@Override
	public int getOn() {
		return onkey.getKey();
	}
	public boolean isShowInfo(){
		return showinfo;
	}
	public GuiPositions getPos(){
		return pos;
	}

	public boolean isSeethroughwall() {
		return seethroughwall;
	}

	public void setSeethroughwall(boolean seethroughwall) {
		this.seethroughwall = seethroughwall;
	}
	public void clearMarks(){
		marks.clear();
	}

}
class PathModGui extends GuiScreen{
	
	private LiteModMain speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	private GuiChooseKeyButton chooseOn;
	
	public PathModGui(LiteModMain speicher){
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
		}else if(b.displayString.contains("clear Marks")){
			((PathMod)speicher.getMod(ModData.PathMod.name())).clearMarks();
		}
	}
	/**
	 * Initialize Buttons and add them to the Button list
	 */
	public void drawButtons(){
		GuiBooleanButton togglespeed = new GuiBooleanButton(1, width/2-170, height/4, 150, 20, "Toggle Highlighter", ((PathMod)speicher.getMod(ModData.PathMod.name())).isEnabled(), "togglehighlighter", ModData.PathMod, speicher,LiteModMain.lconfig.getData("PathMod.toggle").split(";"));

		chooseOn = new GuiChooseKeyButton(2, width/2, height/4, 150, 20, "Enable-Key", ((PathMod)speicher.getMod(ModData.PathMod.name())).getOn(),LiteModMain.lconfig.getData("PathMod.enablekey").split(";"));
		GuiChooseStringButton choosepos = new GuiChooseStringButton(3, width/2-170, height/4+40, 150, 20, "Info-Pos", GuiPositions.getPosList(), "infopos", ModData.PathMod, speicher, GuiPositions.getPos(((PathMod)speicher.getMod(ModData.PathMod.name())).getPos()),LiteModMain.lconfig.getData("Main.choosepos").split(";"));
		GuiBooleanButton showInfo = new GuiBooleanButton(4, width/2, height/4+40, 150, 20, "Show-Info", ((PathMod)speicher.getMod(ModData.PathMod.name())).isShowInfo(), "showinfo", ModData.PathMod, speicher,LiteModMain.lconfig.getData("PathMod.showinfo").split(";"));
		
		GuiBooleanButton seeThroughwall = new GuiBooleanButton(5, width/2, height/4+80, 150, 20, "See Thorugh Walls", ((PathMod)speicher.getMod(ModData.PathMod.name())).isSeethroughwall(), "seethroughwall", ModData.PathMod, speicher,LiteModMain.lconfig.getData("PathMod.seethroughwall").split(";"));
			
		GuiButton back = new GuiButton(6, width/2-100,height-50 , "back to game");

		GuiButton clear = new GuiButton(7, width/2-170,height/4+80 , "clear Marks");	
		clear.setWidth(150);
		
		buttonList.add(clear);
		buttonList.add(seeThroughwall);
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
				System.out.println(key + " " + c);
				valueToManupulate = valueToManupulate.replace(" ", "");
				((PathMod)speicher.getMod(ModData.PathMod.name())).manupulateValue(valueToManupulate, key);
				
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
