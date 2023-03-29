package model.board;

import model.Box;
import model.Token;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ThreeBoard extends FourBoard {
    public ThreeBoard(){
        super();

        this.setNewBox(0, 4, false);
        this.setNewBox(1, 5, false);
        this.setNewBox(3, 1, false);
        this.setNewBox(4, 0, false);
        this.setNewBox(4, 8, false);
        this.setNewBox(5, 7, false);
        this.setNewBox(7, 3, false);
        this.setNewBox(8, 4, false);
        //altre caselle non valide

    }
}
