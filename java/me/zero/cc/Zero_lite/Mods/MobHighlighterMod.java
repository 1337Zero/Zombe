package me.zero.cc.Zero_lite.Mods;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mumfrey.liteloader.core.LiteLoader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;

public class MobHighlighterMod implements Mod{

	private boolean enabled = false;
	private Minecraft minecraft;
	private double time;
	
	public MobHighlighterMod(Minecraft minecraft) {
		this.minecraft = minecraft;
		time = System.currentTimeMillis();
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void use() {
		if(LiteLoader.isDevelopmentEnvironment()){			
			//if(System.currentTimeMillis() - time > 10000){
				List<Entity> mobs= Minecraft.getMinecraft().theWorld.loadedEntityList;
				time = System.currentTimeMillis();
				//GL11.glBegin(GL11.GL_LINES);
				for(Entity mob : mobs){
					if(mob.toString().contains("EntitySheep")){
						//draw with GL11
						//GL11.glColor3f(5, 5, 5);
			          //  GL11.glVertex3f(dx,dy,dz);
			            //GL11.glVertex3f(dx,dy+(1 || !(mob instanceof EntityLiving) ? getEntityHeight(ent) : 2.0f),dz);
			           // GL11.glEnd();
					}
				}
			//}			
		}
	}

	@Override
	public String getName() {
		return ModData.MobHighLighter.name();
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GuiScreen drawGui() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void manupulateValue(String ValueToManupulate, int value) {
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

}
