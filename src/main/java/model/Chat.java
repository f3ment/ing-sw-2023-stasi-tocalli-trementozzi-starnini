package model;

import utils.Event;
import utils.Observable;

import java.io.Serializable;
import java.util.*;

public class Chat extends Observable<Event> implements Serializable {

    private static final long serialVersionUID = 1L;
    //sender -> to -> message
    //if to is null, then the message is sent to evryone
    private ArrayList<ChatMessage> chat;
    private ArrayList<String> online;

    public Chat() {
        this.chat = new ArrayList<ChatMessage>();
        online = new ArrayList<String>();
    }

    public void setChangedAndNotifyObservers(Message message) {
        setChanged();
        notifyObservers(message);
    }

    public synchronized void sendMessage(ChatMessage message) {
        this.chat.add(new ChatMessage(message.getMessage(), message.getSender(), message.getReceiver()));
        addActive(message.getSender());
    }

    public List<ChatMessage> getLastTen() throws NullPointerException {
        if(this.chat.size() == 0){
            throw new NullPointerException("No message has been sent");
        }else{
            return this.chat.subList(Math.max(this.chat.size() - 10, 0), this.chat.size());
        }
    }

    public ChatMessage getLast() throws NullPointerException{
        if(this.chat.size() == 0){
            throw new NullPointerException("No message has been sent");
        }else{
            return this.chat.get(this.chat.size()-1);
        }
    }
    public ArrayList<String> getActive() {
        return this.online;
    }
    public void addActive(String userName) {
        if(!this.online.contains(userName)){
            this.online.add(userName);
        }
    }
    public void removeActive(String userName){
        if(!userName.equals("")){
            this.online.remove(userName);
            setChangedAndNotifyObservers(new Message(Event.EXIT_CHAT, userName));
        }else{
            ArrayList <String> toRemove = new ArrayList<String>(this.online);
            for (String usr : toRemove) {
                this.online.remove(usr);
                setChangedAndNotifyObservers(new Message(Event.EXIT_CHAT, usr));
            }
        }
    }

    public ArrayList<ChatMessage> getChat() {
        return chat;
    }
}
