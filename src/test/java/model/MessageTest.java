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

        ArrayList coords = new ArrayList();
        coords.add(1);
        coords.add(2);

        ChatView chat = new ChatView( new Chat());
        Event event = Event.LOGIN;
        int nPlayers = 4;
        int columnNumber = 1;
        String userName = new String("Michi");
        String message = "ciao";
        ChatMessage chatMessage = new ChatMessage("ciao", "Michi", "Toky33");
        GameView model;
        try {
            model = new GameView(new Game(nicknames));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Message test1 = new Message(event);
        assertTrue(test1.getEvent().equals(event));
        System.out.println("MessageTest test1 : OK");

        Message test2 = new Message(model, event);
        assertTrue(test2.getEvent().equals(event) && test2.getModel().equals(model));
        System.out.println("MessageTest test2 : OK");

        Message test3 = new Message(event, chatMessage);
        assertTrue(test3.getEvent().equals(event) && test3.getChatMessage().equals(chatMessage)
        && test3.getChatMessage().getMessage().equals("ciao") && test3.getChatMessage().getSender().equals("Michi"));
        System.out.println("MessageTest test3 : OK");

        Message test4 = new Message(event, nicknames, nPlayers);
        assertTrue(test4.getEvent().equals(event) && test4.getnPlayers() == nPlayers && test4.getNicknames().equals(nicknames));
        System.out.println("MessageTest test4 : OK");

        Message test5 = new Message(userName, event, chat);
        assertTrue(test5.getEvent().equals(event) && test5.getUserName().equals(userName) && test5.getChat().equals(chat));
        System.out.println("MessageTest test5 : OK");

        Message test6 = new Message(event, nPlayers, columnNumber, userName, coords);
        assertTrue(test6.getEvent().equals(event) && test6.getnPlayers() == nPlayers && test6.getColumnNumber() == columnNumber &&
                test6.getUserName().equals(userName) && test6.getCoords().equals(coords));
        System.out.println("MessageTest test6 : OK");

        Message test7 = new Message(event, columnNumber, coords);
        assertTrue(test7.getEvent().equals(event) && test7.getColumnNumber() == columnNumber && test7.getCoords().equals(coords));
        System.out.println("MessageTest test7 : OK");

        Message test8 = new Message(event, coords);
        assertTrue(test8.getEvent().equals(event) && test8.getCoords().equals(coords));
        System.out.println("MessageTest test8 : OK");

        Message test9 = new Message(event, nPlayers, userName);
        assertTrue(test9.getEvent().equals(event) && test9.getnPlayers() == nPlayers && test9.getUserName().equals(userName));
        System.out.println("MessageTest test9 : OK");

        Message test10 = new Message(event, userName);
        assertTrue(test10.getEvent().equals(event) && test10.getUserName().equals(userName));
        System.out.println("MessageTest test10 : OK");

        Message test11 = new Message(userName, event);
        assertTrue(test11.getEvent().equals(event) && test11.getUserName().equals(userName));
        System.out.println("MessageTest test11 : OK");

        Message test12 = new Message(event, chat ,model);
        assertTrue(test12.getEvent().equals(event) && test12.getChat().equals(chat) && test12.getModel().equals(model));
        System.out.println("MessageTest test12 : OK");

    }
}
