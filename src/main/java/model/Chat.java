package model;

import utils.Event;
import utils.Observable;

import java.io.Serializable;
import java.util.*;

public class Chat extends Observable<Event> implements Serializable {

    private static final long serialVersionUID = 1L;
    //sender -> to -> message
    //if to is null, then the message is sent to evryone
    private LinkedHashMap<Integer, LinkedHashMap<String,LinkedHashMap<String, String>>> chat;

    private int id;

    private LinkedHashMap<String, LinkedHashMap<String, String>> last;
    private ArrayList<Integer> lastTen;
    private ArrayList<String> online;

    public Chat() {
        this.chat = new LinkedHashMap<Integer, LinkedHashMap<String,LinkedHashMap<String, String>>>();
        lastTen = new ArrayList<Integer>();
        online = new ArrayList<String>();
        id = 0;
    }

    public void setChangedAndNotifyObservers(Message message) {
        setChanged();
        notifyObservers(message);
    }

    public synchronized void sendMessage(String userName, String message, String receiver) {
        LinkedHashMap<String, LinkedHashMap<String, String>> msg = new LinkedHashMap<>();
        LinkedHashMap<String, String> to = new LinkedHashMap<>();
        to.put(message, receiver);
        msg.put(userName, to);
        this.last = msg;
        this.chat.put(id,msg);
        if(this.lastTen.size() == 10){
            lastTen.remove(0);
        }
        this.lastTen.add(id);
        id++;
    }

    public ArrayList<LinkedHashMap<String, LinkedHashMap<String, String>>> getLastTen() throws NullPointerException {
        ArrayList<LinkedHashMap<String, LinkedHashMap<String,String>>> list = new ArrayList<>(this.lastTen.size());
        if(getLast().size() == 0){
            throw new NullPointerException("No message has been sent");
        }else{
            for(Integer i : this.lastTen){
                list.add(this.chat.get(i));
            }
            return list;
        }
    }

    public LinkedHashMap<String, LinkedHashMap<String, String>> getLast() throws NullPointerException{
        if(this.last == null){
            throw new NullPointerException("No message has been sent");
        }else{
            return this.last;
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
        this.online.remove(userName);
    }
}
