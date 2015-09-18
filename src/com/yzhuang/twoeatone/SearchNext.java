/**
 * 
 */
package com.yzhuang.twoeatone;
import java.util.*;

import com.yzhuang.twoeatone.Board.TEAM;
/**
 * @author Yuan Zhuang
 * 
 * 
 * 
 */
public class SearchNext{
    private Board mBoard;
    private TEAM mTeam;
    private int mSearchDepth;
    private ArrayList<NextStep> mPossibleSteps;

    
    
    public SearchNext(Board board, TEAM team, int searchDepth){
        mBoard = board;
        mTeam = team;
        mSearchDepth = searchDepth;
        mPossibleSteps = new ArrayList<>();
    }
    
    public SearchNext(Board board, TEAM team, int searchDepth, ArrayList<NextStep> possibleStep){
    	// used for stored next file.
    	mBoard = board;
        mTeam = team;
        mSearchDepth = searchDepth;
        mPossibleSteps = possibleStep;
    }

}