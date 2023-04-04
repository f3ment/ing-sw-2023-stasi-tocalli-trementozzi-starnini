package view;

import utils.Turn;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import utils.Observable;
import utils.Observer;
import utils.Turn;
import utils.TurnView;

public class TextualUI extends Observable<Turn.Event> implements Observer<TurnView, Turn.Event>, Runnable {

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println("--- NEW TURN ---");
            /* Player chooses */
            Turn.Event c = Turn.Event.PLAYER_DRAW;

            setChanged();
            notifyObservers(c);
        }
    }


}
