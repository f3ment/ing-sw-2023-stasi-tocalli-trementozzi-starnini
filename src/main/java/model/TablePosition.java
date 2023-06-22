package model;

import model.goals.PersonalGoal;

import java.io.Serializable;

public class TablePosition implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Player currentPlayer;
    private final PersonalGoal currentPGoal;
    private final Bookshelf bookshelf;
    private boolean firstPosition;
    public TablePosition(String username, PersonalGoal pg, Bookshelf bookshelf){
        this.currentPlayer = new Player(this,username);
        this.currentPGoal =  pg;
        this.bookshelf = bookshelf;
        this.firstPosition = false;
    }
    public Player getPlayer(){
        return this.currentPlayer;
    }
    public PersonalGoal getCurrentPGoal() {
        return this.currentPGoal;
    }
    public Bookshelf getBookshelf() {
        return bookshelf;
    }
    public void setFirstPosition(boolean firstPosition) {
        this.firstPosition = firstPosition;
    }
    public boolean isFirstPosition() {
        return firstPosition;
    }
}

