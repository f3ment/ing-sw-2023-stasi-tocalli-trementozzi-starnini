package view;

import utils.Turn;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import utils.Observable;
import utils.Observer;
import utils.Turn;
import utils.TurnView;
import utils.Turn
import java.util.ArrayList;


public class TextualUI extends Observable<Turn.Event> implements Observer<TurnView, Turn.Event>, Runnable {

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println("--- NEW TURN ---");
            /* Player chooses */
            Turn.Event c = Turn.Event.PLAYER_DRAW;
            ArrayList<Integer[]> drawen= new ArrayList<>();
            Integer[] a= new Integer[2];
            a[0]=3;
            a[1]=2;
            drawen.add(a);
            Integer[] b= new Integer[2];
            b[0]=6;
            b[1]=7;
            drawen.add(b);
            setChanged();
            notifyObservers(c, 0,drawen);
        }
    }

    @Override
    public void update(TurnView model, Turn.Event arg) {
        switch (arg) {
            case PLAYER_DRAW -> playerDraw(model);
           case PLAYER_INSERT -> playerDraw(model);
            default -> System.err.println("Ignoring event from " + model + ": " + arg);
        }
    }

    private void playerDraw(TurnView model) {
        Turn.Event event = model.getPlayerEvent();
        if (event == null) {
            return;
        }
        /* Show PLAYER's draw */
        System.out.println("PLAYER " +event.toString());
    }

}
