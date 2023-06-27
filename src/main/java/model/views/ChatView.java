package model.views;

import model.Chat;
import model.ChatMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an immutable copy of a Chat Object.
 */
public class ChatView implements Serializable {
    private Chat model;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of the ChatView class.
     * @param chat The Chat object to be copied.
     */
    public ChatView(Chat chat) {
        this.model = chat;
    }

    /**
     * This method returns the last ten messages of the chat.
     * @return The last ten messages of the chat.
     * @throws NullPointerException If the chat has less than ten messages.
     */
    public List<ChatMessage> getLastTen() throws NullPointerException {
        return this.model.getLastTen();
    }

    /**
     * This method returns the last message of the chat.
     * @return The last message of the chat.
     * @throws NullPointerException If the chat has no messages.
     */
    public ChatMessage getLast() throws NullPointerException{
        return this.model.getLast();
    }

    /**
     * This method returns the list of active users in the chat.
     * @return The list of active users in the chat.
     */
    public ArrayList<String> getActive() {
        return this.model.getActive();
    }

    /**
     * This method returns the list of messages in the chat.
     * @return The list of messages in the chat.
     */
    public ArrayList<ChatMessage> getChat() {
        return this.model.getChat();
    }
}
