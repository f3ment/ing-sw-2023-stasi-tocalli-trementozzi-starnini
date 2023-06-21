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
            chat.setChangedAndNotifyObservers(new Message(msg.getEvent(), msg.getUserName()));
        } else if (msg.getEvent().equals(Event.SEND_MESSAGE)) {
            chat.getActive().forEach(e -> System.out.println("ACTIVE - " + e));
            chat.sendMessage(msg.getChatMessage());
            chat.setChangedAndNotifyObservers(new Message(msg.getEvent(), msg.getChatMessage()));
        }else if (msg.getEvent().equals(Event.EXIT_CHAT)){
            chat.removeActive(msg.getUserName());
        }
    }
}
