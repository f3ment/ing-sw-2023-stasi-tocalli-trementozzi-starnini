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
                System.out.println("2");
                this.board = new TwoBoard();

                break;
            case 3:
                System.out.println("3");
                this.board = new ThreeBoard();
                break;
            case 4:
                System.out.println("4");
                this.board = new FourBoard();
                break;
        }
    }

    public Board getBoard() {
        return board;
    }
}
