package com.yzhuang.twoeatone;

import com.yzhuang.twoeatone.Board.TEAM;

/**
 * @author YuanZhuang
 * @param xx the coordinate x on the board.
 * @param yy the coordinate y on the board.
 * @param team the side of team of the piece.
 * @next next piece of the team. -1 if the last.
 * @method setCoord set the coordinate of the piece.
 */
public class Piece {
    int x, y;
    private TEAM mTeam;
	
    public Piece(int xx, int yy, TEAM team){
        x = xx;
        y = yy;
        this.mTeam = team;
        return;
    }
	
    public void setCoord(int xx, int yy){
        x = xx;
        y = yy;
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
