package com.yzhuang.twoeatone;

import java.awt.Color;

import com.yzhuang.twoeatone.Board.TEAM;

/**
 * @Author Yuan Zhuang
 * @see https://processing.org/examples/handles.html
 * 
 **/
public class Handle {
	private int x, y;
	private int boxx, boxy;
	private int stretchX;
	private int stretchY;
	private int size;
	private boolean over;
	private boolean press;
	private boolean locked = false;
	private boolean othersLocked = false;
	private TEAM mTeam;
	private TwoEatOneMain mMainActivity;
	private Board mBoard;
	
	private static Color BLACK_HIGHLIGHT = new Color(64,64,64);
	private static Color WHITE_HIGHLIGHT = new Color(192,192,192);

	Handle(int ix, int iy, int il, int is,  TwoEatOneMain mainActivity, Board board, TEAM team) {
		x = ix;
		y = iy;
		stretchX = il;
		stretchY = il;
		size = is;
		boxx = x;
		boxy = y;
		mMainActivity = mainActivity;
		mTeam = team;
		mBoard = board;
	}
	
	void update() {
		boxx = x + stretchX;
		boxy = y + stretchY;
		othersLocked = mBoard.checkOtherLocks();
		if(!othersLocked){
			overEvent();
			pressEvent();
		}
		if (press) {
			stretchX = mMainActivity.mouseX - x;
			stretchY = mMainActivity.mouseY - y;
		}
		
		
	}

	void overEvent() {
		if (overCircle(boxx, boxy, size)) {
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
		int[][] newXY = mBoard.findNearestXY(boxx, boxy);
		if(mBoard.isValidMove(mTeam, this, newXY[1][0], newXY[1][1])){
			boxx = newXY[0][0];
			boxy = newXY[0][1];
			stretchX = boxx - x;
			stretchY = boxy - y;
			mBoard.movePiece(this,newXY[1][0], newXY[1][1]);
		}
		else{
			boxx = x;
			boxy = y;
			stretchX = boxx - x;
			stretchY = boxy - y;
		}
	}

	void display() {
		mMainActivity.stroke(0);
		switch(mTeam){
		case BLACK:
			mMainActivity.fill(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());
			break;
		case WHITE:
			mMainActivity.fill(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
			break;
		default:
			break;
		}
		mMainActivity.ellipse(boxx, boxy, size, size);
		if(over||press){
			//TODO need to find a better way to represent this
			switch(mTeam){
			case BLACK:
				mMainActivity.fill(BLACK_HIGHLIGHT.getRed(),BLACK_HIGHLIGHT.getGreen(),BLACK_HIGHLIGHT.getBlue());
				break;
			case WHITE:
				mMainActivity.fill(WHITE_HIGHLIGHT.getRed(),WHITE_HIGHLIGHT.getGreen(),WHITE_HIGHLIGHT.getBlue());
				break;
			}
			//mMainActivity.fill(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue());			
			mMainActivity.ellipse(boxx, boxy, size, size);
		}
	}
	
	void setNewXY(int newX, int newY){
		x = newX;
		y = newY;
		stretchX = 0;
		stretchY = 0;
		boxx = x;
		boxy = y;
	}
	
	boolean overRect(int x, int y, int width, int height) {
		if (mMainActivity.mouseX >= x && mMainActivity.mouseX <= x+width && 
				mMainActivity.mouseY >= y && mMainActivity.mouseY <= y+height) {
			return true;
		} else {
			return false;
		}
	}
	boolean overCircle(int ix, int iy, int diameter) {
		float disX = ix - mMainActivity.mouseX;
		float disY = iy - mMainActivity.mouseY;
		if (Math.sqrt(disX*disX + disY*disY) < diameter/2 ) {
			return true;
		} else {
			return false;
		}
	}
	int lock(int val, int minv, int maxv) { 
		return  Math.min(Math.max(val, minv), maxv); 
	}
	
	boolean getLocked(){
		return locked;
	}
}


