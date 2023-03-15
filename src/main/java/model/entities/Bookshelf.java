package model.entities;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Bookshelf {
    private Boolean full;
    private ArrayList<Integer> actualColumnLength;
    private ItemTiles[][] items;
    private int choosenColumn;

    public Bookshelf(){
        items= new ItemTiles[5][6];
        actualColumnLength= new ArrayList<Integer>(5);
        full=false;
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
        min=actualColumnLength.stream().min() ;

    }
}
