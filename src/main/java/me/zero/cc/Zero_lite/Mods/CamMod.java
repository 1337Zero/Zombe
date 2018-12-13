package me.zero.cc.Zero_lite.Mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class CamMod implements Mod {

	private boolean enabled = false;
	private Minecraft minecraft = Minecraft.getMinecraft();
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void use() {
		if(enabled){
		}
	}

	@Override
	public String getName() {
		return ModData.CAMMOD.name();
	}

	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public void manupulateValue(String ValueToManupulate, double value) {
		// TODO Auto-generated method stub
	}

	@Override
	public void manupulateValue(String valueToManupulate, boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void manupulateValue(String valueToManupulate, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getOn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GuiScreen drawGui() {
		return null;
	}

}
