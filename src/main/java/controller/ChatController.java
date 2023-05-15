package controller;

import controller.Lobby;
import distributed.Client;
import model.Chat;
import model.Message;
import utils.Event;

public class ChatController {
    private Chat chat;

    public ChatController(Chat chat) {
        this.chat = chat;
    }

    public void update(Client client, Message msg){
        if(client==null){
            return;
        }
        if(msg.getEvent().equals(Event.GET_CHAT)){
            chat.addActive(msg.getUserName());
            chat.setChangedAndNotifyObservers(msg.getEvent());
        } else if (msg.getEvent().equals(Event.SEND_MESSAGE)) {
            chat.addActive(msg.getUserName());
            chat.sendMessage(msg.getUserName(), msg.getMessage());
            chat.setChangedAndNotifyObservers(msg.getEvent());
        }else if (msg.getEvent().equals(Event.EXIT_CHAT)){
            chat.removeActive(msg.getUserName());
            chat.setChangedAndNotifyObservers(Event.EXIT_CHAT);
        }
    }
}
