package me.zero.cc.Zero_lite.Mods;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.core.LiteLoader;

import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseStringButton;
import me.zero.cc.Zero_lite.Gui.Buttons.SimpleSlider;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import me.zero.cc.Zero_lite.utils.Speicher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;

public class FlyMod implements Mod{
	
	private boolean togglefly = false;	
	private boolean flyenabled = false;
	private Minecraft minecraft;
	private String name = ModData.FlyMod.name();
	private String version = "0.1";
	
	private double flyValue = 1;
	
	private int downkey = 0;
	private int upkey = 0;
	private int onkey = 0;
	
	private Speicher speicher;
	private int infoID = 0;
	private double lastpressed = 0;
	private double maxValue = 10;
	private GuiPositions pos = GuiPositions.UP_LEFT;
	private boolean showFlyinfo = true;
	
	public GuiPositions getPos() {
		return pos;
	}
	public void setPos(GuiPositions pos) {
		this.pos = pos;
	}
	public boolean isShowFlyinfo() {
		return showFlyinfo;
	}
	public void setShowFlyinfo(boolean showFlyinfo) {
		this.showFlyinfo = showFlyinfo;
	}
	public FlyMod(Minecraft minecraft, Speicher speicher) {	
		this.speicher = speicher;
		this.minecraft = minecraft;
		
		downkey = Integer.parseInt(speicher.getConfig().getData("Fly-Mod.Key-Down"));
		upkey = Integer.parseInt(speicher.getConfig().getData("Fly-Mod.Key-Up"));
		onkey = Integer.parseInt(speicher.getConfig().getData("Fly-Mod.key-Toggle-fly"));
		
		flyValue = Double.valueOf(speicher.getConfig().getData("Fly-Mod.flyspeed"));
		maxValue = Double.valueOf(speicher.getConfig().getData("Fly-Mod.maxflyspeed"));		
		showFlyinfo = Boolean.valueOf(speicher.getConfig().getData("Fly-Mod.showfly"));	
		flyenabled = Boolean.valueOf(speicher.getConfig().getData("Fly-Mod.fly-enabled"));
		togglefly = Boolean.valueOf(speicher.getConfig().getData("Fly-Mod.toggle-fly"));		
		pos = GuiPositions.valueOf(speicher.getConfig().getData("Fly-Mod.fly-Pos"));
		
		infoID = this.speicher.getInfoLineManager().getInfoLine(pos).addInfo("");
		lastpressed = System.currentTimeMillis();					
	}		
	@Override
	public void use() {
		if(Keyboard.isKeyDown(onkey) && !(Keyboard.isKeyDown(upkey) || Keyboard.isKeyDown(downkey))){
			if((System.currentTimeMillis() - lastpressed) >=100){
				if(flyenabled){
					flyenabled = false;
					speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);					
				}else{				
					flyenabled = true;				
				}
				speicher.getConfig().replaceData("Fly-Mod.fly-enabled", flyenabled + "");
			}							
			lastpressed = System.currentTimeMillis();
			}
		if(flyenabled){
			if(showFlyinfo){
				speicher.getInfoLineManager().getInfoLine(pos).setInfo(infoID, "Fly(" + flyValue + ")");					
			}else{
				speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
			}			
			Updatefly();
		}
	}
	private void Updatefly(){
		if(!togglefly){
			float value = (float) flyValue;
			if(minecraft.gameSettings.keyBindSneak.isKeyDown()){
				value = (float) (flyValue /10);
			}
			if(Keyboard.isKeyDown(upkey)){
				minecraft.thePlayer.motionY = value;
			}else if(Keyboard.isKeyDown(downkey)){
				minecraft.thePlayer.motionY = -value;
			}else{
				minecraft.thePlayer.motionY = 0;
			}
		}else{
			float value = (float) flyValue;
			if(minecraft.gameSettings.keyBindSneak.isKeyDown()){
				value = (float) (flyValue /10);
			}
			if(Keyboard.isKeyDown(upkey) && Keyboard.isKeyDown(onkey)){
				minecraft.thePlayer.motionY = value;
			}else if(Keyboard.isKeyDown(downkey) && Keyboard.isKeyDown(onkey)){
				minecraft.thePlayer.motionY = -value;
			}else{
				minecraft.thePlayer.motionY = 0;
			}
		}
		
	}
	@Override
	public boolean isEnabled() {
		return flyenabled;
	}	
	@Override
	public String getName() {
		return name;
	}
	public double getFlyValue() {
		return flyValue;
	}
	public void setFlyValue(int flyValue) {
		this.flyValue = flyValue;
	}
	@Override
	public String getVersion() {
		return version;
	}
	@Override
	public GuiScreen drawGui() {			
		return new FlyModGui(speicher);
	}
	public boolean isTogglefly() {
		return togglefly;
	}
	public void setTogglefly(boolean togglefly) {
		this.togglefly = togglefly;
	}
	public int getDown() {
		return downkey;
	}
	public void setDown(int down) {
		downkey = down;
	}
	public int getUp() {
		return upkey;
	}
	public void setUp(int up) {
		upkey = up;
	}
	public int getOn() {
		return onkey;
	}
	public void setOn(int on) {
		onkey = on;
	}
	@Override
	public void manupulateValue(String ValueToManupulate, int value) {
		if(ValueToManupulate.equalsIgnoreCase("flyValue")){
			flyValue = (int) ((maxValue / 100.0)*value);
			speicher.getConfig().replaceData("Fly-Mod.flyspeed", flyValue + "");
		}else if(ValueToManupulate.equalsIgnoreCase("FlyDown-Key")){
			downkey = value;
			speicher.getConfig().replaceData("Fly-Mod.Key-Down", downkey + "");
		}else if(ValueToManupulate.equalsIgnoreCase("FlyUp-Key")){
			upkey = value;
			speicher.getConfig().replaceData("Fly-Mod.Key-Up", upkey + "");
		}else if(ValueToManupulate.equalsIgnoreCase("Enable-Key")){
			onkey = value;
			speicher.getConfig().replaceData("Fly-Mod.Toggle-fly", onkey + "");
		}else{
			System.out.println("Fehler: " + ValueToManupulate + " is not a known Value in " + this.getName());
			//throw new IllegalArgumentException(ValueToManupulate + " is not a known Value in " + this.getName());
		}		
	}
	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		if(valueToManupulate.equalsIgnoreCase("togglefly")){
			togglefly = b;
			speicher.getConfig().replaceData("Fly-Mod.toggle-fly", togglefly + "");
		}else if(valueToManupulate.equalsIgnoreCase("flyenabled")){			
			flyenabled = b;
			speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
			speicher.getConfig().replaceData("Fly-Mod.fly-enabled", flyenabled + "");
		}else if(valueToManupulate.equalsIgnoreCase("showfly")){	
			showFlyinfo = b;
			speicher.getConfig().replaceData("Fly-Mod.showfly", showFlyinfo + "");
		}else{		
			System.out.println("Fehler: " + valueToManupulate + " is not a known Value in " + this.getName());
			//throw new IllegalArgumentException(valueToManupulate + " is not a known Value in " + this.getName());
		}	
	}
	@Override
	public void manupulateValue(String valueToManupulate, String value) {
		if(valueToManupulate.equalsIgnoreCase("flypos")){
			speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
			pos = GuiPositions.valueOf(value);
			infoID = speicher.getInfoLineManager().getInfoLine(pos).addInfo("");	
			speicher.getConfig().replaceData("Fly-Mod.fly-Pos", pos.toString());
		}else{
			System.out.println("Fehler: " + valueToManupulate + " is not a known Value in " + this.getName());
		}
		
	}
}
class FlyModGui extends GuiScreen{
	
	private Speicher speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	private GuiChooseKeyButton choosedown;
	private GuiChooseKeyButton chooseUp;
	private GuiChooseKeyButton chooseOn;
	
	public FlyModGui(Speicher speicher){
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
		
		SimpleSlider slider = new SimpleSlider(0, width/2, height/4-10, "Fly-Speed-Up", (int) ((FlyMod)speicher.getMod(ModData.FlyMod.name())).getFlyValue() , 150, 20, ModData.FlyMod, "Flyvalue", speicher);
		GuiBooleanButton enablefly = new GuiBooleanButton(1, width/2-170, height/4+20, 150, 20, "Toggle Fly", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).isTogglefly(), "togglefly", ModData.FlyMod, speicher);		
		GuiBooleanButton booleanb = new GuiBooleanButton(2, width/2-170, height/4-10, 150, 20, "Enable Fly", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).isEnabled(), "flyenabled", ModData.FlyMod, speicher);
		
		chooseUp = new GuiChooseKeyButton(3, width/2-170, height/4+50, 150, 20, "FlyUp-Key", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).getUp());
		choosedown = new GuiChooseKeyButton(4, width/2, height/4+50, 150, 20, "FlyDown-Key", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).getDown());
		chooseOn = new GuiChooseKeyButton(5, width/2, height/4+20, 150, 20, "Enable-Key", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).getDown());
		
		GuiChooseStringButton choosepos = new GuiChooseStringButton(7, width/2-170, height/4+80, 150, 20, "Fly-Pos", GuiPositions.getPosList(), "flypos", ModData.FlyMod, speicher, GuiPositions.getPos(((FlyMod)speicher.getMod(ModData.FlyMod.name())).getPos()));
		GuiBooleanButton showFlyInfo = new GuiBooleanButton(8, width/2, height/4+80, 150, 20, "Show-FlyInfo", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).isShowFlyinfo(), "showFly", ModData.FlyMod, speicher);
		
		
		GuiButton back = new GuiButton(6, width/2-100,height-50 , "back to game");
		buttonList.add(showFlyInfo);
		buttonList.add(choosepos);
		buttonList.add(chooseOn);
		buttonList.add(choosedown);
		buttonList.add(chooseUp);
		buttonList.add(enablefly);
		buttonList.add(booleanb);		
		buttonList.add(slider);		
		buttonList.add(back);
	}
	protected void keyTyped(char c,int key){
		if(GivingKey){
			if(key != 65 && key != 1){
				valueToManupulate = valueToManupulate.replace(" ", "");
				((FlyMod)speicher.getMod(ModData.FlyMod.name())).manupulateValue(valueToManupulate, key);
				
				if(valueToManupulate.equalsIgnoreCase("FlyUp-Key")){
					chooseUp.setButtonkey(key);
				}else if(valueToManupulate.equalsIgnoreCase("FlyUp-Key")){
					choosedown.setButtonkey(key);
				}else if(valueToManupulate.equalsIgnoreCase("Enable-Key")){
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
