package model.board;

import model.Bag;
import model.Box;
import model.Token;
import model.ItemTiles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class FourBoard implements Board{
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

    public FourBoard(){
        this.maxLength = Integer.parseInt(prop.getProperty("board.width"));
        this.maxHeight = Integer.parseInt(prop.getProperty("board.height"));
        board = new Box[maxLength][maxHeight];
        for(int i =0; i<maxHeight; i++){
            for (int j =0 ; j < maxLength; j++){
                if (    (i == 0 && ( j<=2 || j>=5 )) ||
                        (j == 0 && ( i<=2 || i>=5 )) ||
                        (i == 8 && ( j<=2 || j>=5 )) ||
                        (j == 8 && ( i<=2 || i>=5 )) ||
                        (i == 1 && ( j <= 2 || j>=6 )) ||
                        (j == 1 && ( i <= 2 || i>=6 )) ||
                        (i == 7 && ( j <= 2 || j>=6 )) ||
                        (j == 7 && ( i <= 2 || i>=6 ))||(i==8&&j==3)||(i==3&&j==0)
                ) {
                    board[i][j] = new Box(false, null);
                }else{
                    board[i][j] = new Box(true, null);
                }
            }
        }
        //new Box(true, null);
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
