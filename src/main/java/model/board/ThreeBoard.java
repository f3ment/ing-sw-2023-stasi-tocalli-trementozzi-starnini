package model.board;

import model.Box;
import model.Token;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ThreeBoard extends FourBoard {
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

    public ThreeBoard(){
        super();

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
                        (j == 7 && ( i <= 2 || i>=6 ))
                ) {
                    board[i][j] = new Box(false, null);
                }else{
                    board[i][j] = new Box(true, null);
                }
            }
        }
        //new Box(true, null);
        token = new Token(1);
        board[0][4]= new Box(false, null);
        board[1][5]=new Box(false, null);
        board[3][1]=new Box(false, null);
        board[4][0]=new Box(false, null);
        board[4][8]=new Box(false, null);
        board[5][7]=new Box(false, null);
        board[7][3]=new Box(false, null);
        board[8][4]=new Box(false, null);
        //altre caselle non valide

    }
}
