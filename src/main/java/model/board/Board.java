package model.board;

import model.Bag;
import model.Box;
import model.ItemTiles;
import model.Token;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public class Board implements Serializable {

    private static final long serialVersionUID = 1L;
    private Box[][] board;
    private Token token;
    private final int maxLength, maxHeight;

    /*
     * Apertura file di configurazione
     * */
    String configFilePath = "./src/main/resources/config.properties";
    Properties prop = new Properties();


    public Board(int numPlayers){
        FileInputStream ip;

        {
            try {
                ip = new FileInputStream(configFilePath);
                prop.load(ip);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.maxLength = Integer.parseInt(prop.getProperty("board.width"));
        this.maxHeight = Integer.parseInt(prop.getProperty("board.height"));
        board = new Box[maxHeight][maxLength];
        String[]matrix = prop.get("board").toString().split(",");


        for(int i=0 ; i<maxHeight; i++){
            for(int j=0; j<maxLength; j++){
                if(Integer.parseInt(matrix[maxLength*i+j]) != 0 && Integer.parseInt(matrix[maxLength*i+j]) <= numPlayers){
                    board[i][j] = new Box(true, null);
                }else{
                    board[i][j] = new Box(false, null);
                }
            }
        }

        token = new Token(1);

    }

    public Box getBox(int i, int j) throws IndexOutOfBoundsException{
        return this.board[i][j];
    }
    public ItemTiles draw(int i, int j) {
        ItemTiles res = getBox(i, j).getItemContained();
        getBox(i,j).setContent(null);
        return res;
    }
    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public boolean setBox(Bag bag){
        for(int i=0;i<this.maxHeight;i++){
            for(int j=0;j<this.maxLength;j++){
                if(getBox(i,j).getItemContained()==null && getBox(i,j).getValid()){
                    getBox(i,j).setContent(bag.extract());
                }
            }
        }
        return true; //board is full
    }

    public int getMaxHeight() {
        return maxHeight;
    }
    public int getMaxLength() {
        return maxLength;
    }
}
