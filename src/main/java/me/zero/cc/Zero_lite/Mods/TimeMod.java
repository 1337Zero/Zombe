package me.zero.cc.Zero_lite.Mods;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.core.LiteLoader;

import me.zero.cc.Zero_lite.LiteModMain;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiBooleanButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseKeyButton;
import me.zero.cc.Zero_lite.Gui.Buttons.GuiChooseStringButton;
import me.zero.cc.Zero_lite.Gui.Buttons.SimpleSlider;
import me.zero.cc.Zero_lite.utils.GuiPositions;
import me.zero.cc.Zero_lite.utils.KeySetting;
import me.zero.cc.Zero_lite.utils.SunThread;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.EnumSkyBlock;

public class TimeMod implements Mod {

	private Minecraft minecraft;
	private String version = "0.1";
	
	private KeySetting addtime = new KeySetting("Time-Mod.Key-addtime");
	private KeySetting subtime = new KeySetting("Time-Mod.Key-subtime");
	private KeySetting freezekey = new KeySetting("Time-Mod.Key-freezetime");
	
	private double lastpressed = 0;
	private long timetoadd = 0;
	private LiteModMain speicher;
	private int infoid = 0;	
	private boolean enabled = false;
	private SunThread st;
	private boolean freezetime = false;
	private KeySetting multipl = new KeySetting((int) ((50 / 100.0)*1),"Time-Mod.time-multiplier");
	private GuiPositions pos = GuiPositions.UP_CENTER;
	private boolean showTimeInfo = false;
	
	public TimeMod(Minecraft minecraft,LiteModMain speicher){
		this.minecraft = minecraft;
		this.speicher = speicher;
		lastpressed = System.currentTimeMillis();
				
		//addtime = Integer.parseInt(speicher.getConfig().getData("Time-Mod.Key-addtime"));
		//subtime = Integer.parseInt(speicher.getConfig().getData("Time-Mod.Key-subtime"));
		//freezekey = Integer.parseInt(speicher.getConfig().getData("Time-Mod.Key-freezetime"));
		
		timetoadd = Long.valueOf(speicher.getConfig().getData("Time-Mod.timetoadd"));
		enabled = Boolean.valueOf(speicher.getConfig().getData("Time-Mod.Time-Mod-enabled"));
		freezetime = Boolean.valueOf(speicher.getConfig().getData("Time-Mod.time-freezed"));
		//multipl = Integer.parseInt(speicher.getConfig().getData("Time-Mod.time-multiplier"));
		showTimeInfo = Boolean.valueOf(speicher.getConfig().getData("Time-Mod.showTimeinfo"));
		pos = GuiPositions.valueOf(speicher.getConfig().getData("Time-Mod.showtimepos"));
		
		infoid = speicher.getInfoLineManager().getInfoLine(pos).addInfo("");		
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void use() {
		if(enabled){
			if(st == null){
				st = new SunThread(minecraft, timetoadd,minecraft.world.getWorldTime());
				st.start();
			}
		}
		if((System.currentTimeMillis() - lastpressed) >=100){
			if(addtime.isKeyDown() && (minecraft.currentScreen == null)){
				timetoadd = timetoadd + 240;
				if(st != null){
					st.settime2add(timetoadd);
				}				
			}
			if(subtime.isKeyDown() && (minecraft.currentScreen == null)){
				timetoadd = timetoadd - 240;
				if(st != null){
					st.settime2add(timetoadd);
				}
			}
			if(freezekey.isKeyDown() && (minecraft.currentScreen == null)){
				if(freezetime){
					freezetime = false;
				}else{
					freezetime = true;
				}	
				if(st != null){
					st.setFreezetime(freezetime);	
				}				
			}
			lastpressed = System.currentTimeMillis();
		}	
		if(showTimeInfo){
			if(timetoadd < 0){
				speicher.getInfoLineManager().getInfoLine(pos).setInfo(infoid, "{Time " +  timetoadd + "}");
			}else{
				speicher.getInfoLineManager().getInfoLine(pos).setInfo(infoid, "{Time +" +  timetoadd + "}");
			}
			
		}
	}

	@Override
	public String getName() {
		return ModData.TimeMod.name();
	}
	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public GuiScreen drawGui() {		
		return new TimeModGui(speicher);
	}

	@Override
	public void manupulateValue(String ValueToManupulate, double value) {
		if(ValueToManupulate.equalsIgnoreCase("Time-Add")){
			addtime.setKey((int)value);
		}else if(ValueToManupulate.equalsIgnoreCase("Time-Sub")){
			subtime.setKey((int)value);
		}else if(ValueToManupulate.equalsIgnoreCase("Freeze-Time")){
			freezekey.setKey((int)value);
		}else if(ValueToManupulate.equalsIgnoreCase("Time-Multiplier")){
			this.setMultipl((int) ((50 / 100.0)*value));
			if(st != null){
				st.setTimemultipliere((int) ((50 / 100.0)*value));
			}			
		}else{
			System.out.println("[TimeMod] Fehler: " + ValueToManupulate + " is not a known Value in " + this.getName());
		}		
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		if(valueToManupulate.equalsIgnoreCase("freezetime")){
			freezetime = b;
			if(st != null){
				st.setFreezedtime(minecraft.world.getWorldTime());
				st.setFreezetime(b);
			}						
			speicher.getConfig().replaceData("time-freezed", freezetime + "");
		}else if(valueToManupulate.equalsIgnoreCase("enable")){
			enabled = b;
			if(b){				
				st = new SunThread(minecraft, timetoadd, minecraft.world.getWorldTime());
				st.start();
			}else{
				st.interrupt();
			}
			speicher.getConfig().replaceData("Time-Mod-enabled", enabled + "");
		}else if(valueToManupulate.equalsIgnoreCase("showtime")){
			showTimeInfo = b;
			speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoid);
			speicher.getConfig().replaceData("Time-Mod.showTimeinfo", showTimeInfo + "");
		}else{
			System.out.println("[TimeMod] Don't know that " + valueToManupulate);
		}
	}

	@Override
	public void manupulateValue(String valueToManupulate, String value) {
		if(valueToManupulate.equalsIgnoreCase("timepos")){
			speicher.getInfoLineManager().getInfoLine(pos).resetInfo(infoid);
			pos = GuiPositions.valueOf(value);
			infoid = speicher.getInfoLineManager().getInfoLine(pos).addInfo("");
			speicher.getConfig().replaceData("Time-Mod.showtimepos", pos.toString());
		}else{
			System.out.println("[TimeMod] Don't know that " + valueToManupulate);
		}		
	}
	/**
	 * Get if the time is freezed
	 * @return boolean
	 */
	public boolean isFreezetime() {
		return freezetime;
	}
	/**
	 * Get the amount which will be added to time
	 * @return Integer
	 */
	public int getAddtime() {
		return addtime.getKey();
	}
	/**
	 * Set the amount which will be added to time
	 * @param Integer
	 */
	public void setAddtime(int addtime) {
		this.addtime.setKey(addtime);
	}
	/**
	 * Get the amount which will be remove from time
	 * @return Integer
	 */
	public int getSubtime() {
		return subtime.getKey();
	}
	/**
	 * Set the amount which will be remove from time
	 * @param Integer
	 */
	public void setSubtime(int subtime) {
		this.subtime.setKey(subtime);
	}
	/**
	 * Get the key for freeze time
	 * @return Integer
	 */
	public int getFreezetimekey() {
		return freezekey.getKey();
	}
	/**
	 * Set the key for freeze time
	 * @param Integer
	 */
	public void setFreezetimekey(int freezetimekey) {
		freezekey.setKey(freezetimekey);;
	}
	/**
	 * Get the time multiplicator
	 * @return Integer
	 */
	public int getMultipl() {
		return multipl.getKey();
	}
	/**
	 * set the time multiplicator
	 * @param multipl
	 */
	public void setMultipl(int multipl) {
		this.multipl.setKey(multipl);;
	}
	/**
	 * Get the Position of the info
	 * @return GuiPositions
	 */
	public GuiPositions getPos() {
		return pos;
	}
	/**
	 * Set the Position of the info 
	 * @param GuiPositions
	 */
	public void setPos(GuiPositions pos) {
		this.pos = pos;
	}
	/**
	 * Get if the info is shown
	 * @return Boolean
	 */
	public boolean isShowTimeInfo() {
		return showTimeInfo;
	}
	/**
	 * Set if the info is shown
	 * @param Boolean
	 */
	public void setShowTimeInfo(boolean showTimeInfo) {
		this.showTimeInfo = showTimeInfo;
	}

	@Override
	public int getOn() {
		// TODO Auto-generated method stub
		return 0;
	}
}
class TimeModGui extends GuiScreen{
	
	private LiteModMain speicher;
	private boolean GivingKey = false;
	private String valueToManupulate = "";
	private GuiChooseKeyButton key_add;
	private GuiChooseKeyButton key_sub;
	private GuiChooseKeyButton key_freeze;
	
	public TimeModGui(LiteModMain speicher){
		this.speicher = speicher;
	}
	
	public void initGui(){
		drawButtons();
	}
	
	public void actionPerformed(GuiButton b){	
		if(b.displayString.contains("back to game")){
			speicher.getMinecraft().displayGuiScreen(null);
		}else if(b.displayString.contains("waiting")){
			valueToManupulate = b.displayString.split("waiting")[0];
			GivingKey = true;
		}
	}
	/**
	 * Called if a Button is pressed
	 */
	public void drawButtons(){	
		
		GuiBooleanButton freezetime = new GuiBooleanButton(1, width/2-170, height/4-10, 150, 20, "Freeze-Time", ((TimeMod)speicher.getMod(ModData.TimeMod.name())).isFreezetime(), "freezetime", ModData.TimeMod, speicher,LiteModMain.lconfig.getData("TimeMod.freezetime").split(";"));
		
		key_add = new GuiChooseKeyButton(2, width/2-170, height/4+20, 150, 20, "Time-Add", ((TimeMod)speicher.getMod(ModData.TimeMod.name())).getAddtime(),LiteModMain.lconfig.getData("TimeMod.addtime").split(";"));
		key_sub = new GuiChooseKeyButton(3, width/2, height/4+20, 150, 20, "Time-Sub", ((TimeMod)speicher.getMod(ModData.TimeMod.name())).getSubtime(),LiteModMain.lconfig.getData("TimeMod.removetime").split(";"));
		key_freeze = new GuiChooseKeyButton(4, width/2, height/4-10, 150, 20, "Freeze-Time", ((TimeMod)speicher.getMod(ModData.TimeMod.name())).getFreezetimekey(),LiteModMain.lconfig.getData("TimeMod.choosefreeze").split(";"));
		SimpleSlider slider = new SimpleSlider(5, width/2, height/4+50, "Time-Multiplier",(((TimeMod)speicher.getMod(ModData.TimeMod.name())).getMultipl()/5), 150, 20, ModData.TimeMod, "Time-Multiplier", speicher,LiteModMain.lconfig.getData("TimeMod.multiplier").split(";"));
		//(int) ((50 / 100.0)*value)
		GuiBooleanButton enable = new GuiBooleanButton(7, width/2-170, height/4+50, 150, 20, "Enabled", ((TimeMod)speicher.getMod(ModData.TimeMod.name())).isEnabled(), "enable", ModData.TimeMod, speicher,LiteModMain.lconfig.getData("TimeMod.enable").split(";"));
		
		GuiChooseStringButton choosepos = new GuiChooseStringButton(7, width/2-170, height/4+80, 150, 20, "TimeInfo-Pos", GuiPositions.getPosList(), "timepos", ModData.TimeMod, speicher, GuiPositions.getPos(((TimeMod)speicher.getMod(ModData.TimeMod.name())).getPos()),LiteModMain.lconfig.getData("Main.choosepos").split(";"));
		GuiBooleanButton showFlyInfo = new GuiBooleanButton(8, width/2, height/4+80, 150, 20, "Show-TimeInfo", ((TimeMod)speicher.getMod(ModData.TimeMod.name())).isShowTimeInfo(), "showtime", ModData.TimeMod, speicher,LiteModMain.lconfig.getData("TimeMod.showinfo").split(";"));
		
		
		GuiButton back = new GuiButton(6, width/2-100,height-50 , "back to game");
		
		
		buttonList.add(choosepos);
		buttonList.add(showFlyInfo);
		buttonList.add(enable);
		buttonList.add(slider);
		buttonList.add(key_freeze);
		buttonList.add(key_sub);
		buttonList.add(key_add);
		buttonList.add(freezetime);
		buttonList.add(back);
	}
	/**
	 * Initialize Buttons and add them to the Button list
	 */
	protected void keyTyped(char c,int key){
		if(GivingKey){
			if(key != 65 && key != 1){
				//speicher.getMinecraft().thePlayer.playSound("mob.ghast.scream", 1.0F, 1.0F);	
				valueToManupulate = valueToManupulate.replace(" ", "");
				((TimeMod)speicher.getMod(ModData.TimeMod.name())).manupulateValue(valueToManupulate, key);
				
				if(valueToManupulate.equalsIgnoreCase("Time-Add")){
					key_add.setButtonkey(key);
				}else if(valueToManupulate.equalsIgnoreCase("Time-Sub")){
					key_sub.setButtonkey(key);
				}else if(valueToManupulate.equalsIgnoreCase("Freeze-Time")){
					key_freeze.setButtonkey(key);
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

