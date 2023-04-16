import view.TextualUI;
import utils.*;
import model.*;
import controller.*;

import java.io.IOException;
import java.util.*;

import java.util.ArrayList;


public class App {
    public static void main( String[] args ) {

        System.out.println(".___  ___. ____    ____         _______. __    __   _______  __       _______  __   _______");
        System.out.println("|   \\/   | \\   \\  /   /        /       ||  |  |  | |   ____||  |     |   ____||  | |   ____|");
        System.out.println("|  \\  /  |  \\   \\/   /        |   (----`|  |__|  | |  |__   |  |     |  |__   |  | |  |__");
        System.out.println("|  |\\/|  |   \\_    _/          \\   \\    |   __   | |   __|  |  |     |   __|  |  | |   __|  ");
        System.out.println("|  |  |  |     |  |        .----)   |   |  |  |  | |  |____ |  `----.|  |     |  | |  |____");
        System.out.println("|__|  |__|     |__|        |_______/    |__|  |__| |_______||_______||__|     |__| |_______|");
        System.out.println("");

        Game model;
        ArrayList<String> names= new ArrayList<String>();
        names.add("Piero");
        names.add("Giovanni");
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
