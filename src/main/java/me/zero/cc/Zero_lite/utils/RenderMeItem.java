package me.zero.cc.Zero_lite.utils;

import net.minecraft.item.ItemStack;

public class RenderMeItem {

	private int x, y;
	private ItemStack stack;

	public RenderMeItem(int x, int y, ItemStack stack) {
		super();
		this.x = x;
		this.y = y;
		this.stack = stack;
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

	public ItemStack getStack() {
		return stack;
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
	}

}
