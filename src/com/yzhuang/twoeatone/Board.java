/**
 * 
 */
package com.yzhuang.twoeatone;
import java.util.*;
/**
 * @author Yuan Zhuang
 * @param size the size of the board, usually 5.
 * @param black the lower side team as 0.
 * @param white the upper side team as 1.
 */
public class Board {
    static final int SIZE = 5;
    static final int BLACK = 0;
    static final int WHITE = 1;
    
    ArrayList<Piece> black = new ArrayList<>();
    ArrayList<Piece> white = new ArrayList<>();
    Board(){
        int i;
        for(i=0;i<size;i++){
            black.add(new Piece(1,i+1,BLACK));
            white.add(new Piece(SIZE,i+1,WHITE));
        }
        return;
    }
	
    boolean addNew(int team, int xx, int yy){
        if(this.isPieceOnPoint(xx, yy)||xx<0||xx>=size||yy<0||yy>=size)
            return false;
        else{
            if(team==BLACK)
                if(black.size()>=size)
                    return false;
                else{
                    black.add(new Piece(xx,yy,BLACK));
                    return true;
                }
            else if(team==WHITE)
                if(white.size()>=size)
                    return false;
                else{
                    white.add(new Piece(xx,yy,WHITE));
                    return true;
                }		
        }
        return false;
    }
	
    void movePiece(int team, int idx, int xNew, int yNew){
        Piece aPiece;
        if(team==BLACK)
            aPiece = black.get(idx);
        else
            aPiece = white.get(idx);
        aPiece.setCoord(xNew, yNew);
        return;
    }

    ArrayList possibleKill(int team, int idx, int xNew, int yNew){
        ArrayList<Piece> teammate, opposite;
        ArrayList<Integer> possibleKillList = new ArrayList<Integer>();
        int oTeam;
        boolean xPossibleKill = false;
        boolean yPOssibleKill = false;
        int countTeammateOnX = 0;
        int countTeammateOnY = 0;
        int idxTeammateX = idx;
        int idxTeammateY = idx;
        int countOppositeOnX = 0;
        int countOppositeOnY = 0;
        int idxOppositeX = -1;
        int idxOppositeY = -1;
        if(team==BLACK){
            teammate = black;
            opposite = white;
            oTeam = WHITE;
        }
        else{
            teammate = white;
            opposite = black;
            oTeam = BLACK;
        }
        for(int i=0; i<teammate.size(); i++){
            if(i!=idx)
            {
                if(teammate.get(i).getPieceX() == xNew) {
                    countTeammateOnX++;
                    if((teammate.get(i).getPieceY() == yNew+1)||(teammate.get(i).getPieceY() == yNew-1))
                        idxTeammateX = i;
                    else
                        countTeammateOnX++;
                }
                if(teammate.get(i).getPieceY() == yNew){
                    countTeammateOnY++;
                    if((teammate.get(i).getPieceX() == xNew+1)||(teammate.get(i).getPieceX() == xNew-1))
                        idxTeammateY = i;
                    else
                        countTeammateOnY++;
                }
            }
            if( ((countTeammateOnX>1)&&(countTeammateOnY>1)) || ((countTeammateOnX==0)&&(countTeammateOnY==0)) )
                return possibleKillList;
        }
        for(i=0; i<opposite.size(); i++){
            if(TeammateOnX==1){
                if (opposite.get(i).getPieceX() == xNew && countOppositeOnX==0){
                    countOppositeOnX++;
                    if (opposite.get(i).getPieceY() == yNew+1 || opposite.get(i).getPieceY()==yNew-1){
                        possibleKillList.add(i);
                        idxOppositeX = i;                        
                    }
                    else if(opposite.get(i).getPieceY() == teammate.get(i).getPieceY()+1 || opposite.get(i).getPieceY()==teammate.get(i).getPieceY()-1){
                        possibleKillList.add(i);
                        idxOppositeX = i;
                    }
                    else{
                        if (idxOppositeX!=-1)
                    }
                }
                
            }
            if(TeammateOnY==1){
                if (opposite.get(i).getPieceY() == yNew){
                }
            }
        }
        return possibleKillList;
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
        if(Math.abs(yNew-yOld)==0&&Math.abs(xNew-xOld)==0)
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
        if(team==BLACK)
            return black.size();
        else
            return white.size();
    }
	
    int getPieceX(int team, int idx){
        if(team==BLACK)
            return black.get(idx).x;
        else
            return white.get(idx).x;
    }
	
    int getPieceY(int team, int idx){
        if(team==BLACK)
            return black.get(idx).y;
        else
            return white.get(idx).y;
    }
	
    void killPieces(int pieceIdx, int team){
        if(team==BLACK)
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
