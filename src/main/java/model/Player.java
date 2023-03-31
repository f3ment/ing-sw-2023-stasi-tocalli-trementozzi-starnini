package model;
import model.board.Board;
import model.board.FourBoard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Player {
    private final String username;
    private boolean status;
    private int score;
    private ArrayList<ItemTiles> PickedCards;
    private ArrayList<int[]> CardsToCheck;
    private final TablePosition currentPosition;



    /*
     * Apertura file di configurazione
     * */
    String configFilePath = "./src/main/resources/config.properties";
    Properties prop = new Properties();

    FileInputStream ip;

    ArrayList<ScoringToken> tokens; //common goal

    public Player(TablePosition currentPosition,String username){
        {
            try {
                ip = new FileInputStream(configFilePath);
                prop.load(ip);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.currentPosition=currentPosition;
        this.username=username;
        PickedCards = new ArrayList(Integer.parseInt(
                prop.getProperty("cards.maxDrowable")));
        tokens = new ArrayList<ScoringToken>(Integer.parseInt(
                prop.getProperty("cards.maxCommonGoal")));
        this.score = 0;
        CardsToCheck = new ArrayList<int[]>();
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

    public void setScore(int score) {
        this.score = score;
    }

    public void setToken( ScoringToken token) {
        this.tokens.add(token.getNumber()-1, token);
        this.setScore(this.getScore() + token.getScore());
    }
    public ScoringToken getToken(int index){
        return this.tokens.get(index);
    }

    public void setStatus(boolean status){
        this.status=status;
    }

    public void insertInBookshelf(int column, int card_number) throws Exception {
        currentPosition.getBookshelf().setChoosenColumn(column);
        currentPosition.getBookshelf().insert(PickedCards.get(card_number-1));
        PickedCards.remove(PickedCards.get(card_number-1));
    }

/*
* method invoked by the controller when a player chooses 1 or more cards to draw from the
* board, The coordinates are saved in CardsToCheck and then the board checks if the choice is
* valid, returning a boolean
* */
    public void AddCardToCheck(int x,int y){
        CardsToCheck.add(new int[2]);
        CardsToCheck.get(CardsToCheck.size()-1)[0] = x;
        CardsToCheck.get(CardsToCheck.size()-1)[1] = y;
    }

    public ArrayList<int[]> getCardsToCheck() {
        return CardsToCheck;
    }

    public boolean CheckCards(Board board,ArrayList<int[]>){

    }

    public void drawFromBoard(Board box, int i, int j){
        this.PickedCards.add(box.draw(i,j));
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
}
