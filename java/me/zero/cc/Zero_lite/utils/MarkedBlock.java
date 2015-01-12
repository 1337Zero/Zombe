package me.zero.cc.Zero_lite.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;

public class MarkedBlock{

	private Block ent;
	private float r = 0.0F;
	private float g = 0.0F;
	private float b = 0.0F;
	private float alpha = 0.0F;
	private double x = 0;
	private double y = 0;
	private double z = 0;
	
	public MarkedBlock(Block ent,float r,float g,float b,float alpha,double x,double y, double z){
		this.ent = ent;
		this.r = r;
		this.g = g;
		this.b = b;
		this.alpha = alpha;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Block getEnt() {
		return ent;
	}

	public void setEnt(Block ent) {
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

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
}
