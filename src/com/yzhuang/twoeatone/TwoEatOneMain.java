package com.yzhuang.twoeatone;

import java.awt.Color;

import processing.core.*;
import controlP5.*;

import com.yzhuang.twoeatone.Board.TEAM;
/** 
 * @Author Yuan Zhuang
 * @Note This application is using Processing 3. 
 * 
 */

public class TwoEatOneMain extends PApplet {
	private int[][][] mPoints = new int[GUIConstants.BOARD_SIZE][GUIConstants.BOARD_SIZE][2];
	private Board mBoard;
	private ControlP5 mCp5Main;
	
	public void setup() {
		//Add setup code for MyPApplet
		
		initialPoints();
		mBoard = new Board(mPoints, GUIConstants.PIECE_SIZE, 0, this, TEAM.WHITE);
		
		noStroke();
		PFont pfont = createFont("Arial",15,true); // use true/false for smooth/no-smooth
		ControlFont font = new ControlFont(pfont,241);
		mCp5Main = new ControlP5(this);

		mCp5Main.addButton("newGameWhite").setPosition(10,20)
			.setSize(150, 60).setCaptionLabel("New Game(White)");
		mCp5Main.getController("newGameWhite").getCaptionLabel()
	     .setFont(font).toUpperCase(false).setSize(15);
		mCp5Main.addButton("newGameBlack").setPosition(220,20)
			.setSize(150, 60).setCaptionLabel("New Game(Black)");
		mCp5Main.getController("newGameBlack").getCaptionLabel()
	     	.setFont(font).toUpperCase(false).setSize(15);
		mCp5Main.addButton("exitGame").setPosition(430,20)
			.setSize(150, 60).setCaptionLabel("Exit Game");
		mCp5Main.getController("exitGame").getCaptionLabel()
			.setFont(font).toUpperCase(false).setSize(15);
		
	}
	
	public void settings(){
		size(GUIConstants.CANVAS_WIDTH,GUIConstants.CANVAS_HEIGHT);
	}
	
	
	public void draw() {
		//Add drawing code for MyPApplet
		//Draw board
		clear();
		background(255);			//set canvas color	
		drawBoard();
		textSize(40);
		if(mBoard.isBlackVictory()){
			clear();
			background(255);
			fill(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue());
			text("Black wins!",GUIConstants.CANVAS_WIDTH/2-100, GUIConstants.CANVAS_HEIGHT/2-10);
			showAbout(GUIConstants.CANVAS_HEIGHT/2+30);
		}
		else if(mBoard.isWhiteVictory()){
			clear();
			background(255);
			fill(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue());
			text("White wins!",GUIConstants.CANVAS_WIDTH/2-100, GUIConstants.CANVAS_HEIGHT/2-10);
			showAbout(GUIConstants.CANVAS_HEIGHT/2+30);
		}
		else{
			mBoard.updateAndDisplayAll();
		}
		textSize(32);
		switch(mBoard.getNextTurn()){
		case BLACK:
			fill(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue());
			text("Next turn is black.",10, GUIConstants.CANVAS_HEIGHT-10);
			break;
		case WHITE:
			fill(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue());
			text("Next turn is white.",10, GUIConstants.CANVAS_HEIGHT-10);
			break;	
		}
	}
	
	public void mouseReleased()  {
		mBoard.releaseAll();
		
	}
	
	public void drawBoard(){
		stroke(0); //set pen color
		for(int i = 0; i<GUIConstants.BOARD_SIZE ; i++){
			line(mPoints[i][0][0],mPoints[i][0][1]
					,mPoints[i][GUIConstants.BOARD_SIZE-1][0],mPoints[i][GUIConstants.BOARD_SIZE-1][1] );
			line(mPoints[0][i][0],mPoints[0][i][1]
					,mPoints[GUIConstants.BOARD_SIZE-1][i][0],mPoints[GUIConstants.BOARD_SIZE-1][i][1]);
		}
	}

	public static void main(String[] args) {
		PApplet.main(new String[] {"com.yzhuang.twoeatone.TwoEatOneMain"});
	}
	
	public void newGameWhite(){
		newGame(TEAM.WHITE);
	}
	
	public void newGameBlack(){
		newGame(TEAM.BLACK);
	}
	
	private void showAbout( int y){
		textSize(20);
		fill(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());
		text("Two eat one is a traditional board game in Qingdao, China. "
				+ "The no-AI version is developed by Yuan Zhuang under GPL v2. "
				+ "The AI version is under developing by collaboration between my friends and me.", 100,y,400,y+200 );
	}
	
	public void exitGame(){
		System.exit(0);
	}
	
	public void newGame(TEAM team){
		mBoard = new Board(mPoints, GUIConstants.PIECE_SIZE, 0, this, team);
	}
	
	private void initialPoints(){
		int baseTop = GUIConstants.CANVAS_TOP_MARGIN;
		int baseBottom = GUIConstants.CANVAS_HEIGHT - GUIConstants.CANVAS_BOTTOM_MARGIN;
		int baseLeft = GUIConstants.CANVAS_LEFT_MARGIN;
		int baseRight = GUIConstants.CANVAS_WIDTH - GUIConstants.CANVAS_RIGHT_MARGIN;
		int dVertical = (baseBottom - baseTop) / 4;
		int dHorizontal = (baseRight - baseLeft) / 4;
		for(int i=0; i< GUIConstants.BOARD_SIZE;i++){
			for(int j=0; j<GUIConstants.BOARD_SIZE;j++){
				mPoints[i][j][0] = baseLeft + i * dHorizontal;
				mPoints[i][j][1] = baseTop + j * dVertical;
			}
		}
	}
	
	private final class GUIConstants{
		public static final int CANVAS_WIDTH = 600;
		public static final int CANVAS_HEIGHT = 650;
		public static final int CANVAS_LEFT_MARGIN = 100;
		public static final int CANVAS_RIGHT_MARGIN = 100;
		public static final int CANVAS_TOP_MARGIN = 150;
		public static final int CANVAS_BOTTOM_MARGIN = 100;
		public static final int BOARD_SIZE = 5;
		public static final int PIECE_SIZE = 
				(CANVAS_WIDTH-CANVAS_LEFT_MARGIN - CANVAS_RIGHT_MARGIN)/4/2*3/3;
	}
}
