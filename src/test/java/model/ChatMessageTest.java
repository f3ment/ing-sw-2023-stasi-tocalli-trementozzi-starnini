package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {
    ChatMessage message = new ChatMessage("ciao", "Myke01", "TOKY");
    @Test
    void getMessage() {
        assertEquals("ciao", message.getMessage());
        System.out.println("ChatMessageTest getMessage : OK");
    }

    @Test
    void getSender() {
        assertEquals("Myke01", message.getSender());
        System.out.println("ChatMessageTest getSender : OK");
    }

    @Test
    void getReceiver() {
        assertEquals("TOKY", message.getReceiver());
        System.out.println("ChatMessageTest getReceiver : OK");
    }
}