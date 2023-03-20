package model;

import model.board.FourBoard;

import java.util.ArrayList;

public class Player {
    private String username;
    private boolean status;
    private int score;
    private ArrayList<ItemTiles> cards;
    private TablePosition currentPosition;


    public Player(TablePosition currentPosition,String username){
        this.currentPosition=currentPosition;
        this.username=username;
        cards = new ArrayList(3);
    }

    public String getUsername(){
        return username;
    }

    public boolean getStatus(){
        return status;
    }

    public int getScore() {
        return score;
    }


    public void setStatus(boolean status){
        this.status=status;
    }

    public void insertInBookshelf(int column, ItemTiles card) throws Exception {
        currentPosition.getBookshelf().insert(card);
    }

    public void drawFromBoard(FourBoard box, int i, int j){
        this.cards.add(box.draw(i,j));
    }

}
