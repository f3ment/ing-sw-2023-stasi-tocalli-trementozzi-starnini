import view.TextualUI;
import utils.*;
import model.*;
import controller.*;

import java.io.IOException;
import java.util.*;

import java.util.ArrayList;


public class App {
    public static void main( String[] args ) {

        System.out.println("\033[40m \033[1;31m" + ".___  ___. ____    ____         _______. __    __   _______  __       _______  __   _______ "+"\033[0m");
        System.out.println("\033[40m \033[1;33m" + "|   \\/   | \\   \\  /   /        /       ||  |  |  | |   ____||  |     |   ____||  | |   ____|"+"\033[0m");
        System.out.println("\033[40m \033[1;32m" + "|  \\  /  |  \\   \\/   /        |   (----`|  |__|  | |  |__   |  |     |  |__   |  | |  |__   "+"\033[0m");
        System.out.println("\033[40m \033[1;34m" + "|  |\\/|  |   \\_    _/          \\   \\    |   __   | |   __|  |  |     |   __|  |  | |   __|  "+"\033[0m");
        System.out.println("\033[40m \033[1;35m" + "|  |  |  |     |  |        .----)   |   |  |  |  | |  |____ |  `----.|  |     |  | |  |____ "+"\033[0m");
        System.out.println("\033[40m \033[1;36m" + "|__|  |__|     |__|        |_______/    |__|  |__| |_______||_______||__|     |__| |_______|"+"\033[0m");
        System.out.println("\033[40m" + "                                                                                             " + "\33[0m");
        System.out.println("\033[40m\033[1;31m\033[4;37m" + " Cr" + "\033[1;33m" + "ea" + "\033[1;32m" + "te" + "\033[1;34m" + "d B" + "\033[1;35m" + "y: " + "\033[40m" + "                                                                                " +"\033[0m");
        System.out.println("\033[40m"+ " " + "\033[0;30m\033[41m" + "- Michelangelo Stasi (michelangelo.stasi@mail.polimi.it)                                   " + "\033[40m"+ " " + "\33[0m");
        System.out.println("\033[40m"+ " " + "\033[0;30m\033[42m" + "- Nicolo' Tocalli (nicolo.tocalli@mail.polimi.it)                                          " + "\033[40m"+ " " + "\33[0m");
        System.out.println("\033[40m"+ " " + "\033[0;30m\033[43m" + "- Francesco Trementozzi (francesco.trementozzi@mail.polimi.it)                             " + "\033[40m"+ " " + "\33[0m");
        System.out.println("\033[40m"+ " " + "\033[0;30m\033[44m" + "- Giuseppe Starnini (giuseppe.starnini@mail.polimi.it)                                     " + "\033[40m"+ " " + "\33[0m");
        System.out.println("\033[40m" + "                                                                                             " + "\33[0m");
        System.out.println("\033[0;107m" + "                                                                                             " + "\33[0m");
        System.out.println("\033[40m" + "                                                                                             " + "\33[0m");

        Game model;
        ArrayList<String> names= new ArrayList<String>();
        names.add("Piero");
        names.add("Giovanni");
        names.add("Luca");
        //names.add("Giacomo");
        try {
            model = new Game(names);
        } catch (IOException e) {
            System.err.println("Error while creating new match!");
            return;
        }

        GameView modelView = new GameView(model);
        TextualUI view = new TextualUI();
        GameController controller = new GameController(model);

        modelView.addObserver(view);
        view.addObserver(controller);
        view.run();
    }
}
