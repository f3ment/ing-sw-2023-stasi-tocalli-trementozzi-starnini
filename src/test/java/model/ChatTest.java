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

        chat.sendMessage("Myke01", "ciao");
        chat.sendMessage("Myke01", "ciao");
        chat.sendMessage("Myke011", "ciao1");
        chat.sendMessage("Myke012", "ciao2");
        chat.sendMessage("Myke013", "ciao3");

        chat.getLastTen().forEach(e-> e.forEach((key, value) -> System.out.println(key + " > " + value )));
        System.out.println("-----------------------");
        chat.getLast().forEach((key, value) -> System.out.println(key + " > " + value ));
        System.out.println("-----------------------");

        chat.sendMessage("Myke014", "ciao4");
        chat.sendMessage("Myke015", "ciao5");
        chat.sendMessage("Myke016", "ciao6");
        chat.sendMessage("Myke017", "ciao7");
        chat.sendMessage("Myke018", "ciao8");

        chat.getLastTen().forEach(e-> e.forEach((key, value) -> System.out.println(key + " > " + value )));
        System.out.println("-----------------------");

        chat.sendMessage("Myke019", "ciao9");
        chat.sendMessage("Myke0110", "ciao10");
        chat.sendMessage("Myke0111", "ciao11");

        chat.getLastTen().forEach(e-> e.forEach((key, value) -> System.out.println(key + " > " + value )));
        System.out.println("-----------------------");
    }
}