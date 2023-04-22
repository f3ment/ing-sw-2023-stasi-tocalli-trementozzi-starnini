package model.board;

import model.Bag;
import model.Box;
import model.ItemTiles;
import model.Token;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Board {

    private Box[][] board;
    private Token token;
    private int maxLength, maxHeight;

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

    public Board(int numPlayers){
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
        if(this.board[i][j]==null){
            throw new IndexOutOfBoundsException();
        }else{
            return this.board[i][j];
        }
    }
    public ItemTiles draw(int i, int j) {
        ItemTiles res = getBox(i, j).getItemContained();
        getBox(i,j).setContent(null);
        return res;
    }
    public void setToken(Token token) {
        this.token = token;
    }
    public boolean setBox(Bag bag){
        ItemTiles item;
        for(int i=0;i<this.maxHeight;i++){
            for(int j=0;j<this.maxLength;j++){
                if(getBox(i,j).getItemContained()==null && getBox(i,j).getValid()){
                    try{
                        getBox(i,j).setContent(bag.extract());
                    }catch(Exception e){
                        return false;
                    }
                }
            }
        }
        return true; //board is full
    }

    void setNewBox(int i, int j, boolean validate){
        board[i][j] = new Box(validate, null);
    }

    public int getMaxHeight() {
        return maxHeight;
    }
    public int getMaxLength() {
        return maxLength;
    }
}
