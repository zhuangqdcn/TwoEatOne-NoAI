/**
 * 
 */
package com.yzhuang.twoeatone;
import java.util.*;
/**
 * @author Yuan Zhuang
 * @param SIZE the size of the board, usually 5.
 * @method possibleKills returns the possible piece that would be kill by xNew yNew move
 * @method addNew add a new piece, if piece>SIZE, return false
 */
public class Board {
    static final int SIZE = 5;
    public enum TEAM{
        WHITE, BLACK}
    
    ArrayList<Piece> mBlack;
    ArrayList<Piece> mWhite;
    
    Board(){
        int i;
        mBlack = new ArrayList<>();
        mWhite = new ArrayList<>();
        for(i=0;i<SIZE;i++){
            mBlack.add(new Piece(1,i+1,TEAM.BLACK));
            mWhite.add(new Piece(SIZE,i+1,TEAM.WHITE));
        }
        return;
    }
	
    Board(ArrayList<Piece> black, ArrayList<Piece> white){
    	mBlack = black;
    	mWhite = white;
    }
    
    boolean addNew(TEAM team, int xx, int yy){
        if(this.isPieceOnPoint(xx, yy)||xx<0||xx>=SIZE||yy<0||yy>=SIZE)
            return false;
        else{
            if(team==TEAM.BLACK)
                if(mBlack.size()>=SIZE)
                    return false;
                else{
                    mBlack.add(new Piece(xx,yy,TEAM.BLACK));
                    return true;
                }
            else if(team==TEAM.WHITE)
                if(mWhite.size()>=SIZE)
                    return false;
                else{
                    mWhite.add(new Piece(xx,yy,TEAM.WHITE));
                    return true;
                }		
        }
        return false;
    }
	
    void movePiece(TEAM team, int idx, int xNew, int yNew){
        Piece aPiece;
        if(team==TEAM.BLACK)
            aPiece = mBlack.get(idx);
        else
            aPiece = mWhite.get(idx);
        aPiece.setCoord(xNew, yNew);
        return;
    }

    public ArrayList<Piece> possibleKills(TEAM team, int idx, int xNew, int yNew){
        ArrayList<Piece> teammateOnX = new ArrayList<>();
        ArrayList<Piece> oppositeOnX = new ArrayList<>();
        ArrayList<Piece> teammateOnY = new ArrayList<>();
        ArrayList<Piece> oppositeOnY = new ArrayList<>();
        ArrayList<Piece> possibleKillList = new ArrayList<>();
        ArrayList<Piece> teammate;
        ArrayList<Piece> opposite;
        TEAM oppositeTeam;
        if(team==TEAM.BLACK){
            teammate = mBlack;
            opposite = mWhite;
            oppositeTeam = TEAM.WHITE;
        }
        else{
            teammate = mWhite;
            opposite = mBlack;
            oppositeTeam = TEAM.BLACK;
        }
        //first found all the piece on x and y
        for(int i=0; i<SIZE;i++){
        	if(i!=idx){
        		if(teammate.get(i).getPieceX()==xNew)
        			teammateOnX.add(teammate.get(i));
        		if(teammate.get(i).getPieceY()==yNew)
        			teammateOnY.add(teammate.get(i));
        	}
        }
        for(Piece oppositePiece: opposite){
        	if(oppositePiece.getPieceX()==xNew)
        		oppositeOnX.add(oppositePiece);
        	if(oppositePiece.getPieceY()==yNew)
        		oppositeOnY.add(oppositePiece);        	
        }
        //examine kill condition
        if(oppositeOnX.size()==1&&teammateOnX.size()==1){
        	if(Math.abs(teammateOnX.get(0).getPieceY()-yNew)==1){
        		if(yNew+teammateOnX.get(0).getPieceY()+oppositeOnX.get(0).getPieceY()
        				==3*mid3(yNew,teammateOnX.get(0).getPieceY(),oppositeOnX.get(0).getPieceY())){
        			possibleKillList.add(oppositeOnX.get(0));
        		}
        	}
        }
        if(oppositeOnX.size()==1&&teammateOnX.size()==1){
        	if(Math.abs(teammateOnY.get(0).getPieceX()-xNew)==1){
        		if(xNew+teammateOnY.get(0).getPieceX()+oppositeOnY.get(0).getPieceX()
        				==3*mid3(xNew,teammateOnY.get(0).getPieceX(),oppositeOnY.get(0).getPieceX())){
        			possibleKillList.add(oppositeOnY.get(0));
        		}
        	}
        }
        return possibleKillList;
    }
	
    private int mid3(int a, int b, int c){
    	return a>b?  ( c>a? a : (b>c? b:c) )  :  ( c>b? b : (a>c? a:c) ) ;
    }
    
    public boolean isValidMove(TEAM team, int idxPiece, int xNew, int yNew){
        int xOld = getPieceX(team, idxPiece);
        int yOld = getPieceY(team, idxPiece);
        if(xNew<1||xNew>SIZE)
            return false;
        if(yNew<1||yNew>SIZE)
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
	
    public boolean isBlackVictory(){
        if(mWhite.size()==1)
            return true;
        else
            return false;
    }
	
    public boolean isWhiteVictory(){
        if(mBlack.size()==1)
            return true;
        else
            return false;
    }
	
    public int remainingPieces(TEAM team){
        if(team==TEAM.BLACK)
            return mBlack.size();
        else
            return mWhite.size();
    }
	
    int getPieceX(TEAM team, int idx){
        if(team==TEAM.BLACK)
            return mBlack.get(idx).x;
        else
            return mWhite.get(idx).x;
    }
	
    int getPieceY(TEAM team, int idx){
        if(team==TEAM.BLACK)
            return mBlack.get(idx).y;
        else
            return mWhite.get(idx).y;
    }
	
    void killPieces(int pieceIdx, TEAM team){
        if(team==TEAM.BLACK)
            mBlack.remove(pieceIdx);
        else
            mWhite.remove(pieceIdx);
        return;
    }

    private boolean isPieceOnPoint(int xx, int yy){
        for ( Piece pieces : mWhite ){
            if (pieces.x==xx&&pieces.y==yy){
                return true;
            }
        }
        for ( Piece pieces : mBlack ){
            if (pieces.x==xx&&pieces.y==yy){
                return true;
            }
        }
        return false;
    }
}
