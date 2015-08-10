/**
 * 
 */
package com.yzhuang.twoeatone;
import java.util.*;
/**
 * @author YuanZhuang
 * @param size the size of the board, usually 5.
 * @param black the lower side team as 0.
 * @param white the upper side team as 1.
 */
public class Board {
	static int size=5;
	ArrayList<Piece> black = new ArrayList<>();
	ArrayList<Piece> white = new ArrayList<>();
	Board(){
		int i;
		for(i=0;i<size;i++){
			black.add(new Piece(1,i+1,0));
			white.add(new Piece(size,i+1,1));
		}
		return;
	}
	
	boolean addNew(int team, int xx, int yy){
		if(this.isPieceOnPoint(xx, yy)||xx<0||xx>=size||yy<0||yy>=size)
			return false;
		else{
			if(team==0)
				if(black.size()>=size)
					return false;
				else{
					black.add(new Piece(xx,yy,0));
					return true;
				}
			else if(team==1)
				if(white.size()>=size)
					return false;
				else{
					white.add(new Piece(xx,yy,1));
					return true;
				}		
		}
		return false;
	}
	
	void movePiece(int team, int idx, int xNew, int yNew){
		Piece aPiece;
		if(team==0)
			aPiece = black.get(idx);
		else
			aPiece = white.get(idx);
		aPiece.setCoord(xNew, yNew);
		
		return;
	}
	
	int canKill(int team, int idx, int xNew, int yNew){
		ArrayList<Piece> teammate, opposite;
		ArrayList<Integer> possibleKill = new ArrayList<Integer>();
		int oTeam;
		if(team==0){
			teammate = black;
			opposite = white;
			oTeam = 1;
		}
		else{
			teammate = white;
			opposite = black;
			oTeam = 0;
		}
		for(int i=0;i<opposite.size();i++){
			if(this.getPieceX(oTeam, i)==xNew+2&&this.getPieceY(oTeam, i)==yNew)
				possibleKill.add(i);
			else if(this.getPieceX(oTeam, i)==xNew-2&&this.getPieceY(oTeam, i)==yNew)
				possibleKill.add(i);
			else if(this.getPieceX(oTeam, i)==xNew&&this.getPieceY(oTeam, i)==yNew+2)
				possibleKill.add(i);
			else if(this.getPieceX(oTeam, i)==xNew&&this.getPieceY(oTeam, i)==yNew-2)
				possibleKill.add(i);
		}
		return -1;
	}
	
	boolean isValidMove(int team, int idxPiece, int xNew, int yNew){
		int xOld = getPieceX(team, idxPiece);
		int yOld = getPieceY(team, idxPiece);
		if(xNew<1||xNew>size)
			return false;
		if(yNew<1||yNew>size)
			return false;
		if(Math.abs(xNew-xOld)>1)
			return false;
		if(Math.abs(yNew-yOld)>1)
			return false;
		if(Math.abs(yNew-yOld)==1&&Math.abs(xNew-xOld)==1)
			return false;
		if(this.isPieceOnPoint(xNew, yNew))
			return false;
		return true;
	}
	
	boolean isBlackVictory(){
		if(white.size()==1)
			return true;
		else
			return false;
	}
	
	boolean isWhiteVictory(){
		if(black.size()==1)
			return true;
		else
			return false;
	}
	
	int remainingPieces(int team){
		if(team==0)
			return black.size();
		else
			return white.size();
	}
	
	int getPieceX(int team, int idx){
		if(team==0)
			return black.get(idx).x;
		else
			return white.get(idx).x;
	}
	
	int getPieceY(int team, int idx){
		if(team==0)
			return black.get(idx).y;
		else
			return white.get(idx).y;
	}
	
	private void killPieces(int pieceIdx, int team){
		if(team==0)
			black.remove(pieceIdx);
		else
			white.remove(pieceIdx);
		return;
	}

	private boolean isPieceOnPoint(int xx, int yy){
		for ( Piece pieces : white ){
			if (pieces.x==xx&&pieces.y==yy){
				return true;
			}
		}
		for ( Piece pieces : black ){
			if (pieces.x==xx&&pieces.y==yy){
				return true;
			}
		}
		return false;
	}
}
