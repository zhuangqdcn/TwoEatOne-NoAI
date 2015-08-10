package com.yzhuang.twoeatone;



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
	private int team;
	
	Piece(int xx, int yy, int teamIdx){
		x = xx;
		y = yy;
		this.team = teamIdx;
		return;
	}
	
	void setCoord(int xx, int yy){
		x = xx;
		y = yy;
		return;
	}
	
	int getTeam(){
		return this.team;
	}

}
