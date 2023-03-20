package model.board;

import model.Box;
import model.EndGameToken;

public class ThreeBoard extends FourBoard {
    private Box[][] board;
    private EndGameToken token;

    public ThreeBoard(){
        super();
        board[0][4]= new Box(false, null);
        board[1][5]=new Box(false, null);
        board[3][1]=new Box(false, null);
        board[4][0]=new Box(false, null);
        board[4][8]=new Box(false, null);
        board[5][7]=new Box(false, null);
        board[7][3]=new Box(false, null);
        board[8][4]=new Box(false, null);
        //altre caselle non valide

    }
}
