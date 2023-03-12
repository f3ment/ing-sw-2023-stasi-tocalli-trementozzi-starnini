package model.entities;

public class TablePosition {
    private Player currentPlayer;
    private PersonalGoal currentPGoal;
    private Bookshelf bookshelf;

    public TablePosition(Player player, PersonalGoal pg, Bookshelf bookshelf){
        this.currentPlayer = player;
        this.currentPGoal =  pg;
        this.bookshelf = bookshelf;
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
}

