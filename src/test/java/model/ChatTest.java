package model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {

    @Test
    void getLastTen() {
        Chat chat = new Chat();
        try{
            chat.getLastTen();
        }catch (Exception e){
            assertTrue(e instanceof NullPointerException);
            System.out.println("ChatTest getLastTen with empty chat : OK");
        }

        assertTrue(chat.getActive().isEmpty());
        System.out.println("ChatTest getActive with empty chat : OK");
        try{
            ChatMessage last = chat.getLast();
        }catch (Exception e){
            assertTrue(e instanceof NullPointerException);
            System.out.println("ChatTest getLast with empty chat : OK");
        }

        chat.sendMessage(new ChatMessage ("ciao", "Myke01", "TOKY"));
        try{
            ChatMessage last = chat.getLast();
            assertEquals("ciao", last.getMessage());
            assertTrue(last.getSender().equals("Myke01"));
            assertTrue(last.getReceiver().equals("TOKY"));
            System.out.println("ChatTest getLast 1 : OK");
        }catch (Exception e){
            assertTrue(e instanceof NullPointerException);
            System.out.println("ChatTest getLast 1 : FAIL");
        }
        chat.sendMessage(new ChatMessage ("ciao", "Myke01", null));
        chat.sendMessage(new ChatMessage ("ciao1", "Myke012", null));
        chat.sendMessage(new ChatMessage ("ciao2", "Myke013", null));
        chat.sendMessage(new ChatMessage ("ciao3", "Myke01", null));
        try {
            ChatMessage last = chat.getLast();
            assertEquals("ciao3", last.getMessage());
            assertTrue(last.getSender().equals("Myke01"));
            assertTrue(last.getReceiver() == null);
            System.out.println("ChatTest getLast 2 : OK");
        }catch (Exception e){
            assertTrue(e instanceof NullPointerException);
            System.out.println("ChatTest getLast 2 : FAIL");
        }

        assertTrue(chat.getLastTen().size() == 5);
        System.out.println("ChatTest getLastTen size : OK");

        assertTrue(chat.getActive().contains("Myke01") &&
                chat.getActive().contains("Myke012") &&
                chat.getActive().contains("Myke013") && chat.getActive().size() == 3);
        System.out.println("ChatTest getActive : OK");

        chat.removeActive("Myke01");
        assertTrue(!chat.getActive().contains("Myke01") && chat.getActive().size() == 2);
        System.out.println("ChatTest removeActive : OK");

        assertTrue(chat.getChat().size() == 5);
        System.out.println("ChatTest getChat : OK");

        chat.removeActive("");
        assertTrue(chat.getActive().size() == 0);
        System.out.println("ChatTest removeActive with empty string : OK");
    }
}