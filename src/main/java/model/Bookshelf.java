package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;


/**
 * This class represents the bookshelf of the game.
 */
public class Bookshelf implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean full;
    private final int length , height;
    private ArrayList<Integer> actualColumnLength; //ogni colonna è rappresentato da un numero per gli elementi contenuti
    private ItemTiles[][] items;
    private int choosenColumn;
    private int maxDrowable;
    Properties prop = new Properties();

    /**
     * Constructor of the class.
     * @throws IOException
     */
    public Bookshelf() throws IOException {

        String configFilePath = "./src/main/resources/config.properties";
        //FileInputStream ip;
        {

            try {
                //ip = new FileInputStream(configFilePath);
                //prop.load(ip);
                prop.load(Bookshelf.class.getResourceAsStream("/config.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.maxDrowable = Integer.parseInt(prop.getProperty("cards.maxDrowable"));
        this.height = Integer.parseInt(prop.getProperty("bookshelf.height"));
        this.length = Integer.parseInt(prop.getProperty("bookshelf.width"));
        this.items = new ItemTiles[this.height][this.length];
        this.actualColumnLength= new ArrayList<>();
        for(int i = 0; i< this.length; i++){
            this.actualColumnLength.add(0);
        }
        this.full = false;
    }

    /**
     * This method inserts a card in the bookshelf.
     * @param card is the card to insert.
     * @throws Exception if the bookshelf is full.
     */
    public void insert(ItemTiles card) throws Exception{
        if(actualColumnLength.get(choosenColumn)!=this.height){
            items[getHeight()-1-actualColumnLength.get(choosenColumn)][choosenColumn]=card;
            actualColumnLength.set(choosenColumn, actualColumnLength.get(choosenColumn)+1);
        }else{
            throw new Exception();
        }
    }


    /**
     * This method sets the column in which the player wants to insert the card.
     * @param choosenColumn is the column chosen by the player.
     * @throws Exception if the column chosen is not valid.
     */
    public void setChoosenColumn(int choosenColumn) throws Exception{
        if(choosenColumn >=0 && choosenColumn <this.length){
            this.choosenColumn = choosenColumn;
        }else{
            throw new Exception();
        }
    }

    /**
     * This method returns the max number of drawable cards that can be inserted in the bookshelf.
     * @return the max number of drawable cards.
     */
    public int getMaxDrowable(){
        int min;
        min = actualColumnLength.stream().reduce( 6,( a, b)-> a<=b ? a : b);
        return Math.min(height - min, maxDrowable);
    }

    /**
     * This method returns the item at the position (i,j) of the bookshelf.
     * @param i is the row of the bookshelf.
     * @param j is the column of the bookshelf.
     * @return the card in the position (i,j) of the bookshelf.
     * @throws Exception if the position (i,j) is not valid.
     */
    public ItemTiles getItem(int i, int j) throws Exception{
        if(items[i][j] != null){
            return items[i][j];
        }else{
            throw new Exception();
        }
    }

    /**
     * @return the height of the bookshelf.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the length of the bookshelf.
     */
    public int getLength() {
        return length;
    }

    /**
     * @return A list of the actual length of each column of the bookshelf based on the number of cards contained.
     */
    public ArrayList getColumnsSize(){
        return this.actualColumnLength;
    }

    public boolean isFull(){
        for(int i : actualColumnLength){
            if(i!=this.height){
                return false;
            }
        }
        this.full = true;
        return true;
    }

    /**
     * @return the column chosen by the player.
     */
    public int getChoosenColumn() {
        return choosenColumn;
    }


    /**
     * This method returns the score of the player based on the number of adjacent cards of the same type.
     * It is a recursive method that counts the number of adjacent cards of the same type through the whole player shelf
     * @param tablePosition is the position of the player
     * @param i is the row of the bookshelf
     * @param j is the column of the bookshelf
     * @param count is the number of adjacent cards
     * @param batrix is a matrix of booleans that indicates if a cell has been already counted  for objective
     * @param type is the type of the card
     * @param starting true if the current cell is the first of the sequence
     * @param score the current temporary score computed by the recursion so far
     * @param occupied is a matrix of booleans that indicates if a cell contains a card
     * @return the score accumulated by the player with adjacent cards of the same type
     */
    public int validateAdjacentRecursive(TablePosition tablePosition,int i,int j,int count,Boolean[][] batrix,Type type,boolean starting,int score,Boolean[][] occupied){
        try {
            if (!batrix[i][j] && !starting && !occupied[i][j]) {
                if (this.getItem(i, j).getType().equals(type)) {
                    count++;
                    occupied[i][j] = true;
                    if (i < this.getHeight() - 1) {
                        count = validateAdjacentRecursive(tablePosition, i + 1, j, count, batrix, type, false, score, occupied);
                    }
                    if (j < this.getLength() - 1) {
                        count = validateAdjacentRecursive(tablePosition, i, j + 1, count, batrix, type, false, score, occupied);
                    }
                    if (i >0) {
                        count = validateAdjacentRecursive(tablePosition, i -1, j, count, batrix, type, false, score, occupied);
                    }
                    if (j >0) {
                        count = validateAdjacentRecursive(tablePosition, i, j -1, count, batrix, type, false, score, occupied);
                    }
                    if (count >= Integer.parseInt(
                            prop.getProperty("score.MinlimitParameter"))) {
                        batrix[i][j] = true;
                    }else {
                        occupied[i][j] = false;
                    }
                    return count;
                } else {
                    return count;
                }
            } else if (starting && !batrix[i][j] && !occupied[i][j]) {
                occupied[i][j] = true;
                count=1;
                if (i < this.getHeight() - 1) {
                    count = validateAdjacentRecursive(tablePosition, i + 1, j, count, batrix, this.getItem(i, j).getType(), false, score, occupied);
                }
                if (j < this.getLength() - 1) {
                    count = validateAdjacentRecursive(tablePosition, i, j + 1, count, batrix, this.getItem(i, j).getType(), false, score, occupied);
                }
                if (i >0) {
                    count = validateAdjacentRecursive(tablePosition, i -1, j, count, batrix, type, false, score, occupied);
                }
                if (j >0) {
                    count = validateAdjacentRecursive(tablePosition, i, j -1, count, batrix, type, false, score, occupied);
                }

                if (count >= Integer.parseInt(
                        prop.getProperty("score.MinlimitParameter"))) {
                    //Check if count is higher than limit value
                    if (count > Integer.parseInt(
                            prop.getProperty("score.MaxlimitParameter")
                    )) count = Integer.parseInt(
                            prop.getProperty("score.MaxlimitParameter")
                    );
                    score += Integer.parseInt(prop.getProperty("score.adj"+count));
                    batrix[i][j] = true;
                }else{
                    occupied[i][j] = false;
                }
                count = 0;
                if (j < this.getLength() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i, j + 1, count, batrix, null, true, score, occupied);
                } else if (i < this.getHeight() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i + 1, 0, count, batrix, null, true, score, occupied);
                }
                return score;
            } else if (batrix[i][j] && starting) {
                if (j < this.getLength() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i, j + 1, 0, batrix, null, true, score, occupied);
                } else if (i < this.getHeight() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i + 1, 0, 0, batrix, null, true, score, occupied);
                }
                return score;

            } else if(batrix[i][j] && !starting) {
                return count;
            }else if (occupied[i][j] && !starting) {
                return count;
            }
        }catch(Exception e){
            if(!starting){
                return count;
            }else {
                if (j < this.getLength() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i, j + 1, 0, batrix, null, true, score, occupied);
                } else if (i < this.getHeight() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i + 1, 0, 0, batrix, null, true, score, occupied);
                }
                return score;
            }

        }
        return score;
    }
}



