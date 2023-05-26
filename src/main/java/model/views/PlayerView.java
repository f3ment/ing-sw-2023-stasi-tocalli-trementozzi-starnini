package model.views;

import model.ItemTiles;
import model.Player;
import model.ScoringToken;
import model.TablePosition;

import java.util.ArrayList;
import java.util.List;

public class PlayerView {

    private String username;
    private boolean status;
    private int score;
    private ArrayList<ItemTiles> PickedCards;
    private TablePosition currentPosition;

    private ArrayList<ScoringToken> tokens;
    private ItemTiles[][] bookshelf;

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

    public ArrayList<ItemTiles> getPickedCards() {
        return PickedCards;
    }

    public ArrayList<ScoringToken> getTokens() {
        return tokens;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public TablePosition getCurrentPosition() {
        return currentPosition;
    }

    public ItemTiles[][] getBookshelf() {
        return bookshelf;
    }
}
