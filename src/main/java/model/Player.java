package model;
import model.board.Board;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Player class represents a player of the game.
 * It is linked with a Table-position, and it defines all the
 * properties of a player like the score, the status,
 * the picked cards and the tokens he/her achieved during a game.
 */
public class Player implements Serializable {
    private final String username;
    private boolean status;
    private static final long serialVersionUID = 1L;

    private int score;

    private int adjacentScore;
    private ArrayList<ItemTiles> PickedCards;
    private final TablePosition currentPosition;

    Map<Integer,ScoringToken> tokens; //common goal


    /*
     * Apertura file di configurazione
     * */
    String configFilePath = "./src/main/resources/config.properties";
    Properties prop = new Properties();
    private int personalGoalScore;
    private boolean firstGoal;
    private boolean secondGoal;


    /**
     * Constructor of the class Player.
     * It initializes the player with starting values.
     * @param currentPosition the player TablePosition
     * @param username the player username
     */
    public Player(TablePosition currentPosition,String username){
        FileInputStream ip;

        {
            try {
                ip = new FileInputStream(configFilePath);
                prop.load(ip);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.currentPosition=currentPosition;
        this.username=username;
        PickedCards = new ArrayList(Integer.parseInt(
                prop.getProperty("cards.maxDrowable")));
        tokens = new HashMap<>();
        this.score = 0;
        this.adjacentScore = 0;
        this.personalGoalScore = 0;
        this.firstGoal = false;
        this.secondGoal = false;
    }

    /**
     * @return the player username
     */
    public String getUsername(){
        return username;
    }

    /**
     * @return true if the player is online, false otherwise
     */
    public boolean getStatus(){
        return status;
    }

    /**
     * @return the player score
     */
    public int getScore() {
        return score;
    }

    /**
     * @return the player score achieved so far for having adjacent same-type tiles in the shelf
     */
    public int getAdjacentScore() {
        return adjacentScore;
    }

    /**
     * @param adjacentScore the player adjacent score to set
     */
    public void setAdjacentScore(int adjacentScore) {
        this.adjacentScore = adjacentScore;
    }

    /**
     * @param score the player score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This method adds a token to the player when he/her achieves it during the game
     * by completing one or more common goals.
     * @param token the player token to set
     * @param romanNumber the roman number of the token
     */
    public void setToken(ScoringToken token, int romanNumber) {
        try {
            if(token!=null){
                this.tokens.put(token.getNumber(), token);
                this.setScore(this.getScore() + token.getScore());
                switch (romanNumber) {
                    case 1:
                        this.firstGoal = true;
                        break;
                    case 2:
                        this.secondGoal = true;
                        break;
                }
            }
        }catch(Exception e){
            return;
        }
    }

    /**
     * @param index the index of the token to get
     * @return the token of the player with the given index
     */
    public ScoringToken getTokenById(int index){
        return this.tokens.get(index);
    }

    /**
     * @return true if the player has completed the first common goal, false otherwise
     */
    public boolean hasCompletedFirst (){
        return this.firstGoal;
    }

    /**
     * @return true if the player has completed the second common goal, false otherwise
     */
    public boolean hasCompletedSecond (){
        return this.secondGoal;
    }

    /**
     * @return the player tokens
     */
    public Map<Integer,ScoringToken> getToken(){
        return this.tokens;
    }


    /**
     * @param status the player status to set
     */
    public void setStatus(boolean status){
        this.status=status;
    }

    /**
     * This method adds a card to the player shelf selected from the player hand
     * @param column the column of the bookshelf where the player wants to insert the card
     * @param card_number the index of the card to insert
     * @throws Exception if the insertion is not possible
     */
    public void insertInBookshelf(int column, int card_number) throws Exception {
        currentPosition.getBookshelf().setChoosenColumn(column);
        currentPosition.getBookshelf().insert(PickedCards.get(card_number));
        PickedCards.remove(PickedCards.get(card_number));
    }


    /**
     * This method adds a card to the player hand picking it from the board
     * @param box the board from which the player wants to draw a card
     * @param i the row of the board from which the player wants to draw a card
     * @param j the column of the board from which the player wants to draw a card
     */
    public void drawFromBoard(Board box, int i, int j){
        this.PickedCards.add(box.draw(i,j));
    }

    /**
     * This method removes all the cards from the player hand
     */
    public void clearHand(){
        this.getPickedCards().clear();
    }

    /**
     * @return the player TablePosition
     */
    public TablePosition getCurrentPosition() {
        return currentPosition;
    }

    /**
     * @return the last picked cards of the player during his/her turn
     */
    public ArrayList<ItemTiles> getPickedCards() {
        return PickedCards;
    }

    /**
     * @param index the index of the card to get
     * @return the card of the player with the given index from the current hand
     */
    public ItemTiles getPickedCard(int index) {
        return PickedCards.get(index);
    }

    /**
     * @return the player personal goal score achieved so far
     */
    public int getPersonalGoalScore() {
        return this.personalGoalScore;
    }

    /**
     * @param personalGoalScore the player personal goal score to set
     */
    public void setPersonalGoalScore(int personalGoalScore) {
        this.personalGoalScore = personalGoalScore;
    }

}