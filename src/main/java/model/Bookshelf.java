package model;

import java.util.ArrayList;

public class Bookshelf {
    private Boolean full;

    private int length , height;

    private ArrayList<Integer> actualColumnLength;
    private ItemTiles[][] items;
    private int choosenColumn;

    public Bookshelf(){
        this.items = new ItemTiles[6][5];
        this.actualColumnLength= new ArrayList<Integer>(5);
        this.full=false;
        this.height = 6;
        this.length = 5;
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
        //min=actualColumnLength.stream().min() ;
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

}
