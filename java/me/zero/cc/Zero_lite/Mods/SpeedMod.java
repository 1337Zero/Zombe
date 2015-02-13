package me.zero.cc.Zero_lite.Mods;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.core.LiteLoader;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseStringButton;
import me.zero.cc.Zero_lite.Gui.Buttons.SimpleSlider;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MathHelper;

public class SpeedMod implements Mod {

	private Minecraft minecraft;
	private double speedValue = 1;
	private LiteModMain speicher;
	private int onkey = 0;
	private boolean speedenabled = false;
	private double lastpressed = 0;
	private int infoID = 0;
	private String version = "0.1";
	
	private double maxspeed = 10;
	private boolean toggledspeed = false;
	private GuiPositions pos = GuiPositions.UP_LEFT;
	private boolean showspeed = true;

	public SpeedMod(Minecraft minecraft,LiteModMain speicher){
		this.minecraft = minecraft;
		this.speicher = speicher;
		
		onkey = Integer.parseInt(speicher.getConfig().getData("Speed-Mod.Toggle-speed"));
		maxspeed = Double.valueOf(speicher.getConfig().getData("Speed-Mod.maxspeed"));
		speedValue = Double.valueOf(speicher.getConfig().getData("Speed-Mod.speed"));
		showspeed = Boolean.valueOf(speicher.getConfig().getData("Speed-Mod.showspeed"));
		pos = GuiPositions.valueOf(speicher.getConfig().getData("Speed-Mod.speed-Pos"));
		toggledspeed = Boolean.valueOf(speicher.getConfig().getData("Speed-Mod.togglespeed"));
		speedenabled = Boolean.valueOf(speicher.getConfig().getData("Speed-Mod.enabled"));
		
		infoID = this.speicher.getInfoLineManager().getInfoLine(pos).addInfo("");
		lastpressed = System.currentTimeMillis();
	}
	
	@Override
	public boolean isEnabled() {
		return speedenabled;
	}
	/**
	 * Get if the info is shown
	 * @return Boolean
	 */
	public boolean isShowspeed() {
		return showspeed;
	}
	/**
	 * Set if the info is shown
	 * @param Boolean
	 */
	public void setShowspeed(boolean showspeed) {
		this.showspeed = showspeed;
	}
	@Override
	public void use() {		
		if(Keyboard.isKeyDown(onkey) && !(minecraft.gameSettings.keyBindRight.isKeyDown() || minecraft.gameSettings.keyBindLeft.isKeyDown() || minecraft.gameSettings.keyBindForward.isKeyDown() || minecraft.gameSettings.keyBindBack.isKeyDown() || (minecraft.currentScreen != null))){
			if((System.currentTimeMillis() - lastpressed) >=100){
				if(speedenabled){
					speedenabled = false;
					speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
				}else{
					speedenabled = true;				
				}
				speicher.getConfig().replaceData("Speed-Mod.enabled", speedenabled + "");
			}							
			lastpressed = System.currentTimeMillis();
		}		
		
		if(speedenabled){
			if(toggledspeed){
				if(Keyboard.isKeyDown(onkey) && (minecraft.currentScreen == null)){
					if(showspeed){
						speicher.getInfoLineManager().getInfoLine(pos).setInfo(infoID, "Speed (" + speedValue + ")");
					}else{
						speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
					}
						UpdateSpeed(minecraft);									
				}
			}else{
				if(showspeed){
					speicher.getInfoLineManager().getInfoLine(pos).setInfo(infoID, "Speed (" + speedValue + ")");
				}else{
					speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
				}
					UpdateSpeed(minecraft);				
			}
			
		}				
	}
	/**
	 * Get the Position of the Info
	 * @return GuiPositions
	 */
	public GuiPositions getPos() {
		return pos;
	}
	/**
	 * Set the Position of the Info
	 * @param pos
	 */
	public void setPos(GuiPositions pos) {
		this.pos = pos;
	}

	@Override
	public String getName() {
		return ModData.SpeedMod.name();
	}
	@Override
	public String getVersion() {
		return version;
	}
	/**
	 * Get the key to enable
	 * @return Integer
	 */
	public int getOn() {
		return onkey;
	}
	/**
	 * Set the key to enable
	 * @param Integer
	 */
	public void setOn(int on) {
		onkey = on;
	}

	private void UpdateSpeed(Minecraft minecraft){	
			int percent = 0;
			boolean forward = true;
			boolean pressed = false;
			if(minecraft.thePlayer.rotationYaw < 0){
				minecraft.thePlayer.rotationYaw = minecraft.thePlayer.rotationYaw + 360;
			}
			if(minecraft.gameSettings.keyBindRight.isKeyDown() && minecraft.gameSettings.keyBindForward.isKeyDown()){
				percent = (int)(minecraft.thePlayer.rotationYaw - (((int)minecraft.thePlayer.rotationYaw/360))*360)+45;
				pressed = true;
			}else if(minecraft.gameSettings.keyBindLeft.isKeyDown() && minecraft.gameSettings.keyBindForward.isKeyDown()){
				percent = (int)(minecraft.thePlayer.rotationYaw - (((int)minecraft.thePlayer.rotationYaw/360))*360)-45;
				pressed = true;
			}else if(minecraft.gameSettings.keyBindRight.isKeyDown() && minecraft.gameSettings.keyBindBack.isKeyDown()){
				percent = (int)(minecraft.thePlayer.rotationYaw - (((int)minecraft.thePlayer.rotationYaw/360))*360)-45;
				pressed = true;
				forward = false;
			}else if(minecraft.gameSettings.keyBindLeft.isKeyDown() && minecraft.gameSettings.keyBindBack.isKeyDown()){
				percent = (int)(minecraft.thePlayer.rotationYaw - (((int)minecraft.thePlayer.rotationYaw/360))*360)+45;
				pressed = true;
				forward = false;
			}else if(minecraft.gameSettings.keyBindForward.isKeyDown()){
				percent = (int)(minecraft.thePlayer.rotationYaw - (((int)minecraft.thePlayer.rotationYaw/360))*360);
				pressed = true;
			}else if(minecraft.gameSettings.keyBindBack.isKeyDown()){
				percent = (int)(minecraft.thePlayer.rotationYaw - (((int)minecraft.thePlayer.rotationYaw/360))*360);
				forward = false;
				pressed = true;
			}else if(minecraft.gameSettings.keyBindLeft.isKeyDown()){
				percent = (int)(minecraft.thePlayer.rotationYaw - (((int)minecraft.thePlayer.rotationYaw/360))*360)-90;
				pressed = true;
			}else if(minecraft.gameSettings.keyBindRight.isKeyDown()){
				percent = (int)(minecraft.thePlayer.rotationYaw - (((int)minecraft.thePlayer.rotationYaw/360))*360)+90;
				pressed = true;
			}else{
				minecraft.thePlayer.motionX = 0;
				minecraft.thePlayer.motionZ = 0;
			}
			if(pressed){
				doUpDateSpeed(percent, minecraft,forward);			
			}			
	}
	private void doUpDateSpeed(int percent,Minecraft minecraft,boolean forward){
		float mx = 0; float mz = 0;
		float move = (float) speedValue;
		float movef = -move * MathHelper.cos(minecraft.thePlayer.rotationPitch * (float)Math.PI / 180.0f);
        mx += movef * MathHelper.sin(percent * (float)Math.PI / 180.0f);
        mz += -movef * MathHelper.cos(percent * (float)Math.PI / 180.0f);
        if(forward){
        	minecraft.thePlayer.motionX = mx;
            minecraft.thePlayer.motionZ = mz;	
        }else{
        	minecraft.thePlayer.motionX = -mx;
            minecraft.thePlayer.motionZ = -mz;
        }
        
	}
	/**
	 * Get The Speed value
	 * @return Double
	 */
	public double getSpeedValue() {
		return speedValue;
	}
	/**
	 * Set the Speed value
	 * @param speedValue
	 */
	public void setSpeedValue(double speedValue) {
		this.speedValue = speedValue;
	}
	/**
	 * Set enabled
	 * @param Boolean
	 */
	public void setEnabled(boolean enabled) {
		this.speedenabled = enabled;
	}
	@Override
	public GuiScreen drawGui() {
		return new SpeedModGui(speicher);
	}
	@Override
	public void manupulateValue(String ValueToManupulate, int percent) {
		if(ValueToManupulate.equalsIgnoreCase("Speed")){
			speedValue = (int) ((maxspeed / 100.0)*percent);
			speicher.getConfig().replaceData("Speed-Mod.speed", speedValue + "");
		}else if(ValueToManupulate.equalsIgnoreCase("Enable-Key")){
			onkey = percent;
			speicher.getConfig().replaceData("Speed-Mod.Toggle-speed", onkey + "");
		}else{		
			System.out.println("Fehler: " + ValueToManupulate + " is not a known Value in " + this.getName());
		}		
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		if(valueToManupulate.equalsIgnoreCase("enablespeed")){
			speedenabled = b;
			speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
			speicher.getConfig().replaceData("Speed-Mod.speed-enabled", speedenabled + "");
		}else if(valueToManupulate.equalsIgnoreCase("togglespeed")){
			toggledspeed = b;
			speicher.getConfig().replaceData("Speed-Mod.togglespeed", toggledspeed + "");
		}else if(valueToManupulate.equalsIgnoreCase("showspeed")){
			showspeed = b;
			speicher.getConfig().replaceData("Speed-Mod.showspeed", showspeed + "");
		}else{	
			System.out.println("Fehler: " + valueToManupulate + " is not a known Value in " + this.getName());
		}
	}
	@Override
	public void manupulateValue(String valueToManupulate, String value) {
		
		if(valueToManupulate.equalsIgnoreCase("posspeed")){
			speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoID);
			pos = GuiPositions.valueOf(value);
			infoID = speicher.getInfoLineManager().getInfoLine(pos).addInfo("");	
			speicher.getConfig().replaceData("Speed-Mod.speed-Pos", pos.toString());
		}else{
			System.out.println("Don't know that " + value + " " + valueToManupulate);
		}		
	}
	/**
	 * Get is the Speed is toggled
	 * @return Boolean
	 */
	public boolean isToggledspeed() {
		return toggledspeed;
	}
	/**
	 * Set the Speed toggled
	 * @param Boolean
	 */
	public void setToggledspeed(boolean toggledspeed) {
		this.toggledspeed = toggledspeed;
	}
}
class SpeedModGui extends GuiScreen{
	
	private LiteModMain speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	private GuiChooseKeyButton chooseOn;
	
	public SpeedModGui(LiteModMain speicher){
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
		
		SimpleSlider slider = new SimpleSlider(0, width/2, height/4-10, "Speed", (int) ((SpeedMod)speicher.getMod(ModData.SpeedMod.name())).getSpeedValue() , 150, 20, ModData.SpeedMod, "Speed", speicher);
		GuiBooleanButton enablespeed = new GuiBooleanButton(1, width/2-170, height/4-10, 150, 20, "Enable Speed", ((SpeedMod)speicher.getMod(ModData.SpeedMod.name())).isEnabled(), "enablespeed", ModData.SpeedMod, speicher);		
		GuiBooleanButton togglespeed = new GuiBooleanButton(2, width/2-170, height/4+20, 150, 20, "Toggle Speed", ((SpeedMod)speicher.getMod(ModData.SpeedMod.name())).isToggledspeed(), "togglespeed", ModData.SpeedMod, speicher);
		
		GuiChooseStringButton choosepos = new GuiChooseStringButton(4, width/2-170, height/4+50, 150, 20, "Speed-Position" , GuiPositions.getPosList(), "posspeed", ModData.SpeedMod, speicher,GuiPositions.getPos(((SpeedMod)speicher.getMod(ModData.SpeedMod.name())).getPos()));
		GuiBooleanButton showSpeedInfo = new GuiBooleanButton(5, width/2,height/4+50, 150, 20, "Show Speed", ((SpeedMod)speicher.getMod(ModData.SpeedMod.name())).isShowspeed(), "showspeed", ModData.SpeedMod, speicher);
		
		chooseOn = new GuiChooseKeyButton(3, width/2, height/4+20, 150, 20, "Enable-Key", ((SpeedMod)speicher.getMod(ModData.SpeedMod.name())).getOn());
		
		GuiButton back = new GuiButton(6, width/2-100,height-50 , "back to game");
		buttonList.add(showSpeedInfo);
		buttonList.add(chooseOn);
		buttonList.add(choosepos);
		buttonList.add(togglespeed);
		buttonList.add(enablespeed);
		buttonList.add(slider);	
		buttonList.add(back);	
	}
	protected void keyTyped(char c,int key){
		if(GivingKey){
			if(key != 65 && key != 1){
				//speicher.getMinecraft().thePlayer.playSound("mob.ghast.scream", 1.0F, 1.0F);	
				valueToManupulate = valueToManupulate.replace(" ", "");
				((SpeedMod)speicher.getMod(ModData.SpeedMod.name())).manupulateValue(valueToManupulate, key);
				
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
