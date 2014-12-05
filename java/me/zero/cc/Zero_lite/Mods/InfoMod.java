package me.zero.cc.Zero_lite.Mods;

import java.awt.Toolkit;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.core.LiteLoader;

import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseStringButton;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import me.zero.cc.Zero_lite.utils.Speicher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;

public class InfoMod implements Mod {

	private Minecraft minecraft;
	private double percent = 0;
	private int rotation = 0;
	private String Facing = "";
	private int onkey = 0;
	private Speicher speicher;
	
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
	
	private float rotationyam = 0;
	private long lastdiraktu = 0;
	
	public InfoMod(Minecraft minecraft,Speicher speicher){		
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
		if(showdir){
			if(System.currentTimeMillis() - lastdiraktu >= 500){
				rotationyam = minecraft.thePlayer.rotationYawHead;			
				UpdateDirection(getPercent());
				UpdateRotation(minecraft);
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
	private String getWorldAge(){
		long worldage = minecraft.theWorld.getTotalWorldTime();
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
			speicher.getInfoLineManager().getInfoLine(posCoor).setInfo(infoidCoor, "(" + (int)minecraft.thePlayer.posX + "," + (int)minecraft.thePlayer.posY + "," + (int)minecraft.thePlayer.posZ + ")");
		}else{
			speicher.getInfoLineManager().getInfoLine(posCoor).setInfo(infoidCoor, "");
		}
		if(showWorldAge){
			speicher.getInfoLineManager().getInfoLine(posWorldAge).setInfo(infoidWorldAge, getWorldAge());
		}else{
			speicher.getInfoLineManager().getInfoLine(posWorldAge).setInfo(infoidWorldAge, "");
		}
	}
	private void UpdateDirection(double value){
		if(value >= 135 && value < 180 || value > -180 && value <= -135){
			if(!getFacing().contains("North")){
				setFacing("North");
			}
		}else		
		if(value > -135 && value <= -45){
			if(!getFacing().contains("East")){
				setFacing("East");		
			}
		}else
		if(value > -45 && value < 45){
			if(!getFacing().contains("South")){
				setFacing("South");
			}
		}else
		if(value >= 45 && value < 135){
			if(!getFacing().contains("West")){
				setFacing("West");
			}
		}else{
			setFacing("ERROR");
			System.out.println(value);
		}
	}
	public int getOn() {
		return onkey;
	}
	public void setOn(int on) {
		onkey = on;
	}
	private void UpdateRotation(Minecraft minecraft){
		if(rotationyam < 360){			
			if(rotationyam > 0){
				rotationyam = rotationyam + (360 * ((rotationyam/360)));
			}else{
				rotationyam = rotationyam + (360 * (int)(((rotationyam*-1)/360)+2));
			}
		}
		if(rotationyam > 360){				
			if((rotationyam -(getRotation()*360)) > 180){
				setPercent((rotationyam -(getRotation()*360)-360));
			}else{
				setPercent(rotationyam -(getRotation()*360));
			}
			setRotation((int)rotationyam / 360);
		}
	}
	public int getRotation() {
		return rotation;
	}
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public String getFacing() {
		return Facing;
	}
	public void setFacing(String facing) {
		Facing = facing;
	}

	@Override
	public GuiScreen drawGui() {
		return new InfoModGui(speicher);
	}

	@Override
	public void manupulateValue(String ValueToManupulate, int percent) {		
		System.out.println("Don't know that " + ValueToManupulate);
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		
		if(valueToManupulate.equalsIgnoreCase("showcoor")){
			showcoor = b;
			speicher.getConfig().replaceData("Info-Mod.showcoor", showcoor + "");
		}else
		if(valueToManupulate.equalsIgnoreCase("showfps")){
			showfps = b;
			speicher.getConfig().replaceData("Info-Mod.showFPS", showfps + "");
		}else
		if(valueToManupulate.equalsIgnoreCase("showdir")){
			showdir = b;
			speicher.getConfig().replaceData("Info-Mod.showdir", showdir + "");
		}else
		if(valueToManupulate.equalsIgnoreCase("showworldage")){
			showWorldAge = b;
			speicher.getConfig().replaceData("Info-Mod.showworldage", showWorldAge + "");
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

	public GuiPositions getPosFPS() {
		return posFPS;
	}

	public void setPosFPS(GuiPositions posFPS) {
		this.posFPS = posFPS;
	}

	public GuiPositions getPosDir() {
		return posDir;
	}

	public GuiPositions getPosWorldAge() {
		return posWorldAge;
	}

	public void setPosWorldAge(GuiPositions posWorldAge) {
		this.posWorldAge = posWorldAge;
	}

	public void setPosDir(GuiPositions posDir) {
		this.posDir = posDir;
	}

	public GuiPositions getPosCoor() {
		return posCoor;
	}

	public void setPosCoor(GuiPositions posCoor) {
		this.posCoor = posCoor;
	}

	public boolean isShowfps() {
		return showfps;
	}

	public void setShowfps(boolean showfps) {
		this.showfps = showfps;
	}

	public boolean isShowWorldAge() {
		return showWorldAge;
	}

	public void setShowWorldAge(boolean showWorldAge) {
		this.showWorldAge = showWorldAge;
	}

	public boolean isShowdir() {
		return showdir;
	}

	public void setShowdir(boolean showdir) {
		this.showdir = showdir;
	}

	public boolean isShowcoor() {
		return showcoor;
	}

	public void setShowcoor(boolean showcoor) {
		this.showcoor = showcoor;
	}
}
class InfoModGui extends GuiScreen{
	
	private Speicher speicher;
	
	public InfoModGui(Speicher speicher){
		this.speicher = speicher;
	}
	
	public void initGui(){
		drawButtons();
	}
	
	public void actionPerformed(GuiButton b){	
		if(b.displayString.contains("back to game")){
			speicher.getMinecraft().displayGuiScreen(null);
			speicher.getZm().setShown(false);
		}
	}
	
	public void drawButtons(){
		GuiBooleanButton showDirection = new GuiBooleanButton(1, 10, 20, 150, 20, "Show Direction", ((InfoMod)speicher.getMod(ModData.InfoMod.name())).isShowdir(), "showdir", ModData.InfoMod, speicher);
		GuiChooseStringButton dirpos = new GuiChooseStringButton(2, width/2+50, 20, 150, 20, "Dir-Position", GuiPositions.getPosList(), "posdir", ModData.InfoMod, speicher,GuiPositions.getPos(((InfoMod)speicher.getMod(ModData.InfoMod.name())).getPosDir()));
		
		GuiBooleanButton showFPS= new GuiBooleanButton(3, 10, 45, 150, 20, "Show FPS", ((InfoMod)speicher.getMod(ModData.InfoMod.name())).isShowfps(), "showfps", ModData.InfoMod, speicher);
		GuiChooseStringButton fpspos = new GuiChooseStringButton(4, width/2+50, 45, 150, 20, "FPS-Position", GuiPositions.getPosList(), "posfps", ModData.InfoMod, speicher,GuiPositions.getPos(((InfoMod)speicher.getMod(ModData.InfoMod.name())).getPosFPS()));
		
		GuiBooleanButton showCoor = new GuiBooleanButton(5, 10, 70, 150, 20, "Show Coor", ((InfoMod)speicher.getMod(ModData.InfoMod.name())).isShowcoor(), "showcoor", ModData.InfoMod, speicher);
		GuiChooseStringButton coorpos = new GuiChooseStringButton(6, width/2+50, 70, 150, 20, "Coor-Position", GuiPositions.getPosList(), "poscoor", ModData.InfoMod, speicher,GuiPositions.getPos(((InfoMod)speicher.getMod(ModData.InfoMod.name())).getPosCoor()));
		
		GuiBooleanButton showworldage = new GuiBooleanButton(7, 10, 95, 150, 20, "Show WorldAge", ((InfoMod)speicher.getMod(ModData.InfoMod.name())).isShowWorldAge(), "showworldage", ModData.InfoMod, speicher);
		GuiChooseStringButton worldagepos = new GuiChooseStringButton(8, width/2+50, 95, 150, 20, "WorldAge-Position", GuiPositions.getPosList(), "posworldage", ModData.InfoMod, speicher,GuiPositions.getPos(((InfoMod)speicher.getMod(ModData.InfoMod.name())).getPosWorldAge()));
		
		
		
		GuiButton back = new GuiButton(9, width/2-100,height-50 , "back to game");
		
		
		
		/*
		 * TODO: Remove Position-Display
		 * TODO: Add Position Display with the new System
		 * TODO: Add disable funktion for all Gui-Positions
		 */
		
		buttonList.add(showworldage);
		buttonList.add(worldagepos);
		buttonList.add(dirpos);
		buttonList.add(fpspos);
		buttonList.add(coorpos);
		buttonList.add(showCoor);
		buttonList.add(showFPS);
		buttonList.add(showDirection);
		
		buttonList.add(back);
	}
	protected void keyTyped(char c,int key){			
			if(key == 65 || key == 1){
				speicher.getMinecraft().displayGuiScreen(null);
				speicher.getZm().setShown(false);			
		}		
	}
}
