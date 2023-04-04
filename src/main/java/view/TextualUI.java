package view;

import utils.Turn;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public Turn.Event askPlayer() {
        Scanner s = new Scanner(System.in);
        System.out.println("Make your choice: ");
        System.out.println(
                "Signs: " +
                        Arrays.stream(Choice.values())
                                .map(Choice::name)
                                .collect(
                                        Collectors.joining(",", "[", "]")));
        while (true) {
            String input = s.next();
            try {
                return Choice.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.err.println("I don't know this sign: " + input);
                System.err.println("Try again...");
            }
        }
    }
}
