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
    Board mBoard;
    int mTeam;
    int mSearchDepth;
    SearchNext(Board board, int team, int searchDepth){
        mBoard = board;
        mTeam = team;
        mSearchDepth = searchDepth;
    }
}