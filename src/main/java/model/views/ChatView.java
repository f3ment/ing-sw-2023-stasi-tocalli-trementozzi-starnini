package model.views;

import model.Chat;
import model.ChatMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatView implements Serializable {
    private Chat model;
    private static final long serialVersionUID = 1L;

    public ChatView(Chat chat) {
        this.model = chat;
    }

    public List<ChatMessage> getLastTen() throws NullPointerException {
        return this.model.getLastTen();
    }

    public ChatMessage getLast() throws NullPointerException{
        return this.model.getLast();
    }

    public ArrayList<String> getActive() {
        return this.model.getActive();
    }
    public ArrayList<ChatMessage> getChat() {
        return this.model.getChat();
    }
}
