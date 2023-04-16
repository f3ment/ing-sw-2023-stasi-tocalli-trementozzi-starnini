package distributed;


import model.GameView;
import utils.Event;

import java.util.ArrayList;

public interface Client {
    void update(GameView o, Event arg, Integer columnNumber, ArrayList coords);
}
