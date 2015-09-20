package com.yzhuang.twoeatone;

import processing.core.*;
/* @Author Yuan Zhuang
 * This application is using Processing 3. 
 * 
 */

public class TwoEatOneMain extends PApplet {
	private int[][][] mPoints = new int[GUIConstants.BOARD_SIZE][GUIConstants.BOARD_SIZE][2];
	Board mBoard;
	
	public void setup() {
		//Add setup code for MyPApplet
		background(255);			//set canvas color	
		initialPoints();
	}
	
	public void settings(){
		size(GUIConstants.CANVAS_WIDTH,GUIConstants.CANVAS_HEIGHT);
	}
	
	
	public void draw() {
		//Add drawing code for MyPApplet
		//Draw board
		drawBoard();
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
	
	private void initialPoints(){
		int baseTop = GUIConstants.CANVAS_TOP_MARGIN;
		int baseBottom = GUIConstants.CANVAS_HEIGHT - GUIConstants.CANVAS_BOTTOM_MARGIN;
		int baseLeft = GUIConstants.CANVAS_LEFT_MARGIN;
		int baseRight = GUIConstants.CANVAS_WIDTH - GUIConstants.CANVAS_RIGHT_MARGIN;
		int dVertical = (baseBottom - baseTop) / 4;
		int dHorizontal = (baseRight - baseLeft) / 4;
		for(int i=0; i< GUIConstants.BOARD_SIZE;i++){
			for(int j=0; j<GUIConstants.BOARD_SIZE;j++){
				mPoints[i][j][0] = baseTop + i * dHorizontal;
				mPoints[i][j][1] = baseTop + j * dVertical;
			}
		}
	}
	
	private final class GUIConstants{
		public static final int CANVAS_WIDTH = 600;
		public static final int CANVAS_HEIGHT = 600;
		public static final int CANVAS_LEFT_MARGIN = 100;
		public static final int CANVAS_RIGHT_MARGIN = 100;
		public static final int CANVAS_TOP_MARGIN = 100;
		public static final int CANVAS_BOTTOM_MARGIN = 100;
		public static final int BOARD_SIZE = 5;
		public static final int PIECE_SIZE = 
				(CANVAS_WIDTH-CANVAS_LEFT_MARGIN - CANVAS_RIGHT_MARGIN)/4/2*2/3;
	}
}
