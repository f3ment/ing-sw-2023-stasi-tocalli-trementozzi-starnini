package model.board;

import model.Bag;
import model.Box;
import model.ItemTiles;
import model.Token;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * This class represents the board of the game
 */
public class Board implements Serializable {

    private static final long serialVersionUID = 1L;
    private Box[][] board;
    private Token token;
    private final int maxLength, maxHeight;


    String configFilePath = "./src/main/resources/config.properties";
    Properties prop = new Properties();


    /**
     * Constructor of the class, based on the player number it fills
     * the board with the right number of tiles chosen randomly at a
     * specified positions on the board
     * @param numPlayers number of players
     */
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

    /**
     * This method returns the box at the specified position
     * @param i row index
     * @param j column index
     * @return the box at the specified position
     * @throws IndexOutOfBoundsException
     */
    public Box getBox(int i, int j) throws IndexOutOfBoundsException{
        return this.board[i][j];
    }

    /**
     * This method returns the ItemTiles contained in the box
     * at the specified position, and then it empties the box
     * @param i row index
     * @param j column index
     * @return the ItemTiles contained in the box at the specified position
     */
    public ItemTiles draw(int i, int j) {
        ItemTiles res = getBox(i, j).getItemContained();
        getBox(i,j).setContent(null);
        return res;
    }

    /**
     * Sets the final token on the board
     * @param token the token to set
     */
    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * Returns the final token on the board
     * @return the final token on the board
     */
    public Token getToken() {
        return token;
    }

    /**
     * This method fills the board with tiles extracted from the bag
     * @param bag the bag from which to extract the tiles
     * @return true if the board is full, false otherwise
     */
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

    /**
     * @return the maximum height of the board
     */
    public int getMaxHeight() {
        return maxHeight;
    }

    /**
     * @return the maximum length of the board
     */
    public int getMaxLength() {
        return maxLength;
    }
}
