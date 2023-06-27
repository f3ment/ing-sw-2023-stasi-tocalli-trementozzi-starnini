package model.views;

import model.ItemTiles;
import model.Player;
import model.ScoringToken;
import model.TablePosition;

import java.util.ArrayList;
import java.util.Map;

/**
 * An immutable class that represents a copy of a Player.
 */
public class PlayerView {

    private final String username;
    private final boolean status;
    private final int score;
    private final ArrayList<ItemTiles> PickedCards;
    private final TablePosition currentPosition;

    private final Map<Integer, ScoringToken> tokens;
    private final ItemTiles[][] bookshelf;

    /**
     * Creates a PlayerView object that represents a copy of a Player.
     * @param player the Player to be copied.
     */
    public PlayerView(Player player){
        this.username = player.getUsername();
        this.status = player.getStatus();
        this.score = player.getScore();
        PickedCards = player.getPickedCards();
        this.currentPosition = player.getCurrentPosition();
        this.tokens = player.getToken();
        this.bookshelf = new ItemTiles[player.getCurrentPosition().getBookshelf().getHeight()][player.getCurrentPosition().getBookshelf().getLength()];

        for(int k=0;k< player.getCurrentPosition().getBookshelf().getHeight();k++){
            for(int j=0;j<player.getCurrentPosition().getBookshelf().getLength();j++){
                try{
                    bookshelf[k][j]= new ItemTiles(player.getCurrentPosition().getBookshelf().getItem(k,j).getType(),player.getCurrentPosition().getBookshelf().getItem(k,j).getId());
                }catch (Exception e){
                    bookshelf[k][j]=null;
                }
            }
        }
    }

    /**
     * @return the itemtiles picked from the board during the current turn.
     */
    public ArrayList<ItemTiles> getPickedCards() {
        return PickedCards;
    }

    /**
     * This method returns a map of the scoring tokens owned by the player.
     * @return a map of the scoring tokens owned by the player.
     */
    public Map<Integer,ScoringToken> getTokens() {
        return tokens;
    }

    /**
     * @return the score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * @return the username of the player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the table position of the player.
     */
    public TablePosition getCurrentPosition() {
        return currentPosition;
    }

    /**
     * @return the shelf of the player.
     */
    public ItemTiles[][] getBookshelf() {
        return bookshelf;
    }

}
