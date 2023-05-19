package model.views;

import model.Chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ChatView implements Serializable {
    private Chat model;
    private static final long serialVersionUID = 1L;

    public ChatView(Chat chat) {
        this.model = chat;
    }

    public ArrayList<LinkedHashMap<String, LinkedHashMap<String, String>>> getLastTen() throws NullPointerException {
        return this.model.getLastTen();
    }

    public LinkedHashMap<String, LinkedHashMap<String, String>> getLast() throws NullPointerException{
        return this.model.getLast();
    }

    public ArrayList<String> getActive() {
        return this.model.getActive();
    }
}
