package com.yzhuang.twoeatone;

import com.yzhuang.twoeatone.Board.TEAM;

/**
 * @author Yuan Zhuang
 * @method setCoord set the coordinate of the piece.
 */
public class Piece extends Handle{
    int x;	//the x coordinate of the piece 
    int y;	//the y coordinate of the piece
    private TEAM mTeam;
	
    /**
     * Default constructor
     * @param xx x coordinate of the piece
     * @param yy y coordinate of the piece
     * @param team team side
     * @param xScreen x coordinate in the screen
     * @param yScreen y coordinate in the screen
     * @param il the default difference
     * @param size the size of the piece drawed on the screen
     * @param mainActivity main/drawing activity
     * @param board the board of the game
     */
    public Piece(int xx, int yy, TEAM team,int xScreen
    		, int yScreen, int il, int size, TwoEatOneMain mainActivity, Board board){
    	super(xScreen, yScreen, il, size, mainActivity, board, team);
        x = xx;
        y = yy;
        this.mTeam = team;
        return;
    }
	
    public void setCoord(int xx, int yy, int pxx, int pyy){
        x = xx;
        y = yy;
        super.setNewXY(pxx, pyy);
        return;
    }
	
    public TEAM getTeam(){
        return this.mTeam;
    }
    
    public int getPieceX(){
    	return x;
    }
    
    public int getPieceY(){
    	return y;
    }
    

}
