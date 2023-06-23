package model;
import model.board.Board;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
        this.status = true;
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

    public int getAdjacentScore() {
        return adjacentScore;
    }

    public void setAdjacentScore(int adjacentScore) {
        this.adjacentScore = adjacentScore;
    }

    public void setScore(int score) {
        this.score = score;
    }

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
    public ScoringToken getTokenById(int index){
        return this.tokens.get(index);
    }

    public boolean hasCompletedFirst (){
        return this.firstGoal;
    }

    public boolean hasCompletedSecond (){
        return this.secondGoal;
    }

    public Map<Integer,ScoringToken> getToken(){
        return this.tokens;
    }


    public void setStatus(boolean status){
        this.status=status;
    }

    public void insertInBookshelf(int column, int card_number) throws Exception {
        currentPosition.getBookshelf().setChoosenColumn(column);
        currentPosition.getBookshelf().insert(PickedCards.get(card_number));
        PickedCards.remove(PickedCards.get(card_number));
    }


    public void drawFromBoard(Board box, int i, int j){
        this.PickedCards.add(box.draw(i,j));
    }

    public void clearHand(){
        this.getPickedCards().clear();
    }

    public TablePosition getCurrentPosition() {
        return currentPosition;
    }

    public ArrayList<ItemTiles> getPickedCards() {
        return PickedCards;
    }

    public ItemTiles getPickedCard(int index) {
        return PickedCards.get(index);
    }

    public int getPersonalGoalScore() {
        return this.personalGoalScore;
    }
    public void setPersonalGoalScore(int personalGoalScore) {
        this.personalGoalScore = personalGoalScore;
    }

}