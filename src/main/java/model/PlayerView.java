package model;

import java.util.ArrayList;

public class PlayerView {

    private final String username;
    private final boolean status;
    private final int score;
    private final ArrayList<ItemTiles> PickedCards;
    private final TablePosition currentPosition;

    final ArrayList<ScoringToken> tokens;


    public PlayerView(String username, boolean status, int score, ArrayList<ItemTiles> pickedCards, TablePosition currentPosition,ArrayList<ScoringToken> tokens){
        this.username = username;
        this.status = status;
        this.score = score;
        PickedCards = pickedCards;
        this.currentPosition = currentPosition;
        this.tokens = tokens;
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
}
