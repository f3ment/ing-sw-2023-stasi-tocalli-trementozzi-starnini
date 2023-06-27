package model;

import utils.Event;
import utils.Observable;

import java.io.Serializable;
import java.util.*;
/**
 * Chat class
 * This class is responsible for managing the chat.
 * It contains the list of messages sent and the list of active users.
 * It extends the Observable class. It notifies the observers when a message is sent or a user connects/disconnects.
 * The observers are anonymous classes that are created in the lobby each time a new user connects.
 */
public class Chat extends Observable<Event> implements Serializable {

    private static final long serialVersionUID = 1L;
    //sender -> to -> message
    //if to is null, then the message is sent to evryone
    private ArrayList<ChatMessage> chat;
    private ArrayList<String> online;

    /**
     * Constructor for the Chat class that initializes the chat and the list of active users.
     */
    public Chat() {
        this.chat = new ArrayList<>();
        online = new ArrayList<>();
    }

    /**
     * This method notifies the observers that a message has been sent.
     * @param message The message that is received and that has to be sent to the observers
     */
    public void setChangedAndNotifyObservers(Message message) {
        setChanged();
        notifyObservers(message);
    }

    /**
     * This method adds the sender of the message to the list of active users.
     * The message is then added to the chat.
     * @param message The message that has to be added to the chat
     */
    public synchronized void sendMessage(ChatMessage message) {
        this.chat.add(new ChatMessage(message.getMessage(), message.getSender(), message.getReceiver()));
        addActive(message.getSender());
    }

    /**
     * This method returns the last 10 messages sent.
     * @return The last 10 messages sent
     * @throws NullPointerException If no message has been sent
     */
    public List<ChatMessage> getLastTen() throws NullPointerException {
        if(this.chat.size() == 0){
            throw new NullPointerException("No message has been sent");
        }else{
            return this.chat.subList(Math.max(this.chat.size() - 10, 0), this.chat.size());
        }
    }

    /**
     * This method returns the last message sent.
     * @return The last message sent
     * @throws NullPointerException If no message has been sent
     */
    public ChatMessage getLast() throws NullPointerException{
        if(this.chat.size() == 0){
            throw new NullPointerException("No message has been sent");
        }else{
            return this.chat.get(this.chat.size()-1);
        }
    }

    /**
     * This method returns the list of active users.
     * @return The list of active users
     */
    public ArrayList<String> getActive() {
        return this.online;
    }

    /**
     * This method adds the user to the list of active users only if it is not already present.
     * @param userName The user that has to be added to the list of active users
     */
    public void addActive(String userName) {
        if(!this.online.contains(userName)){
            this.online.add(userName);
        }
    }

    /**
     * This method removes the user from the list of active users. If the user is not specified, it removes all the users from the list.
     * Useful when the match is ending.
     * @param userName The user that has to be removed from the list of active users
     */
    public void removeActive(String userName){
        if(!userName.equals("")){
            this.online.remove(userName);
            setChangedAndNotifyObservers(new Message(Event.EXIT_CHAT, userName));
        }else{
            ArrayList <String> toRemove = new ArrayList<>(this.online);
            for (String usr : toRemove) {
                this.online.remove(usr);
                setChangedAndNotifyObservers(new Message(Event.EXIT_CHAT, usr));
            }
        }
    }

    /**
     * This method returns the list of messages sent.
     * @return The list of messages sent
     */
    public ArrayList<ChatMessage> getChat() {
        return chat;
    }
}
