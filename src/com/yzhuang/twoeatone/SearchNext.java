/**
 * 
 */
package com.yzhuang.twoeatone;
import java.util.*;
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

    private class NextStep{
        TEAM mTeam;
        int mXMove;
        int mYMove;
        int mWeight;
        NextStep(TEAM team, int xMove, int yMove, int weight){
            team = mTeam;
            mXMove = xMove;
            mYMove = yMove;
            mWeight = weight;            
        }
    }
    
    public SearchNext(Board board, TEAM team, int searchDepth){
        mBoard = board;
        mTeam = team;
        mSearchDepth = searchDepth;
    }

    
}