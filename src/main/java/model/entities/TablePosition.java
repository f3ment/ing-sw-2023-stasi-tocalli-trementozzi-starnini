package model.entities;

public class TablePosition {
    private Player currentPlayer;
    private PersonalGoal currentPGoal;
    private Bookshelf bookshelf;

    public TablePosition(Player player, PersonalGoal pg){
        this.currentPlayer = player;
        this.currentPGoal =  pg;
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
}

