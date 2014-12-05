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
						minecraft.theWorld.setWorldTime((freezedtime)-24000);	
					}else{
						minecraft.theWorld.setWorldTime((freezedtime));	
					}				
				}else{
					if((realtime + time2add) >= 24000 ){
						minecraft.theWorld.setWorldTime((realtime + time2add)-24000);
					}else{
						minecraft.theWorld.setWorldTime(realtime + time2add);
					}
				}	
				this.yield();
		}
	}
	public void settime2add(long time2add){
		this.time2add = time2add;
	}
	public boolean isWork() {
		return work;
	}

	public void setWork(boolean work) {
		this.work = work;
	}


	public long getFreezedtime() {
		return freezedtime;
	}

	public void setFreezedtime(long freezedtime) {
		this.freezedtime = freezedtime;
	}


	public boolean isFreezetime() {
		return freezetime;
	}


	public void setFreezetime(boolean freezetime) {
		this.freezetime = freezetime;
	}

	public int getTimemultipliere() {
		return timemultipliere;
	}

	public void setTimemultipliere(int timemultipliere) {
		this.timemultipliere = timemultipliere;
	}
}
