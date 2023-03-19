package model.board;

import model.Box;
import model.EndGameToken;

public class TwoBoard extends ThreeBoard {
    private Box[][] board;{

    }
    private EndGameToken token;

    public TwoBoard(){
        super();
        board[0][3]= new Box(false, null);
        board[2][2]= new Box(false, null);
        board[2][6]= new Box(false, null);
        board[3][8]= new Box(false, null);
        board[5][0]= new Box(false, null);
        board[6][2]= new Box(false, null);
        board[6][6]= new Box(false, null);
        board[8][5]=new Box(false, null);
        //altre caselle non valide

    }
}
