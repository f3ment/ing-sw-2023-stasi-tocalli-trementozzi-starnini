package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Bookshelf {
    private Boolean full;

    private final int length , height;

    private ArrayList<Integer> actualColumnLength;
    private ItemTiles[][] items;
    private int choosenColumn;

    public Bookshelf() throws FileNotFoundException, IOException {
        //this.height = 6;
        //this.length = 5;
        String configFilePath = "./src/main/resources/config.properties";
        Properties prop = new Properties();
        FileInputStream ip = new FileInputStream(configFilePath);
        prop.load(ip);
        this.height=Integer.parseInt(prop.getProperty("bookshelf.height"));
        this.length=Integer.parseInt(prop.getProperty("bookshelf.width"));

        this.items = new ItemTiles[this.height][this.length];
        this.actualColumnLength= new ArrayList<Integer>(5);
        this.full = false;

    }

    public void insert(ItemTiles card) throws Exception{
        if(actualColumnLength.get(choosenColumn)!=6){
            items[actualColumnLength.get(choosenColumn)][choosenColumn]=card;
            actualColumnLength.set(choosenColumn, actualColumnLength.get(choosenColumn) +1);
        }else{
            throw new Exception();
        }
    }

    public void setChoosenColumn(int choosenColumn) {
        this.choosenColumn = choosenColumn;
    }

    public int getMaxDrowable(){
        int min;
        min = actualColumnLength.stream().reduce(0, ( a, b)-> a<b ? a : b);
        return Math.min(length - min, 3);
    }
    public ItemTiles getItem(int i, int j){
        return items[i][j];
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
            if(i!=6){
                return false;
            }
        }
        this.full = true;
        return true;
    }
}
