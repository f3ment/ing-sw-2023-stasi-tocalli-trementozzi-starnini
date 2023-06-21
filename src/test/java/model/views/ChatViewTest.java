package model.views;

import model.Chat;
import model.ChatMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatViewTest {
    Chat model = new Chat();

    @Test
    void test() {
        model.sendMessage(new ChatMessage("ciao", "Michi", "Toky33"));
        model.sendMessage(new ChatMessage("ciao1", "Michi", null));
        model.sendMessage(new ChatMessage("ciao2", "Michi1", "Toky33"));
        ChatView test = new ChatView(model);
        assertTrue(test.getChat().size() == 3);
        System.out.println("ChatViewTest getChat : OK");

        assertTrue(test.getActive().size() == 2 && test.getActive().contains("Michi") && !test.getActive().contains("Toky33"));
        System.out.println("ChatViewTest getActive : OK");

        assertTrue(test.getLast().getMessage().equals("ciao2"));
        System.out.println("ChatViewTest getLast : OK");

        assertTrue(test.getLastTen().size() == 3);
        System.out.println("ChatViewTest getLastTen : OK");
    }
}