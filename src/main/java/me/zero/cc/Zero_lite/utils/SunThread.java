package me.zero.cc.Zero_lite.utils;

import net.minecraft.client.Minecraft;

public class SunThread extends Thread{

	private long time2add = 0;
	private Minecraft minecraft;
	private boolean work = true;	
	private long lasttick = System.currentTimeMillis();
	private long realtime =0;
	private long freezedtime = 0;
	private boolean freezetime = false;
	private int timemultipliere = 1;
	
	public SunThread(Minecraft minecraft,long time2add,long realtime){
		this.minecraft = minecraft;
		this.time2add = time2add;
		this.realtime = realtime;
	}	
	
	public void run(){
		while(!isInterrupted()){
				if(System.currentTimeMillis()-lasttick >=50){
					realtime = realtime + timemultipliere;
					if(realtime >= 24000){
						realtime = realtime -24000;
					}
					lasttick = System.currentTimeMillis();
				}
				if(freezetime){
					if((freezedtime) >= 24000){
						minecraft.world.setWorldTime((freezedtime)-24000);	
					}else{
						minecraft.world.setWorldTime((freezedtime));	
					}				
				}else{
					if((realtime + time2add) >= 24000 ){
						minecraft.world.setWorldTime((realtime + time2add)-24000);
					}else{
						minecraft.world.setWorldTime(realtime + time2add);
					}
				}	
				this.yield();
		}
	}
	/**
	 * Sets the time, this thread should add
	 * @param long
	 */
	public void settime2add(long time2add){
		this.time2add = time2add;
	}
	/**
	 * Returns if this thread should do something
	 * @return Boolean
	 */
	public boolean isWork() {
		return work;
	}
	/**
	 * Gets if the Thread is working
	 * @param String
	 */
	public void setWork(boolean work) {
		this.work = work;
	}
	/**
	 * Returns the time which is freezed
	 * @return long
	 */
	public long getFreezedtime() {
		return freezedtime;
	}
	/**
	 * Set the time which is freezed
	 * @param long
	 */
	public void setFreezedtime(long freezedtime) {
		this.freezedtime = freezedtime;
	}
	/**
	 * Return if the Time is freezed
	 * @return Boolean
	 */
	public boolean isFreezetime() {
		return freezetime;
	}
	/**
	 * Set if the time is freezed
	 * @param Boolean
	 */
	public void setFreezetime(boolean freezetime) {
		this.freezetime = freezetime;
	}
	/**
	 * Returns the Multiplier of the Time
	 * @return Integer
	 */
	public int getTimemultipliere() {
		return timemultipliere;
	}
	/**
	 * Sets the Time Multiplier
	 * @param int
	 */
	public void setTimemultipliere(int timemultipliere) {
		this.timemultipliere = timemultipliere;
	}
}
