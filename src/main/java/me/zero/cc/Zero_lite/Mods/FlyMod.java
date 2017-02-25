package me.zero.cc.Zero_lite.Mods;

import java.text.NumberFormat;


import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseStringButton;
import me.zero.cc.Zero_lite.Gui.Buttons.SimpleSlider;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import me.zero.cc.Zero_lite.utils.KeySetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.GameType;

public class FlyMod implements Mod{
	
	private boolean togglefly = false;	
	private boolean flyenabled = false;
	private Minecraft minecraft;
	private String name = ModData.FlyMod.name();
	private String version = "0.1";
	
	private double flyValue = 1;
	
	private KeySetting downkey = new KeySetting("Fly-Mod.Key-Down");
	private KeySetting upkey = new KeySetting("Fly-Mod.Key-Up");
	private KeySetting onkey = new KeySetting("Fly-Mod.key-Toggle-fly");
	
	private LiteModMain speicher;
	private int infoID = 0;
	private double lastpressed = 0;
	private double maxValue = 10;
	private GuiPositions pos = GuiPositions.UP_LEFT;
	private boolean showFlyinfo = true;
	private boolean ignoreshift = true;
	private boolean nerfcreaetivefly = true;
	
	public FlyMod(Minecraft minecraft, LiteModMain speicher) {	
		this.speicher = speicher;
		this.minecraft = minecraft;
		
		//downkey = Integer.parseInt(speicher.getConfig().getData("Fly-Mod.Key-Down"));
		//upkey = Integer.parseInt(speicher.getConfig().getData("Fly-Mod.Key-Up"));
		//onkey = Integer.parseInt(speicher.getConfig().getData("Fly-Mod.key-Toggle-fly"));
		
		flyValue = Double.valueOf(speicher.getConfig().getData("Fly-Mod.flyspeed"));
		maxValue = Double.valueOf(speicher.getConfig().getData("Fly-Mod.maxflyspeed"));		
		showFlyinfo = Boolean.valueOf(speicher.getConfig().getData("Fly-Mod.showfly"));	
		flyenabled = Boolean.valueOf(speicher.getConfig().getData("Fly-Mod.fly-enabled"));
		nerfcreaetivefly = Boolean.valueOf(speicher.getConfig().getData("Fly-Mod.nerfcreaetivefly"));
		togglefly = Boolean.valueOf(speicher.getConfig().getData("Fly-Mod.toggle-fly"));		
		pos = GuiPositions.valueOf(speicher.getConfig().getData("Fly-Mod.fly-Pos"));
		ignoreshift = Boolean.valueOf(speicher.getConfig().getData("Fly-Mod.ignoreshift"));	
		
		infoID = this.speicher.getInfoLineManager().getInfoLine(pos).addInfo("");
		lastpressed = System.currentTimeMillis();
	}		
	@Override
	public void use() {
		if(onkey.isKeyDown() && !(upkey.isKeyDown() || downkey.isKeyDown() || (minecraft.currentScreen != null))){
			if((System.currentTimeMillis() - lastpressed) >=100){
				if(flyenabled){
					flyenabled = false;
					//minecraft.thePlayer.hurtResistantTime = 1000;
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
		if(nerfcreaetivefly){
			if(minecraft.player.capabilities.isCreativeMode){
				if(minecraft.player.motionX != 0 | minecraft.player.motionZ != 0 /*| minecraft.thePlayer.motionY != 0 */){
					if(!minecraft.gameSettings.keyBindForward.isKeyDown() && !minecraft.gameSettings.keyBindBack.isKeyDown() && !minecraft.gameSettings.keyBindLeft.isKeyDown() && !minecraft.gameSettings.keyBindRight.isKeyDown() && !minecraft.gameSettings.keyBindJump.isKeyDown() && !minecraft.gameSettings.keyBindSneak.isKeyDown()){
						minecraft.player.motionZ = 0;
						minecraft.player.motionX = 0;
					}
				}
			}
		}
	}
	private void Updatefly(){
		if(minecraft.isSingleplayer()){
			minecraft.getIntegratedServer().getEntityWorld().getPlayerEntityByName(minecraft.player.getName()).fallDistance = 0;
		}
		
		if(!togglefly){
			float value = (float) flyValue;
			if(minecraft.gameSettings.keyBindSneak.isKeyDown() && !ignoreshift){
				value = (float) (flyValue /10);
			}
			if(upkey.isKeyDown() && (minecraft.currentScreen == null)){
				minecraft.player.motionY = value;
			}else if(downkey.isKeyDown() && (minecraft.currentScreen == null)){
				minecraft.player.motionY = -value;
			}else{
				minecraft.player.motionY = 0;
			}
		}else{
			float value = (float) flyValue;
			if(minecraft.gameSettings.keyBindSneak.isKeyDown() && (minecraft.currentScreen == null)){
				value = (float) (flyValue /10);
			}
			if(upkey.isKeyDown() && onkey.isKeyDown()  && (minecraft.currentScreen == null)){
				minecraft.player.motionY = value;
			}else if(downkey.isKeyDown() && onkey.isKeyDown()  && (minecraft.currentScreen == null)){
				minecraft.player.motionY = -value;
			}else{
				minecraft.player.motionY = 0;
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
	/**
	 * Get the flyUpSpeed
	 * @return Double
	 */
	public double getFlyValue() {
		return flyValue;
	}
	/**
	 * Set the flyUpSpeeds
	 * @param flyValue the new FlySpeed
	 */
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
	/**
	 * Get if fly is toggled
	 * @return Boolean
	 */
	public boolean isTogglefly() {
		return togglefly;
	}
	/**
	 * Set if fly is toggled
	 * @param togglefly a boolean value
	 */
	public void setTogglefly(boolean togglefly) {
		this.togglefly = togglefly;
	}
	/**
	 * Get the key to fly down
	 * @return Integer
	 */
	public int getDown() {
		return downkey.getKey();
	}
	/**
	 * Set the key to fly down
	 * @param down ,the key representing the key on the keyboard
	 */
	public void setDown(int down) {
		downkey.setKey(down);;
	}
	/**
	 * Get the key to fly up
	 * @return Integer
	 */
	public int getUp() {
		return upkey.getKey();
	}
	/**
	 * Set the key to fly up
	 * @param up ,the key representing the key on the keyboard
	 */
	public void setUp(int up) {
		upkey.setKey(up);
	}
	/**
	 * Get the key to enable
	 * @return Integer
	 */
	public int getOn() {
		return onkey.getKey();
	}
	/**
	 * Set the key to enable
	 * @param on ,the key representing the key on the keyboard
	 */
	public void setOn(int on) {
		onkey.setKey(on);
	}
	@Override
	public void manupulateValue(String ValueToManupulate, double value) {
		if(ValueToManupulate.equalsIgnoreCase("flyValue")){
			flyValue = ((maxValue / 100.0)*value);
			NumberFormat df = NumberFormat.getInstance();
			df.setMaximumFractionDigits(2);
			flyValue = Double.valueOf(df.format(flyValue).replace(",", "."));
			speicher.getConfig().replaceData("Fly-Mod.flyspeed", flyValue + "");
		}else if(ValueToManupulate.equalsIgnoreCase("FlyDown-Key")){
			//downkey = (int)value;
			downkey.setKey((int)value);
			//speicher.getConfig().replaceData("Fly-Mod.Key-Down", downkey + "");
		}else if(ValueToManupulate.equalsIgnoreCase("FlyUp-Key")){
			upkey.setKey((int)value);
			//upkey = (int)value;
			//speicher.getConfig().replaceData("Fly-Mod.Key-Up", upkey + "");
		}else if(ValueToManupulate.equalsIgnoreCase("Enable-Key")){
			onkey.setKey((int)value);
			//onkey = (int)value;
			//speicher.getConfig().replaceData("Fly-Mod.Toggle-fly", onkey + "");
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
		}else if(valueToManupulate.equalsIgnoreCase("ignoreshift")){	
			ignoreshift = b;
			speicher.getConfig().replaceData("Fly-Mod.showfly", ignoreshift + "");
		}else if(valueToManupulate.equalsIgnoreCase("nerfcreativefly")){
			nerfcreaetivefly = b;
			speicher.getConfig().replaceData("Fly-Mod.nerfcreaetivefly", nerfcreaetivefly + "");
		}else{		
			System.out.println("Fehler: " + valueToManupulate + " is not a known Value in " + this.getName());
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
	/**
	 * Get if the Mod is ignoring shift
	 * @return Boolean
	 */
	public boolean isIgnoreshift() {
		return ignoreshift;
	}
	/**
	 * Set if the Mod is ignoring shift
	 * @param ignoreshift , a Boolean value
	 */
	public void setIgnoreshift(boolean ignoreshift) {
		this.ignoreshift = ignoreshift;
	}
	/**
	 * Get the current Position of the Infoscreen
	 * @return GuiPositions
	 */
	public GuiPositions getPos() {
		return pos;
	}
	/**
	 * Sets the current Position of the Infoscreen 
	 * @param GuiPositions
	 */
	public void setPos(GuiPositions pos) {
		this.pos = pos;
	}
	/**
	 * returns if the flyInfo is shown
	 * @return Boolean
	 */
	public boolean isShowFlyinfo() {
		return showFlyinfo;
	}
	/**
	 * Set the flyInfo
	 * @param Boolean
	 */
	public void setShowFlyinfo(boolean showFlyinfo) {
		this.showFlyinfo = showFlyinfo;
	}
	public boolean isNerfcreaetivefly() {
		return nerfcreaetivefly;
	}
	public void setNerfcreaetivefly(boolean nerfcreaetivefly) {
		this.nerfcreaetivefly = nerfcreaetivefly;
	}
}
class FlyModGui extends GuiScreen{
	
	private LiteModMain speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	private GuiChooseKeyButton choosedown;
	private GuiChooseKeyButton chooseUp;
	private GuiChooseKeyButton chooseOn;
	
	public FlyModGui(LiteModMain speicher){
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
		
		SimpleSlider slider = new SimpleSlider(0, width/2, height/4-20, "Fly-Speed-Up", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).getFlyValue() , 150, 20, ModData.FlyMod, "Flyvalue", speicher ,LiteModMain.lconfig.getData("FlyMod.changeflyvalue").split(";"));
		GuiBooleanButton enablefly = new GuiBooleanButton(1, width/2-170, height/4+10, 150, 20, "Toggle Fly", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).isTogglefly(), "togglefly", ModData.FlyMod, speicher,LiteModMain.lconfig.getData("FlyMod.togglefly").split(";"));		
		GuiBooleanButton booleanb = new GuiBooleanButton(2, width/2-170, height/4-20, 150, 20, "Enable Fly", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).isEnabled(), "flyenabled", ModData.FlyMod, speicher,LiteModMain.lconfig.getData("FlyMod.enablefly").split(";"));
		GuiBooleanButton ignoreshift = new GuiBooleanButton(3, width/2-170, height-80, 150, 20, "Ignore Shift", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).isIgnoreshift(), "ignoreshift", ModData.FlyMod, speicher,LiteModMain.lconfig.getData("FlyMod.ignoreshift").split(";"));
		GuiBooleanButton antiCreativeSwimm  = new GuiBooleanButton(4, width/2, height-80, 150, 20, "Disable Creative Swimm", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).isNerfcreaetivefly(), "nerfcreativefly", ModData.FlyMod, speicher,LiteModMain.lconfig.getData("FlyMod.nerfswim").split(";"));
		
		chooseUp = new GuiChooseKeyButton(5, width/2-170, height/4+40, 150, 20, "FlyUp-Key", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).getUp(),LiteModMain.lconfig.getData("FlyMod.chooseflyup").split(";"));
		choosedown = new GuiChooseKeyButton(6, width/2, height/4+10, 150, 20, "FlyDown-Key", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).getDown(),LiteModMain.lconfig.getData("FlyMod.chooseflydown").split(";"));
		chooseOn = new GuiChooseKeyButton(7, width/2, height/4+40, 150, 20, "Enable-Key", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).getOn(),LiteModMain.lconfig.getData("FlyMod.enable").split(";"));
		
		GuiChooseStringButton choosepos = new GuiChooseStringButton(8, width/2-170, height/4+70, 150, 20, "Fly-Pos", GuiPositions.getPosList(), "flypos", ModData.FlyMod, speicher, GuiPositions.getPos(((FlyMod)speicher.getMod(ModData.FlyMod.name())).getPos()),LiteModMain.lconfig.getData("Main.choosepos").split(";"));
		GuiBooleanButton showFlyInfo = new GuiBooleanButton(9, width/2, height/4+70, 150, 20, "Show-FlyInfo", ((FlyMod)speicher.getMod(ModData.FlyMod.name())).isShowFlyinfo(), "showFly", ModData.FlyMod, speicher,LiteModMain.lconfig.getData("FlyMod.Flyinfo").split(";"));

		GuiButton back = new GuiButton(10, width/2-100,height-50 , "back to game");
		
		buttonList.add(antiCreativeSwimm);
		buttonList.add(showFlyInfo);
		buttonList.add(choosepos);
		buttonList.add(chooseOn);
		buttonList.add(choosedown);
		buttonList.add(chooseUp);
		buttonList.add(enablefly);
		buttonList.add(booleanb);		
		buttonList.add(ignoreshift);
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
				}else if(valueToManupulate.equalsIgnoreCase("FlyDown-Key")){
					choosedown.setButtonkey(key);
				}else if(valueToManupulate.equalsIgnoreCase("Enable-Key")){
					chooseOn.setButtonkey(key);
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
