package model;

import model.goals.PersonalGoal;

import java.io.Serializable;

/**
 * This class represents the position of a player on the table.
 * This class is used by the server to keep track of the players, their positions and to link them
 * to their personal goal and bookshelf. It is also used to manage the game flow
 */
public class TablePosition implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Player currentPlayer;
    private final PersonalGoal currentPGoal;
    private final Bookshelf bookshelf;
    private boolean firstPosition;

    /**
     * Constructor of the class
     * @param username the username of the player
     * @param pg the personal goal of the player
     * @param bookshelf the bookshelf of the player
     */
    public TablePosition(String username, PersonalGoal pg, Bookshelf bookshelf){
        this.currentPlayer = new Player(this,username);
        this.currentPGoal =  pg;
        this.bookshelf = bookshelf;
        this.firstPosition = false;
    }

    /**
     * @return the player at this position
     */
    public Player getPlayer(){
        return this.currentPlayer;
    }

    /**
     * @return the personal goal of the player at this position
     */
    public PersonalGoal getCurrentPGoal() {
        return this.currentPGoal;
    }

    /**
     * @return the bookshelf of the player at this position
     */
    public Bookshelf getBookshelf() {
        return bookshelf;
    }


    /**
     * @param firstPosition set to true if the player at this position is the first player, false otherwise
     */
    public void setFirstPosition(boolean firstPosition) {
        this.firstPosition = firstPosition;
    }

    /**
     * @return true if the player at this position is the first player, false otherwise
     */
    public boolean isFirstPosition() {
        return firstPosition;
    }
}

