package controller;
import model.*;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.Random;
public class GameController implements Observer {
    private final Game game;

    //to implement
    //private final TextualUI view;;
    public GameController(Game game){
        this.game = game;
    }

    private boolean checkDraw(){
        return false;
    }
    private boolean insert(){
        return false;
    }

    private void changeCurrentPosition(){}




    @Override
    public void update(Observable o, Enum arg , int columnNumber , ArrayList<int[]> coords) {
        switch(arg){
            case():

        }
    }
}
