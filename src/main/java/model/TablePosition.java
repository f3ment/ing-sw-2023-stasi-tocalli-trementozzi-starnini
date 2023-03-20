package model;

import model.goals.PersonalGoal;

public class TablePosition {
    private Player currentPlayer;
    private PersonalGoal currentPGoal;
    private Bookshelf bookshelf;

    private boolean firstPosition;

    public TablePosition(String player, PersonalGoal pg, Bookshelf bookshelf){
        this.currentPlayer = new Player(this, player);
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

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public void setFirstPosition(boolean firstPosition) {
        this.firstPosition = firstPosition;
    }
}

