/**
 * 
 */
package com.yzhuang.twoeatone;
import java.util.*;
/**
 * @author Yuan Zhuang
 * @method possibleKills returns the possible piece that would be kill by xNew yNew move
 * @method addNew add a new piece, if piece>SIZE, return false
 */
public class Board {
    static final int SIZE = 5;
    public enum TEAM{
        WHITE, BLACK}
    
    private ArrayList<Piece> mBlack;
    private ArrayList<Piece> mWhite;
    private int[][][] mPositions;
    private int mPieceSize;
    private TwoEatOneMain mMainActivity;
    private int mIl; //initial x away from center
    private TEAM mTurn;
    
    /**
     * Default constructor of the board.
     * @param positions all the possible positions in the board
     * @param pieceSize the size of the piece
     * @param il the default difference from positions
     * @param mainActivity the main activity handler
     * @param firstTurn the first turn of the team
     */
    public Board(int[][][] positions, int pieceSize, int il, TwoEatOneMain mainActivity, TEAM firstTurn){
        int i;
        mBlack = new ArrayList<>();
        mWhite = new ArrayList<>();
        mPositions = positions;
        mPieceSize = pieceSize;
        mMainActivity = mainActivity;
        mIl = il;
        for(i=0;i<SIZE;i++){
            mBlack.add(new Piece(i+1,1,TEAM.BLACK
            		, positions[i][0][0], positions[i][0][1], il, pieceSize, mainActivity, this));
            mWhite.add(new Piece(i+1,SIZE,TEAM.WHITE
            		, positions[i][SIZE-1][0], positions[i][SIZE-1][1], il, pieceSize, mainActivity, this));
        }
        mTurn = firstTurn;
    }
    
	/**
	 * Overloaded constructor. used for construction from two piece array.
	 * @param black the black team
	 * @param white the white team
	 * @param positions all the possible positions in the board
     * @param pieceSize the size of the piece
     * @param il the default difference from positions
     * @param mainActivity the main activity handler
     * @param firstTurn the first turn of the team
	 */
    public Board(ArrayList<Piece> black, ArrayList<Piece> white,int[][][] positions, int pieceSize, int il, TwoEatOneMain mainActivity, TEAM firstTurn){
    	mBlack = black;
    	mWhite = white;
    	mBlack = new ArrayList<>();
        mWhite = new ArrayList<>();
        mPositions = positions;
        mPieceSize = pieceSize;
        mMainActivity = mainActivity;
        mIl = il;
        mTurn = firstTurn;
    }
    
    /**
     * Add a new piece to board
     * @param team team side
     * @param xx x coordinate
     * @param yy y coordinate
     * @return true: add success, false: not fail
     */
    public boolean addNew(TEAM team, int xx, int yy){
        if(this.isPieceOnPoint(xx, yy)||xx<0||xx>=SIZE||yy<0||yy>=SIZE)
            return false;
        else{
            if(team==TEAM.BLACK)
                if(mBlack.size()>=SIZE)
                    return false;
                else{
                    mBlack.add(new Piece(xx,yy,TEAM.BLACK
                    		, mPositions[xx-1][yy-1][0], mPositions[xx-1][yy-1][1], mIl, mPieceSize, mMainActivity, this));
                    return true;
                }
            else if(team==TEAM.WHITE)
                if(mWhite.size()>=SIZE)
                    return false;
                else{
                    mWhite.add(new Piece(xx,yy,TEAM.WHITE
                    		, mPositions[xx-1][yy-1][0], mPositions[xx-1][yy-1][1], mIl, mPieceSize, mMainActivity, this));
                    return true;
                }
        }
        return false;
    }
	
    /**
     * Move the piece idx of team to xNew yNew
     * @param team team side
     * @param idx index of the piece
     * @param xNew new x coordinate
     * @param yNew new y coordinate
     */
    public void movePiece(TEAM team, int idx, int xNew, int yNew){
        Piece piece;
        if(team==TEAM.BLACK)
            piece = mBlack.get(idx);
        else
            piece = mWhite.get(idx);
        piece.setCoord(xNew, yNew,mPositions[xNew-1][yNew-1][0], mPositions[xNew-1][yNew-1][1]);
        switch(mTurn){
        case BLACK:
        	mTurn = TEAM.WHITE;
        	break;
        case WHITE:
        	mTurn = TEAM.BLACK;
        	break;
        }
        HashSet<Piece> killedPieces = possibleKills(piece.getTeam(), idx, xNew, yNew);
        killPieces(killedPieces, piece.getTeam());
        piece.setCoord(xNew, yNew, mPositions[xNew-1][yNew-1][0], mPositions[xNew-1][yNew-1][1]);
        switch(mTurn){
        case BLACK:
        	mTurn = TEAM.WHITE;
        	break;
        case WHITE:
        	mTurn = TEAM.BLACK;
        	break;
        }
        return;
    }
    
    /**
     * overloaded method by moving piece object to xNew yNew
     * @param currPiece current Piece object
     * @param xNew new x coordinate
     * @param yNew new y coordinate
     */
    public void movePiece(Object currPiece, int xNew, int yNew){
        Piece piece = (Piece)currPiece;
        int idxPiece = 0;
        switch(piece.getTeam()){
        case BLACK:
        	for(int i=0;i<mBlack.size();i++){
        		if(mBlack.get(i)==piece){
        			idxPiece = i;
        			break;
        		}
        	}
        	break;
        case WHITE:
        	for(int i=0;i<mWhite.size();i++){
        		if(mWhite.get(i)==piece){
        			idxPiece = i;
        			break;
        		}
        	}
        	break;
        }
        HashSet<Piece> killedPieces = possibleKills(piece.getTeam(), idxPiece, xNew, yNew);
        killPieces(killedPieces, piece.getTeam());
        piece.setCoord(xNew, yNew, mPositions[xNew-1][yNew-1][0], mPositions[xNew-1][yNew-1][1]);
        switch(mTurn){
        case BLACK:
        	mTurn = TEAM.WHITE;
        	break;
        case WHITE:
        	mTurn = TEAM.BLACK;
        	break;
        }
        return;
    }
    
    /**
     * get a set of the piece that possibly can be killed
     * @param team team side
     * @param idx piece idx of team
     * @param xNew new x coordinate
     * @param yNew new y coordinate
     * @return a hashset of the piece when move to xNew yNew
     */
    public HashSet<Piece> possibleKills(TEAM team, int idx, int xNew, int yNew){
        ArrayList<Piece> teammateOnX = new ArrayList<>();
        ArrayList<Piece> oppositeOnX = new ArrayList<>();
        ArrayList<Piece> teammateOnY = new ArrayList<>();
        ArrayList<Piece> oppositeOnY = new ArrayList<>();
        HashSet<Piece> possibleKillList = new HashSet<>();
        ArrayList<Piece> teammate;
        ArrayList<Piece> opposite;
        if(team==TEAM.BLACK){
            teammate = mBlack;
            opposite = mWhite;
        }
        else{
            teammate = mWhite;
            opposite = mBlack;
        }
        //first found all the piece on x and y
        for(int i=0; i<teammate.size();i++){
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
        if(oppositeOnY.size()==1&&teammateOnY.size()==1){
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
    
    /**
     * determine whether a move is valid or not
     * @param team team side
     * @param idxPiece piece index of team
     * @param xNew new x coordinate
     * @param yNew new y coordinate
     * @return true: a valid move, false: invalid move.
     */
    public boolean isValidMove(TEAM team, int idxPiece, int xNew, int yNew){
        int xOld = getPieceX(team, idxPiece);
        int yOld = getPieceY(team, idxPiece);
        if(team!=mTurn)
    		return false;
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
    
    /**
     * overloaded method by using currPiece object.
     * @param team team side
     * @param currPiece current piece object
     * @param xNew new x coordinate
     * @param yNew new y coordinate
     * @return
     */
    public boolean isValidMove(TEAM team, Object currPiece, int xNew, int yNew){
    	Piece piece = (Piece) currPiece;
    	int xOld = piece.getPieceX();
    	int yOld = piece.getPieceY();
    	if(piece.getTeam()!=mTurn)
    		return false;
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
        if(isPieceOnPoint(xNew, yNew))
            return false;
        return true;
    }
	
    /**
     * determine whether black is victory
     * @return true: black is victory, false: not
     */
    public boolean isBlackVictory(){
        if(mWhite.size()==1)
            return true;
        else
            return false;
    }
	
    /**
     * determine whether white is victory
     * @return true: white is victory, false: not
     */
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
    
    /**
     * remove set pieces from the board
     * @param pieces pieces
     * @param team team side
     */
    void killPieces(HashSet<Piece> pieces, TEAM team){
        if(pieces.size()>0){
        	int idx = 0;
        	ArrayList<Piece> killTeam = null;
        	switch(team){
        	case BLACK:
        		killTeam = mWhite;
        		break;
        	case WHITE:
        		killTeam = mBlack;
        		break;
        	}
        	while(idx<killTeam.size()){
        		if(pieces.contains(killTeam.get(idx))){
        			killTeam.remove(idx);
        		}
        		else
        			idx++;        		
        	}
        }
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
    
    public void displayAll(){
    	for(Piece piece: mWhite){
    		piece.display();
    	}
    	for(Piece piece: mBlack){
    		piece.display();
    	}
    }
    
    public void updateAll(){
    	for(Piece piece: mWhite){
    		piece.update();
    	}
    	for(Piece piece: mBlack){
    		piece.update();
    	}
    }
    
    public boolean checkOtherLocks(){
    	for(Piece piece: mWhite){
    		if(piece.getLocked())
    			return true;
    	}
    	for(Piece piece: mBlack){
    		if(piece.getLocked())
    			return true;
    	}
    	return false;
    }
    
    public void releaseAll(){
    	for(Piece piece: mWhite){
    		piece.releaseEvent();
    	}
    	for(Piece piece: mBlack){
    		piece.releaseEvent();
    	}
    }
    
    public void updateAndDisplayAll(){
    	for(Piece piece: mWhite){
    		piece.update();
    		piece.display();
    	}
    	for(Piece piece: mBlack){
    		piece.update();
    		piece.display();
    	}
    }
    
    public int[][] findNearestXY(int xInput, int yInput){
    	int[][] nearestXY = new int[2][2];
    	int minDistance = Integer.MAX_VALUE;
    	for(int i=0;i<SIZE; i++)
    		for(int j=0; j<SIZE; j++){
    			int newDistance = distance2(xInput, yInput
    					, mPositions[i][j][0], mPositions[i][j][1]);
    			if(newDistance<minDistance){
    				minDistance = newDistance;
    				nearestXY[0][0] = mPositions[i][j][0];
    				nearestXY[0][1] = mPositions[i][j][1];
    				nearestXY[1][0] = i+1;
    				nearestXY[1][1] = j+1;
    			}
    		}
    	return nearestXY;
    }
    
    private int distance2(int x0, int y0, int x1, int y1){
    	return (x0-x1)*(x0-x1) +(y0-y1)*(y0-y1);
    }
    
    /**
     * 
     * @return return team of next turn
     */
    public TEAM getNextTurn(){
    	return mTurn;
    }
    
}
