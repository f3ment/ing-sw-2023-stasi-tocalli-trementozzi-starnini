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
    private ArrayList<ItemTiles> cards;
    private final TablePosition currentPosition;


    /*
     * Apertura file di configurazione
     * */
    String configFilePath = "./src/main/resources/config.properties";
    Properties prop = new Properties();

    FileInputStream ip;

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


    ArrayList<ScoringToken> tokens; //common goal

    public Player(TablePosition currentPosition,String username){
        this.currentPosition=currentPosition;
        this.username=username;
        cards = new ArrayList(Integer.parseInt(
                prop.getProperty("cards.maxDrowable")));
        tokens = new ArrayList<ScoringToken>(Integer.parseInt(
                prop.getProperty("cards.maxCommonGoal")));
    }

    public String getUsername(){
        return username;
    }

    public boolean getStatus(){
        return status;
    }
//turno attrim00tk-y
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //token ottenuto dal common goal e score sommato  allo score del player
    public void setToken(int index,ScoringToken token) {
        this.tokens.add(index, token);
        this.setScore(this.getScore() + token.getScore());
    }
    public ScoringToken getToken(int index){
        return this.tokens.get(index);
    }

    public void setStatus(boolean status){
        this.status=status;
    }

    public void insertInBookshelf(int column, ItemTiles card) throws Exception {
        currentPosition.getBookshelf().setChoosenColumn(column);
        currentPosition.getBookshelf().insert(card);
        cards.remove(card);
    }

    public void drawFromBoard(Board box, int i, int j){
        this.cards.add(box.draw(i,j));
    }

    public TablePosition getCurrentPosition() {
        return currentPosition;
    }

    public ArrayList<ItemTiles> getCards() {
        return cards;
    }

    public ItemTiles getCard(int index) {
        return cards.get(index);
    }

}
