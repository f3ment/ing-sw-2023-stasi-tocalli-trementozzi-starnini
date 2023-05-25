package model;

import model.views.ChatView;
import model.views.GameView;
import org.junit.jupiter.api.Test;
import utils.Event;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageTest {

    @Test
    void constructors(){
        // random values to test getter and all constructors
        String receiverUsername = new String("Myke");
        ArrayList<String> nicknames = new ArrayList<>();
        nicknames.add("Myke01");
        nicknames.add("Toky33");
        nicknames.add("Sterning17");
        nicknames.add("F3ment");

        ChatView chat = new ChatView( new Chat());
        Event event = Event.LOGIN;
        int nPlayers = 4;
        int columnNumber = 1;
        String userName = new String("Michi");
        String message = "ciao";
        GameView model;
        try {
            model = new GameView(new Game(nicknames));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Message test1 = new Message(event);
        assertTrue(test1.getEvent().equals(event));

        //Message test2 = new Message()
    }
}
