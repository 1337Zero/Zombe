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
	  abx("Mushroomcow"), 
	  aco("Squid"), 
	  agp("Villager"), 
	  acb("Rabbit"), 
	  aep("Creeper"), 
	  afw("Skeleton"), 
	  afy("Slime"), 
	  afa("Ghast"), 
	  agj("Zombie"), 
	  age("Spider"), 
	  aer("Enderman"), 
	  afo("PigZombie"), 
	  aeo("Cavespider"), 
	  aft("Silverfish"), 
	  aem("Blaze"), 
	  afl("Magmacube"), 
	  agi("Witch"), 
	  aew("Endermite"), 
	  afg("Guardian"), 
	  acu("Wolf"), 
	  cip("Player"), 
	  adw("Item"), 
	  aff("Giant"), 
	  acq("Irongolem"), 
	  adb("Enderdragon"), 
	  adu("Boat"), 
	  aeg("Minecart"), 
	  aeh("Spawnerminecart"), 
	  aef("Hopperminecart"), 
	  aea("Chestminecart"), 
	  aej("Tntminecart"), 
	  aee("Furnaceminecart"), 
	  xk("Xp"), 
	  adf("Wither"), 
	  acn("Snowman");
	
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
