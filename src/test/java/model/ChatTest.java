package model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {

    @Test
    void getLastTen() {
        Chat chat = new Chat();

        try{
            chat.getLast().forEach((key, value) -> System.out.println(key + " > " + value ));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        System.out.println("-----------------------");

        chat.sendMessage("Myke01", "ciao", "TOKY");
        chat.sendMessage("Myke01", "ciao", null);
        chat.sendMessage("Myke011", "ciao1", null);
        chat.sendMessage("Myke012", "ciao2", null);
        chat.sendMessage("Myke013", "ciao3", null);

        chat.getLastTen().forEach(e-> e.forEach((key, value) -> {
            value.forEach((mesg, to) ->{
                System.out.println(key +( to!=null? " to " + to + " > " + mesg : ">" + mesg) );
            });
        }));
        System.out.println("-----------------------");

        chat.getLastTen().forEach(e-> e.forEach((key, value) -> {
            value.forEach((to, mesg) ->{
                System.out.println(key +( to!=null? " to " + to + "> " + mesg : ">" + mesg) );
            });
        }));
        System.out.println("-----------------------");

        chat.sendMessage("Myke014", "ciao4", null);
        chat.sendMessage("Myke015", "ciao5", null);
        chat.sendMessage("Myke016", "ciao6", null);
        chat.sendMessage("Myke017", "ciao7", null);
        chat.sendMessage("Myke018", "ciao8", null);

        chat.getLastTen().forEach(e-> e.forEach((key, value) -> {
            value.forEach((to, mesg) ->{
                System.out.println(key +( to!=null? " to " + to + "> " + mesg : ">" + mesg) );
            });
        }));
        System.out.println("-----------------------");

        chat.sendMessage("Myke019", "ciao9", "Michi");
        chat.sendMessage("Myke0110", "ciao10", null);
        chat.sendMessage("Myke0111", "ciao11", null);

        chat.getLastTen().forEach(e-> e.forEach((key, value) -> {
            value.forEach((to, mesg) ->{
                System.out.println(key +( to!=null? " to " + to + "> " + mesg : ">" + mesg) );
            });
        }));
        System.out.println("-----------------------");
    }
}