package com.yzhuang.twoeatone;

import com.yzhuang.twoeatone.Board.TEAM;

/* @Author Yuan Zhuang
 * Changes from https://processing.org/examples/handles.html
 * 
 */
public class Handle {
	private int x, y;
	private int boxx, boxy;
	private int stretch;
	private int size;
	private boolean over;
	private boolean press;
	private boolean locked = false;

	private TwoEatOneMain mMainActivity;

	Handle(int ix, int iy, int il, int is,  TwoEatOneMain mainActivity, TEAM team) {
		x = ix;
		y = iy;
		stretch = il;
		size = is;
		boxx = x+stretch - size/2;
		boxy = y - size/2;
		mMainActivity = mainActivity;
	}

	void update() {
		boxx = x+stretch;
		boxy = y - size/2;

		if (press) {
			stretch = lock(mMainActivity.mouseX-mMainActivity.width/2-size/2, 0, mMainActivity.width/2-size-1);
		}
	}

	void overEvent() {
		if (overRect(boxx, boxy, size, size)) {
			over = true;
		} else {
			over = false;
		}
	}

	void pressEvent() {
		if (over && mMainActivity.mousePressed || locked) {
			press = true;
			locked = true;
		} else {
			press = false;
		}
	}

	void releaseEvent() {
		locked = false;
	}

	void display() {
		mMainActivity.stroke(0);
		mMainActivity.ellipse(boxx, boxy, size, size);
		if (over || press) {
			mMainActivity.line(boxx, boxy, boxx+size, boxy+size);
			mMainActivity.line(boxx, boxy+size, boxx+size, boxy);
		}

	}
	
	boolean overRect(int x, int y, int width, int height) {
		if (mMainActivity.mouseX >= x && mMainActivity.mouseX <= x+width && 
				mMainActivity.mouseY >= y && mMainActivity.mouseY <= y+height) {
			return true;
		} else {
			return false;
		}
	}

	int lock(int val, int minv, int maxv) { 
		return  Math.min(Math.max(val, minv), maxv); 
	}
}


