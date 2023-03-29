package model.board;

import model.Box;
import model.Token;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TwoBoard extends ThreeBoard {

    public TwoBoard(){
        super();

        this.setNewBox(0, 3, false);
        this.setNewBox(2, 2, false);
        this.setNewBox(2, 6, false);
        this.setNewBox(3, 8, false);
        this.setNewBox(5, 0, false);
        this.setNewBox(6, 2, false);
        this.setNewBox(6, 6, false);
        this.setNewBox(8, 5, false);
        //altre caselle non valide


    }
}
