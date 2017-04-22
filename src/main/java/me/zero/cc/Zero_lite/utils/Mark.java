package me.zero.cc.Zero_lite.utils;

import net.minecraft.entity.Entity;

public class Mark implements Markables{

	private float r = 0.0F;
	private float g = 0.0F;
	private float b = 0.0F;
	private float alpha = 0.0F;
	private double x = 0;
	private double y = 0;
	private double z = 0;
	private double maxheight = 0;
	private double lastz = 0;
	private double lasty = 0;
	private double lastx = 0;
	private Entity Entity;
	
	public Mark(float r,float g,float b,float alpha,double x,double y, double z){
		this.r = r;
		this.g = g;
		this.b = b;
		this.alpha = alpha;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Mark(float r,float g,float b,float alpha,double x,double y, double z,double maxheight,Entity entity){
		this.r = r;
		this.g = g;
		this.b = b;
		this.alpha = alpha;
		this.x = x;
		this.y = y;
		this.z = z;
		this.maxheight = maxheight;
		this.Entity = entity;
	}
	public Mark(float r,float g,float b,float alpha,double lastx,double lasty, double lastz,double x,double y,double z){
		this.r = r;
		this.g = g;
		this.b = b;
		this.alpha = alpha;
		this.x = x;
		this.y = y;
		this.z = z;
		this.lastx = lastx;
		this.lasty = lasty;
		this.lastz = lastz;
	}
	/**
	 * Returns the float value for Red
	 * @return Float
	 */
	public float getR() {
		return r;
	}
	/**
	 * Sets the float value for Red
	 * @param Float
	 */
	public void setR(float r) {
		this.r = r;
	}
	/**
	 * Returns the float value for Green
	 * @return Float
	 */
	public float getG() {
		return g;
	}
	/**
	 * Sets the float value for Green
	 * @param Float
	 */
	public void setG(float g) {
		this.g = g;
	}
	/**
	 * Returns the float value for Blue
	 * @return Float
	 */
	public float getB() {
		return b;
	}
	/**
	 * Sets the float value for Blue
	 * @param Float
	 */
	public void setB(float b) {
		this.b = b;
	}
	/**
	 * Returns the float value for Alpha
	 * @return Float
	 */
	public float getAlpha() {
		return alpha;
	}
	/**
	 * Sets the float value for Alpha
	 * @param Float
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	/**
	 * Returns the X Coordinate of the Block
	 * @return Double
	 */
	public double getX() {
		return x;
	}
	/**
	 * Sets the X Coordinate of the Block
	 * @param Double
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * Returns the Y Coordinate of the Block
	 * @return Double
	 */
	public double getY() {
		return y;
	}
	/**
	 * Sets the Y Coordinate of the Block
	 * @param Double
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * Returns the Z Coordinate of the Block
	 * @return Double
	 */
	public double getZ() {
		return z;
	}
	/**
	 * Sets the Z Coordinate of the Block
	 * @param Double
	 */
	public void setZ(double z) {
		this.z = z;
	}
	public double getMaxheight() {
		return maxheight;
	}
	public void setMaxheight(double maxheight) {
		this.maxheight = maxheight;
	}
	public double getLastz() {
		return lastz;
	}
	public void setLastz(double lastz) {
		this.lastz = lastz;
	}
	public double getLasty() {
		return lasty;
	}
	public void setLasty(double lasty) {
		this.lasty = lasty;
	}
	public double getLastx() {
		return lastx;
	}
	public void setLastx(double lastx) {
		this.lastx = lastx;
	}
	@Override
	public String toString() {
		return "Mark [r=" + r + ", g=" + g + ", b=" + b + ", alpha=" + alpha
				+ ", x=" + x + ", y=" + y + ", z=" + z + ", maxheight="
				+ maxheight + ", lastz=" + lastz + ", lasty=" + lasty
				+ ", lastx=" + lastx + "]";
	}
	public Entity getEntity() {
		return Entity;
	}
	public void setEntity(Entity entity) {
		Entity = entity;
	}
	@Override
	public void draw(float partialTicks) {
		throw new UnsupportedOperationException("calling Mark.draw() is not supported...");
	}	
}
