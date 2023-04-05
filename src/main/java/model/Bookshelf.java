package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Bookshelf {
    private Boolean full;
    private final int length , height;
    private ArrayList<Integer> actualColumnLength; //ogni colonna è rappresentato da un numero per gli elementi contenuti
    private ItemTiles[][] items;
    private int choosenColumn;

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
    private int maxDrowable = Integer.parseInt(prop.getProperty("cards.maxDrowable"));

    public Bookshelf() throws FileNotFoundException, IOException {
        //this.height = 6;
        //this.length = 5;
        String configFilePath = "./src/main/resources/config.properties";
        Properties prop = new Properties();
        FileInputStream ip = new FileInputStream(configFilePath);
        prop.load(ip);
        this.height = Integer.parseInt(prop.getProperty("bookshelf.height"));
        this.length = Integer.parseInt(prop.getProperty("bookshelf.width"));
        this.items = new ItemTiles[this.height][this.length];
        this.actualColumnLength= new ArrayList<Integer>();
        for(int i = 0; i< this.length; i++){
            this.actualColumnLength.add(0);
        }
        this.full = false;
    }

    public void insert(ItemTiles card) throws Exception{
        if(actualColumnLength.get(choosenColumn)!=this.height){
            items[getHeight()-1-actualColumnLength.get(choosenColumn)][choosenColumn]=card;
            actualColumnLength.set(choosenColumn, actualColumnLength.get(choosenColumn)+1);
        }else{
            throw new Exception();
        }
    }

    public void setChoosenColumn(int choosenColumn) throws Exception{
        if(choosenColumn >=0 && choosenColumn <this.length){
            this.choosenColumn = choosenColumn;
        }else{
            throw new Exception();
        }
    }

    public int getMaxDrowable(){
        int min;
        min = actualColumnLength.stream().reduce( 6,( a, b)-> a<=b ? a : b);
        return Math.min(height - min, maxDrowable);
    }
    public ItemTiles getItem(int i, int j) throws Exception{
        if(items[i][j] != null){
            return items[i][j];
        }else{
            throw new Exception();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

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

    public int getChoosenColumn() {
        return choosenColumn;
    }




    public int validateAdjacentRecursive(TablePosition tablePosition,int i,int j,int count,Boolean[][] batrix,Type type,boolean starting,int score,Boolean[][] occupied) throws Exception{
        //Bookshelf validateshelf= tablePosition.getBookshelf();
        try {
            if (batrix[i][j]==false  && starting==false && occupied[i][j]==false) {
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
            } else if (starting==true&&batrix[i][j]==false&&occupied[i][j]==false) {
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
                    //System.out.println(this.getItem(i, j).getType());
                    //System.out.println(count);
                }else{
                    occupied[i][j] = false;
                }
                /*if(count==3){
                    score+=2;
                } else if (count==4) {
                    score+=3;
                } else if (count==5) {
                    score+=5;
                } else if (count>=6) {
                    score+=8;
                }
                if(count>=3){
                    batrix[i][j]=true;
                    System.out.println(this.getItem(i, j).getType());
                    System.out.println(count);
                }else{
                    occupied[i][j] = false;
                }*/
                count = 0;
                if (j < this.getLength() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i, j + 1, count, batrix, null, true, score, occupied);
                } else if (i < this.getHeight() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i + 1, 0, count, batrix, null, true, score, occupied);
                }
                return score;
            } else if (batrix[i][j]==true && starting==true) {
                if (j < this.getLength() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i, j + 1, 0, batrix, null, true, score, occupied);
                } else if (i < this.getHeight() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i + 1, 0, 0, batrix, null, true, score, occupied);
                }
                return score;

            } else if(batrix[i][j]==true && starting==false) {
                return count;
            }else if (occupied[i][j]==true&&starting==false) {
                return count;
            }
        }catch(Exception e){
            if(starting==false){
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



