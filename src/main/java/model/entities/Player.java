package model.entities;

import model.ItemTiles;
import model.TablePosition;

public class Player {
    private String username;
    private boolean status;
    private int score;
    private int currentDrowable;

    private TablePosition currentPosition;


    public Player(TablePosition currentPosition,String username){

        this.currentPosition=currentPosition;
        this.username=username;

    }

    public String getUsername(){
        return username;
    }

    public boolean getStatus(){
        return status;
    }

    public int getScore() {
        return score;
    }

    public int getCurrentDrowable() {
        return currentDrowable;
    }

    public void setStatus(boolean status){
        this.status=status;
    }

    public void insert(int column){
        currentPosition.getBookshelf().insert(ItemTiles card );
    }



}
