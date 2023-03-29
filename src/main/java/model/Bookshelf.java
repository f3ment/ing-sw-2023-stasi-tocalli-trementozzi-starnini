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
            for(int j = 0; j< this.height; j++){

            }
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
        min = actualColumnLength.stream().reduce(0, ( a, b)-> a<b ? a : b);
        return Math.min(length - min, maxDrowable);
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
}
