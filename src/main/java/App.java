import view.TextualUI;
import utils.*;
import model.*;
import controller.*;
import java.util.*;

import java.util.ArrayList;


public class App {
    public static void main( String[] args ) {
        ArrayList<String> names= new ArrayList<String>();
        names.add("Piero");
        names.add("Giovanni");
        Game model = new Game(names);
        GameView modelView = new GameView(model);
        TextualUI view = new TextualUI();
        GameController controller = new GameController(model);

        modelView.addObserver(view);
        view.addObserver(controller);

        view.run();
    }
}
