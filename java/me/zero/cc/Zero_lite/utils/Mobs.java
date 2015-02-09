package me.zero.cc.Zero_lite.utils;

import com.mumfrey.liteloader.core.LiteLoader;

public enum Mobs {
	//The first value represent the Compiled name of the Class, the second is the name given from config
	
	abr("Chicken"),
	abs("Cow"),
	abt("Horse"),
	aby("Ocelot"),
	aca("Pig"),
	acl("Sheep"),
	abo("Bat"),
	abx("Mooshroomcow"),
	aco("Squid"),
	aqp("Villager"),
	acb("Rabbit"),
	aep("Creeper"),
	afw("Skeleton"),
	afy("Slime"),
	afa("Ghast"),
	agj("Zombie"),
	age("Spider"),
	aer("Enderman"),
	afo("ZombiePigZombie"),
	aeo("Cavespider"),
	aft("Silverfish"),
	aem("Lohe"),
	afl("Magma"),
	agi("Witch"),
	awe("Endermite"),
	afg("Guardian"),
	acu("Wolf"),
	cip("Player"),
	adw("Item");
	
	private String text;

	Mobs(String text) {
		this.text = text;
	}
	/**
	 * Returns the compiled Classname for a Class, if in development Enviroment it returns the given Namen without Entity
	 * @param mob xxx.getClass().getSimpleName() -> Classname
	 * @return String Name of the Mob
	 */
	public static String getClassNameFromString(String mob){
		if(mob != null){
			
			if(LiteLoader.isDevelopmentEnvironment()){
				return mob.replace("Entity", "");
			}
			
			for(Mobs m : Mobs.values()){
				if(m.name().equalsIgnoreCase(mob)){
					return m.text;
				}
			}			
		}		
		return null;
	}
	
}