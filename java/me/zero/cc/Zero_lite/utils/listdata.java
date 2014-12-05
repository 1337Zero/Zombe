package me.zero.cc.Zero_lite.utils;

public class listdata {

	private int x = 0;
	private int y = 0;
	private String label = "";
	private String path = "";
	
	public listdata(int x, int y,String path){
		this.x = x;
		this.y = y;
		this.label = path.substring(0, 10);
		this.path = path;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
