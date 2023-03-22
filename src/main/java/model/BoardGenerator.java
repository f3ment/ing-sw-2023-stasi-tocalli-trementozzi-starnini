package model;

import model.board.Board;
import model.board.FourBoard;
import model.board.ThreeBoard;
import model.board.TwoBoard;

public class BoardGenerator {

    private Board board;

    BoardGenerator(int playerNumber){
        switch (playerNumber) {
            case 2:
                this.board = new TwoBoard();
            case 3:
                this.board = new ThreeBoard();
            case 4:
                this.board = new FourBoard();
        }
    }

    public Board getBoard() {
        return board;
    }
}
