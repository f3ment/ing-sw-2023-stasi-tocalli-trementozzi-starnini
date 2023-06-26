package controller;

import distributed.Client;
import model.Chat;
import model.Message;
import utils.Event;

/**
 * ChatController class
 * This class is responsible for updating the chat
 */
public class ChatController {
    private Chat chat;

    /**
     * Constructor
     * @param chat The Chat object
     */
    public ChatController(Chat chat) {
        this.chat = chat;
    }

    /**
     * Updates the chat
     * @param client The client that sent the message
     * @param msg The message sent by the client
     */
    public void update(Client client, Message msg){
        if(client==null){
            return;
        }
        if(msg.getEvent().equals(Event.GET_CHAT)){
            chat.addActive(msg.getUserName());
            chat.setChangedAndNotifyObservers(new Message(msg.getEvent(), msg.getUserName()));
        } else if (msg.getEvent().equals(Event.SEND_MESSAGE)) {
            chat.sendMessage(msg.getChatMessage());
            chat.setChangedAndNotifyObservers(new Message(msg.getEvent(), msg.getChatMessage()));
        }else if (msg.getEvent().equals(Event.EXIT_CHAT)){
            chat.removeActive(msg.getUserName());
        }
    }
}
