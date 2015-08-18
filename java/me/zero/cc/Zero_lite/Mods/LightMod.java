package me.zero.cc.Zero_lite.Mods;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.core.LiteLoader;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.Gui.Buttons.SimpleSlider;
import me.zero.cc.Zero_lite.utils.KeySetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;

public class LightMod implements Mod {

	private Minecraft minecraft;
	private boolean enabled = false;
	
	private KeySetting onkey = new KeySetting("Light-Mod.Toggle-Light");
	
	private float gamasetting = 0;
	private double lastpressed = 0;
	private String version = "0.1";
	private LiteModMain speicher;
	
	public LightMod(Minecraft minecraft,LiteModMain speicher){
		this.minecraft = minecraft;
		this.speicher = speicher;
		
		//onkey = Integer.parseInt(speicher.getConfig().getData("Light-Mod.Toggle-Light"));
		enabled = Boolean.valueOf(speicher.getConfig().getData("Light-Mod.lightmod-enabled"));
		
		gamasetting = minecraft.gameSettings.gammaSetting;
		lastpressed = System.currentTimeMillis();
		
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
				}else{
					enabled = true;
				}				
			}	
			lastpressed = System.currentTimeMillis();
		}
		addjustBrightness();			
		
	}
	private void addjustBrightness(){
		if(enabled){
			minecraft.gameSettings.gammaSetting = 100;
		}else{
			minecraft.gameSettings.gammaSetting = gamasetting;
		}		
	}

	@Override
	public String getName() {
		return ModData.LightMod.name();
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
	 * @param on
	 */
	public void setOn(int on) {
		onkey.setKey(on);
	}
	
	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public GuiScreen drawGui() {
		return new LightModGui(speicher);
	}

	@Override
	public void manupulateValue(String ValueToManupulate, double percent) {	
		if(ValueToManupulate.equalsIgnoreCase("Enable-Key")){
			onkey.setKey((int)percent);
		}else{
			System.out.println("Fehler: " + ValueToManupulate + " is not a known Value in " + this.getName());
		}
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		
		if(valueToManupulate.equalsIgnoreCase("toggleLight")){
			enabled = b;
			speicher.getConfig().replaceData("Light-Mod.lightmod-enabled", enabled + "");
		}else{
			System.out.println("Fehler: " + valueToManupulate + " is not a known Value in " + this.getName());
		}	
	}

	@Override
	public void manupulateValue(String valueToManupulate, String value) {
		System.out.println("Fehler: " + valueToManupulate + " is not a known Value in " + this.getName());
	}
}
class LightModGui extends GuiScreen{
	
	private LiteModMain speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	private GuiChooseKeyButton chooseOn;
	
	public LightModGui(LiteModMain speicher){
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
		GuiBooleanButton togglespeed = new GuiBooleanButton(2, width/2-170, height/4+20, 150, 20, "Toggle Light", ((LightMod)speicher.getMod(ModData.LightMod.name())).isEnabled(), "toggleLight", ModData.LightMod, speicher,LiteModMain.lconfig.getData("LightMod.togglelight").split(";"));
		
		chooseOn = new GuiChooseKeyButton(3, width/2, height/4+20, 150, 20, "Enable-Key", ((LightMod)speicher.getMod(ModData.LightMod.name())).getOn(),LiteModMain.lconfig.getData("LightMod.enable").split(";"));

		GuiButton back = new GuiButton(6, width/2-100,height-50 , "back to game");
		
		buttonList.add(chooseOn);
		buttonList.add(togglespeed);
		buttonList.add(back);
	}
	protected void keyTyped(char c,int key){
		if(GivingKey){
			if(key != 65 && key != 1){
				valueToManupulate = valueToManupulate.replace(" ", "");
				((LightMod)speicher.getMod(ModData.LightMod.name())).manupulateValue(valueToManupulate, key);
				
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
