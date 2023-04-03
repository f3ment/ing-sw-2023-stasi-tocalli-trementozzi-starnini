package controller;
import model.*;
import utils.Observable;

import java.util.Random;
public class GameController extends Observable {
    private final Game game;

    //to implement
    //private final TextualUI view;;
    public GameController(Game game){
        this.game = game;
    }

    private boolean checkDraw(){

    }
    private boolean insert(){}

    private void changeCurrentPosition(){}

    @Override
    public void update(TextualUI o, Choice arg) {
        if (o != view){
            System.err.println("Discarding notification from " + o);
            return;
        }

    }


}
