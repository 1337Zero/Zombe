package me.zero.cc.Zero_lite.utils;

import net.minecraft.entity.Entity;

public class MarkedEntity{

	private Entity ent;
	private float r = 0.0F;
	private float g = 0.0F;
	private float b = 0.0F;
	private float alpha = 0.0F;
	
	public MarkedEntity(Entity ent,float r,float g,float b,float alpha){
		this.ent = ent;
		this.r = r;
		this.g = g;
		this.b = b;
		this.alpha = alpha;
	}

	public Entity getEnt() {
		return ent;
	}

	public void setEnt(Entity ent) {
		this.ent = ent;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
}
