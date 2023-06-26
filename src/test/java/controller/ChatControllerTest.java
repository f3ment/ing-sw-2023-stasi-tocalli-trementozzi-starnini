package controller;

import distributed.Client;
import distributed.ClientImpl;
import distributed.ServerImpl;
import model.Chat;
import model.ChatMessage;
import model.Message;
import org.junit.jupiter.api.Test;
import utils.Event;

import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class ChatControllerTest {

    @Test
    void update() throws RemoteException {
        String data = "1" +
                "\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        Chat chat = new Chat();
        Client client = new ClientImpl(new ServerImpl());
        ChatController chatController = new ChatController(chat);
        chatController.update(null, null);
        assertTrue(chat.getActive().isEmpty());
        assertTrue(chat.getChat().isEmpty());
        System.out.println("ChatControllerTest update 1 : OK");

        chatController.update(client, new Message(Event.GET_CHAT, "mike"));
        assertEquals(1, chat.getActive().size());
        assertTrue(chat.getChat().isEmpty());
        System.out.println("ChatControllerTest update 2 : OK");

        chatController.update(client, new Message(Event.SEND_MESSAGE, new ChatMessage("ciao", "mike", null)));
        assertEquals(1, chat.getActive().size());
        assertEquals(1, chat.getChat().size());
        assertTrue(chat.getChat().get(0).getMessage().equals("ciao"));
        assertTrue(chat.getChat().get(0).getSender().equals("mike"));
        assertTrue(chat.getChat().get(0).getReceiver() == null);
        assertTrue(chat.getLast().getMessage().equals("ciao"));
        assertTrue(chat.getLast().getSender().equals("mike"));
        assertTrue(chat.getLast().getReceiver() == null);
        System.out.println("ChatControllerTest update 3 : OK");

        chatController.update(client, new Message(Event.EXIT_CHAT, "mike"));
        assertTrue(chat.getActive().isEmpty());
        assertEquals(1, chat.getChat().size());
        System.out.println("ChatControllerTest update 4 : OK");
    }
}